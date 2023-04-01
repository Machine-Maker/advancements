package me.machinemaker.datapacks.advancements.adapters.types;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class BaseTagAdapter<T extends Tag<K>, K extends Keyed> extends TypeAdapter<T> implements Typed<T> {

    private final String tagRegistry;
    private final Function<Tag<K>, T> constructor;
    private final Class<K> keyedClass;
    private final Class<T> tagClass;

    private BaseTagAdapter(final String tagRegistry, final Function<Tag<K>, T> constructor, final Class<K> keyedClass, final Class<T> tagClass) {
        this.tagRegistry = tagRegistry;
        this.constructor = constructor;
        this.keyedClass = keyedClass;
        this.tagClass = tagClass;
    }

    public static <T extends Tag<K>, K extends Keyed> BaseTagAdapter<T, K> tag(final String tagRegistry, final Function<Tag<K>, T> constructor, final Class<K> keyedClass, final Class<T> tagClass) {
        return new BaseTagAdapter<>(tagRegistry, constructor, keyedClass, tagClass);
    }

    @Override
    public void write(final JsonWriter out, final @Nullable T value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.getKey().toString());
        }
    }

    @Override
    public T read(final JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        final String str = in.nextString();
        final @Nullable NamespacedKey key = NamespacedKey.fromString(str);
        if (key == null) {
            throw new JsonParseException(str + " could not be parsed into a NamespacedKey");
        }
        final Tag<K> tag = Bukkit.getTag(this.tagRegistry, key, this.keyedClass);
        if (tag == null) {
            throw new NullPointerException();
        }
        return this.constructor.apply(Objects.requireNonNull(tag));
    }

    @Override
    public TypeToken<T> getType() {
        return TypeToken.get(this.tagClass);
    }
}
