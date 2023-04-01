package me.machinemaker.datapacks.advancements.testing.types.conditions.entity.sub;

import me.machinemaker.datapacks.advancements.conditions.entity.sub.LightningBoltCondition;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.advancements.testing.types.conditions.ConditionProvider;

public class LightningBoltConditionProvider extends ConditionProvider<LightningBoltCondition, LightningBoltCondition.Builder> {

    public LightningBoltConditionProvider() {
        super(LightningBoltCondition::builder);
        this.component(() -> RandomProviders.INTEGER_RANGE, LightningBoltCondition.Builder::blocksSetOnFire);
        this.component(() -> RandomProviders.ENTITY_CONDITION, LightningBoltCondition.Builder::entityStruck);
    }
}
