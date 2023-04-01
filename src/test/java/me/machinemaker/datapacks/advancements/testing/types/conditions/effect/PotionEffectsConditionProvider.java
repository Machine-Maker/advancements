package me.machinemaker.datapacks.advancements.testing.types.conditions.effect;

import me.machinemaker.datapacks.advancements.conditions.effect.PotionEffectsCondition;
import me.machinemaker.datapacks.advancements.testing.Provider;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;

public class PotionEffectsConditionProvider implements Provider<PotionEffectsCondition> {

    @Override
    public PotionEffectsCondition get() {
        final PotionEffectsCondition.Builder builder = PotionEffectsCondition.builder();
        for (int i = 0; i < this.integer(1, 5); i++) {
            if (this.integer(100) < 25) {
                builder.addEffectType(RandomProviders.POTION_EFFECT_TYPE.get());
            } else {
                builder.addEffectType(RandomProviders.POTION_EFFECT_TYPE.get(), RandomProviders.POTION_EFFECT_INSTANCE_CONDITION.get());
            }
        }
        return builder.build();
    }
}
