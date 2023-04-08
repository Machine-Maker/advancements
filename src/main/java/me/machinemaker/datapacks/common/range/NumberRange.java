package me.machinemaker.datapacks.common.range;

import java.util.Objects;
import me.machinemaker.datapacks.advancements.conditions.Condition;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

public sealed interface NumberRange<N extends Number> extends Condition permits DoubleRange, IntegerRange {

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
