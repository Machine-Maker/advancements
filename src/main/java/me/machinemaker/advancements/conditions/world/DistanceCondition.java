package me.machinemaker.advancements.conditions.world;

import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.ConditionType;
import me.machinemaker.advancements.conditions.range.DoubleRange;
import org.jetbrains.annotations.Contract;

public interface DistanceCondition extends Condition.Buildable<DistanceCondition, DistanceCondition.Builder> {

    @Contract(pure = true)
    static ConditionType<DistanceCondition> conditionType() {
        return DistanceConditionImpl.TYPE;
    }

    @Contract(value = "-> new", pure = true)
    static Builder builder() {
        return new DistanceConditionImpl.BuilderImpl();
    }

    @Contract(pure = true)
    static GsonBuilderApplicable requiredGson() {
        return DistanceConditionImpl.REQUIRED_GSON;
    }

    @Contract(pure = true)
    DoubleRange x();

    @Contract(pure = true)
    DoubleRange y();

    @Contract(pure = true)
    DoubleRange z();

    @Contract(pure = true)
    DoubleRange horizontal();

    @Contract(pure = true)
    DoubleRange absolute();

    interface Builder extends Condition.Builder<DistanceCondition> {

        @Contract(pure = true)
        DoubleRange x();

        @Contract(value = "_ -> this", pure = true)
        Builder x(DoubleRange x);

        @Contract(pure = true)
        DoubleRange y();

        @Contract(value = "_ -> this", pure = true)
        Builder y(DoubleRange y);

        @Contract(pure = true)
        DoubleRange z();

        @Contract(value = "_ -> this", pure = true)
        Builder z(DoubleRange z);

        @Contract(pure = true)
        DoubleRange horizontal();

        @Contract(value = "_ -> this", pure = true)
        Builder horizontal(DoubleRange horizontal);

        @Contract(pure = true)
        DoubleRange absolute();

        @Contract(value = "_ -> this", pure = true)
        Builder absolute(DoubleRange absolute);
    }
}
