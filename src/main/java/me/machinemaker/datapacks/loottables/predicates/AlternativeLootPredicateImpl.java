package me.machinemaker.datapacks.loottables.predicates;

import com.google.gson.TypeAdapterFactory;
import java.util.List;
import me.machinemaker.datapacks.common.adapters.factories.InterfaceImplAdapterFactory;

record AlternativeLootPredicateImpl(
    List<LootPredicate> terms
) implements LootPredicateImpl, AlternativeLootPredicate {

    static final TypeAdapterFactory FACTORY = InterfaceImplAdapterFactory.simple(AlternativeLootPredicate.class, AlternativeLootPredicateImpl.class);

    AlternativeLootPredicateImpl {
        terms = List.copyOf(terms);
    }
}
