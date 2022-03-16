package me.machinemaker.advancements.conditions.blocks;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.papermc.paper.world.data.Property;
import me.machinemaker.advancements.GsonHelper;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.util.Buildable;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

// TODO tests
@JsonAdapter(value = PropertyCondition.Adapter.class, nullSafe = false)
public record PropertyCondition(List<Matcher> properties) implements Condition<PropertyCondition>, Buildable<PropertyCondition, PropertyCondition.Builder> {

    public static final PropertyCondition ANY = new PropertyCondition(Collections.emptyList());

    @Override
    public @NotNull PropertyCondition any() {
        return ANY;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
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

    @Contract(value = "-> new", pure = true)
    public static Builder builder() {
        return new Builder();
    }

    public static abstract sealed class Matcher permits ExactMatcher, RangedMatcher {

        private final String name;

        protected Matcher(String name) {
            this.name = name;
        }

        public final String name() {
            return this.name;
        }

        protected abstract JsonElement toJson();

        static Matcher fromJson(String name, JsonElement element) {
            if (element.isJsonPrimitive()) {
                return new ExactMatcher(name, element.getAsString());
            } else if (element.isJsonObject()) {
                JsonObject object = element.getAsJsonObject();
                final @Nullable String min = GsonHelper.getString(object, "min", null);
                final @Nullable String max = GsonHelper.getString(object, "max", null);
                return min != null && min.equals(max) ? new ExactMatcher(name, min) : new RangedMatcher(name, min, max);
            } else {
                throw new JsonSyntaxException("Expected string or object, got " + element.getClass().getSimpleName());
            }
        }
    }

    public static final class ExactMatcher extends Matcher {

        private final String value;

        public ExactMatcher(String name, String value) {
            super(name);
            this.value = value;
        }

        public String value() {
            return this.value;
        }

        @Override
        protected JsonElement toJson() {
            return new JsonPrimitive(this.value);
        }
    }

    public static final class RangedMatcher extends Matcher {

        private final @Nullable String min;
        private final @Nullable String max;

        public RangedMatcher(String name, @Nullable String min, @Nullable String max) {
            super(name);
            this.min = min;
            this.max = max;
        }

        public @Nullable String min() {
            return this.min;
        }

        public @Nullable String max() {
            return this.max;
        }

        @Override
        protected JsonElement toJson() {
            JsonObject object = new JsonObject();
            if (this.min != null) {
                object.addProperty("min", this.min);
            }
            if (this.max != null) {
                object.addProperty("max", this.max);
            }
            return object;
        }
    }

    public static final class Builder implements Condition.Builder<PropertyCondition> {

        private List<Matcher> properties = new ArrayList<>();

        private Builder() {
        }

        private Builder(PropertyCondition condition) {
            this.properties = new ArrayList<>(condition.properties);
        }

        public List<Matcher> properties() {
            return this.properties;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder add(Matcher matcher) {
            this.properties.add(matcher);
            return this;
        }

        @Contract(value = "_, _ -> this", mutates = "this")
        public <T extends Comparable<T>> Builder addExact(Property<T> property, T value) {
            return this.add(new ExactMatcher(property.name(), property.name(value)));
        }

        @Contract(value = "_, _, _ -> this", mutates = "this")
        public <T extends Comparable<T>> Builder addRanged(Property<T> property, @Nullable T min, @Nullable T max) {
            return this.add(new RangedMatcher(property.name(), min != null ? property.name(min) : null, max != null ? property.name(max) : null));
        }

        @Override
        public PropertyCondition build() {
            return new PropertyCondition(this.properties);
        }
    }

    static final class Adapter extends TypeAdapter<PropertyCondition> {

        private static final GsonHelper HELPER = new GsonHelper();

        @Override
        public void write(JsonWriter out, @Nullable PropertyCondition value) throws IOException {
            if (value == null || value.isAny()) {
                out.nullValue();
            } else {
                JsonObject object = new JsonObject();
                value.properties().forEach(matcher -> object.add(matcher.name(), matcher.toJson()));
                HELPER.toWriter(out, object);
            }
        }

        @Override
        public PropertyCondition read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return ANY;
            } else if (in.peek() == JsonToken.BEGIN_OBJECT) {
                JsonObject object = HELPER.objectFromReader(in);
                List<Matcher> properties = new ArrayList<>();
                for (final Map.Entry<String, JsonElement> entry : object.entrySet()) {
                    properties.add(Matcher.fromJson(entry.getKey(), entry.getValue()));
                }
                return new PropertyCondition(List.copyOf(properties));
            } else {
                throw new JsonSyntaxException("Expected object or null, got " + in.peek());
            }
        }
    }
}
