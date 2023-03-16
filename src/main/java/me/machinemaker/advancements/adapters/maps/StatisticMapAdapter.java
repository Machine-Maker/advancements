package me.machinemaker.advancements.adapters.maps;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.papermc.paper.statistic.Statistic;
import io.papermc.paper.statistic.StatisticType;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.builders.Builders;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.range.IntegerRange;
import net.kyori.adventure.key.Key;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.checkerframework.checker.nullness.qual.Nullable;

import static java.util.Objects.requireNonNull;
import static me.machinemaker.advancements.util.GsonUtils.deserializeFrom;

public class StatisticMapAdapter extends TypeAdapter<Map<Statistic<?>, IntegerRange>> {

    private static final Gson GSON;
    static final TypeToken<StatisticType<?>> STATISTIC_TYPE_TYPE_TOKEN = new TypeToken<StatisticType<?>>() {};
    static final TypeToken<Map<Statistic<?>, IntegerRange>> STAT_MAP_TYPE_TOKEN = new TypeToken<Map<Statistic<?>, IntegerRange>>() {};
    static final GsonBuilderApplicable REQUIRED_GSON = Builders.collection(
        Builders.type(STAT_MAP_TYPE_TOKEN, new StatisticMapAdapter()),
        Adapters.STATISTIC_TYPE_ADAPTER,
        IntegerRange.requiredGson(),
        Adapters.MATERIAL_ADAPTER,
        Adapters.KEY_ADAPTER,
        Adapters.ENTITY_TYPE_ADAPTER,
        Adapters.CUSTOM_STAT_TYPE_ADAPTER
    );
    static {
        final GsonBuilder builder = new GsonBuilder();
        REQUIRED_GSON.applyTo(builder);
        GSON = builder.create();
    }

    private static <S extends Keyed> Statistic<S> getStatistic(final StatisticType<S> statisticType, final Key statKey) {
        final @Nullable S statSubject = statisticType.registry().get(statKey instanceof final NamespacedKey ns ? ns : new NamespacedKey(statKey.namespace(), statKey.value()));
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
                obj.add("type", GSON.toJsonTree(stat.type(), STATISTIC_TYPE_TYPE_TOKEN.getType()));
                obj.add("stat", GSON.toJsonTree(stat.value().key()));
                obj.add("value", GSON.toJsonTree(range));
                array.add(obj);
            });
            Streams.write(array, out);
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
            final JsonArray array = Streams.parse(in).getAsJsonArray();
            for (final JsonElement element : array) {
                final JsonObject statsObj = element.getAsJsonObject();
                final StatisticType<?> statType = requireNonNull(deserializeFrom(GSON, statsObj, "type", STATISTIC_TYPE_TYPE_TOKEN.getType()));
                final Key stat = requireNonNull(deserializeFrom(GSON, statsObj, "stat", Key.class));
                final Statistic<?> statistic = getStatistic(statType, stat);
                final IntegerRange intRange = requireNonNull(deserializeFrom(GSON, statsObj, "value", IntegerRange.class));
                stats.put(statistic, intRange);
            }
            return stats;
        }
    }
}
