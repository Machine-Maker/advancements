package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import me.machinemaker.datapacks.GsonTest;
import org.junit.jupiter.api.Test;

class AdvancementConditionTest extends GsonTest {

    @Test
    void testAdvancementDoneCondition() {
        final AdvancementCondition condition = AdvancementCondition.done(true);
        final JsonElement element = new JsonPrimitive(true);
        this.testJsonConversion(condition, element, AdvancementCondition.class);
    }

    @Test
    void testAdvancementCriteriaCondition() {
        final Object2BooleanMap<String> criteria = new Object2BooleanOpenHashMap<>();
        criteria.put("test_criteria", true);
        criteria.put("other_criteria", false);
        final AdvancementCondition condition = AdvancementCondition.criteria(criteria);
        final JsonObject obj = new JsonObject();
        obj.addProperty("test_criteria", true);
        obj.addProperty("other_criteria", false);
        this.testJsonConversion(condition, obj, AdvancementCondition.class);
    }
}
