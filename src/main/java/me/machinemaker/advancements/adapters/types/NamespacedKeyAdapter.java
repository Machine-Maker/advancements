package me.machinemaker.advancements.adapters.types;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.NamespacedKey;

import java.io.IOException;

public class NamespacedKeyAdapter extends TypeAdapter<NamespacedKey> {

    @Override
    public void write(JsonWriter out, NamespacedKey value) throws IOException {
        out.value(value.toString());
    }

    @Override
    public NamespacedKey read(JsonReader in) throws IOException {
        return NamespacedKey.fromString(in.nextString());
    }
}
