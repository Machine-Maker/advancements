package me.machinemaker.advancements.tests.random.types.conditions.block;

import me.machinemaker.advancements.conditions.block.LightCondition;
import me.machinemaker.advancements.tests.random.Provider;
import me.machinemaker.advancements.tests.random.RandomProviders;

public class LightConditionProvider implements Provider<LightCondition> {

    @Override
    public LightCondition get() {
        return LightCondition.create(RandomProviders.INTEGER_RANGE.get());
    }
}
