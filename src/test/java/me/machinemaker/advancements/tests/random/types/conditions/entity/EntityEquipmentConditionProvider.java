package me.machinemaker.advancements.tests.random.types.conditions.entity;

import me.machinemaker.advancements.conditions.entity.EntityEquipmentCondition;
import me.machinemaker.advancements.tests.random.RandomProviders;
import me.machinemaker.advancements.tests.random.types.conditions.ConditionProvider;

public class EntityEquipmentConditionProvider extends ConditionProvider<EntityEquipmentCondition, EntityEquipmentCondition.Builder> {

    public EntityEquipmentConditionProvider() {
        super(EntityEquipmentCondition::builder);
        this.component(() -> RandomProviders.ITEM_CONDITION, EntityEquipmentCondition.Builder::head);
        this.component(() -> RandomProviders.ITEM_CONDITION, EntityEquipmentCondition.Builder::chest);
        this.component(() -> RandomProviders.ITEM_CONDITION, EntityEquipmentCondition.Builder::legs);
        this.component(() -> RandomProviders.ITEM_CONDITION, EntityEquipmentCondition.Builder::feet);
        this.component(() -> RandomProviders.ITEM_CONDITION, EntityEquipmentCondition.Builder::mainhand);
        this.component(() -> RandomProviders.ITEM_CONDITION, EntityEquipmentCondition.Builder::offhand);
    }
}
