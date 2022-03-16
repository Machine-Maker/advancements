package me.machinemaker.advancements.conditions;

import me.machinemaker.advancements.util.Buildable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.jetbrains.annotations.Contract;

@DefaultQualifier(NonNull.class)
public interface Condition<C extends Condition<C>> {

    /**
     * Checks if this condition will match any input.
     *
     * @return true if any input will match this condition
     */
    default boolean isAny() {
        return this.equals(this.any());
    }

    /**
     * Returns the "any" instance of this condition.
     *
     * @return the "any" instance
     */
    @Contract(pure = true)
    C any();

    /**
     * When serializing, if the condition is any, should the field be set to null
     *
     * @return true to set to null
     */
    default boolean anyIsNull() {
        return true;
    }

    interface Builder<C extends Condition<C>> extends Buildable.Builder<C> {

        /**
         * Constructs a new condition.
         *
         * @return a new condition
         */
        @Contract(value = "-> new", pure = true)
        @Override
        C build();
    }
}
