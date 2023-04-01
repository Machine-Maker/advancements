package me.machinemaker.datapacks.advancements.testing.types.conditions.effect;

import me.machinemaker.datapacks.advancements.conditions.effect.PotionEffectInstanceCondition;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.advancements.testing.types.conditions.ConditionProvider;

public class PotionEffectInstanceConditionProvider extends ConditionProvider<PotionEffectInstanceCondition, PotionEffectInstanceCondition.Builder> {

    public PotionEffectInstanceConditionProvider() {
        super(PotionEffectInstanceCondition::builder);
        this.component(() -> RandomProviders.INTEGER_RANGE, PotionEffectInstanceCondition.Builder::amplifier);
        this.component(() -> RandomProviders.INTEGER_RANGE, PotionEffectInstanceCondition.Builder::duration);
        this.component(() -> RandomProviders.BOOLEAN, PotionEffectInstanceCondition.Builder::ambient);
        this.component(() -> RandomProviders.BOOLEAN, PotionEffectInstanceCondition.Builder::visible);
    }
}
