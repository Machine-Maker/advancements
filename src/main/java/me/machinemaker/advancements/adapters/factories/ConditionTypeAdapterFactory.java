package me.machinemaker.advancements.adapters.factories;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.Conditions;

import java.io.IOException;

public class ConditionTypeAdapterFactory implements TypeAdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        if (!Condition.class.isAssignableFrom(type.getRawType())) {
            return null;
        }
        TypeToken<? extends Condition<?>> conditionType = (TypeToken<? extends Condition<?>>) type;
        if (!Conditions.isRegistered(conditionType)) {
            throw new IllegalArgumentException(conditionType + " is not a registered condition type");
        }
        TypeAdapter<Condition<?>> adapter = (TypeAdapter<Condition<?>>) gson.getDelegateAdapter(this, type);
        return (TypeAdapter<T>) new TypeAdapter<Condition<?>>() {
            @Override
            public void write(JsonWriter out, Condition value) throws IOException {
                if (value == null) {
                    out.nullValue();
                } else if (value.isAny()) {
                    if (value.anyIsNull()) {
                        out.nullValue();
                    } else {
                        out.beginObject().endObject();
                    }
                } else {
                    adapter.write(out, value);
                }
            }

            @Override
            public Condition<?> read(JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return Conditions.getDefaultCondition(conditionType);
                }
                Condition<?> condition = adapter.read(in);
                if (condition != null && condition.isAny()) {
                    return condition.any();
                }
                return condition;
            }

            @Override
            public String toString() {
                return "ConditionTypeAdapterFactory{type=" + type.getRawType() + "}";
            }
        };
    }
}
