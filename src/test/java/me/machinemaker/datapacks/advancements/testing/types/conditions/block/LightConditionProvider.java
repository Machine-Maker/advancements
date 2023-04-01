package me.machinemaker.datapacks.advancements.testing.types.conditions.block;

import me.machinemaker.datapacks.advancements.conditions.block.LightCondition;
import me.machinemaker.datapacks.advancements.testing.Provider;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;

public class LightConditionProvider implements Provider<LightCondition> {

    @Override
    public LightCondition get() {
        return LightCondition.create(RandomProviders.INTEGER_RANGE.get());
    }
}
