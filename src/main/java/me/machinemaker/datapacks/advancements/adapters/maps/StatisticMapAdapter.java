package me.machinemaker.datapacks.advancements.adapters.maps;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
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
import me.machinemaker.datapacks.advancements.conditions.range.IntegerRange;
import me.machinemaker.datapacks.advancements.utils.GsonUtils;
import net.kyori.adventure.key.Key;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;

import static java.util.Objects.requireNonNull;

@ApiStatus.Internal
public final class StatisticMapAdapter extends TypeAdapter<Map<Statistic<?>, IntegerRange>> {

    static final TypeToken<StatisticType<?>> STATISTIC_TYPE_TYPE_TOKEN = new TypeToken<StatisticType<?>>() {};
    static final TypeToken<Map<Statistic<?>, IntegerRange>> STAT_MAP_TYPE_TOKEN = new TypeToken<Map<Statistic<?>, IntegerRange>>() {};
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings("unchecked")
        @Override
        public <T> @Nullable TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
            if (!type.equals(STAT_MAP_TYPE_TOKEN)) {
                return null;
            }

            return (TypeAdapter<T>) new StatisticMapAdapter(gson);
        }
    };

    private final Gson gson;

    private StatisticMapAdapter(final Gson gson) {
        this.gson = gson;
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
                obj.add("type", this.gson.toJsonTree(stat.type(), STATISTIC_TYPE_TYPE_TOKEN.getType()));
                obj.add("stat", this.gson.toJsonTree(stat.value().key()));
                obj.add("value", this.gson.toJsonTree(range));
                array.add(obj);
            });
            Streams.write(array, out);
        } else {
            out.beginArray().endArray();
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
                final StatisticType<?> statType = requireNonNull(GsonUtils.deserializeFrom(this.gson, statsObj, "type", STATISTIC_TYPE_TYPE_TOKEN.getType()));
                final Key stat = requireNonNull(GsonUtils.deserializeFrom(this.gson, statsObj, "stat", Key.class));
                final Statistic<?> statistic = getStatistic(statType, stat);
                final IntegerRange intRange = requireNonNull(GsonUtils.deserializeFrom(this.gson, statsObj, "value", IntegerRange.class));
                stats.put(statistic, intRange);
            }
            return stats;
        }
    }
}
