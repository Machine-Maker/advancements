package me.machinemaker.advancements.conditions.blocks;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.conditions.ConditionTest;
import me.machinemaker.advancements.ranges.IntegerRange;
import me.machinemaker.advancements.tests.providers.RangeProviders;

class LightConditionTest extends ConditionTest<LightCondition> {

    LightConditionTest() {
        super(LightCondition.conditionType());
    }

    @RangeProviders.Ints
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
}
