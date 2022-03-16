package me.machinemaker.advancements.mocks;

import me.machinemaker.advancements.mocks.impls.TestPotionEffectType;
import org.bukkit.potion.PotionEffectType;

public final class DummyEffects {

    private DummyEffects() {
    }

    private static boolean setup = false;

    public static void setup() {
        if (!setup) {
            setupEffect("ABSORPTION", "absorption", 22);
            setupEffect("FAST_DIGGING", "haste", 3);
            setup = true;
        }
    }

    private static void setupEffect(String name, String key, int id) {
        PotionEffectType.registerPotionEffectType(new TestPotionEffectType(id, name, key));
    }
}
