package me.machinemaker.advancements.conditions.effect;

import java.util.Map;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.ConditionType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

@ApiStatus.NonExtendable
public interface PotionEffectsCondition extends Condition.Buildable<PotionEffectsCondition, PotionEffectsCondition.Builder> {

    @Contract(pure = true)
    static ConditionType<PotionEffectsCondition> conditionType() {
        return PotionEffectsConditionImpl.TYPE;
    }

    @Contract(value = "-> new", pure = true)
    static Builder builder() {
        return new PotionEffectsConditionImpl.BuilderImpl();
    }

    @Contract(pure = true)
    static GsonBuilderApplicable requiredGson() {
        return PotionEffectsConditionImpl.REQUIRED_GSON;
    }

    @Contract(pure = true)
    @Unmodifiable Map<PotionEffectType, PotionEffectInstanceCondition> effects();

    @ApiStatus.NonExtendable
    interface Builder extends Condition.Builder<PotionEffectsCondition> {

        @Contract(pure = true)
        Map<PotionEffectType, PotionEffectInstanceCondition> effects();

        @Contract(value = "_ -> this", mutates = "this")
        Builder addEffectType(PotionEffectType type);

        @Contract(value = "_, _ -> this", mutates = "this")
        Builder addEffectType(PotionEffectType type, PotionEffectInstanceCondition condition);

        @Contract(value = "_ -> this", mutates = "this")
        Builder addEffect(PotionEffect effect);

        @Contract(value = "_ -> new", mutates = "this")
        Builder effects(Map<PotionEffectType, PotionEffectInstanceCondition> effects);
    }
}
