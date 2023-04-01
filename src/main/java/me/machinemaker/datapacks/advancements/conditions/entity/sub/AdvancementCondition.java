package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

@ApiStatus.NonExtendable
public interface AdvancementCondition {

    @Contract(value = "_ -> new", pure = true)
    static AdvancementCondition done(final boolean state) {
        return new AdvancementConditionImpl.DoneImpl(state);
    }

    @Contract(value = "_ -> new", pure = true)
    static AdvancementCondition criteria(final Object2BooleanMap<String> criteria) {
        return new AdvancementConditionImpl.CriteriaImpl(criteria);
    }

    @ApiStatus.NonExtendable
    interface Done extends AdvancementCondition {

        @Contract(pure = true)
        boolean state();
    }

    @ApiStatus.NonExtendable
    interface Criteria extends AdvancementCondition {

        @Contract(pure = true)
        @Unmodifiable Object2BooleanMap<String> criteria();
    }
}
