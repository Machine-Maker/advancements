package me.machinemaker.datapacks.loottables.predicates;

import org.jetbrains.annotations.Contract;

public interface RandomChanceWithLootingLootPredicate extends LootPredicate {

    @Contract(value = "_, _ -> new", pure = true)
    static RandomChanceWithLootingLootPredicate chanceWithLooting(final float chance, final float lootingMultiplier) {
        return new RandomChanceWithLootingLootPredicateImpl(chance, lootingMultiplier);
    }

    @Contract(pure = true)
    float chance();

    @Contract(pure = true)
    float lootingMultiplier();

}
