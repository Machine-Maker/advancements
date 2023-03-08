package me.machinemaker.advancements.conditions.world;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.conditions.ConditionTest;
import me.machinemaker.advancements.ranges.DoubleRange;
import me.machinemaker.advancements.tests.providers.CompoundProvider;
import me.machinemaker.advancements.tests.providers.RangeProviders;

class DistanceConditionTest extends ConditionTest<DistanceCondition> {

    DistanceConditionTest() {
        super(DistanceCondition.conditionType());
    }

    @CompoundProvider({RangeProviders.Doubles.Provider.class, RangeProviders.Doubles.Provider.class, RangeProviders.Doubles.Provider.class})
    void testDistanceConditionPosition(final DoubleRange x, final DoubleRange y, final DoubleRange z) {
        final DistanceCondition.Builder builder = DistanceCondition.builder();
        final JsonObject obj = new JsonObject();
        builder.x(x);
        obj.add("x", this.toTree(x));
        builder.y(y);
        obj.add("y", this.toTree(y));
        builder.z(z);
        obj.add("z", this.toTree(z));
        this.testJsonConversion(builder.build(), obj);
    }

    @CompoundProvider({RangeProviders.Doubles.Provider.class, RangeProviders.Doubles.Provider.class})
    void testDistanceConditionPosAndAbsolute(final DoubleRange z, final DoubleRange absolute) {
        final DistanceCondition.Builder builder = DistanceCondition.builder();
        final JsonObject obj = new JsonObject();
        builder.z(z);
        obj.add("z", this.toTree(z));
        builder.absolute(absolute);
        obj.add("absolute", this.toTree(absolute));
        this.testJsonConversion(builder.build(), obj);
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
