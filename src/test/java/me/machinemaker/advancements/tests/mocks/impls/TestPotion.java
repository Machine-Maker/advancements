package me.machinemaker.advancements.tests.mocks.impls;

import io.papermc.paper.potion.Potion;
import java.util.Collections;
import java.util.List;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffect;

@SuppressWarnings("NonExtendableApiUsage")
public final class TestPotion extends Potion {

    public TestPotion(final NamespacedKey key) {
        super(key);
    }

    @Override
    public List<PotionEffect> getEffects() {
        return Collections.emptyList();
    }

    @Override
    public boolean hasInstantEffects() {
        return false;
    }

    @Override
    public Color getColor() {
        return Color.AQUA;
    }

    @Override
    public boolean isUpgradeable() {
        return true;
    }

    @Override
    public boolean isExtendable() {
        return true;
    }

    @Override
    public boolean isUpgraded() {
        return false;
    }

    @Override
    public boolean isExtended() {
        return false;
    }
}
