package me.machinemaker.datapacks.advancements.conditions.effect;

import com.google.gson.JsonObject;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemSource;
import me.machinemaker.datapacks.testing.sources.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class PotionEffectsConditionTest extends ConditionTest<PotionEffectsCondition> {

    PotionEffectsConditionTest() {
        super(PotionEffectsCondition.conditionType());
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testPotionEffectsCondition(final PotionEffectsCondition condition) {
        final JsonObject obj = new JsonObject();
        condition.effects().forEach((type, instanceCondition) -> {
            obj.add(type.key().asString(), this.toTree(instanceCondition));
        });
        this.testJsonConversion(condition, obj);
    }

    private static final class Provider extends RandomItemSource<PotionEffectsCondition> {

        Provider() {
            super(RandomProviders.POTION_EFFECTS_CONDITION);
        }
    }

    @Override
    protected void additionalAnyTests() {
        this.testIsAny("{}");
    }
}
