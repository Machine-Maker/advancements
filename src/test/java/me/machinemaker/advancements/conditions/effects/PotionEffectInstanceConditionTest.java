package me.machinemaker.advancements.conditions.effects;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.GsonTestBase;
import me.machinemaker.advancements.ranges.IntegerRange;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PotionEffectInstanceConditionTest extends GsonTestBase {

    @Test
    void testPotionEffectInstanceCondition() {
        PotionEffectInstanceCondition condition = new PotionEffectInstanceCondition(
                IntegerRange.isExactly(2),
                IntegerRange.isBetween(1, 3),
                null,
                false
        );
        JsonObject obj = new JsonObject();
        obj.addProperty("amplifier", 2);
        JsonObject duration = new JsonObject();
        duration.addProperty("min", 1);
        duration.addProperty("max", 3);
        obj.add("duration", duration);
        obj.addProperty("visible", false);
        assertEquals(obj, tree(condition));
        assertEquals(condition, fromJson(obj, PotionEffectInstanceCondition.class));
    }

    @Test
    void testAnyPotionEffectInstanceCondition() {
        JsonObject obj = new JsonObject();
        obj.add("duration", JsonNull.INSTANCE);
        anyTest(obj, PotionEffectInstanceCondition.class);
        anyTest("null", PotionEffectInstanceCondition.class);
        anyTest("{ \"amplifier\": null }", PotionEffectInstanceCondition.class);
        anyTest("{ \"visible\": null, \"duration\": {\"min\": null }}", PotionEffectInstanceCondition.class);
    }
}
