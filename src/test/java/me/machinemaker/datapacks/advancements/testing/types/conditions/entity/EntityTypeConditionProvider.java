package me.machinemaker.datapacks.advancements.testing.types.conditions.entity;

import me.machinemaker.datapacks.advancements.conditions.entity.EntityTypeCondition;
import me.machinemaker.datapacks.advancements.testing.Provider;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
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
