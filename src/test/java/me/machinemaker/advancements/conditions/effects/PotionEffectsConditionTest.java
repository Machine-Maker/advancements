package me.machinemaker.advancements.conditions.effects;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.GsonTestBase;
import me.machinemaker.advancements.mocks.DummyEffects;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PotionEffectsConditionTest extends GsonTestBase {

    static {
        DummyEffects.setup();
    }

    @Test
    void testPotionEffectsCondition() {
        PotionEffectsCondition condition = new PotionEffectsCondition(Map.of(
                PotionEffectType.ABSORPTION, PotionEffectInstanceCondition.fromPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 3, 10, false, true)),
                PotionEffectType.FAST_DIGGING, PotionEffectInstanceCondition.ANY
        ));
        JsonObject completeObject = fromJson("{\"effects\":{\"minecraft:haste\":{},\"minecraft:absorption\":{\"amplifier\":10,\"duration\":3,\"ambient\":false,\"visible\":true}}}", JsonObject.class);
        assertEquals(tree(condition), completeObject);
        JsonObject obj = new JsonObject();
        JsonObject effects = new JsonObject();
        JsonObject absorbtion = new JsonObject();
        absorbtion.addProperty("amplifier", 10);
        absorbtion.addProperty("duration", 3);
        absorbtion.addProperty("ambient", false);
        absorbtion.addProperty("visible", true);
        effects.add("minecraft:absorption", absorbtion);
        effects.add("minecraft:haste", new JsonObject());
        obj.add("effects", effects);
        assertEquals(obj, tree(condition));
        assertEquals(condition, fromJson(obj, PotionEffectsCondition.class));
    }

    @Test
    void testAnyPotionEffectsCondition() {
        JsonObject obj = new JsonObject();
        obj.add("effects", JsonNull.INSTANCE);
        anyTest(obj, PotionEffectsCondition.class);
        anyTest("null", PotionEffectsCondition.class);
        anyTest("{ \"effects\": null }", PotionEffectsCondition.class);
        anyTest("{ \"effects\": {} }", PotionEffectsCondition.class);
    }
}
