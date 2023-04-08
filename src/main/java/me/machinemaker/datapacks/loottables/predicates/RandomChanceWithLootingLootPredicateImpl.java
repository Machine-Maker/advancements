package me.machinemaker.datapacks.loottables.predicates;

import com.google.gson.TypeAdapterFactory;
import me.machinemaker.datapacks.common.adapters.factories.InterfaceImplAdapterFactory;

public record RandomChanceWithLootingLootPredicateImpl(
    float chance,
    float lootingMultiplier
) implements LootPredicateImpl, RandomChanceWithLootingLootPredicate {

    static final TypeAdapterFactory FACTORY = InterfaceImplAdapterFactory.simple(LootPredicate.class, LootPredicateImpl.class);
}
