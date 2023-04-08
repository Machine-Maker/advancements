package me.machinemaker.datapacks.loottables.predicates;

import org.jetbrains.annotations.Contract;

public interface KilledByPlayerLootPredicate extends LootPredicate {

    @Contract(pure = true)
    static KilledByPlayerLootPredicate instance() {
        return KilledByPlayerLootPredicateImpl.INSTANCE;
    }
}
