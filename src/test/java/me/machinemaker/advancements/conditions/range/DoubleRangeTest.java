package me.machinemaker.advancements.conditions.range;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.ThreadLocalRandom;
import me.machinemaker.advancements.conditions.ConditionTest;
import me.machinemaker.advancements.conditions.range.DoubleRange;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DoubleRangeTest extends ConditionTest<DoubleRange> {

    DoubleRangeTest() {
        super(DoubleRange.conditionType());
    }

    @DoubleTest
    void testIsExactly(final double testDouble) {
        final DoubleRange range = DoubleRange.isExactly(testDouble);
        final JsonElement element = new JsonPrimitive(testDouble);
        this.testJsonConversion(range, element);

        final JsonObject obj = new JsonObject();
        obj.addProperty("min", testDouble);
        obj.addProperty("max", testDouble);
        assertEquals(range, this.fromTree(obj));
    }

    @DoubleTest
    void testIsBetween(final double testDouble) {
        final double otherTestDouble = testDouble + ThreadLocalRandom.current().nextDouble();
        final DoubleRange range = DoubleRange.isBetween(testDouble, otherTestDouble);
        final JsonObject obj = new JsonObject();
        obj.addProperty("min", testDouble);
        obj.addProperty("max", otherTestDouble);
        this.testJsonConversion(range, obj);
    }

    @DoubleTest
    void testIsAtLeast(final double testDouble) {
        final DoubleRange range = DoubleRange.isAtLeast(testDouble);
        final JsonObject obj = new JsonObject();
        obj.addProperty("min", testDouble);
        this.testJsonConversion(range, obj);
    }

    @DoubleTest
    void testIsAtMost(final double testDouble) {
        final DoubleRange range = DoubleRange.isAtMost(testDouble);
        final JsonObject obj = new JsonObject();
        obj.addProperty("max", testDouble);
        this.testJsonConversion(range, obj);
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject obj = new JsonObject();
        obj.add("min", JsonNull.INSTANCE);
        this.testIsAny(obj);
    }

    @ParameterizedTest
    @ValueSource(doubles = {2.5, -65.32})
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface DoubleTest {}
}
