package me.machinemaker.advancements.mocks;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Server;
import org.bukkit.Tag;
import org.bukkit.UnsafeValues;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DummyServer {

    private DummyServer() {
    }

    static final Map<String, Map<NamespacedKey, Tag<?>>> tagCache = Maps.newHashMap();

    static {
        tagCache.put(Tag.REGISTRY_ITEMS, new HashMap<>());
        tagCache.put(Tag.REGISTRY_FLUIDS, new HashMap<>());
        tagCache.put(Tag.REGISTRY_BLOCKS, new HashMap<>());
        tagCache.put(Tag.REGISTRY_ENTITY_TYPES, new HashMap<>());
        tagCache.put(Tag.REGISTRY_GAME_EVENTS, new HashMap<>());
    }

    @SuppressWarnings("unchecked")
    public static void setup() {
        if (Bukkit.getServer() != null) return;
        Server server = mock(Server.class);
        when(server.getTag(anyString(), any(NamespacedKey.class), any())).thenAnswer(invocation -> {
            String registry = invocation.getArgument(0, String.class);
            NamespacedKey key = invocation.getArgument(1, NamespacedKey.class);
            return tagCache.get(registry).computeIfAbsent(key, k -> {
                Tag<?> tag = mock(Tag.class);
                when(tag.getKey()).thenReturn(k);
                return tag;
            });
        });

        when(server.getName()).thenReturn("DummyServer");
        when(server.getVersion()).thenReturn("DummyServer");
        when(server.getBukkitVersion()).thenReturn("DummyServer");
        when(server.getLogger()).thenReturn(Logger.getAnonymousLogger());

        UnsafeValues unsafeValues = mock(UnsafeValues.class);
        final Map<Class<? extends Keyed>, Registry<?>> registryMap = new HashMap<>();
        when(unsafeValues.registryFor(any())).thenAnswer(invocation -> {
            Class<? extends Keyed> clazz = invocation.getArgument(0, Class.class);
            if (Keyed.class.isAssignableFrom(clazz)) {
                return registryMap.computeIfAbsent(clazz, c -> mock(Registry.class));
            }
            throw new IllegalArgumentException(clazz.getSimpleName() + " is not a valid argument");
        });
        when(server.getUnsafe()).thenReturn(unsafeValues);


        Bukkit.setServer(server);
    }
}
