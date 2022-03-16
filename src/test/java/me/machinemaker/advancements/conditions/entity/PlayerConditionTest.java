package me.machinemaker.advancements.conditions.entity;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import io.papermc.paper.statistics.Statistic;
import io.papermc.paper.statistics.StatisticType;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import me.machinemaker.advancements.MinecraftGsonTestBase;
import me.machinemaker.advancements.ranges.IntegerRange;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerConditionTest extends MinecraftGsonTestBase {

    @Test
    void testPlayerCondition() {
        Map<Statistic<?>, IntegerRange> statMap = new HashMap<>();
        statMap.put(StatisticType.BLOCK_MINED.of(Material.DIAMOND_BLOCK), IntegerRange.isAtLeast(4));
        Object2BooleanMap<NamespacedKey> recipeMap = new Object2BooleanOpenHashMap<>();
        recipeMap.put(NamespacedKey.minecraft("oak_log"), true);
        Map<NamespacedKey, AdvancementCondition> advancementsMap = new HashMap<>();
        advancementsMap.put(NamespacedKey.minecraft("story/root"), AdvancementCondition.done(true));
        PlayerCondition condition = new PlayerCondition(
                IntegerRange.isExactly(3),
                GameMode.ADVENTURE,
                statMap,
                recipeMap,
                advancementsMap,
                EntityCondition.ANY
        );
        JsonObject object = fromJson("{\"level\":3,\"gamemode\":\"adventure\",\"stats\":[{\"type\":\"minecraft:mined\",\"stat\":\"minecraft:diamond_block\",\"value\":{\"min\":4}}],\"recipes\":{\"minecraft:oak_log\":true},\"advancements\":{\"minecraft:story/root\":true}}", JsonObject.class);
        assertEquals(object, tree(condition));
        assertEquals(condition, fromJson(object, PlayerCondition.class));
    }

    @Test
    void testAnyPlayerCondition() {
        JsonObject object = new JsonObject();
        object.add("gamemode", JsonNull.INSTANCE);
        object.add("recipes", JsonNull.INSTANCE);
        anyTest(object, PlayerCondition.class);
        anyTest("null", PlayerCondition.class);
        anyTest("{ \"advancements\": null }", PlayerCondition.class);
    }
}
