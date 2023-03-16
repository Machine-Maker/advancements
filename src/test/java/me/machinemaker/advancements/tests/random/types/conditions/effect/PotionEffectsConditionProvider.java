package me.machinemaker.advancements.tests.random.types.conditions.effect;

import me.machinemaker.advancements.conditions.effect.PotionEffectsCondition;
import me.machinemaker.advancements.tests.random.Provider;
import me.machinemaker.advancements.tests.random.RandomProviders;

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
