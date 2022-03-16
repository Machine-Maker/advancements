package me.machinemaker.advancements.ranges;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.machinemaker.advancements.GsonTestBase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class IntegerRangeTest extends GsonTestBase {

    @IntegerTest
    void testIsExactly(int testInteger) {
        IntegerRange range = IntegerRange.isExactly(testInteger);
        JsonElement element = new JsonPrimitive(testInteger);
        assertEquals(GSON.toJson(element), GSON.toJson(range));

        JsonObject obj = new JsonObject();
        obj.addProperty("min", testInteger);
        obj.addProperty("max", testInteger);
        assertEquals(range, GSON.fromJson(obj, IntegerRange.class));
    }

    @IntegerTest
    void testIsBetween(int testInteger) {
        int otherTestInteger = testInteger + ThreadLocalRandom.current().nextInt();
        IntegerRange range = IntegerRange.isBetween(testInteger, otherTestInteger);
        JsonObject obj = new JsonObject();
        obj.addProperty("min", testInteger);
        obj.addProperty("max", otherTestInteger);
        assertEquals(GSON.toJson(obj), GSON.toJson(range));
        assertEquals(range, GSON.fromJson(obj, IntegerRange.class));
    }

    @IntegerTest
    void testIsAtLeast(int testInteger) {
        IntegerRange range = IntegerRange.isAtLeast(testInteger);
        JsonObject obj = new JsonObject();
        obj.addProperty("min", testInteger);
        assertEquals(GSON.toJson(obj), GSON.toJson(range));
        assertEquals(range, GSON.fromJson(obj, IntegerRange.class));
    }

    @IntegerTest
    void testIsAtMost(int testInteger) {
        IntegerRange range = IntegerRange.isAtMost(testInteger);
        JsonObject obj = new JsonObject();
        obj.addProperty("max", testInteger);
        assertEquals(GSON.toJson(obj), GSON.toJson(range));
        assertEquals(range, GSON.fromJson(obj, IntegerRange.class));
    }

    static Stream<Arguments> args() {
        return IntStream.range(0, 4).mapToObj(IntegerRangeTest::createRandomIntegerRange).map(Arguments::of);
    }

    private static IntegerRange createRandomIntegerRange(int i) {
        int x = ThreadLocalRandom.current().nextInt();
        if (i % 4 == 3) {
            int y = ThreadLocalRandom.current().nextInt();
            return IntegerRange.isBetween(Math.min(x, y), Math.max(x, y));
        } else if (i % 4 == 2) {
            return IntegerRange.isAtLeast(x);
        } else if (i % 4 == 1) {
            return IntegerRange.isAtMost(x);
        } else {
            return IntegerRange.isExactly(x);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {32, -6503})
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface IntegerTest { }
}
