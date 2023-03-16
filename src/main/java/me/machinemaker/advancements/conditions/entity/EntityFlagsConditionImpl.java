package me.machinemaker.advancements.conditions.entity;

import com.google.gson.TypeAdapterFactory;
import me.machinemaker.advancements.adapters.builders.Builders;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.adapters.factories.ConditionAdapterFactory;
import me.machinemaker.advancements.conditions.ConditionType;
import org.checkerframework.checker.nullness.qual.Nullable;

public record EntityFlagsConditionImpl(
    @Nullable Boolean isOnFire,
    @Nullable Boolean isCrouching,
    @Nullable Boolean isSprinting,
    @Nullable Boolean isSwimming,
    @Nullable Boolean isBaby
) implements EntityFlagsCondition {

    static final EntityFlagsCondition ANY = new EntityFlagsConditionImpl(null, null, null, null, null);
    static final ConditionType<EntityFlagsCondition> TYPE = ConditionType.create(EntityFlagsCondition.class, ANY, EntityFlagsCondition::requiredGson);
    static final TypeAdapterFactory FACTORY = ConditionAdapterFactory.record(TYPE, EntityFlagsConditionImpl.class);
    static final GsonBuilderApplicable REQUIRED_GSON = Builders.factory(FACTORY);

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public EntityFlagsCondition.Builder toBuilder() {
        return new BuilderImpl(this);
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

    static final class BuilderImpl implements EntityFlagsCondition.Builder {

        private @Nullable Boolean isOnFire;
        private @Nullable Boolean isCrouching;
        private @Nullable Boolean isSprinting;
        private @Nullable Boolean isSwimming;
        private @Nullable Boolean isBaby;

        BuilderImpl() {
        }

        private BuilderImpl(final EntityFlagsCondition condition) {
            this.isOnFire = condition.isOnFire();
            this.isCrouching = condition.isCrouching();
            this.isSprinting = condition.isSprinting();
            this.isSwimming = condition.isSwimming();
            this.isBaby = condition.isBaby();
        }

        @Override
        public @Nullable Boolean isOnFire() {
            return this.isOnFire;
        }

        @Override
        public EntityFlagsCondition.Builder isOnFire(final @Nullable Boolean isOnFire) {
            this.isOnFire = isOnFire;
            return this;
        }

        @Override
        public @Nullable Boolean isCrouching() {
            return this.isCrouching;
        }

        @Override
        public EntityFlagsCondition.Builder isCrouching(final @Nullable Boolean isCrouching) {
            this.isCrouching = isCrouching;
            return this;
        }

        @Override
        public @Nullable Boolean isSprinting() {
            return this.isSprinting;
        }

        @Override
        public EntityFlagsCondition.Builder isSprinting(final @Nullable Boolean isSprinting) {
            this.isSprinting = isSprinting;
            return this;
        }

        @Override
        public @Nullable Boolean isSwimming() {
            return this.isSwimming;
        }

        @Override
        public EntityFlagsCondition.Builder isSwimming(final @Nullable Boolean isSwimming) {
            this.isSwimming = isSwimming;
            return this;
        }

        @Override
        public @Nullable Boolean isBaby() {
            return this.isBaby;
        }

        @Override
        public EntityFlagsCondition.Builder isBaby(final @Nullable Boolean isBaby) {
            this.isBaby = isBaby;
            return this;
        }

        @Override
        public EntityFlagsCondition build() {
            return new EntityFlagsConditionImpl(this.isOnFire, this.isCrouching, this.isSprinting, this.isSwimming, this.isBaby);
        }
    }
}
