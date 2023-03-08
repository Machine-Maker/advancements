package me.machinemaker.advancements.conditions.effects;

import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.ranges.IntegerRange;
import me.machinemaker.advancements.util.Buildable;
import org.bukkit.potion.PotionEffect;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

public record PotionEffectInstanceCondition(
        IntegerRange amplifier,
        IntegerRange duration,
        @Nullable Boolean ambient,
        @Nullable Boolean visible
) implements Condition<PotionEffectInstanceCondition>, Buildable<PotionEffectInstanceCondition, PotionEffectInstanceCondition.Builder> {

    public static final PotionEffectInstanceCondition ANY = new PotionEffectInstanceCondition(IntegerRange.conditionType().any(), IntegerRange.conditionType().any(), null, null);

    @Contract(value = "_ -> new", pure = true)
    public static PotionEffectInstanceCondition fromPotionEffect(PotionEffect potionEffect) {
        return new PotionEffectInstanceCondition(IntegerRange.isExactly(potionEffect.getAmplifier()), IntegerRange.isExactly(potionEffect.getDuration()), potionEffect.isAmbient(), potionEffect.hasParticles());
    }

    @Override
    public PotionEffectInstanceCondition any() {
        return ANY;
    }

    @Override
    public boolean anyIsNull() {
        return false;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

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

    @Contract(value = "-> new", pure = true)
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements Condition.Builder<PotionEffectInstanceCondition> {

        private IntegerRange amplifier = IntegerRange.conditionType().any();
        private IntegerRange duration = IntegerRange.conditionType().any();
        private @Nullable Boolean ambient;
        private @Nullable Boolean visible;

        private Builder() {
        }

        private Builder(PotionEffectInstanceCondition condition) {
            this.amplifier = condition.amplifier;
            this.duration = condition.duration;
            this.ambient = condition.ambient;
            this.visible = condition.visible;
        }

        public IntegerRange amplifier() {
            return this.amplifier;
        }

        @Contract(mutates = "this")
        public Builder amplifier(IntegerRange amplifier) {
            this.amplifier = amplifier;
            return this;
        }

        public IntegerRange duration() {
            return this.duration;
        }

        @Contract(mutates = "this")
        public Builder duration(IntegerRange duration) {
            this.duration = duration;
            return this;
        }

        public @Nullable Boolean ambient() {
            return this.ambient;
        }

        @Contract(mutates = "this")
        public Builder ambient(@Nullable Boolean ambient) {
            this.ambient = ambient;
            return this;
        }

        public @Nullable Boolean visible() {
            return this.visible;
        }

        @Contract(mutates = "this")
        public Builder visible(@Nullable Boolean visible) {
            this.visible = visible;
            return this;
        }

        @Override
        public PotionEffectInstanceCondition build() {
            return new PotionEffectInstanceCondition(this.amplifier, this.duration, this.ambient, this.visible);
        }
    }
}
