package me.machinemaker.datapacks.advancements.testing.types.conditions.entity.sub;

import me.machinemaker.datapacks.advancements.conditions.entity.sub.SlimeCondition;
import me.machinemaker.datapacks.advancements.conditions.range.IntegerRange;
import me.machinemaker.datapacks.advancements.testing.Provider;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;

public class SlimeConditionProvider implements Provider<SlimeCondition> {

    @Override
    public SlimeCondition get() {
        return this.bool() ? SlimeCondition.create(RandomProviders.INTEGER_RANGE.get()) : SlimeCondition.create(IntegerRange.conditionType().any());
    }
}
