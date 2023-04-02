package me.machinemaker.datapacks.advancements.conditions.entity;

import java.util.List;
import me.machinemaker.datapacks.advancements.conditions.Condition;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.conditions.misc.TagCondition;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

@ApiStatus.NonExtendable
public interface DamageSourceCondition extends Condition.Buildable<DamageSourceCondition, DamageSourceCondition.Builder> {

    @Contract(pure = true)
    static ConditionType<DamageSourceCondition> conditionType() {
        return DamageSourceConditionImpl.TYPE;
    }

    @Contract(value = "-> new", pure = true)
    static Builder builder() {
        return new DamageSourceConditionImpl.BuilderImpl();
    }

    @Contract(pure = true)
    @Unmodifiable List<TagCondition<Key>> tags();

    @Contract(pure = true)
    EntityCondition directEntity();

    @Contract(pure = true)
    EntityCondition sourceEntity();

    @ApiStatus.NonExtendable
    interface Builder extends Condition.Builder<DamageSourceCondition> {

        @Contract(pure = true)
        List<TagCondition<Key>> tags();

        @Contract(value = "_ -> this", mutates = "this")
        Builder tags(List<TagCondition<Key>> tags);

        @Contract(pure = true)
        EntityCondition directEntity();

        @Contract(value = "_ -> this", mutates = "this")
        Builder directEntity(EntityCondition directEntity);

        @Contract(pure = true)
        EntityCondition sourceEntity();

        @Contract(value = "_ -> this", mutates = "this")
        Builder sourceEntity(EntityCondition sourceEntity);
    }
}
