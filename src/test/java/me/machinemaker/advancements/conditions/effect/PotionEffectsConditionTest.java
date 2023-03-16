package me.machinemaker.advancements.conditions.effect;

import com.google.gson.JsonObject;
import me.machinemaker.advancements.conditions.ConditionTest;
import me.machinemaker.advancements.tests.mocks.DummyEffects;
import me.machinemaker.advancements.tests.random.RandomProviders;
import me.machinemaker.advancements.tests.sources.RandomItemSource;
import me.machinemaker.advancements.tests.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class PotionEffectsConditionTest extends ConditionTest<PotionEffectsCondition> {

    static {
        DummyEffects.setup();
    }

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
