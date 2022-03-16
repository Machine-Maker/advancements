package me.machinemaker.advancements.conditions.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import me.machinemaker.advancements.GsonHelper;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.util.IgnoreRecordTypeAdapter;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

import java.io.IOException;

@JsonAdapter(AdvancementCondition.Adapter.class)
public interface AdvancementCondition { // Doesn't extend Condition because it's not "really" a condition in that sense, only used as a map value type

    JsonElement toJson();

    @Contract(value = "_ -> new", pure = true)
    static AdvancementCondition done(boolean state) {
        return new AdvancementDoneCondition(state);
    }

    @Contract(value = "_ -> new", pure = true)
    static AdvancementCondition criteria(Object2BooleanMap<String> criteria) {
        return new AdvancementCriteriaCondition(criteria);
    }

    @IgnoreRecordTypeAdapter
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

    @IgnoreRecordTypeAdapter
    @JsonAdapter(AdvancementCondition.Adapter.class)
    record AdvancementCriteriaCondition(Object2BooleanMap<String> criteria) implements AdvancementCondition {

        @Override
        public JsonElement toJson() {
            JsonObject object = new JsonObject();
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
        public void write(JsonWriter out, AdvancementCondition value) throws IOException {
            HELPER.toWriter(out, value.toJson());
        }

        @Override
        public AdvancementCondition read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.BOOLEAN) {
                return new AdvancementDoneCondition(in.nextBoolean());
            } else if (in.peek() == JsonToken.BEGIN_OBJECT) {
                return new AdvancementCriteriaCondition(HELPER.fromReader(in, new TypeToken<Object2BooleanMap<String>>() {}.getType()));
            }
            throw new JsonSyntaxException("Expected boolean or object, got " + in.peek());
        }
    }
}
