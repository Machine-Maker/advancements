package me.machinemaker.advancements.conditions.entity;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.GsonTestBase;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityFlagsConditionTest extends GsonTestBase {

    @Test
    void testEntityFlagsConditionSingle() {
        EntityFlagsCondition condition = new EntityFlagsCondition(true, null, null, null, null);
        JsonObject object = new JsonObject();
        object.addProperty("is_on_fire", true);
        assertEquals(object, tree(condition));
        assertEquals(condition, fromJson(object, EntityFlagsCondition.class));
    }

    @Test
    void testEntityFlagsConditionMultiple() {
        EntityFlagsCondition condition = new EntityFlagsCondition(false, null, true, null, true);
        JsonObject object = new JsonObject();
        object.addProperty("is_on_fire", false);
        object.addProperty("is_sprinting", true);
        object.addProperty("is_baby", true);
        assertEquals(object, tree(condition));
        assertEquals(condition, fromJson(object, EntityFlagsCondition.class));
    }

    @Test
    void testAnyEntityFlagsCondition() {
        JsonObject object = new JsonObject();
        object.add("is_on_fire", JsonNull.INSTANCE);
        object.add("is_baby", JsonNull.INSTANCE);
        anyTest(object, EntityFlagsCondition.class);
        anyTest("null", EntityFlagsCondition.class);
        anyTest("{\"is_crouching\": null }", EntityFlagsCondition.class);
        anyTest("{\"is_swimming\": null, \"is_on_fire\": null }", EntityFlagsCondition.class);
    }
}
