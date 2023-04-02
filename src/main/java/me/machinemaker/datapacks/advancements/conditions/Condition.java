package me.machinemaker.datapacks.advancements.conditions;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.NonExtendable
public interface Condition<C extends Condition<C>> {

    /**
     * Checks if this condition will match any input.
     *
     * @return true if any input will match this condition
     */
    boolean isAny();

    @ApiStatus.NonExtendable
    interface Buildable<C extends Condition<C>, B extends Builder<C>> extends Condition<C>, me.machinemaker.datapacks.advancements.utils.Buildable<C, B> {
    }

    @ApiStatus.NonExtendable
    interface Builder<C extends Condition<? super C>> extends me.machinemaker.datapacks.advancements.utils.Buildable.Builder<C> {

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
