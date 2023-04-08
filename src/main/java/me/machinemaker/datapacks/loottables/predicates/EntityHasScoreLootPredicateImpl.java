package me.machinemaker.datapacks.loottables.predicates;

import me.machinemaker.datapacks.common.range.IntegerRange;
import me.machinemaker.datapacks.toremove.EntityTarget;

import java.util.Map;

record EntityHasScoreLootPredicateImpl(
    Map<String, IntegerRange> scores,
    EntityTarget entity
) implements LootPredicateImpl, EntityHasScoreLootPredicate {

    EntityHasScoreLootPredicateImpl {
        scores = Map.copyOf(scores);
    }
}
