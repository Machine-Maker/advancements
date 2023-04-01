package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.NonExtendable
public interface FishingHookCondition extends EntitySubCondition {

    @Contract(value = "_ -> new", pure = true)
    static FishingHookCondition create(final boolean isOpenWater) {
        return new FishingHookConditionImpl(isOpenWater);
    }

    @Contract(pure = true)
    boolean isOpenWater();
}
