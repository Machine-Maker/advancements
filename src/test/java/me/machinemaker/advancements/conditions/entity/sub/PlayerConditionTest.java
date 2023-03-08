package me.machinemaker.advancements.conditions.entity.sub;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import io.papermc.paper.statistic.Statistic;
import io.papermc.paper.statistic.StatisticType;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.HashMap;
import java.util.Map;
import me.machinemaker.advancements.GsonTestBase;
import me.machinemaker.advancements.conditions.entity.AdvancementCondition;
import me.machinemaker.advancements.conditions.entity.EntityCondition;
import me.machinemaker.advancements.conditions.entity.EntitySubCondition;
import me.machinemaker.advancements.conditions.entity.PlayerCondition;
import me.machinemaker.advancements.ranges.IntegerRange;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerConditionTest extends GsonTestBase {

    @Test
    void testPlayerCondition() {
        final Map<Statistic<?>, IntegerRange> statMap = new HashMap<>();
        statMap.put(StatisticType.BLOCK_MINED.of(Material.DIAMOND_BLOCK), IntegerRange.isAtLeast(4));
        final Object2BooleanMap<NamespacedKey> recipeMap = new Object2BooleanOpenHashMap<>();
        recipeMap.put(NamespacedKey.minecraft("oak_log"), true);
        final Map<NamespacedKey, AdvancementCondition> advancementsMap = new HashMap<>();
        advancementsMap.put(NamespacedKey.minecraft("story/root"), AdvancementCondition.done(true));
        final PlayerCondition condition = new PlayerCondition(
            IntegerRange.isExactly(3),
            GameMode.ADVENTURE,
            statMap,
            recipeMap,
            advancementsMap,
            EntityCondition.ANY
        );
        final JsonObject object = this.fromJson("{\"level\":3,\"gamemode\":\"adventure\",\"stats\":[{\"type\":\"minecraft:mined\",\"stat\":\"minecraft:diamond_block\",\"value\":{\"min\":4}}],\"recipes\":{\"minecraft:oak_log\":true},\"advancements\":{\"minecraft:story/root\":true}}", JsonObject.class);
        assertEquals(object, this.tree(condition));
        assertEquals(condition, this.fromJson(object, PlayerCondition.class));
    }

    @Test
    void testAnyPlayerCondition() {
        final JsonObject object = new JsonObject();
        object.add("gamemode", JsonNull.INSTANCE);
        object.add("recipes", JsonNull.INSTANCE);
        this.anyTest(object, PlayerCondition.class);
        this.anyTest("null", EntitySubCondition.class);
        this.anyTest("{ \"advancements\": null }", PlayerCondition.class);
    }
}