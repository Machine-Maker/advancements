package me.machinemaker.advancements.conditions.entity;

import com.google.gson.annotations.SerializedName;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.ranges.DoubleRange;
import me.machinemaker.advancements.util.Buildable;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

// TODO tests
public record DamageCondition(
        @SerializedName("dealt") DoubleRange dealtDamage,
        @SerializedName("taken") DoubleRange takenDamage,
        EntityCondition sourceEntity,
        @Nullable Boolean blocked,
        DamageSourceCondition type
) implements Condition<DamageCondition>, Buildable<DamageCondition, DamageCondition.Builder> {

    public static final DamageCondition ANY = new Builder().build();

    @Override
    public DamageCondition any() {
        return ANY;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "DamageCondition{ANY}";
        }
        return "DamageCondition{" +
                "dealtDamage=" + this.dealtDamage +
                ", takenDamage=" + this.takenDamage +
                ", sourceEntity=" + this.sourceEntity +
                ", blocked=" + this.blocked +
                ", type=" + this.type +
                '}';
    }

    @Contract(value = " -> new", pure = true)
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements Condition.Builder<DamageCondition> {

        private DoubleRange dealtDamage = DoubleRange.ANY;
        private DoubleRange takenDamage = DoubleRange.ANY;
        private EntityCondition sourceEntity = EntityCondition.ANY;
        private @Nullable Boolean blocked;
        private DamageSourceCondition type = DamageSourceCondition.ANY;

        private Builder() {
        }

        public Builder(DamageCondition condition) {
            this.dealtDamage = condition.dealtDamage;
            this.takenDamage = condition.takenDamage;
            this.sourceEntity = condition.sourceEntity;
            this.blocked = condition.blocked;
            this.type = condition.type;
        }

        public DoubleRange dealtDamage() {
            return this.dealtDamage;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder dealtDamage(DoubleRange dealtDamage) {
            this.dealtDamage = dealtDamage;
            return this;
        }

        public DoubleRange takenDamage() {
            return this.takenDamage;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder takenDamage(DoubleRange takenDamage) {
            this.takenDamage = takenDamage;
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

        public @Nullable Boolean blocked() {
            return this.blocked;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder blocked(@Nullable Boolean blocked) {
            this.blocked = blocked;
            return this;
        }

        public DamageSourceCondition type() {
            return this.type;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder type(DamageSourceCondition type) {
            this.type = type;
            return this;
        }

        @Override
        public DamageCondition build() {
            return new DamageCondition(this.dealtDamage, this.takenDamage, this.sourceEntity, this.blocked, this.type);
        }
    }
}
