package me.machinemaker.datapacks.advancements.conditions.entity;

import me.machinemaker.datapacks.advancements.conditions.Condition;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.tags.EntityTag;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.NonExtendable
public interface EntityTypeCondition extends Condition<EntityTypeCondition> {

    @Contract(pure = true)
    static ConditionType<EntityTypeCondition> conditionType() {
        return EntityTypeConditionImpl.TYPE;
    }

    @Contract(value = "_ -> new", pure = true)
    static EntityTypeCondition.Type type(final EntityType type) {
        return new EntityTypeConditionImpl.TypeImpl(type);
    }

    @Contract(value = "_ -> new", pure = true)
    static EntityTypeCondition.Tag tag(final org.bukkit.Tag<EntityType> tag) {
        return new EntityTypeConditionImpl.TagImpl(tag instanceof final EntityTag entityTag ? entityTag : new EntityTag(tag));
    }

    @ApiStatus.NonExtendable
    interface Type extends EntityTypeCondition {

        @Contract(pure = true)
        org.bukkit.entity.EntityType type();
    }

    @ApiStatus.NonExtendable
    interface Tag extends EntityTypeCondition {

        @Contract(pure = true)
        EntityTag tag();
    }
}
