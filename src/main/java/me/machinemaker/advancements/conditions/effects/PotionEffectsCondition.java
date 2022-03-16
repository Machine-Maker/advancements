package me.machinemaker.advancements.conditions.effects;

import com.google.gson.annotations.JsonAdapter;
import me.machinemaker.advancements.adapters.maps.PotionEffectMapAdapter;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.util.Buildable;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Contract;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public record PotionEffectsCondition(@JsonAdapter(value = PotionEffectMapAdapter.class, nullSafe = false) Map<PotionEffectType, PotionEffectInstanceCondition> effects) implements Condition<PotionEffectsCondition>, Buildable<PotionEffectsCondition, PotionEffectsCondition.Builder> {

    public static final PotionEffectsCondition ANY = new PotionEffectsCondition(Collections.emptyMap());

    @Override
    public PotionEffectsCondition any() {
        return ANY;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "PotionEffectsCondition{ANY}";
        }
        return "PotionEffectsCondition{" +
                "effects=" + this.effects +
                '}';
    }

    @Contract(value = "-> new", pure = true)
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements Condition.Builder<PotionEffectsCondition> {

        private Map<PotionEffectType, PotionEffectInstanceCondition> effects = new HashMap<>();

        private Builder() {
        }

        private Builder(PotionEffectsCondition condition) {
            this.effects = new HashMap<>(condition.effects);
        }

        public Map<PotionEffectType, PotionEffectInstanceCondition> effects() {
            return this.effects;
        }

        @Contract(mutates = "this")
        public Builder effects(Map<PotionEffectType, PotionEffectInstanceCondition> effects) {
            this.effects = effects;
            return this;
        }

        @Override
        public PotionEffectsCondition build() {
            return new PotionEffectsCondition(this.effects);
        }
    }
}
