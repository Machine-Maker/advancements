package me.machinemaker.datapacks.loottables.predicates;

import java.util.List;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

public interface AlternativeLootPredicate {

    @Contract(value = "_ -> new", pure = true)
    static AlternativeLootPredicate alternative(final List<LootPredicate> terms) {
        return new AlternativeLootPredicateImpl(terms);
    }

    @Contract(pure = true)
    @Unmodifiable List<LootPredicate> terms();
}
