package me.machinemaker.advancements.mocks.impls;

import io.papermc.paper.potion.Potion;
import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public final class TestPotion extends Potion {

    public TestPotion(@NotNull NamespacedKey key) {
        super(key);
    }

    @Override
    public @NotNull List<PotionEffect> getEffects() {
        return Collections.emptyList();
    }

    @Override
    public boolean hasInstantEffects() {
        return false;
    }

    @Override
    public @NotNull Color getColor() {
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
