package me.machinemaker.advancements.mocks;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.Tag;

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
        tagCache.put(Tag.REGISTRY_ITEMS, Maps.newHashMap());
        tagCache.put(Tag.REGISTRY_FLUIDS, Maps.newHashMap());
        tagCache.put(Tag.REGISTRY_BLOCKS, Maps.newHashMap());
        tagCache.put(Tag.REGISTRY_ENTITY_TYPES, Maps.newHashMap());
    }

    public static void setup() {
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


        Bukkit.setServer(server);
    }
}
