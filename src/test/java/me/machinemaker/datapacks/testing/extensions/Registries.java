package me.machinemaker.datapacks.testing.extensions;

import java.util.Map;
import net.minecraft.core.RegistryAccess;
import org.bukkit.Keyed;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.v1_19_R2.CraftRegistry;
import org.mockito.invocation.InvocationOnMock;

final class Registries {

    private Registries() {
    }

    static Registry<?> getRegistry(final InvocationOnMock invocation, final Map<Class<? extends Keyed>, Registry<?>> registryMap, final RegistryAccess.Frozen registryAccess) {
        final Class<? extends Keyed> clazz = invocation.getArgument(0);
        if (Keyed.class.isAssignableFrom(clazz)) {
            return registryMap.computeIfAbsent(clazz, type -> CraftRegistry.createRegistry(type, registryAccess));
        }
        throw new IllegalArgumentException(clazz.getSimpleName() + " is not a valid argument");
    }
}
