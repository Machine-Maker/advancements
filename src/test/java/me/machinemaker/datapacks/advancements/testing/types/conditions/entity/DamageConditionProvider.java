package me.machinemaker.datapacks.advancements.testing.types.conditions.entity;

import me.machinemaker.datapacks.advancements.conditions.entity.DamageCondition;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.advancements.testing.types.conditions.ConditionProvider;

public class DamageConditionProvider extends ConditionProvider<DamageCondition, DamageCondition.Builder> {

    public DamageConditionProvider() {
        super(DamageCondition::builder);
        this.component(() -> RandomProviders.DOUBLE_RANGE, DamageCondition.Builder::dealtDamage);
        this.component(() -> RandomProviders.DOUBLE_RANGE, DamageCondition.Builder::takenDamage);
        this.component(() -> RandomProviders.ENTITY_CONDITION, DamageCondition.Builder::sourceEntity);
        this.component(() -> this::bool, DamageCondition.Builder::blocked);
        this.component(() -> RandomProviders.DAMAGE_SOURCE_CONDITION, DamageCondition.Builder::type);
    }
}
