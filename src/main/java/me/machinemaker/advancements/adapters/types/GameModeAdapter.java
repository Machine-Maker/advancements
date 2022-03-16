package me.machinemaker.advancements.adapters.types;

import com.google.common.base.Enums;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.bukkit.GameMode;

import java.io.IOException;
import java.util.Locale;

public class GameModeAdapter extends TypeAdapter<GameMode> {

    @Override
    public void write(JsonWriter out, GameMode gameMode) throws IOException {
        out.value(gameMode.name().toLowerCase(Locale.ENGLISH)); // TODO when paper API updates to include methods for game mode name.
    }

    @Override
    public GameMode read(JsonReader in) throws IOException {
        return Enums.getIfPresent(GameMode.class, in.nextString().toUpperCase(Locale.ENGLISH)).orNull(); // TODO when paper API updates to include methods for game mode name.
    }
}
