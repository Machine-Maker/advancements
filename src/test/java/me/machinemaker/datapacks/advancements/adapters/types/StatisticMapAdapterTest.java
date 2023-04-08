package me.machinemaker.datapacks.advancements.adapters.types;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.papermc.paper.statistic.Statistic;
import java.util.Map;
import me.machinemaker.datapacks.GsonTest;
import me.machinemaker.datapacks.advancements.adapters.types.StatisticMapAdapter;
import me.machinemaker.datapacks.common.range.IntegerRange;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemSource;
import me.machinemaker.datapacks.testing.sources.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class StatisticMapAdapterTest extends GsonTest {

    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testStatisticMap(final Map<Statistic<?>, IntegerRange> map) {
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

    private static final class Provider extends RandomItemSource<Map<Statistic<?>, IntegerRange>> {

        Provider() {
            super(RandomProviders.STATISTIC_MAP_PROVIDER);
        }
    }
}
