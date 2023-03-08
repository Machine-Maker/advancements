package me.machinemaker.advancements.adapters.maps;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.papermc.paper.statistic.Statistic;
import io.papermc.paper.statistic.StatisticType;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import me.machinemaker.advancements.GsonHelper;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.builders.Builders;
import me.machinemaker.advancements.ranges.IntegerRange;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;

public class StatisticMapAdapter extends TypeAdapter<Map<Statistic<?>, IntegerRange>> {

    private static final GsonHelper HELPER = new GsonHelper(Builders.collection(Adapters.STATISTIC_TYPE_ADAPTER));

    private static <S extends Keyed> Statistic<S> getStatistic(final StatisticType<S> statisticType, final NamespacedKey statKey) {
        final S statSubject = statisticType.registry().get(statKey);
        if (statSubject == null) {
            throw new JsonParseException(statKey + " is not known to stat type: " + statisticType);
        }
        return statisticType.of(statSubject);
    }

    @Override
    public void write(final JsonWriter out, final Map<Statistic<?>, IntegerRange> value) throws IOException {
        if (!value.isEmpty()) {
            final JsonArray array = new JsonArray();
            value.forEach((stat, range) -> {
                final JsonObject obj = new JsonObject();
                obj.add("type", HELPER.toJsonTree(stat.type()));
                obj.add("stat", HELPER.toJsonTree(stat.value().getKey()));
                obj.add("value", HELPER.toJsonTree(range));
                array.add(obj);
            });
            HELPER.toWriter(out, array);
        }
    }

    @Override
    public Map<Statistic<?>, IntegerRange> read(final JsonReader in) throws IOException {
        final Map<Statistic<?>, IntegerRange> stats = new LinkedHashMap<>();
        if (in.peek() == JsonToken.NULL) {
            return stats;
        } else if (in.peek() != JsonToken.BEGIN_ARRAY) {
            throw new JsonParseException("Expected array, got " + in.peek());
        } else {
            final JsonArray array = HELPER.arrayFromReader(in);
            for (final JsonElement element : array) {
                final JsonObject statsObj = element.getAsJsonObject();
                final StatisticType<?> statType = HELPER.getAs(statsObj, "type", StatisticType.class);
                final NamespacedKey stat = HELPER.getAs(statsObj, "stat", NamespacedKey.class);
                final Statistic<?> statistic = getStatistic(statType, stat);
                final IntegerRange intRange = HELPER.getAs(statsObj, "value", IntegerRange.class);
                stats.put(statistic, intRange);
            }
            return stats;
        }
    }
}
