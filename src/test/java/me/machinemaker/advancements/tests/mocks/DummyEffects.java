package me.machinemaker.advancements.tests.mocks;

import me.machinemaker.advancements.tests.mocks.impls.TestPotionEffectType;
import org.bukkit.potion.PotionEffectType;

public final class DummyEffects {

    private static boolean setup = false;

    private DummyEffects() {
    }

    public static void setup() {
        if (!setup) {
            setupEffect("ABSORPTION", "absorption", 22);
            setupEffect("FAST_DIGGING", "haste", 3);
            setup = true;
        }
    }

    private static void setupEffect(final String name, final String key, final int id) {
        PotionEffectType.registerPotionEffectType(new TestPotionEffectType(id, name, key));
    }
}
