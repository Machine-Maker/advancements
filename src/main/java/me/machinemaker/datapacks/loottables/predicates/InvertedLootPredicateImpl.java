package me.machinemaker.datapacks.loottables.predicates;

record InvertedLootPredicateImpl(
    LootPredicate term
) implements LootPredicateImpl, InvertedLootPredicate {

}
