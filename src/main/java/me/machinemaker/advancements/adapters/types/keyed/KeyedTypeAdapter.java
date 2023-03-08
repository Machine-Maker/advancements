package me.machinemaker.advancements.adapters.types.keyed;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import io.papermc.paper.registry.Reference;
import java.io.IOException;
import java.util.Objects;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.bukkit.Registry;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.Contract;

/**
 * Adapter for Keyed types. Is null-safe
 *
 * @param <T> keyed type
 */
public abstract class KeyedTypeAdapter<T extends Keyed> extends TypeAdapter<T> {

    private final TypeToken<T> type;

    protected KeyedTypeAdapter(final TypeToken<T> type) {
        this.type = type;
    }

    public static <E extends Enum<E> & Keyed> KeyedTypeAdapter<E> forEnum(final Class<E> enumClass) {
        return new KeyedEnumTypeAdapter<>(enumClass);
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static <T extends org.bukkit.Keyed> KeyedTypeAdapter<T> forRegistry(final Registry<T> registry, final Class<T> classOfT) {
        return forRegistry(registry, TypeToken.get(classOfT));
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static <T extends org.bukkit.Keyed> KeyedTypeAdapter<T> forRegistry(final Registry<T> registry, final TypeToken<T> type) {
        return new RegistryTypeAdapter<>(type, registry);
    }

    @SuppressWarnings("unchecked")
    @Contract(value = "_, _ -> new", pure = true)
    public static <T extends org.bukkit.Keyed> KeyedTypeAdapter<Reference<T>> forReference(final Registry<T> registry, final TypeToken<T> type) {
        return new ReferenceTypeAdapter<>((TypeToken<Reference<T>>) TypeToken.getParameterized(Reference.class, type.getType()), registry);
    }

    public abstract @Nullable T fromKey(Key key);

    @Override
    public final void write(final JsonWriter out, final @Nullable T value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.key().asString());
        }
    }

    @Override
    public final @Nullable T read(final JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        @Subst("test:key") final String string = in.nextString();
        return this.fromKey(Key.key(string));
    }

    public TypeToken<T> getType() {
        return this.type;
    }

    @Override
    public boolean equals(final @Nullable Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final KeyedTypeAdapter<?> that = (KeyedTypeAdapter<?>) o;
        return this.type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type);
    }

    @Override
    public String toString() {
        return "KeyedTypeAdapter{" +
            "type=" + this.type +
            "} " + super.toString();
    }
}
