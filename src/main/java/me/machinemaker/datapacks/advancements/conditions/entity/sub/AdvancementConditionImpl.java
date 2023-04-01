package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import java.io.IOException;
import org.checkerframework.checker.nullness.qual.Nullable;

interface AdvancementConditionImpl extends AdvancementCondition {

    TypeAdapterFactory FACTORY = new AdapterFactory();

    JsonElement toJson();

    record DoneImpl(
            boolean state
    ) implements AdvancementConditionImpl, AdvancementCondition.Done {

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

    record CriteriaImpl(
            Object2BooleanMap<String> criteria
    ) implements AdvancementConditionImpl, AdvancementCondition.Criteria {

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

    class AdapterFactory implements TypeAdapterFactory {

        private static final TypeToken<Object2BooleanMap<String>> MAP_TYPE = new TypeToken<Object2BooleanMap<String>>() {};

        @SuppressWarnings("unchecked")
        @Override
        public <T> @Nullable TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
            if (!AdvancementCondition.class.isAssignableFrom(type.getRawType())) {
                return null;
            }
            return (TypeAdapter<T>) new TypeAdapter<AdvancementCondition>() {

                @Override
                public void write(final JsonWriter out, final AdvancementCondition value) throws IOException {
                    Streams.write(((AdvancementConditionImpl) value).toJson(), out);
                }

                @Override
                public AdvancementCondition read(final JsonReader in) throws IOException {
                    if (in.peek() == JsonToken.BOOLEAN) {
                        return new DoneImpl(in.nextBoolean());
                    } else if (in.peek() == JsonToken.BEGIN_OBJECT) {
                        return new CriteriaImpl(gson.fromJson(in, MAP_TYPE));
                    }
                    throw new JsonParseException("Expected boolean or object, got " + in.peek());
                }
            };
        }
    }
}
