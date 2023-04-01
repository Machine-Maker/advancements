package me.machinemaker.datapacks.advancements.adapters.types;

import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import net.kyori.adventure.key.Key;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.intellij.lang.annotations.Subst;

public class KeyAdapter extends TypeAdapter<Key> implements Typed<Key> {

    @Override
    public void write(final JsonWriter out, final @Nullable Key value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.asString());
        }
    }

    @Override
    public Key read(final JsonReader in) throws IOException {
        @Subst("test:key") final String string = in.nextString();
        return Key.key(string);
    }

    @Override
    public TypeToken<Key> getType() {
        return TypeToken.get(Key.class);
    }
}
