package me.machinemaker.advancements.conditions.entity;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.GsonTestBase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FishingHookConditionTest extends GsonTestBase {

    @Test
    void testFishingHookCondition() {
        FishingHookCondition condition = new FishingHookCondition(true);
        JsonObject object = new JsonObject();
        object.addProperty("is_open_water", true);
        assertEquals(object, tree(condition));
        assertEquals(condition, fromJson(object, FishingHookCondition.class));
    }

    @Test
    void testAnyFishingHookCondition() {
        JsonObject object = new JsonObject();
        object.add("is_open_water", JsonNull.INSTANCE);
        anyTest(object, FishingHookCondition.class);
        anyTest("null", FishingHookCondition.class);
        anyTest("{ \"is_open_water\":  false }", FishingHookCondition.class);
    }
}
