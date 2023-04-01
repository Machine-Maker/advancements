package me.machinemaker.datapacks.testing.extensions;

import com.google.common.collect.Iterators;
import com.google.common.util.concurrent.MoreExecutors;
import io.papermc.paper.configuration.GlobalConfiguration;
import io.papermc.paper.configuration.PaperConfigurations;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.SharedConstants;
import net.minecraft.commands.Commands;
import net.minecraft.core.LayeredRegistryAccess;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.Bootstrap;
import net.minecraft.server.RegistryLayer;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.WorldLoader;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.packs.resources.CloseableResourceManager;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.world.flag.FeatureFlags;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Server;
import org.bukkit.Tag;
import org.bukkit.craftbukkit.v1_19_R2.inventory.CraftItemFactory;
import org.bukkit.craftbukkit.v1_19_R2.inventory.RecipeIterator;
import org.bukkit.craftbukkit.v1_19_R2.util.CraftMagicNumbers;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstanceFactoryContext;
import org.junit.jupiter.api.extension.TestInstancePreConstructCallback;
import org.mockito.Mockito;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import sun.misc.Unsafe;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServerBootstrap implements TestInstancePreConstructCallback, ParameterResolver {

    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private static final MethodHandle PAPER_CONF_CREATE_FOR_TESTING;
    private static final MethodHandle GLOBAL_CONF_SET;
    private static final Unsafe UNSAFE;

    static {
        try {
            PAPER_CONF_CREATE_FOR_TESTING = MethodHandles.privateLookupIn(PaperConfigurations.class, LOOKUP).findStatic(PaperConfigurations.class, "createForTesting", MethodType.methodType(ConfigurationNode.class));
            GLOBAL_CONF_SET = MethodHandles.privateLookupIn(GlobalConfiguration.class, LOOKUP).findStatic(GlobalConfiguration.class, "set", MethodType.methodType(void.class, GlobalConfiguration.class));
        } catch (final ReflectiveOperationException exception) {
            throw new IllegalStateException(exception);
        }
        try {
            final Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.trySetAccessible();
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (final ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }

    }

    private final CloseableResourceManager resourceManager;
    private RegistryAccess.@MonotonicNonNull Frozen registryAccess;
    private @MonotonicNonNull ReloadableServerResources serverResources;

    public ServerBootstrap() {
        SharedConstants.tryDetectVersion();
        Bootstrap.bootStrap();
        this.resourceManager = new MultiPackResourceManager(PackType.SERVER_DATA, Collections.singletonList(new ServerPacksSource().getVanillaPack()));
    }

    @SuppressWarnings("deprecation")
    private Server createServer() throws InstantiationException {
        final Server server = mock(Server.class, Mockito.withSettings().stubOnly());
        final Map<Class<? extends Keyed>, Registry<?>> registryMap = new HashMap<>();
        final Map<String, Map<ResourceLocation, Tag<?>>> tagMap = Tags.createTagMap();


        when(server.getLogger()).thenReturn(NOPLogger.INSTANCE);
        when(server.getName()).thenReturn("BootstrappedServer");
        when(server.getUnsafe()).thenAnswer(ignored -> CraftMagicNumbers.INSTANCE);
        when(server.getItemFactory()).thenAnswer(ignored -> CraftItemFactory.instance());
        when(server.getTag(anyString(), any(NamespacedKey.class), any())).thenAnswer(invocation -> Tags.getTag(invocation, tagMap));
        when(server.getRegistry(any())).thenAnswer(invocation -> Registries.getRegistry(invocation, registryMap, this.registryAccess));
        when(server.recipeIterator()).thenAnswer(invocation -> this.createRecipeIterator());
        when(server.advancementIterator()).thenAnswer(invocation -> {
            return Iterators.unmodifiableIterator(Iterators.transform(this.serverResources.getAdvancements().getAllAdvancements().iterator(), advancement -> advancement.bukkit));
        });

        return server;
    }

    private RecipeIterator createRecipeIterator() throws InstantiationException, NoSuchFieldException, IllegalAccessException {
        final RecipeIterator iterator = (RecipeIterator) UNSAFE.allocateInstance(RecipeIterator.class);
        final Field field = RecipeIterator.class.getDeclaredField("recipes");
        field.trySetAccessible();
        field.set(iterator, this.serverResources.getRecipeManager().recipes.entrySet().iterator());
        return iterator;
    }

    private static void setupGlobalConfigForTest() {
        try {
            // noinspection ConstantConditions
            if (GlobalConfiguration.get() == null) {
                final ConfigurationNode node = (ConfigurationNode) PAPER_CONF_CREATE_FOR_TESTING.invoke();
                try {
                    final GlobalConfiguration globalConfiguration = node.require(GlobalConfiguration.class);
                    GLOBAL_CONF_SET.invoke(globalConfiguration);
                } catch (final SerializationException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (final Throwable error) {
            throw new IllegalStateException(error);
        }
    }

    @Override
    public void preConstructTestInstance(final TestInstanceFactoryContext factoryContext, final ExtensionContext context) throws InstantiationException {
        // noinspection ConstantConditions
        if (Bukkit.getServer() == null) {
            LayeredRegistryAccess<RegistryLayer> registryLayers = RegistryLayer.createRegistryAccess();
            registryLayers = WorldLoader.loadAndReplaceLayer(this.resourceManager, registryLayers, RegistryLayer.WORLDGEN, RegistryDataLoader.WORLDGEN_REGISTRIES);
            this.registryAccess = registryLayers.compositeAccess();
            this.serverResources = ReloadableServerResources.loadResources(this.resourceManager, this.registryAccess, FeatureFlags.DEFAULT_FLAGS, Commands.CommandSelection.DEDICATED, 2, MoreExecutors.directExecutor(), MoreExecutors.directExecutor()).join();
            this.serverResources.updateRegistryTags(this.registryAccess);

            setupGlobalConfigForTest();

            Bukkit.setServer(this.createServer());
            System.setOut(Bootstrap.STDOUT);
        }
    }

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == RegistryAccess.class || parameterContext.getParameter().getType() == ReloadableServerResources.class;
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) throws ParameterResolutionException {
        if (parameterContext.getParameter().getType() == RegistryAccess.class) {
            return this.registryAccess;
        } else if (parameterContext.getParameter().getType() == ReloadableServerResources.class) {
            return this.serverResources;
        }
        throw new ParameterResolutionException("Unexpected param %s".formatted(parameterContext.getParameter()));
    }
}
