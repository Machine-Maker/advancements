package me.machinemaker.datapacks.utils;

import io.papermc.paper.adventure.PaperAdventure;
import net.kyori.adventure.key.Key;
import net.minecraft.resources.ResourceLocation;
import org.bukkit.NamespacedKey;

public final class TestUtils {

    private TestUtils() {
    }

    public static NamespacedKey fromResourceLocation(final ResourceLocation loc) {
        return new NamespacedKey(loc.getNamespace(), loc.getPath());
    }

    public static ResourceLocation toResourceLocation(final Key key) {
        return PaperAdventure.asVanilla(key);
    }
}
