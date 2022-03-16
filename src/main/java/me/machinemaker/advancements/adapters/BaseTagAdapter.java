package me.machinemaker.advancements.adapters;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

public abstract class BaseTagAdapter<T extends Tag<K>, K extends Keyed> extends TypeAdapter<T> {

    private final String tagRegistry;
    private final Function<Tag<K>, T> constructor;
    private final Class<K> keyedClass;

    private BaseTagAdapter(@NotNull String tagRegistry, @NotNull Function<@NotNull Tag<K>, @NotNull T> constructor, @NotNull Class<K> keyedClass) {
        this.tagRegistry = tagRegistry;
        this.constructor = constructor;
        this.keyedClass = keyedClass;
    }

    public static <T extends Tag<K> ,K extends Keyed> @NotNull TypeAdapter<T> of(@NotNull String tagRegistry, @NotNull Function< @NotNull Tag<K>, @NotNull T> constructor, @NotNull Class<K> keyedClass) {
        return new BaseTagAdapter<>(tagRegistry, constructor, keyedClass) {}.nullSafe();
    }

    @Override
    public final void write(JsonWriter out, T value) throws IOException {
        out.value(value.getKey().toString());
    }

    @Override
    public T read(JsonReader in) throws IOException {
        String str = in.nextString();
        NamespacedKey key = NamespacedKey.fromString(str);
        if (key == null) {
            throw new JsonParseException(str + " could not be parsed into a NamespacedKey");
        }
        return constructor.apply(Bukkit.getTag(tagRegistry, key, keyedClass));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseTagAdapter<?, ?> that = (BaseTagAdapter<?, ?>) o;
        return tagRegistry.equals(that.tagRegistry) && constructor.equals(that.constructor) && keyedClass.equals(that.keyedClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagRegistry, constructor, keyedClass);
    }

    @Override
    public String toString() {
        return "BaseTagAdapter{" +
                "tagRegistry='" + tagRegistry + '\'' +
                ", keyedClass=" + keyedClass +
                "} " + super.toString();
    }
}
