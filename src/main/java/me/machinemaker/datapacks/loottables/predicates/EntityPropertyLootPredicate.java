package me.machinemaker.datapacks.loottables.predicates;

import me.machinemaker.datapacks.advancements.conditions.entity.EntityCondition;
import me.machinemaker.datapacks.toremove.EntityTarget;
import org.jetbrains.annotations.Contract;

public interface EntityPropertyLootPredicate {

    @Contract(pure = true)
    EntityCondition predicate();

    @Contract(pure = true)
    EntityTarget entity();
}
