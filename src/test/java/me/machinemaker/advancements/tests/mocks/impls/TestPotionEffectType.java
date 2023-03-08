package me.machinemaker.advancements.tests.mocks.impls;

import java.util.Collections;
import java.util.Map;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.potion.PotionEffectType;

public class TestPotionEffectType extends PotionEffectType {

    private final String name;

    public TestPotionEffectType(final int id, final String name, final String key) {
        super(id, NamespacedKey.minecraft(key));
        this.name = name;
    }

    @Override
    public double getDurationModifier() {
        return 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public Map<Attribute, AttributeModifier> getEffectAttributes() {
        return Collections.emptyMap();
    }

    @Override
    public double getAttributeModifierAmount(final Attribute attribute, final int effectAmplifier) {
        return 0;
    }

    @Override
    public PotionEffectType.Category getEffectCategory() {
        return PotionEffectType.Category.BENEFICIAL;
    }

    @Override
    public String translationKey() {
        return "empty :(";
    }
}
