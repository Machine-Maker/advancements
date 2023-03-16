package me.machinemaker.advancements.conditions.entity;

import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.ConditionType;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.NonExtendable
public interface EntityFlagsCondition extends Condition.Buildable<EntityFlagsCondition, EntityFlagsCondition.Builder> {

    @Contract(pure = true)
    static ConditionType<EntityFlagsCondition> conditionType() {
        return EntityFlagsConditionImpl.TYPE;
    }

    @Contract(value = "-> new", pure = true)
    static Builder builder() {
        return new EntityFlagsConditionImpl.BuilderImpl();
    }

    @Contract(pure = true)
    static GsonBuilderApplicable requiredGson() {
        return EntityFlagsConditionImpl.REQUIRED_GSON;
    }

    @Contract(pure = true)
    @Nullable Boolean isOnFire();

    @Contract(pure = true)
    @Nullable Boolean isCrouching();

    @Contract(pure = true)
    @Nullable Boolean isSprinting();

    @Contract(pure = true)
    @Nullable Boolean isSwimming();

    @Contract(pure = true)
    @Nullable Boolean isBaby();

    @ApiStatus.NonExtendable
    interface Builder extends Condition.Builder<EntityFlagsCondition> {

        @Contract(pure = true)
        @Nullable Boolean isOnFire();

        @Contract(value = "_ -> this", mutates = "this")
        Builder isOnFire(@Nullable Boolean isOnFire);

        @Contract(pure = true)
        @Nullable Boolean isCrouching();

        @Contract(value = "_ -> this", mutates = "this")
        Builder isCrouching(@Nullable Boolean isCrouching);

        @Contract(pure = true)
        @Nullable Boolean isSprinting();

        @Contract(value = "_ -> this", mutates = "this")
        Builder isSprinting(@Nullable Boolean isSprinting);

        @Contract(pure = true)
        @Nullable Boolean isSwimming();

        @Contract(value = "_ -> this", mutates = "this")
        Builder isSwimming(@Nullable Boolean isSwimming);

        @Contract(pure = true)
        @Nullable Boolean isBaby();

        @Contract(value = "_ -> this", mutates = "this")
        Builder isBaby(@Nullable Boolean isBaby);
    }
}
