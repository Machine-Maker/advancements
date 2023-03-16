package me.machinemaker.advancements.tests.random.types.conditions.entity;

import me.machinemaker.advancements.conditions.entity.EntityFlagsCondition;
import me.machinemaker.advancements.tests.random.RandomProviders;
import me.machinemaker.advancements.tests.random.types.conditions.ConditionProvider;

public class EntityFlagsConditionProvider extends ConditionProvider<EntityFlagsCondition, EntityFlagsCondition.Builder> {

    public EntityFlagsConditionProvider() {
        super(EntityFlagsCondition::builder);
        this.component(() -> RandomProviders.BOOLEAN, EntityFlagsCondition.Builder::isOnFire);
        this.component(() -> RandomProviders.BOOLEAN, EntityFlagsCondition.Builder::isCrouching);
        this.component(() -> RandomProviders.BOOLEAN, EntityFlagsCondition.Builder::isSprinting);
        this.component(() -> RandomProviders.BOOLEAN, EntityFlagsCondition.Builder::isSwimming);
        this.component(() -> RandomProviders.BOOLEAN, EntityFlagsCondition.Builder::isBaby);
    }
}
