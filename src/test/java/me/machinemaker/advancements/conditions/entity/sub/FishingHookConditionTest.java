package me.machinemaker.advancements.conditions.entity.sub;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.GsonTestBase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FishingHookConditionTest extends GsonTestBase {

    @Test
    void testFishingHookCondition() {
        final FishingHookCondition condition = new FishingHookConditionImpl(true);
        final JsonObject object = new JsonObject();
        object.addProperty("is_open_water", true);
        assertEquals(object, this.tree(condition));
        assertEquals(condition, this.fromJson(object, FishingHookCondition.class));
    }

    @Test
    void testAnyFishingHookCondition() {
        final JsonObject object = new JsonObject();
        object.add("is_open_water", JsonNull.INSTANCE);
        this.anyTest(object, FishingHookCondition.class);
        this.anyTest("null", FishingHookCondition.class);
        this.anyTest("null", EntitySubCondition.class);
        this.anyTest("{ \"is_open_water\":  false }", FishingHookCondition.class);
    }
}
