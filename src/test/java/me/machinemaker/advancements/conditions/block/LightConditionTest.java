package me.machinemaker.advancements.conditions.block;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.conditions.ConditionTest;
import me.machinemaker.advancements.conditions.range.IntegerRange;
import me.machinemaker.advancements.tests.random.RandomProviders;
import me.machinemaker.advancements.tests.sources.RandomItemSource;
import me.machinemaker.advancements.tests.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class LightConditionTest extends ConditionTest<LightCondition> {

    LightConditionTest() {
        super(LightCondition.conditionType());
    }

    @Sources.Config(count = 50)
    @ArgumentsSource(Provider.class)
    void testLightCondition(final IntegerRange range) {
        final LightCondition condition = LightCondition.create(range);
        final JsonObject obj = new JsonObject();
        obj.add("light", this.toTree(range));
        this.testJsonConversion(condition, obj);
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject obj = new JsonObject();
        obj.add("light", JsonNull.INSTANCE);
        this.testIsAny(obj);
        this.testIsAny("{ \"light\": null }");
    }

    private static final class Provider extends RandomItemSource<IntegerRange> {

        Provider() {
            super(RandomProviders.INTEGER_RANGE);
        }
    }
}
