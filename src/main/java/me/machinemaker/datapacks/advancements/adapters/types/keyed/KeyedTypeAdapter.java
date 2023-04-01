package me.machinemaker.datapacks.advancements.adapters.types.keyed;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import me.machinemaker.datapacks.advancements.adapters.types.Typed;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.bukkit.Registry;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.intellij.lang.annotations.Subst;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.Internal
public abstract class KeyedTypeAdapter<T extends Keyed> extends TypeAdapter<T> implements Typed<T> {

    private final TypeToken<T> type;

    protected KeyedTypeAdapter(final TypeToken<T> type) {
        this.type = type;
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static <T extends org.bukkit.Keyed> KeyedTypeAdapter<T> registry(final Registry<T> registry, final Class<T> classOfT) {
        return registry(registry, TypeToken.get(classOfT));
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static <T extends org.bukkit.Keyed> KeyedTypeAdapter<T> registry(final Registry<T> registry, final TypeToken<T> type) {
        return new RegistryTypeAdapter<>(type, registry);
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

    @Override
    public TypeToken<T> getType() {
        return this.type;
    }
}
