package me.machinemaker.datapacks.advancements.testing.types.conditions.entity;

import me.machinemaker.datapacks.advancements.conditions.entity.EntityCondition;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.advancements.testing.types.conditions.ConditionProvider;
import org.apache.commons.lang.RandomStringUtils;

public class EntityConditionProvider extends ConditionProvider<EntityCondition, EntityCondition.Builder> {

    public EntityConditionProvider() {
        super(EntityCondition::builder);
        this.component(() -> RandomProviders.ENTITY_TYPE_CONDITION, EntityCondition.Builder::type);
        this.component(() -> RandomProviders.DISTANCE_CONDITION, EntityCondition.Builder::distance);
        this.component(() -> RandomProviders.LOCATION_CONDITION, EntityCondition.Builder::location);
        this.component(() -> RandomProviders.LOCATION_CONDITION, EntityCondition.Builder::steppingOn);
        this.component(() -> RandomProviders.POTION_EFFECTS_CONDITION, EntityCondition.Builder::effects);
        this.component(() -> RandomProviders.NBT_CONDITION, EntityCondition.Builder::nbt);
        this.component(() -> RandomProviders.ENTITY_FLAGS_CONDITION, EntityCondition.Builder::flags);
        this.component(() -> RandomProviders.ENTITY_EQUIPMENT_CONDITION, EntityCondition.Builder::equipment);
        this.component(() -> RandomProviders.ENTITY_SUB_CONDITION, EntityCondition.Builder::typeSpecific);
        // don't do recursion in here, that just gets messy
        this.component(() -> () -> RandomStringUtils.randomAlphabetic(15), EntityCondition.Builder::team);
    }
}
