package me.machinemaker.datapacks.common.range;

import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import org.jetbrains.annotations.Contract;

public sealed interface DoubleRange extends NumberRange<Double> permits DoubleRangeImpl {

    @Contract(pure = true)
    static ConditionType<DoubleRange> conditionType() {
        return DoubleRangeImpl.TYPE;
    }

    @Contract(value = "_ -> new", pure = true)
    static DoubleRange isExactly(final double value) {
        return new DoubleRangeImpl(value, value);
    }

    @Contract(value = "_, _ -> new", pure = true)
    static DoubleRange isBetween(final double min, final double max) {
        return new DoubleRangeImpl(min, max);
    }

    @Contract(value = "_ -> new", pure = true)
    static DoubleRange isAtLeast(final double min) {
        return new DoubleRangeImpl(min, null);
    }

    @Contract(value = "_ -> new", pure = true)
    static DoubleRange isAtMost(final double max) {
        return new DoubleRangeImpl(null, max);
    }
}
