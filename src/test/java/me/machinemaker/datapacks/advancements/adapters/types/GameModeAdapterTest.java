package me.machinemaker.datapacks.advancements.adapters.types;

import com.google.common.base.Enums;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import java.util.Locale;
import me.machinemaker.datapacks.GsonTest;
import org.bukkit.GameMode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNull;

class GameModeAdapterTest extends GsonTest {

    @ParameterizedTest
    @ValueSource(strings = {"creative", "survival", "spectator", "adventure"})
    void testGameModeAdapter(final String mode) {
        final JsonElement element = new JsonPrimitive(mode);
        final GameMode bukkit = Enums.getIfPresent(GameMode.class, mode.toUpperCase(Locale.ENGLISH)).toJavaUtil().orElseThrow();
        this.testJsonConversion(bukkit, element, GameMode.class);
    }

    @Test
    void testNull() {
        assertNull(this.fromTree(new JsonPrimitive("unknown"), GameMode.class));
        assertNull(this.fromTree(JsonNull.INSTANCE, GameMode.class));
    }
}
