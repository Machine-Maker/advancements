package me.machinemaker.datapacks.advancements.conditions.effect;

import me.machinemaker.datapacks.advancements.conditions.Condition;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.conditions.range.IntegerRange;
import org.bukkit.potion.PotionEffect;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.NonExtendable
public interface PotionEffectInstanceCondition extends Condition.Buildable<PotionEffectInstanceCondition, PotionEffectInstanceCondition.Builder> {

    @Contract(pure = true)
    static ConditionType<PotionEffectInstanceCondition> conditionType() {
        return PotionEffectInstanceConditionImpl.TYPE;
    }

    @Contract(value = "_ -> new", pure = true)
    static PotionEffectInstanceCondition fromPotionEffect(final PotionEffect potionEffect) {
        return builder()
            .amplifier(IntegerRange.isExactly(potionEffect.getAmplifier()))
            .duration(IntegerRange.isExactly(potionEffect.getDuration()))
            .ambient(potionEffect.isAmbient())
            .visible(potionEffect.hasParticles())
            .build();
    }

    @Contract(value = "-> new", pure = true)
    static Builder builder() {
        return new PotionEffectInstanceConditionImpl.BuilderImpl();
    }

    @Contract(pure = true)
    IntegerRange amplifier();

    @Contract(pure = true)
    IntegerRange duration();

    @Contract(pure = true)
    @Nullable Boolean ambient();

    @Contract(pure = true)
    @Nullable Boolean visible();

    @ApiStatus.NonExtendable
    interface Builder extends Condition.Builder<PotionEffectInstanceCondition> {

        @Contract(pure = true)
        IntegerRange amplifier();

        @Contract(value = "_ -> new", mutates = "this")
        Builder amplifier(IntegerRange amplifier);

        @Contract(pure = true)
        IntegerRange duration();

        @Contract(value = "_ -> new", mutates = "this")
        Builder duration(IntegerRange duration);

        @Contract(pure = true)
        @Nullable Boolean ambient();

        @Contract(value = "_ -> new", mutates = "this")
        Builder ambient(@Nullable Boolean ambient);

        @Contract(pure = true)
        @Nullable Boolean visible();

        @Contract(value = "_ -> new", mutates = "this")
        Builder visible(@Nullable Boolean visible);
    }

}
