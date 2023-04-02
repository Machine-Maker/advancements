package me.machinemaker.datapacks.advancements.conditions.entity;

import com.google.gson.annotations.SerializedName;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.conditions.range.DoubleRange;
import org.checkerframework.checker.nullness.qual.Nullable;

record DamageConditionImpl(
    @SerializedName("dealt") DoubleRange dealtDamage,
    @SerializedName("taken") DoubleRange takenDamage,
    EntityCondition sourceEntity,
    @Nullable Boolean blocked,
    DamageSourceCondition type
) implements DamageCondition {

    static final DamageCondition ANY = DamageCondition.builder().build();
    static final ConditionType<DamageCondition> TYPE = ConditionType.create(DamageCondition.class, ANY, DamageConditionImpl.class);

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
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

    static final class BuilderImpl implements DamageCondition.Builder {

        private DoubleRange dealtDamage = DoubleRange.conditionType().any();
        private DoubleRange takenDamage = DoubleRange.conditionType().any();
        private EntityCondition sourceEntity = EntityCondition.conditionType().any();
        private @Nullable Boolean blocked;
        private DamageSourceCondition type = DamageSourceCondition.conditionType().any();

        BuilderImpl() {
        }

        public BuilderImpl(final DamageCondition condition) {
            this.dealtDamage = condition.dealtDamage();
            this.takenDamage = condition.takenDamage();
            this.sourceEntity = condition.sourceEntity();
            this.blocked = condition.blocked();
            this.type = condition.type();
        }

        @Override
        public DoubleRange dealtDamage() {
            return this.dealtDamage;
        }

        @Override
        public DamageCondition.Builder dealtDamage(final DoubleRange dealtDamage) {
            this.dealtDamage = dealtDamage;
            return this;
        }

        @Override
        public DoubleRange takenDamage() {
            return this.takenDamage;
        }

        @Override
        public DamageCondition.Builder takenDamage(final DoubleRange takenDamage) {
            this.takenDamage = takenDamage;
            return this;
        }

        @Override
        public EntityCondition sourceEntity() {
            return this.sourceEntity;
        }

        @Override
        public DamageCondition.Builder sourceEntity(final EntityCondition sourceEntity) {
            this.sourceEntity = sourceEntity;
            return this;
        }

        @Override
        public @Nullable Boolean blocked() {
            return this.blocked;
        }

        @Override
        public DamageCondition.Builder blocked(final @Nullable Boolean blocked) {
            this.blocked = blocked;
            return this;
        }

        @Override
        public DamageSourceCondition type() {
            return this.type;
        }

        @Override
        public DamageCondition.Builder type(final DamageSourceCondition type) {
            this.type = type;
            return this;
        }

        @Override
        public DamageCondition build() {
            return new DamageConditionImpl(this.dealtDamage, this.takenDamage, this.sourceEntity, this.blocked, this.type);
        }
    }
}
