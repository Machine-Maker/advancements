package me.machinemaker.advancements.adapters.maps;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.papermc.paper.statistic.Statistic;
import java.util.LinkedHashMap;
import java.util.Map;
import me.machinemaker.advancements.GsonTest;
import me.machinemaker.advancements.conditions.range.IntegerRange;
import me.machinemaker.advancements.tests.random.RandomProviders;
import org.junit.jupiter.api.Test;

class StatisticMapAdapterTest extends GsonTest {

    StatisticMapAdapterTest() {
        super(StatisticMapAdapter.REQUIRED_GSON);
    }

    @Test
    void testStatisticMap() {
        final Map<Statistic<?>, IntegerRange> map = new LinkedHashMap<>();
        for (int i = 0; i < 5; i++) {
            map.put(RandomProviders.STATISTIC_PROVIDER.get(), RandomProviders.INTEGER_RANGE.get());
        }
        final JsonArray array = new JsonArray();
        map.forEach((statistic, range) -> {
            final JsonObject obj = new JsonObject();
            obj.add("type", this.toTree(statistic.type(), StatisticMapAdapter.STATISTIC_TYPE_TYPE_TOKEN.getType()));
            obj.add("stat", this.toTree(statistic.value()));
            obj.add("value", this.toTree(range));
            array.add(obj);
        });
        this.testJsonConversion(map, array, StatisticMapAdapter.STAT_MAP_TYPE_TOKEN.getType());
    }
}
