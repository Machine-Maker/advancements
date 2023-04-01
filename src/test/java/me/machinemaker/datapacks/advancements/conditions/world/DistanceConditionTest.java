package me.machinemaker.datapacks.advancements.conditions.world;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemSource;
import me.machinemaker.datapacks.testing.sources.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class DistanceConditionTest extends ConditionTest<DistanceCondition> {

    DistanceConditionTest() {
        super(DistanceCondition.conditionType());
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testDistanceCondition(final DistanceCondition condition) {
        final JsonObject obj = new JsonObject();
        this.add(obj, "x", condition.x());
        this.add(obj, "y", condition.y());
        this.add(obj, "z", condition.z());
        this.add(obj, "horizontal", condition.horizontal());
        this.add(obj, "absolute", condition.absolute());
        this.testJsonConversion(condition, obj);
    }

    private static final class Provider extends RandomItemSource<DistanceCondition> {

        Provider() {
            super(RandomProviders.DISTANCE_CONDITION);
        }
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject obj = new JsonObject();
        obj.add("x", JsonNull.INSTANCE);
        obj.add("horizontal", JsonNull.INSTANCE);
        this.testIsAny(obj);
        this.testIsAny("{ \"z\": null }");
        this.testIsAny("{ \"absolute\": null }");
    }
}
