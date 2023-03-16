package me.machinemaker.advancements.tests.random.types.conditions.entity;

import me.machinemaker.advancements.conditions.entity.EntityTypeCondition;
import me.machinemaker.advancements.tests.random.Provider;
import me.machinemaker.advancements.tests.random.RandomProviders;
import org.bukkit.Tag;

public class EntityTypeConditionProvider implements Provider<EntityTypeCondition> {

    @Override
    public EntityTypeCondition get() {
        if (this.integer(100) < 25) {
            return EntityTypeCondition.tag(Tag.ENTITY_TYPES_SKELETONS);
        } else {
            return EntityTypeCondition.type(RandomProviders.ENTITY_TYPE.get());
        }
    }
}
