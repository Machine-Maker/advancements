package me.machinemaker.advancements.conditions.range;

import java.util.Objects;
import me.machinemaker.advancements.conditions.Condition;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

public sealed interface NumberRange<N extends Number, C extends NumberRange<N, C>> extends Condition<C> permits DoubleRange, IntegerRange {

    @Contract(pure = true)
    @Nullable N min();

    @Contract(pure = true)
    @Nullable N max();

    @Override
    default boolean isAny() {
        return this.min() == null && this.max() == null;
    }

    @Contract(pure = true)
    default boolean isExact() {
        return this.min() != null && this.max() != null && Objects.equals(this.min(), this.max());
    }

}
