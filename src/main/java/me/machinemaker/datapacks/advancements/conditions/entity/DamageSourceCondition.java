package me.machinemaker.datapacks.advancements.conditions.entity;

import me.machinemaker.datapacks.advancements.conditions.Condition;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

// TODO tests
public record DamageSourceCondition(
        @Nullable Boolean isProjectile,
        @Nullable Boolean isExplosion,
        @Nullable Boolean bypassesArmor,
        @Nullable Boolean bypassesInvulnerability,
        @Nullable Boolean bypassesMagic,
        @Nullable Boolean isFire,
        @Nullable Boolean isMagic,
        @Nullable Boolean isLightning,
        EntityCondition directEntity,
        EntityCondition sourceEntity
) implements Condition.Buildable<DamageSourceCondition, DamageSourceCondition.Builder> {

    public static final DamageSourceCondition ANY = new Builder().build();

    @Override
    public DamageSourceCondition any() {
        return ANY;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "DamageSourceCondition{ANY}";
        }
        return "DamageSourceCondition{" +
                "isProjectile=" + this.isProjectile +
                ", isExplosion=" + this.isExplosion +
                ", bypassesArmor=" + this.bypassesArmor +
                ", bypassesInvulnerability=" + this.bypassesInvulnerability +
                ", bypassesMagic=" + this.bypassesMagic +
                ", isFire=" + this.isFire +
                ", isMagic=" + this.isMagic +
                ", isLightning=" + this.isLightning +
                ", directEntity=" + this.directEntity +
                ", sourceEntity=" + this.sourceEntity +
                '}';
    }

    @Contract(value = "-> new", pure = true)
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements Condition.Builder<DamageSourceCondition> {

        private @Nullable Boolean isProjectile;
        private @Nullable Boolean isExplosion;
        private @Nullable Boolean bypassesArmor;
        private @Nullable Boolean bypassesInvulnerability;
        private @Nullable Boolean bypassesMagic;
        private @Nullable Boolean isFire;
        private @Nullable Boolean isMagic;
        private @Nullable Boolean isLightning;
        private EntityCondition directEntity = EntityCondition.conditionType().any();
        private EntityCondition sourceEntity = EntityCondition.conditionType().any();

        private Builder() {
        }

        private Builder(DamageSourceCondition condition) {
            this.isProjectile = condition.isProjectile;
            this.isExplosion = condition.isExplosion;
            this.bypassesArmor = condition.bypassesArmor;
            this.bypassesInvulnerability = condition.bypassesInvulnerability;
            this.bypassesMagic = condition.bypassesMagic;
            this.isFire = condition.isFire;
            this.isMagic = condition.isMagic;
            this.isLightning = condition.isLightning;
            this.directEntity = condition.directEntity;
            this.sourceEntity = condition.sourceEntity;
        }

        public @Nullable Boolean isProjectile() {
            return this.isProjectile;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder isProjectile(@Nullable Boolean isProjectile) {
            this.isProjectile = isProjectile;
            return this;
        }

        public @Nullable Boolean isExplosion() {
            return this.isExplosion;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder isExplosion(@Nullable Boolean isExplosion) {
            this.isExplosion = isExplosion;
            return this;
        }

        public @Nullable Boolean bypassesArmor() {
            return this.bypassesArmor;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder bypassesArmor(@Nullable Boolean bypassesArmor) {
            this.bypassesArmor = bypassesArmor;
            return this;
        }

        public @Nullable Boolean bypassesInvulnerability() {
            return this.bypassesInvulnerability;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder bypassesInvulnerability(@Nullable Boolean bypassesInvulnerability) {
            this.bypassesInvulnerability = bypassesInvulnerability;
            return this;
        }

        public @Nullable Boolean bypassesMagic() {
            return this.bypassesMagic;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder bypassesMagic(@Nullable Boolean bypassesMagic) {
            this.bypassesMagic = bypassesMagic;
            return this;
        }

        public @Nullable Boolean isFire() {
            return this.isFire;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder isFire(@Nullable Boolean isFire) {
            this.isFire = isFire;
            return this;
        }

        public @Nullable Boolean isMagic() {
            return this.isMagic;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder isMagic(@Nullable Boolean isMagic) {
            this.isMagic = isMagic;
            return this;
        }

        public @Nullable Boolean isLightning() {
            return this.isLightning;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder isLightning(@Nullable Boolean isLightning) {
            this.isLightning = isLightning;
            return this;
        }

        public EntityCondition directEntity() {
            return this.directEntity;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder directEntity(EntityCondition directEntity) {
            this.directEntity = directEntity;
            return this;
        }

        public EntityCondition sourceEntity() {
            return this.sourceEntity;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder sourceEntity(EntityCondition sourceEntity) {
            this.sourceEntity = sourceEntity;
            return this;
        }

        @Override
        public DamageSourceCondition build() {
            return new DamageSourceCondition(this.isProjectile,
                    this.isExplosion,
                    this.bypassesArmor,
                    this.bypassesInvulnerability,
                    this.bypassesMagic,
                    this.isFire,
                    this.isMagic,
                    this.isLightning,
                    this.directEntity,
                    this.sourceEntity);
        }
    }
}
