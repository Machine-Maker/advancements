package me.machinemaker.advancements.conditions.blocks;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.MinecraftGsonTestBase;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BlockConditionTest extends MinecraftGsonTestBase {

    @Test
    void testBlockConditionWithBlocksFail() {
        assertThrows(IllegalArgumentException.class, () -> BlockCondition.forBlocks(Material.DIAMOND_AXE));
    }

    @Test
    void testBlockConditionWithBlocks() {
        BlockCondition condition = BlockCondition.forBlocks(Material.STONE);
        JsonObject obj = new JsonObject();
        JsonArray array = new JsonArray();
        array.add(Material.STONE.getKey().toString());
        obj.add("blocks", array);
        assertEquals(obj, tree(condition));
        assertEquals(condition, fromJson(obj, BlockCondition.class));
    }

    @Test
    void testBlockConditionWithBlockTag() {
        BlockCondition condition = BlockCondition.forTag(Tag.ACACIA_LOGS);
        JsonObject obj = new JsonObject();
        obj.addProperty("tag", Tag.ACACIA_LOGS.getKey().toString());
        assertEquals(obj, tree(condition));
        assertEquals(condition, fromJson(obj, BlockCondition.class));
    }

    // TODO block data condition test

    @Test
    void testAnyBlockCondition() {
        JsonObject obj = new JsonObject();
        obj.add("blocks", JsonNull.INSTANCE);
        anyTest(obj, BlockCondition.class);
        anyTest("null", BlockCondition.class);
        anyTest("{ \"blocks\": null }", BlockCondition.class);
        anyTest("{ \"tag\": null }", BlockCondition.class);
        anyTest("{ \"blocks\": null, \"nbt\": null }", BlockCondition.class);
    }

}
