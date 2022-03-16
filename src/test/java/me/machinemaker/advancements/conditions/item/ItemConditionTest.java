package me.machinemaker.advancements.conditions.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import io.papermc.paper.potion.Potion;
import me.machinemaker.advancements.MinecraftGsonTestBase;
import me.machinemaker.advancements.conditions.misc.NBTCondition;
import me.machinemaker.advancements.mocks.DummyEnchantments;
import me.machinemaker.advancements.mocks.DummyPotions;
import me.machinemaker.advancements.ranges.IntegerRange;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.enchantments.Enchantment;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemConditionTest extends MinecraftGsonTestBase {

    static {
        DummyEnchantments.setup();
        DummyPotions.setup();
    }

    @Test
    void testItemConditionTag() {
        ItemCondition condition = ItemCondition.forTag(Tag.FENCES);
        JsonObject object = new JsonObject();
        object.addProperty("tag", Tag.FENCES.getKey().toString());
        assertEquals(object, tree(condition));
        assertEquals(condition, fromJson(object, ItemCondition.class));
    }

    @Test
    void testItemConditionItems() {
        ItemCondition condition = new ItemCondition(null, Set.of(Material.BONE), IntegerRange.isExactly(30), IntegerRange.ANY, EnchantmentCondition.NONE, EnchantmentCondition.NONE, Potion.LEAPING, NBTCondition.ANY);
        JsonObject object = new JsonObject();
        JsonArray array = new JsonArray();
        array.add(Material.BONE.getKey().toString());
        object.add("items", array);
        object.addProperty("count", 30);
        object.addProperty("potion", Potion.LEAPING.getKey().toString());
        assertEquals(object, tree(condition));
        assertEquals(condition, fromJson(object, ItemCondition.class));
    }

    @Test
    void testItemConditionEnchantments() {
        EnchantmentCondition[] conditions = new EnchantmentCondition[1];
        conditions[0] = new EnchantmentCondition(Enchantment.ARROW_DAMAGE, IntegerRange.isExactly(2));
        ItemCondition condition = new ItemCondition(null, null, IntegerRange.ANY, IntegerRange.ANY, conditions, EnchantmentCondition.NONE, null, NBTCondition.ANY);
        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();
        JsonObject ench1 = new JsonObject();
        ench1.addProperty("enchantment", "minecraft:power");
        ench1.addProperty("level", 2);
        array.add(ench1);
        obj.add("enchantments", array);
        assertEquals(obj, tree(condition));
        assertEquals(condition, fromJson(obj, ItemCondition.class));
    }

    // TODO more tests probably

    @Test
    void testAnyItemCondition() {
        JsonObject object = new JsonObject();
        object.add("items", JsonNull.INSTANCE);
        object.add("count", JsonNull.INSTANCE);
        anyTest(object, ItemCondition.class);
        anyTest("null", ItemCondition.class);
        anyTest("{ \"enchantments\": null, \"potion\": null, \"count\": { \"min\":  null } }", ItemCondition.class);
    }
}
