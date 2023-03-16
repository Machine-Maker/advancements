package me.machinemaker.advancements.conditions.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import me.machinemaker.advancements.GsonTestBase;
import me.machinemaker.advancements.conditions.entity.sub.AdvancementCondition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdvancementConditionTest extends GsonTestBase {

    @Test
    void testAdvancementDoneCondition() {
        AdvancementCondition condition = AdvancementCondition.done(true);
        JsonElement element = new JsonPrimitive(true);
        assertEquals(element, tree(condition));
        assertEquals(condition, fromJson(element, AdvancementCondition.class));
    }

    @Test
    void testAdvancementCriteriaCondition() {
        Object2BooleanMap<String> criteria = new Object2BooleanOpenHashMap<>();
        criteria.put("test_criteria", true);
        criteria.put("other_criteria", false);
        AdvancementCondition condition = AdvancementCondition.criteria(criteria);
        JsonObject element = new JsonObject();
        element.addProperty("test_criteria", true);
        element.addProperty("other_criteria", false);
        assertEquals(element, tree(condition));
        assertEquals(condition, fromJson(element, AdvancementCondition.class));
    }
}
