package me.machinemaker.datapacks.advancements.testing.types.conditions.entity;

import me.machinemaker.datapacks.advancements.conditions.entity.EntityFlagsCondition;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.advancements.testing.types.conditions.ConditionProvider;

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
