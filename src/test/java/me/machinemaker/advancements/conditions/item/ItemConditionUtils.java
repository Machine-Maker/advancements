package me.machinemaker.advancements.conditions.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.Set;
import org.bukkit.Material;

public final class ItemConditionUtils {

    private ItemConditionUtils() {
    }

    public static void addItems(final ItemCondition.Builder builder, final JsonObject obj, final Set<Material> items) {
        final JsonArray array = new JsonArray();
        items.forEach(i -> array.add(i.key().asString()));
        obj.add("items", array);
        builder.items(items);
    }
}
