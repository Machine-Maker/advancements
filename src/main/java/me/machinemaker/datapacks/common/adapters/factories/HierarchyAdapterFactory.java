package me.machinemaker.datapacks.common.adapters.factories;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public abstract class HierarchyAdapterFactory<A> implements TypeAdapterFactory {

    private final Class<A> baseType;

    protected HierarchyAdapterFactory(final Class<A> baseType) {
        this.baseType = baseType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> @Nullable TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        if (!this.baseType.isAssignableFrom(type.getRawType())) {
            return null;
        }

        return (TypeAdapter<T>) new TypeAdapter<A>() {
            @Override
            public void write(final JsonWriter out, final A value) throws IOException {
                HierarchyAdapterFactory.this.write(gson, out, value);
            }

            @Override
            public A read(final JsonReader in) throws IOException {
                return HierarchyAdapterFactory.this.read(gson, in);
            }
        };
    }

    public abstract void write(final Gson gson, final JsonWriter out, final A value) throws IOException;

    public abstract A read(final Gson gson, final JsonReader in) throws IOException;
}
