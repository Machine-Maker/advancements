package me.machinemaker.advancements.adapters.maps;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.papermc.paper.statistics.Statistic;
import io.papermc.paper.statistics.StatisticType;
import me.machinemaker.advancements.GsonHelper;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.ranges.IntegerRange;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class StatisticMapAdapter extends TypeAdapter<Map<Statistic<?>, IntegerRange>> {

    private static final GsonHelper HELPER = new GsonHelper(Adapters.of(Adapters.NAMESPACED_KEY_ADAPTER, Adapters.STATISTIC_TYPE_ADAPTER));

    @Override
    public void write(JsonWriter out, Map<Statistic<?>, IntegerRange> value) throws IOException {
        if (!value.isEmpty()) {
            JsonArray array = new JsonArray();
            value.forEach((stat, range) -> {
                JsonObject obj = new JsonObject();
                obj.add("type", HELPER.toJsonTree(stat.getType()));
                obj.add("stat", HELPER.toJsonTree(stat.getStat().getKey()));
                obj.add("value", HELPER.toJsonTree(range));
                array.add(obj);
            });
            HELPER.toWriter(out, array);
        }
    }

    @Override
    public Map<Statistic<?>, IntegerRange> read(JsonReader in) throws IOException {
        final Map<Statistic<?>, IntegerRange> stats = new LinkedHashMap<>();
        if (in.peek() == JsonToken.NULL) {
            return stats;
        } else if (in.peek() != JsonToken.BEGIN_ARRAY) {
            throw new JsonParseException("Expected array, got " + in.peek());
        } else {
            JsonArray array = HELPER.arrayFromReader(in);
            for (JsonElement element : array) {
                JsonObject statsObj = element.getAsJsonObject();
                StatisticType<?> statType = HELPER.getAs(statsObj, "type", StatisticType.class);
                NamespacedKey stat = HELPER.getAs(statsObj, "stat", NamespacedKey.class);
                Statistic<?> statistic = getStatistic(statType, stat);
                IntegerRange intRange = HELPER.getAs(statsObj, "value", IntegerRange.class);
                stats.put(statistic, intRange);
            }
            return stats;
        }
    }

    private static <S extends Keyed> Statistic<S> getStatistic(StatisticType<S> statisticType, NamespacedKey statKey) {
        S statSubject = statisticType.getRegistry().get(statKey);
        if (statSubject == null) {
            throw new JsonParseException(statKey + " is not known to stat type: " + statisticType);
        }
        return statisticType.of(statSubject);
    }
}
