package me.machinemaker.datapacks.advancements.testing.types.conditions.entity.sub;

import me.machinemaker.datapacks.advancements.conditions.entity.sub.FishingHookCondition;
import me.machinemaker.datapacks.advancements.testing.Provider;

public class FishingHookConditionProvider implements Provider<FishingHookCondition> {

    @Override
    public FishingHookCondition get() {
        return FishingHookCondition.create(this.bool());
    }
}
