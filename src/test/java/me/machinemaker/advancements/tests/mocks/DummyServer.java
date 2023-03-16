package me.machinemaker.advancements.tests.mocks;

import com.google.common.collect.Maps;
import io.papermc.paper.potion.Potion;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import me.machinemaker.advancements.tests.mocks.impls.TestPotion;
import me.machinemaker.advancements.tests.mocks.impls.TestRegistry;
import me.machinemaker.advancements.tests.mocks.impls.TestStructure;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Server;
import org.bukkit.Tag;
import org.bukkit.generator.structure.Structure;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class DummyServer {

    static final Map<String, Map<NamespacedKey, Tag<?>>> tagCache = Maps.newHashMap();

    static {
        tagCache.put(Tag.REGISTRY_ITEMS, new HashMap<>());
        tagCache.put(Tag.REGISTRY_FLUIDS, new HashMap<>());
        tagCache.put(Tag.REGISTRY_BLOCKS, new HashMap<>());
        tagCache.put(Tag.REGISTRY_ENTITY_TYPES, new HashMap<>());
        tagCache.put(Tag.REGISTRY_GAME_EVENTS, new HashMap<>());
    }

    private DummyServer() {
    }

    @SuppressWarnings("unchecked")
    public static void setup() {
        //noinspection ConstantConditions
        if (Bukkit.getServer() != null) return;
        final Server server = mock(Server.class, Mockito.withSettings().stubOnly());
        when(server.getTag(anyString(), any(NamespacedKey.class), any())).thenAnswer(invocation -> {
            final String registry = invocation.getArgument(0, String.class);
            final NamespacedKey key = invocation.getArgument(1, NamespacedKey.class);
            return tagCache.get(registry).computeIfAbsent(key, k -> {
                final Tag<?> tag = mock(Tag.class, Mockito.withSettings().stubOnly());
                when(tag.getKey()).thenReturn(k);
                return tag;
            });
        });

        when(server.getName()).thenReturn("DummyServer");
        when(server.getVersion()).thenReturn("DummyServer");
        when(server.getBukkitVersion()).thenReturn("DummyServer");
        when(server.getLogger()).thenReturn(Logger.getAnonymousLogger());

        final Map<Class<? extends Keyed>, Registry<?>> registryMap = new HashMap<>();
        when(server.getRegistry(any())).thenAnswer(invocation -> {
            final Class<? extends Keyed> clazz = invocation.getArgument(0, Class.class);
            if (Keyed.class.isAssignableFrom(clazz)) {
                return registryMap.computeIfAbsent(clazz, DummyServer::createRegistry);
            }
            throw new IllegalArgumentException(clazz.getSimpleName() + " is not a valid argument");
        });


        Bukkit.setServer(server);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Keyed> Registry<T> createRegistry(final Class<T> type) {
        if (type == Structure.class) {
            return (Registry<T>) new TestRegistry<>(TestStructure::new);
        }
        if (type == Potion.class) {
            return (Registry<T>) new TestRegistry<>(TestPotion::new);
        }
        return mock(Registry.class, Mockito.withSettings().stubOnly());
    }
}
