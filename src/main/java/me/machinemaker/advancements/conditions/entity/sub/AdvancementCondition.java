package me.machinemaker.advancements.conditions.entity.sub;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import java.io.IOException;
import me.machinemaker.advancements.GsonHelper;
import me.machinemaker.advancements.adapters.Adapters;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@JsonAdapter(AdvancementCondition.Adapter.class)
public interface AdvancementCondition { // Doesn't extend Condition because it's not "really" a condition in that sense, only used as a map value type

    @Contract(value = "_ -> new", pure = true)
    static AdvancementCondition done(final boolean state) {
        return new AdvancementDoneCondition(state);
    }

    @Contract(value = "_ -> new", pure = true)
    static AdvancementCondition criteria(final Object2BooleanMap<String> criteria) {
        return new AdvancementCriteriaCondition(criteria);
    }

    JsonElement toJson();

    @JsonAdapter(AdvancementCondition.Adapter.class)
    record AdvancementDoneCondition(boolean state) implements AdvancementCondition {

        @Override
        public JsonElement toJson() {
            return new JsonPrimitive(this.state);
        }

        @Override
        public String toString() {
            return "AdvancementDoneCondition{" +
                    "state=" + this.state +
                    '}';
        }
    }

    @JsonAdapter(AdvancementCondition.Adapter.class)
    record AdvancementCriteriaCondition(Object2BooleanMap<String> criteria) implements AdvancementCondition {

        @Override
        public JsonElement toJson() {
            final JsonObject object = new JsonObject();
            this.criteria.forEach(object::addProperty);
            return object;
        }

        @Override
        public String toString() {
            return "AdvancementCriteriaCondition{" +
                    "criteria=" + this.criteria +
                    '}';
        }
    }

    @ApiStatus.Internal
    class Adapter extends TypeAdapter<AdvancementCondition> {

        private static final GsonHelper HELPER = new GsonHelper(Adapters.OBJECT_2_BOOLEAN_MAP_INSTANTIATOR);

        @Override
        public void write(final JsonWriter out, final AdvancementCondition value) throws IOException {
            HELPER.toWriter(out, value.toJson());
        }

        @Override
        public AdvancementCondition read(final JsonReader in) throws IOException {
            if (in.peek() == JsonToken.BOOLEAN) {
                return new AdvancementDoneCondition(in.nextBoolean());
            } else if (in.peek() == JsonToken.BEGIN_OBJECT) {
                return new AdvancementCriteriaCondition(HELPER.fromReader(in, new TypeToken<Object2BooleanMap<String>>() {}.getType()));
            }
            throw new JsonParseException("Expected boolean or object, got " + in.peek());
        }
    }
}
