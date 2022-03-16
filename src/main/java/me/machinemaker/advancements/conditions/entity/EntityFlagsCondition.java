package me.machinemaker.advancements.conditions.entity;

import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.util.Buildable;
import org.checkerframework.checker.nullness.qual.Nullable;

public record EntityFlagsCondition(
        @Nullable Boolean isOnFire,
        @Nullable Boolean isCrouching,
        @Nullable Boolean isSprinting,
        @Nullable Boolean isSwimming,
        @Nullable Boolean isBaby
) implements Condition<EntityFlagsCondition>, Buildable<EntityFlagsCondition, EntityFlagsCondition.Builder> {

    public static final EntityFlagsCondition ANY = new EntityFlagsCondition(null, null, null, null, null);

    @Override
    public EntityFlagsCondition any() {
        return ANY;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "EntityFlagsCondition{ANY}";
        }
        return "EntityFlagsCondition{" +
                "isOnFire=" + this.isOnFire +
                ", isCrouching=" + this.isCrouching +
                ", isSprinting=" + this.isSprinting +
                ", isSwimming=" + this.isSwimming +
                ", isBaby=" + this.isBaby +
                '}';
    }

    public static final class Builder implements Condition.Builder<EntityFlagsCondition> {

        private @Nullable Boolean isOnFire;
        private @Nullable Boolean isCrouching;
        private @Nullable Boolean isSprinting;
        private @Nullable Boolean isSwimming;
        private @Nullable Boolean isBaby;

        private Builder() {
        }

        private Builder(EntityFlagsCondition condition) {
            this.isOnFire = condition.isOnFire;
            this.isCrouching = condition.isCrouching;
            this.isSprinting = condition.isSprinting;
            this.isSwimming = condition.isSwimming;
            this.isBaby = condition.isBaby;
        }

        public @Nullable Boolean isOnFire() {
            return this.isOnFire;
        }

        public Builder isOnFire(@Nullable Boolean isOnFire) {
            this.isOnFire = isOnFire;
            return this;
        }

        public @Nullable Boolean isCrouching() {
            return this.isCrouching;
        }

        public Builder isCrouching(@Nullable Boolean isCrouching) {
            this.isCrouching = isCrouching;
            return this;
        }

        public @Nullable Boolean isSprinting() {
            return this.isSprinting;
        }

        public Builder isSprinting(@Nullable Boolean isSprinting) {
            this.isSprinting = isSprinting;
            return this;
        }

        public @Nullable Boolean isSwimming() {
            return this.isSwimming;
        }

        public Builder isSwimming(@Nullable Boolean isSwimming) {
            this.isSwimming = isSwimming;
            return this;
        }

        public @Nullable Boolean isBaby() {
            return this.isBaby;
        }

        public Builder isBaby(@Nullable Boolean isBaby) {
            this.isBaby = isBaby;
            return this;
        }

        @Override
        public EntityFlagsCondition build() {
            return new EntityFlagsCondition(this.isOnFire, this.isCrouching, this.isSprinting, this.isSwimming, this.isBaby);
        }
    }
}
