package me.machinemaker.advancements.tests.mocks.impls;

import io.papermc.paper.enchantments.EnchantmentRarity;
import java.util.Collections;
import java.util.Set;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class TestEnchantment extends Enchantment {

    private final String name;

    public TestEnchantment(final String key, final String name) {
        super(NamespacedKey.minecraft(key));
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public int getStartLevel() {
        return 0;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ARMOR;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(final Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(final ItemStack item) {
        return false;
    }

    @Override
    public Component displayName(final int level) {
        return Component.empty();
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public EnchantmentRarity getRarity() {
        return EnchantmentRarity.COMMON;
    }

    @Override
    public float getDamageIncrease(final int level, final EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public Set<EquipmentSlot> getActiveSlots() {
        return Collections.emptySet();
    }

    @Override
    public String translationKey() {
        return "empty :(";
    }
}
