package me.machinemaker.advancements.adapters;

import com.google.common.collect.Iterables;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import io.papermc.paper.statistic.CustomStatistic;
import io.papermc.paper.statistic.Statistic;
import io.papermc.paper.statistic.StatisticType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import me.machinemaker.advancements.GsonTestBase;
import me.machinemaker.advancements.adapters.maps.StatisticMapAdapter;
import me.machinemaker.advancements.ranges.IntegerRange;
import org.bukkit.Material;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StatisticMapAdapterTest extends GsonTestBase {

    private static final Type STATISTIC_MAP_TYPE = new TypeToken<Map<Statistic<?>, IntegerRange>>() {}.getType();

    @BeforeAll
    void beforeAll() {
        registerTypeAdapter(STATISTIC_MAP_TYPE, new StatisticMapAdapter());
    }
    
    @Test
    void testStatisticMap() {
        Map<Statistic<?>, IntegerRange> map = new HashMap<>();
        map.put(StatisticType.BLOCK_MINED.of(Material.DIAMOND_BLOCK), IntegerRange.isAtLeast(4));
        map.put(CustomStatistic.JUMP.statistic(), IntegerRange.isExactly(34));
        JsonArray array = fromJson("[{\"type\":\"minecraft:mined\",\"stat\":\"minecraft:diamond_block\",\"value\":{\"min\":4}}, {\"type\":\"minecraft:custom\",\"stat\":\"minecraft:jump\",\"value\":34}]", JsonArray.class);
        assertThat(array, containsInAnyOrder(Iterables.toArray((JsonArray) this.tree(map, STATISTIC_MAP_TYPE), JsonElement.class)));
        assertEquals(map, fromJson(array, STATISTIC_MAP_TYPE));
    }

    @Test
    void testInvalidStatisticsMap() {
        JsonArray array = fromJson("[{\"type\":\"minecraft:mined\",\"stat\":\"minecraft:not_a_block\",\"value\":{\"min\":4}},{\"type\":\"minecraft:custom\",\"stat\":\"minecraft:jump\",\"value\":34}]", JsonArray.class);
        assertThrows(JsonParseException.class, () -> fromJson(array, STATISTIC_MAP_TYPE));
        JsonArray array1 = fromJson("[{\"type\":\"minecraft:not_a_stat\",\"stat\":\"minecraft:diamond_block\",\"value\":{\"min\":4}}, {\"type\":\"minecraft:custom\",\"stat\":\"minecraft:jump\",\"value\":34}]", JsonArray.class);
        assertThrows(JsonParseException.class, () -> fromJson(array1, STATISTIC_MAP_TYPE));
    }
}
