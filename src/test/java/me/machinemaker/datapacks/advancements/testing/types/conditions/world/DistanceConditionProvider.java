package me.machinemaker.datapacks.advancements.testing.types.conditions.world;

import me.machinemaker.datapacks.advancements.conditions.world.DistanceCondition;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.advancements.testing.types.conditions.ConditionProvider;

public class DistanceConditionProvider extends ConditionProvider<DistanceCondition, DistanceCondition.Builder> {

    public DistanceConditionProvider() {
        super(DistanceCondition::builder);
        this.component(() -> RandomProviders.DOUBLE_RANGE, DistanceCondition.Builder::x);
        this.component(() -> RandomProviders.DOUBLE_RANGE, DistanceCondition.Builder::y);
        this.component(() -> RandomProviders.DOUBLE_RANGE, DistanceCondition.Builder::z);
        this.component(() -> RandomProviders.DOUBLE_RANGE, DistanceCondition.Builder::horizontal);
        this.component(() -> RandomProviders.DOUBLE_RANGE, DistanceCondition.Builder::absolute);
    }
}
