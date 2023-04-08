package me.machinemaker.datapacks.advancements.conditions.block;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.papermc.paper.world.data.BlockProperty;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.common.adapters.utils.GsonUtils;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.UnmodifiableView;

record BlockPropertyConditionImpl(List<BlockPropertyCondition.Matcher> properties) implements BlockPropertyCondition {

    static final BlockPropertyConditionImpl ANY = new BlockPropertyConditionImpl(Collections.emptyList());
    static final ConditionType<BlockPropertyCondition> TYPE = ConditionType.create(BlockPropertyCondition.class, ANY, new Adapter());

    BlockPropertyConditionImpl {
        properties = List.copyOf(properties);
    }

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "PropertyCondition{ANY}";
        }
        return "PropertyCondition{" +
            "properties=" + this.properties +
            '}';
    }

    public sealed interface MatcherImpl permits ExactMatcherImpl, RangedMatcherImpl {

        static BlockPropertyCondition.Matcher fromJson(final String name, final JsonElement element) {
            if (element.isJsonPrimitive()) {
                return new ExactMatcherImpl(name, element.getAsString());
            } else if (element.isJsonObject()) {
                final JsonObject object = element.getAsJsonObject();
                final @Nullable String min = GsonUtils.getString(object, "min", null);
                final @Nullable String max = GsonUtils.getString(object, "max", null);
                return min != null && min.equals(max) ? new ExactMatcherImpl(name, min) : new RangedMatcherImpl(name, min, max);
            } else {
                throw new JsonParseException("Expected string or object, got " + element.getClass().getSimpleName());
            }
        }

        JsonElement toJson();
    }

    record ExactMatcherImpl(String name, String value) implements MatcherImpl, BlockPropertyCondition.ExactMatcher {

        @Override
        public JsonElement toJson() {
            return new JsonPrimitive(this.value);
        }
    }

    record RangedMatcherImpl(String name, @Nullable String min, @Nullable String max) implements MatcherImpl, BlockPropertyCondition.RangedMatcher {

        @Override
        public JsonElement toJson() {
            final JsonObject object = new JsonObject();
            if (this.min != null) {
                object.addProperty("min", this.min);
            }
            if (this.max != null) {
                object.addProperty("max", this.max);
            }
            return object;
        }
    }

    public static final class BuilderImpl implements BlockPropertyCondition.Builder {

        private List<BlockPropertyCondition.Matcher> properties = new ArrayList<>();

        BuilderImpl() {
        }

        BuilderImpl(final BlockPropertyCondition condition) {
            this.properties = new ArrayList<>(condition.properties());
        }

        @Contract(pure = true)
        public @UnmodifiableView List<BlockPropertyCondition.Matcher> properties() {
            return Collections.unmodifiableList(this.properties);
        }

        @Contract(value = "_ -> this", mutates = "this")
        public BlockPropertyCondition.Builder add(final BlockPropertyCondition.Matcher matcher) {
            this.properties.add(matcher);
            return this;
        }

        @Contract(value = "_, _ -> this", mutates = "this")
        public <T extends Comparable<T>> BlockPropertyCondition.Builder addExact(final BlockProperty<T> property, final T value) {
            return this.add(new ExactMatcherImpl(property.name(), property.name(value)));
        }

        @Contract(value = "_, _, _ -> this", mutates = "this")
        public <T extends Comparable<T>> BlockPropertyCondition.Builder addRanged(final BlockProperty<T> property, final @Nullable T min, final @Nullable T max) {
            return this.add(new RangedMatcherImpl(property.name(), min != null ? property.name(min) : null, max != null ? property.name(max) : null));
        }

        @Override
        public BlockPropertyCondition build() {
            return new BlockPropertyConditionImpl(this.properties);
        }
    }

    static final class Adapter extends TypeAdapter<BlockPropertyCondition> {

        @Override
        public void write(final JsonWriter out, final @Nullable BlockPropertyCondition value) throws IOException {
            if (value == null || value.isAny()) {
                out.nullValue();
            } else {
                final JsonObject object = new JsonObject();
                value.properties().forEach(matcher -> object.add(matcher.name(), ((MatcherImpl) matcher).toJson()));
                Streams.write(object, out);
            }
        }

        @Override
        public BlockPropertyCondition read(final JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return ANY;
            } else if (in.peek() == JsonToken.BEGIN_OBJECT) {
                final JsonObject object = Streams.parse(in).getAsJsonObject();
                final List<BlockPropertyCondition.Matcher> properties = new ArrayList<>();
                for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
                    properties.add(MatcherImpl.fromJson(entry.getKey(), entry.getValue()));
                }
                return new BlockPropertyConditionImpl(List.copyOf(properties));
            } else {
                throw new JsonParseException("Expected object or null, got " + in.peek());
            }
        }
    }
}
