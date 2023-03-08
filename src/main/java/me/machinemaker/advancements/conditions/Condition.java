package me.machinemaker.advancements.conditions;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.NonExtendable
public interface Condition<C extends Condition<C>> {

    /**
     * Checks if this condition will match any input.
     *
     * @return true if any input will match this condition
     */
    default boolean isAny() {
        throw new UnsupportedOperationException();
    }
    // boolean isAny();

    // legacy
    default C any() {
        throw new UnsupportedOperationException();
    }

    default boolean anyIsNull() {
        throw new UnsupportedOperationException();
    }

    @ApiStatus.NonExtendable
    interface Buildable<C extends Condition<C>, B extends Builder<C>> extends Condition<C>, me.machinemaker.advancements.util.Buildable<C, B> {
    }

    @ApiStatus.NonExtendable
    interface Builder<C extends Condition<? super C>> extends me.machinemaker.advancements.util.Buildable.Builder<C> {

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
