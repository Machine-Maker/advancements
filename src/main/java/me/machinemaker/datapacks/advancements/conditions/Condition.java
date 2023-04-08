package me.machinemaker.datapacks.advancements.conditions;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.NonExtendable
public interface Condition {

    /**
     * Checks if this condition will match any input.
     *
     * @return true if any input will match this condition
     */
    boolean isAny();

    @ApiStatus.NonExtendable
    interface Buildable<C extends Condition, B extends Builder<C>> extends Condition, me.machinemaker.datapacks.common.utils.Buildable<C, B> {
    }

    @ApiStatus.NonExtendable
    interface Builder<C extends Condition> extends me.machinemaker.datapacks.common.utils.Buildable.Builder<C> {

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
