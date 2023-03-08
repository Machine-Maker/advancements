package me.machinemaker.advancements.conditions;

import java.util.function.Supplier;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

public interface ConditionType<C extends Condition<C>> {

    @ApiStatus.Internal
    static <C extends Condition<C>> ConditionType<C> create(final Class<C> baseType, final C any, final Supplier<GsonBuilderApplicable> requiredGson) {
        return new ConditionTypeImpl<>(baseType, any, requiredGson);
    }

    @Contract(pure = true)
    C any();

    @Contract(pure = true)
    @ApiStatus.Internal
    boolean anyIsNull();

    @Contract(pure = true)
    Class<C> baseType();

    @Contract(pure = true)
    GsonBuilderApplicable requiredGson();
}
