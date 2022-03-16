package me.machinemaker.advancements.mocks;

import me.machinemaker.advancements.mocks.impls.TestEnchantment;
import org.bukkit.enchantments.Enchantment;

public class DummyEnchantments {

    private DummyEnchantments() {
    }

    private static boolean setup = false;

    public static void setup() {
        if (!setup) {
            setupEnchantment("power", "ARROW_DAMAGE");
            setupEnchantment("sharpness", "DAMAGE_ALL");
            setup = true;
        }
    }

    private static void setupEnchantment(String key, String name) {
        Enchantment.registerEnchantment(new TestEnchantment(key, name));
    }
}
