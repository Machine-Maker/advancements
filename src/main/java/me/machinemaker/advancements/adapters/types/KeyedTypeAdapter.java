package me.machinemaker.advancements.adapters.types;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * Adapter for Keyed types. Is null-safe
 * @param <T> keyed type
 */
public abstract class KeyedTypeAdapter<T extends Keyed> extends TypeAdapter<T> {

    private final TypeToken<T> type;

    protected KeyedTypeAdapter(TypeToken<T> type) {
        this.type = type;
    }

    public abstract @Nullable T fromKey(NamespacedKey key);

    @Override
    public final void write(JsonWriter out, @Nullable T value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.getKey().toString());
        }
    }

    @Override
    public final @Nullable T read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        return this.fromKey(Objects.requireNonNull(NamespacedKey.fromString(in.nextString())));
    }

    public TypeToken<T> getType() {
        return type;
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyedTypeAdapter<?> that = (KeyedTypeAdapter<?>) o;
        return this.type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "KeyedTypeAdapter{" +
                "type=" + this.type +
                "} " + super.toString();
    }

    public static <E extends Enum<E> & Keyed> KeyedTypeAdapter<E> forEnum(Class<E> enumClass) {
        return new KeyedEnumTypeAdapter<>(enumClass);
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static <T extends Keyed> KeyedTypeAdapter<T> forRegistry(Registry<T> registry, Class<T> classOfT) {
        return forRegistry(registry, TypeToken.get(classOfT));
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static <T extends Keyed> KeyedTypeAdapter<T> forRegistry(Registry<T> registry, TypeToken<T> type) {
        return new RegistryTypeAdapter<T>(type, registry);
    }
}
