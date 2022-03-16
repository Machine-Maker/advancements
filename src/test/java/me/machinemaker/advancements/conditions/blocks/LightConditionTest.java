package me.machinemaker.advancements.conditions.blocks;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.GsonTestBase;
import me.machinemaker.advancements.ranges.IntegerRange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LightConditionTest extends GsonTestBase {

    @ParameterizedTest
    @MethodSource("me.machinemaker.advancements.ranges.IntegerRangeTest#args")
    void testLightCondition(IntegerRange range) {
        LightCondition condition = new LightCondition(range);
        JsonObject obj = new JsonObject();
        obj.add("light", tree(range));
        assertEquals(obj, tree(condition));
        assertEquals(condition, fromJson(obj, LightCondition.class));
    }

    @Test
    void testAnyLightCondition() {
        JsonObject obj = new JsonObject();
        obj.add("light", JsonNull.INSTANCE);
        anyTest(obj, LightCondition.class);
        anyTest("null", LightCondition.class);
        anyTest("{ \"light\": null }", LightCondition.class);
    }
}
