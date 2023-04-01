package me.machinemaker.datapacks.advancements.conditions.world;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemSource;
import me.machinemaker.datapacks.testing.sources.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class LocationConditionTest extends ConditionTest<LocationCondition> {

    LocationConditionTest() {
        super(LocationCondition.conditionType());
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testLocationCondition(final LocationCondition condition) {
        final JsonObject obj = new JsonObject();
        final JsonObject position = new JsonObject();
        this.add(position, "x", condition.x());
        this.add(position, "y", condition.y());
        this.add(position, "z", condition.z());
        if (position.size() > 0) {
            obj.add("position", position);
        }
        this.add(obj, "biome", condition.biome());
        this.add(obj, "structure", condition.structure());
        this.add(obj, "dimension", condition.dimension());
        this.add(obj, "smokey", condition.smokey());
        this.add(obj, "light", condition.light());
        this.add(obj, "block", condition.block());
        this.add(obj, "fluid", condition.fluid());
        this.testJsonConversion(condition, obj);
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject obj = new JsonObject();
        obj.add("position", JsonNull.INSTANCE);
        this.testIsAny(obj);
        this.testIsAny("{ \"position\": { \"x\": null }, \"block\": null }");
    }

    private static class Provider extends RandomItemSource<LocationCondition> {

        Provider() {
            super(RandomProviders.LOCATION_CONDITION);
        }
    }
}
