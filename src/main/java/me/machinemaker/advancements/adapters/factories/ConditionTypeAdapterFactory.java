package me.machinemaker.advancements.adapters.factories;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.Conditions;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ConditionTypeAdapterFactory implements TypeAdapterFactory {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T> @Nullable TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        if (!Condition.class.isAssignableFrom(type.getRawType())) {
            return null;
        }
        return (TypeAdapter<T>) this.createForCondition(gson, (TypeToken<Condition>) type);
    }

    @SuppressWarnings("unchecked")
    public <C extends Condition<C>, T extends C> TypeAdapter<T> createForCondition(final Gson gson, final TypeToken<C> type) {
        final TypeToken<T> actualType;
        if (Conditions.shouldConvertType(type)) {
            actualType = (TypeToken<T>) Conditions.getAdapterType(type);
        } else {
            if (!Conditions.isRegistered(type)) {
                throw new IllegalArgumentException(type + " is not a registered condition type");
            }
            actualType = (TypeToken<T>) type;
        }
        final TypeAdapter<T> adapter = gson.getDelegateAdapter(this, actualType);
        return new TypeAdapter<>() {
            @Override
            public void write(final JsonWriter out, final @Nullable T value) throws IOException {
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
            public T read(final JsonReader in) throws IOException {
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    return Conditions.getDefaultCondition(actualType);
                }
                final @Nullable T condition = adapter.read(in);
                if (condition != null && condition.isAny()) {
                    return (T) condition.any();
                }
                return condition;
            }

            @Override
            public String toString() {
                return "ConditionTypeAdapterFactory{type=" + actualType.getRawType() + "}";
            }
        };
    }
}
