package me.machinemaker.advancements.adapters;

import com.google.common.base.Enums;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import me.machinemaker.advancements.GsonTestBase;
import me.machinemaker.advancements.adapters.types.GameModeAdapter;
import org.bukkit.GameMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GameModeAdapterTest extends GsonTestBase {

    @BeforeAll
    void beforeAll() {
        registerTypeAdapter(GameMode.class, new GameModeAdapter().nullSafe());
    }

    @ParameterizedTest
    @ValueSource(strings = {"creative", "survival", "spectator", "adventure"})
    void testGameModeAdapter(String mode) {
        JsonElement element = new JsonPrimitive(mode);
        GameMode bukkit = Enums.getIfPresent(GameMode.class, mode.toUpperCase(Locale.ENGLISH)).toJavaUtil().orElseThrow();
        assertEquals(toJson(element), toJson(bukkit));
        assertEquals(bukkit, fromJson(element, GameMode.class));
    }

    @Test
    void testNull() {
        assertNull(fromJson(new JsonPrimitive("unknown"), GameMode.class));
    }
}
