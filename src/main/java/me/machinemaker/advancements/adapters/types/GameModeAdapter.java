package me.machinemaker.advancements.adapters.types;

import com.google.common.base.Enums;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Locale;
import org.bukkit.GameMode;
import org.checkerframework.checker.nullness.qual.Nullable;

public class GameModeAdapter extends TypeAdapter<GameMode> {

    @Override
    public void write(final JsonWriter out, final GameMode gameMode) throws IOException {
        out.value(gameMode.name().toLowerCase(Locale.ENGLISH));
    }

    @Override
    public @Nullable GameMode read(final JsonReader in) throws IOException {
        return Enums.getIfPresent(GameMode.class, in.nextString().toUpperCase(Locale.ENGLISH)).orNull();
    }
}
