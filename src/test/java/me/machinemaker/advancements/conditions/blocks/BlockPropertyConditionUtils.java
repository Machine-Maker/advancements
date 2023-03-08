package me.machinemaker.advancements.conditions.blocks;

import com.google.common.collect.Iterators;
import com.google.gson.JsonObject;
import io.papermc.paper.world.data.BlockProperty;
import io.papermc.paper.world.data.IntegerBlockProperty;
import java.util.concurrent.ThreadLocalRandom;

public final class BlockPropertyConditionUtils {

    private BlockPropertyConditionUtils() {
    }

    public static <T extends Comparable<T>> void addExact(final JsonObject obj, final BlockPropertyCondition.Builder builder, final BlockProperty<T> property) {
        final T value = Iterators.get(property.values().iterator(), ThreadLocalRandom.current().nextInt(property.values().size()));
        builder.addExact(property, value);
        obj.addProperty(property.name(), property.name(value));
    }

    public static void addRanged(final JsonObject obj, final BlockPropertyCondition.Builder builder, final IntegerBlockProperty property) {
        builder.addRanged(property, property.min(), property.max());
        final JsonObject o = new JsonObject();
        o.addProperty("min", property.name(property.min()));
        o.addProperty("max", property.name(property.max()));
        obj.add(property.name(), o);
    }
}
