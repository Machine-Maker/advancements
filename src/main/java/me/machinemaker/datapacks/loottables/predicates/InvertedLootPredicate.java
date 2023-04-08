package me.machinemaker.datapacks.loottables.predicates;

import org.jetbrains.annotations.Contract;

public interface InvertedLootPredicate extends LootPredicate {

    @Contract(value = "_ -> new", pure = true)
    static InvertedLootPredicate inverted(final LootPredicate term) {
        return new InvertedLootPredicateImpl(term);
    }

    @Contract(pure = true)
    LootPredicate term();


}
