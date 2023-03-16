package me.machinemaker.advancements.conditions;

import java.util.function.Supplier;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;

record ConditionTypeImpl<C extends Condition<? super C>>(
    Class<C> baseType,
    C any,
    Supplier<GsonBuilderApplicable> requiredGsonSupplier,
    boolean anyIsNull
) implements ConditionType<C> {

    @Override
    public GsonBuilderApplicable requiredGson() {
        return this.requiredGsonSupplier.get();
    }
}
