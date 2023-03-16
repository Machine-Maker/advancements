package me.machinemaker.advancements.conditions.effect;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.conditions.ConditionTest;
import me.machinemaker.advancements.tests.random.RandomProviders;
import me.machinemaker.advancements.tests.sources.RandomItemSource;
import me.machinemaker.advancements.tests.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class PotionEffectInstanceConditionTest extends ConditionTest<PotionEffectInstanceCondition> {

    PotionEffectInstanceConditionTest() {
        super(PotionEffectInstanceCondition.conditionType());
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testPotionEffectInstanceCondition(final PotionEffectInstanceCondition condition) {
        final JsonObject obj = new JsonObject();
        this.add(obj, "amplifier", condition.amplifier());
        this.add(obj, "duration", condition.duration());
        this.add(obj, "ambient", condition.ambient());
        this.add(obj, "visible", condition.visible());
    }

    private static final class Provider extends RandomItemSource<PotionEffectInstanceCondition> {

        Provider() {
            super(RandomProviders.POTION_EFFECT_INSTANCE_CONDITION);
        }
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject obj = new JsonObject();
        obj.add("duration", JsonNull.INSTANCE);
        this.testIsAny(obj);
        this.testIsAny("{ \"amplifier\": null }");
        this.testIsAny("{ \"visible\": null, \"duration\": {\"min\": null }}");
    }
}
