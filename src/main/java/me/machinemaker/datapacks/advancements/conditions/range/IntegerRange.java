package me.machinemaker.datapacks.advancements.conditions.range;

import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import org.jetbrains.annotations.Contract;

public sealed interface IntegerRange extends NumberRange<Integer> permits IntegerRangeImpl {

    @Contract(pure = true)
    static ConditionType<IntegerRange> conditionType() {
        return IntegerRangeImpl.TYPE;
    }

    @Contract(value = "_ -> new", pure = true)
    static IntegerRange isExactly(final int value) {
        return new IntegerRangeImpl(value, value);
    }

    @Contract(value = "_, _ -> new", pure = true)
    static IntegerRange isBetween(final int min, final int max) {
        return new IntegerRangeImpl(min, max);
    }

    @Contract(value = "_ -> new", pure = true)
    static IntegerRange isAtLeast(final int min) {
        return new IntegerRangeImpl(min, null);
    }

    @Contract(value = "_ -> new", pure = true)
    static IntegerRange isAtMost(final int max) {
        return new IntegerRangeImpl(null, max);
    }
}
