package me.machinemaker.advancements.conditions.block;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.conditions.ConditionTest;
import me.machinemaker.advancements.tests.random.RandomProviders;
import me.machinemaker.advancements.tests.sources.RandomItemSource;
import me.machinemaker.advancements.tests.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class FluidConditionTest extends ConditionTest<FluidCondition> {

    FluidConditionTest() {
        super(FluidCondition.conditionType());
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testFluidCondition(final FluidCondition condition) {
        final JsonObject obj = new JsonObject();
        this.add(obj, "tag", condition.tag());
        this.add(obj, "fluid", condition.fluid());
        this.add(obj, "state", condition.state());
        this.testJsonConversion(condition, obj);
    }

    private static final class Provider extends RandomItemSource<FluidCondition> {

        Provider() {
            super(RandomProviders.FLUID_CONDITION);
        }
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject obj = new JsonObject();
        obj.add("fluid", JsonNull.INSTANCE);
        this.testIsAny(obj);
        this.testIsAny("{ \"tag\": null }");
        this.testIsAny("{ \"fluid\": null }");
    }
}
