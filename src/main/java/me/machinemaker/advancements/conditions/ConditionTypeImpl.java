package me.machinemaker.advancements.conditions;

import java.util.function.Supplier;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;

record ConditionTypeImpl<C extends Condition<C>>(
    Class<C> baseType,
    C any,
    Supplier<GsonBuilderApplicable> requiredGsonSupplier,
    boolean anyIsNull
) implements ConditionType<C> {

    ConditionTypeImpl(final Class<C> baseType, final C any, final Supplier<GsonBuilderApplicable> requiredGson) {
        this(baseType, any, requiredGson, true);
    }

    @Override
    public GsonBuilderApplicable requiredGson() {
        return this.requiredGsonSupplier.get();
    }
}
