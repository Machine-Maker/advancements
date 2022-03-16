package me.machinemaker.advancements.mocks.impls;

import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;

public class TestPotionEffectType extends PotionEffectType {

    private final String name;
    public TestPotionEffectType(int id, String name, String key) {
        super(id, NamespacedKey.minecraft(key));
        this.name = name;
    }

    @Override
    public double getDurationModifier() {
        return 0;
    }

    @Override
    public @NotNull String getName() {
        return this.name;
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    @Override
    public @NotNull Color getColor() {
        return Color.RED;
    }

    @Override
    public @NotNull Map<Attribute, AttributeModifier> getEffectAttributes() {
        return Collections.emptyMap();
    }

    @Override
    public double getAttributeModifierAmount(@NotNull Attribute attribute, int effectAmplifier) {
        return 0;
    }

    @Override
    public @NotNull PotionEffectType.Category getEffectCategory() {
        return PotionEffectType.Category.BENEFICIAL;
    }

    @Override
    public @NotNull String translationKey() {
        return "empty :(";
    }
}
