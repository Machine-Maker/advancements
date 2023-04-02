package me.machinemaker.datapacks.advancements.conditions.entity;

import me.machinemaker.datapacks.advancements.conditions.Condition;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.conditions.range.DoubleRange;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.NonExtendable
public interface DamageCondition extends Condition.Buildable<DamageCondition, DamageCondition.Builder> {

    @Contract(pure = true)
    static ConditionType<DamageCondition> conditionType() {
        return DamageConditionImpl.TYPE;
    }

    @Contract(value = " -> new", pure = true)
    static Builder builder() {
        return new DamageConditionImpl.BuilderImpl();
    }

    @Contract(pure = true)
    DoubleRange dealtDamage();

    @Contract(pure = true)
    DoubleRange takenDamage();

    @Contract(pure = true)
    EntityCondition sourceEntity();

    @Contract(pure = true)
    @Nullable Boolean blocked();

    @Contract(pure = true)
    DamageSourceCondition type();

    @ApiStatus.NonExtendable
    interface Builder extends Condition.Builder<DamageCondition> {

        @Contract(pure = true)
        DoubleRange dealtDamage();

        @Contract(value = "_ -> this", mutates = "this")
        Builder dealtDamage(final DoubleRange dealtDamage);

        @Contract(pure = true)
        DoubleRange takenDamage();

        @Contract(value = "_ -> this", mutates = "this")
        Builder takenDamage(final DoubleRange takenDamage);

        @Contract(pure = true)
        EntityCondition sourceEntity();

        @Contract(value = "_ -> this", mutates = "this")
        Builder sourceEntity(final EntityCondition sourceEntity);

        @Contract(pure = true)
        @Nullable Boolean blocked();

        @Contract(value = "_ -> this", mutates = "this")
        Builder blocked(final @Nullable Boolean blocked);

        @Contract(pure = true)
        DamageSourceCondition type();

        @Contract(value = "_ -> this", mutates = "this")
        Builder type(final DamageSourceCondition type);
    }
}
