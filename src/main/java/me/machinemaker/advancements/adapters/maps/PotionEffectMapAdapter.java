package me.machinemaker.advancements.adapters.maps;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import me.machinemaker.advancements.GsonHelper;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.conditions.effects.PotionEffectInstanceCondition;
import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class PotionEffectMapAdapter extends TypeAdapter<Map<PotionEffectType, PotionEffectInstanceCondition>> {

    private static final GsonHelper HELPER = new GsonHelper(Adapters.POTION_EFFECT_TYPE_ADAPTER);

    @Override
    public void write(JsonWriter out, Map<PotionEffectType, PotionEffectInstanceCondition> value) throws IOException {
        out.beginObject();
        for (var entry : value.entrySet()) {
            HELPER.toWriter(out, HELPER.toJsonTreeString(entry.getKey()), entry.getValue(), PotionEffectInstanceCondition.class);
        }
        out.endObject();
    }

    @Override
    public Map<PotionEffectType, PotionEffectInstanceCondition> read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return Collections.emptyMap();
        }
        JsonObject obj = HELPER.objectFromReader(in);
        return obj.entrySet().stream().collect(Collectors.toMap(entry -> {
            NamespacedKey key = NamespacedKey.fromString(entry.getKey());
            if (key == null) {
                throw new JsonParseException(entry.getKey() + " cannot be converted to a NamespacedKey");
            }
            return PotionEffectType.getByKey(key);
        }, entry -> HELPER.fromJsonTree(entry.getValue(), PotionEffectInstanceCondition.class)));
    }
}
