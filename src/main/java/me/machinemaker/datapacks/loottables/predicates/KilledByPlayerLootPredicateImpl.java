package me.machinemaker.datapacks.loottables.predicates;

record KilledByPlayerLootPredicateImpl() implements LootPredicateImpl, KilledByPlayerLootPredicate {

    static final KilledByPlayerLootPredicate INSTANCE = new KilledByPlayerLootPredicateImpl();

    KilledByPlayerLootPredicateImpl {
        if (INSTANCE != null) {
            throw new IllegalStateException();
        }
    }
}
