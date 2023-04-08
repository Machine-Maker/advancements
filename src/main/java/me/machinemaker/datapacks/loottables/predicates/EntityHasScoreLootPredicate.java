package me.machinemaker.datapacks.loottables.predicates;

import me.machinemaker.datapacks.common.range.IntegerRange;
import me.machinemaker.datapacks.toremove.EntityTarget;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;

public interface EntityHasScoreLootPredicate extends LootPredicate {

    @Contract(pure = true)
    @Unmodifiable Map<String, IntegerRange> scores();

    @Contract(pure = true)
    EntityTarget entity();
}
