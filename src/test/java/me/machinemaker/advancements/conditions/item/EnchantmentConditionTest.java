package me.machinemaker.advancements.conditions.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.GsonTestBase;
import me.machinemaker.advancements.mocks.DummyEnchantments;
import me.machinemaker.advancements.ranges.IntegerRange;
import org.bukkit.enchantments.Enchantment;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EnchantmentConditionTest extends GsonTestBase {

    static {
        DummyEnchantments.setup();
    }

    @Test
    void testSingleEnchantmentCondition() {
        EnchantmentCondition condition = new EnchantmentCondition(Enchantment.ARROW_DAMAGE, IntegerRange.isExactly(2));
        JsonObject object = new JsonObject();
        object.addProperty("enchantment", Enchantment.ARROW_DAMAGE.getKey().toString());
        object.addProperty("level", 2);
        assertEquals(object, tree(condition));
        assertEquals(condition, fromJson(object, EnchantmentCondition.class));
    }

    @Test
    void testMultipleEnchantmentConditions() {
        EnchantmentCondition condition1 = new EnchantmentCondition(Enchantment.DAMAGE_ALL, IntegerRange.isAtLeast(2));
        EnchantmentCondition condition2 = new EnchantmentCondition(null, IntegerRange.isBetween(1, 3));
        JsonArray array = new JsonArray();
        JsonObject object1 = new JsonObject();
        object1.addProperty("enchantment", Enchantment.DAMAGE_ALL.getKey().toString());
        JsonObject object1Level = new JsonObject();
        object1Level.addProperty("min", 2);
        object1.add("level", object1Level);
        array.add(object1);
        JsonObject object2 = new JsonObject();
        JsonObject object2Level = new JsonObject();
        object2Level.addProperty("min", 1);
        object2Level.addProperty("max", 3);
        object2.add("level", object2Level);
        array.add(object2);
        EnchantmentCondition[] conditions = new EnchantmentCondition[]{condition1, condition2};
        assertEquals(array, tree(conditions));
        assertArrayEquals(conditions, fromJson(array, EnchantmentCondition[].class));
    }

    @Test
    void testAnySingleEnchantmentCondition() {
        JsonObject object = new JsonObject();
        object.add("enchantment", JsonNull.INSTANCE);
        anyTest(object, EnchantmentCondition.class);
        anyTest("null", EnchantmentCondition.class);
        anyTest("{ \"enchantment\": null }", EnchantmentCondition.class);
        anyTest("{ \"level\": { \"min\": null } }", EnchantmentCondition.class);
    }

    @Test
    void testAnyMultipleEnchantmentConditions() {
        JsonArray array = new JsonArray();
        array.add(JsonNull.INSTANCE);
        JsonObject object = new JsonObject();
        object.add("levels", JsonNull.INSTANCE);
        array.add(object);
        Predicate<EnchantmentCondition[]> predicate = conditions -> Arrays.stream(conditions).allMatch(Predicate.isEqual(EnchantmentCondition.ANY));
        test(array, EnchantmentCondition[].class, predicate);
        test("[ null, null, null ]", EnchantmentCondition[].class, predicate);
        test("[ {}, null ]", EnchantmentCondition[].class, predicate);
        test("[ null, { \"level\": null } ]", EnchantmentCondition[].class, predicate);
    }
}
