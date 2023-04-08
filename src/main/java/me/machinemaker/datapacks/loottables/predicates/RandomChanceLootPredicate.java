package me.machinemaker.datapacks.loottables.predicates;

import org.jetbrains.annotations.Contract;

public interface RandomChanceLootPredicate {

    @Contract(pure = true)
    float chance();
}
