package me.machinemaker.datapacks.advancements.conditions.effect;

import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.common.range.IntegerRange;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

record PotionEffectInstanceConditionImpl(
    IntegerRange amplifier,
    IntegerRange duration,
    @Nullable Boolean ambient,
    @Nullable Boolean visible
) implements PotionEffectInstanceCondition {

    static final PotionEffectInstanceCondition ANY = new PotionEffectInstanceConditionImpl(IntegerRange.conditionType().any(), IntegerRange.conditionType().any(), null, null);
    static final ConditionType<PotionEffectInstanceCondition> TYPE = ConditionType.create(PotionEffectInstanceCondition.class, ANY, PotionEffectInstanceConditionImpl.class, false);

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    //<editor-fold desc="toString" defaultstate="collapsed">
    @Override
    public String toString() {
        if (this.isAny()) {
            return "PotionEffectInstanceCondition{ANY}";
        }
        return "PotionEffectInstanceCondition{" +
            "amplifier=" + this.amplifier +
            ", duration=" + this.duration +
            ", ambient=" + this.ambient +
            ", visible=" + this.visible +
            '}';
    }
    //</editor-fold>

    static final class BuilderImpl implements PotionEffectInstanceCondition.Builder {

        private IntegerRange amplifier = IntegerRange.conditionType().any();
        private IntegerRange duration = IntegerRange.conditionType().any();
        private @Nullable Boolean ambient = null;
        private @Nullable Boolean visible = null;

        BuilderImpl() {
        }

        BuilderImpl(final PotionEffectInstanceCondition condition) {
            this.amplifier = condition.amplifier();
            this.duration = condition.duration();
            this.ambient = condition.ambient();
            this.visible = condition.visible();
        }

        public IntegerRange amplifier() {
            return this.amplifier;
        }

        @Contract(mutates = "this")
        public PotionEffectInstanceCondition.Builder amplifier(final IntegerRange amplifier) {
            this.amplifier = amplifier;
            return this;
        }

        public IntegerRange duration() {
            return this.duration;
        }

        @Contract(mutates = "this")
        public PotionEffectInstanceCondition.Builder duration(final IntegerRange duration) {
            this.duration = duration;
            return this;
        }

        public @Nullable Boolean ambient() {
            return this.ambient;
        }

        @Contract(mutates = "this")
        public PotionEffectInstanceCondition.Builder ambient(final @Nullable Boolean ambient) {
            this.ambient = ambient;
            return this;
        }

        public @Nullable Boolean visible() {
            return this.visible;
        }

        @Contract(mutates = "this")
        public PotionEffectInstanceCondition.Builder visible(final @Nullable Boolean visible) {
            this.visible = visible;
            return this;
        }

        @Override
        public PotionEffectInstanceCondition build() {
            return new PotionEffectInstanceConditionImpl(this.amplifier, this.duration, this.ambient, this.visible);
        }
    }

}
