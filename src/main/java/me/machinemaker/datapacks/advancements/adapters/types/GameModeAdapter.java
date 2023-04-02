package me.machinemaker.datapacks.advancements.adapters.types;

import com.google.common.base.Enums;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Locale;
import org.bukkit.GameMode;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class GameModeAdapter extends TypeAdapter<GameMode> {

    public static final TypeAdapter<GameMode> INSTANCE = new GameModeAdapter().nullSafe();

    private GameModeAdapter() {
    }

    @Override
    public void write(final JsonWriter out, final GameMode gameMode) throws IOException {
        out.value(gameMode.name().toLowerCase(Locale.ENGLISH));
    }

    @Override
    public @Nullable GameMode read(final JsonReader in) throws IOException {
        return Enums.getIfPresent(GameMode.class, in.nextString().toUpperCase(Locale.ENGLISH)).orNull();
    }
}
