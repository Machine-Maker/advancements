package me.machinemaker.datapacks.advancements.conditions.range;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.ThreadLocalRandom;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegerRangeTest extends ConditionTest<IntegerRange> {

    IntegerRangeTest() {
        super(IntegerRange.conditionType());
    }

    @IntegerTest
    void testIsExactly(final int testInteger) {
        final IntegerRange range = IntegerRange.isExactly(testInteger);
        final JsonElement element = new JsonPrimitive(testInteger);
        this.testJsonConversion(range, element);
    }

    @IntegerTest
    void testIsExactlyDeserialization(final int testInteger) {
        final IntegerRange range = IntegerRange.isExactly(testInteger);
        final JsonObject obj = new JsonObject();
        obj.addProperty("min", testInteger);
        obj.addProperty("max", testInteger);
        assertEquals(range, this.gson.fromJson(obj, IntegerRange.class));
    }

    @IntegerTest
    void testIsBetween(final int testInteger) {
        final int otherTestInteger = testInteger + ThreadLocalRandom.current().nextInt();
        final IntegerRange range = IntegerRange.isBetween(testInteger, otherTestInteger);
        final JsonObject obj = new JsonObject();
        obj.addProperty("min", testInteger);
        obj.addProperty("max", otherTestInteger);
        this.testJsonConversion(range, obj);
    }

    @IntegerTest
    void testIsAtLeast(final int testInteger) {
        final IntegerRange range = IntegerRange.isAtLeast(testInteger);
        final JsonObject obj = new JsonObject();
        obj.addProperty("min", testInteger);
        this.testJsonConversion(range, obj);
    }

    @IntegerTest
    void testIsAtMost(final int testInteger) {
        final IntegerRange range = IntegerRange.isAtMost(testInteger);
        final JsonObject obj = new JsonObject();
        obj.addProperty("max", testInteger);
        this.testJsonConversion(range, obj);
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject obj = new JsonObject();
        obj.add("min", JsonNull.INSTANCE);
        this.testIsAny(obj);
    }

    @ParameterizedTest
    @ValueSource(ints = {32, -6503})
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface IntegerTest {}
}
