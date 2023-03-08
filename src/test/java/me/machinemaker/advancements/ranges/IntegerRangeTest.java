package me.machinemaker.advancements.ranges;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.ThreadLocalRandom;
import me.machinemaker.advancements.GsonTestBase;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegerRangeTest extends GsonTestBase {

    @Override
    protected GsonBuilderApplicable applicable() {
        return IntegerRange.requiredGson();
    }

    @IntegerTest
    void testIsExactly(final int testInteger) {
        final IntegerRange range = IntegerRange.isExactly(testInteger);
        final JsonElement element = new JsonPrimitive(testInteger);
        assertEquals(this.gson.toJson(element), this.gson.toJson(range));

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
        assertEquals(this.gson.toJson(obj), this.gson.toJson(range));
        assertEquals(range, this.gson.fromJson(obj, IntegerRange.class));
    }

    @IntegerTest
    void testIsAtLeast(final int testInteger) {
        final IntegerRange range = IntegerRange.isAtLeast(testInteger);
        final JsonObject obj = new JsonObject();
        obj.addProperty("min", testInteger);
        assertEquals(this.gson.toJson(obj), this.gson.toJson(range));
        assertEquals(range, this.gson.fromJson(obj, IntegerRange.class));
    }

    @IntegerTest
    void testIsAtMost(final int testInteger) {
        final IntegerRange range = IntegerRange.isAtMost(testInteger);
        final JsonObject obj = new JsonObject();
        obj.addProperty("max", testInteger);
        assertEquals(this.gson.toJson(obj), this.gson.toJson(range));
        assertEquals(range, this.gson.fromJson(obj, IntegerRange.class));
    }

    @Test
    void testAnyRange() {
        final JsonObject obj = new JsonObject();
        obj.add("min", JsonNull.INSTANCE);
        this.anyTest(obj, IntegerRange.class);
        this.anyTest("null", IntegerRange.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {32, -6503})
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface IntegerTest {}
}
