package me.machinemaker.advancements.adapters.types;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import net.kyori.adventure.key.Key;

public class KeyAdapter extends TypeAdapter<Key> {

    @Override
    public void write(final JsonWriter out, final Key value) throws IOException {
        out.value()
    }

    @Override
    public Key read(final JsonReader in) throws IOException {
        return null;
    }
}
