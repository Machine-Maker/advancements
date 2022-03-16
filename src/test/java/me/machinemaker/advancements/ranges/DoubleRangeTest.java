package me.machinemaker.advancements.ranges;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import me.machinemaker.advancements.GsonTestBase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.ThreadLocalRandom;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

class DoubleRangeTest extends GsonTestBase {

    @DoubleTest
    void testIsExactly(double testDouble) {
        DoubleRange range = DoubleRange.isExactly(testDouble);
        JsonElement element = new JsonPrimitive(testDouble);
        assertThat(GSON.toJson(range), equalTo(GSON.toJson(element)));

        JsonObject obj = new JsonObject();
        obj.addProperty("min", testDouble);
        obj.addProperty("max", testDouble);
        assertThat(GSON.fromJson(obj, DoubleRange.class), equalTo(range));
    }

    @DoubleTest
    void testIsBetween(double testDouble) {
        double otherTestDouble = testDouble + ThreadLocalRandom.current().nextDouble();
        DoubleRange range = DoubleRange.isBetween(testDouble, otherTestDouble);
        JsonObject obj = new JsonObject();
        obj.addProperty("min", testDouble);
        obj.addProperty("max", otherTestDouble);
        assertThat(GSON.toJson(range), equalTo(GSON.toJson(obj)));
        assertThat(GSON.fromJson(obj, DoubleRange.class), equalTo(range));
    }

    @DoubleTest
    void testIsAtLeast(double testDouble) {
        DoubleRange range = DoubleRange.isAtLeast(testDouble);
        JsonObject obj = new JsonObject();
        obj.addProperty("min", testDouble);
        assertThat(GSON.toJson(range), equalTo(GSON.toJson(obj)));
        assertThat(GSON.fromJson(obj, DoubleRange.class), equalTo(range));
    }

    @DoubleTest
    void testIsAtMost(double testDouble) {
        DoubleRange range = DoubleRange.isAtMost(testDouble);
        JsonObject obj = new JsonObject();
        obj.addProperty("max", testDouble);
        assertThat(GSON.toJson(range), equalTo(GSON.toJson(obj)));
        assertThat(GSON.fromJson(obj, DoubleRange.class), equalTo(range));
    }

    @Test
    void testAnyRange() {
        JsonObject obj = new JsonObject();
        obj.add("min", JsonNull.INSTANCE);
        anyTest(obj, DoubleRange.class);
    }

    @ParameterizedTest
    @ValueSource(doubles = {2.5, -65.32})
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface DoubleTest { }
}
