package me.machinemaker.advancements.tests.mocks;

import me.machinemaker.advancements.tests.mocks.impls.TestEnchantment;
import org.bukkit.enchantments.Enchantment;

public final class DummyEnchantments {

    private static boolean setup = false;

    private DummyEnchantments() {
    }

    public static void setup() {
        if (!setup) {
            setupEnchantment("power", "ARROW_DAMAGE");
            setupEnchantment("sharpness", "DAMAGE_ALL");
            setup = true;
        }
    }

    private static void setupEnchantment(final String key, final String name) {
        Enchantment.registerEnchantment(new TestEnchantment(key, name));
    }
}
