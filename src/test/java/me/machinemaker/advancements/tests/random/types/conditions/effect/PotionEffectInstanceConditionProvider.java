package me.machinemaker.advancements.tests.random.types.conditions.effect;

import me.machinemaker.advancements.conditions.effect.PotionEffectInstanceCondition;
import me.machinemaker.advancements.tests.random.RandomProviders;
import me.machinemaker.advancements.tests.random.types.conditions.ConditionProvider;

public class PotionEffectInstanceConditionProvider extends ConditionProvider<PotionEffectInstanceCondition, PotionEffectInstanceCondition.Builder> {

    public PotionEffectInstanceConditionProvider() {
        super(PotionEffectInstanceCondition::builder);
        this.component(() -> RandomProviders.INTEGER_RANGE, PotionEffectInstanceCondition.Builder::amplifier);
        this.component(() -> RandomProviders.INTEGER_RANGE, PotionEffectInstanceCondition.Builder::duration);
        this.component(() -> RandomProviders.BOOLEAN, PotionEffectInstanceCondition.Builder::ambient);
        this.component(() -> RandomProviders.BOOLEAN, PotionEffectInstanceCondition.Builder::visible);
    }
}
