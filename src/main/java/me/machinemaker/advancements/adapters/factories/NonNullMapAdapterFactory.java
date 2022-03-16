package me.machinemaker.advancements.adapters.factories;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * For use only in {@link com.google.gson.annotations.JsonAdapter} annotations.
 */
public class NonNullMapAdapterFactory implements TypeAdapterFactory {

    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<? super T> classOfT = type.getRawType();
        if (!Map.class.isAssignableFrom(classOfT)) {
            return null;
        }

        TypeAdapter<T> delegate = gson.getAdapter( type);

        return (TypeAdapter<T>) this.newMapAdapter(type, (TypeAdapter<Map<?, ?>>) delegate);
    }

    private TypeAdapter<Map<?, ?>> newMapAdapter(TypeToken<?> type, TypeAdapter<Map<?, ?>> delegate) {
        return new TypeAdapter<Map<?, ?>>() {
            @Override
            public void write(JsonWriter out, Map<?, ?> value) throws IOException {
                delegate.write(out, value);
            }

            @Override
            public Map<?, ?> read(JsonReader in) throws IOException {
                Map<?, ?> map;
                if (in.peek() == JsonToken.NULL) {
                    in.nextNull();
                    map = delegate.read(new JsonTreeReader(new JsonArray()));
                } else {
                    map = delegate.read(in);
                }
                return map;
            }
        };
    }
}
