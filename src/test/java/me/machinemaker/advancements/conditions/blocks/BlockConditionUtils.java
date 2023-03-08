package me.machinemaker.advancements.conditions.blocks;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.papermc.paper.world.data.BlockProperty;
import java.util.Set;
import org.bukkit.Material;

import static me.machinemaker.advancements.conditions.blocks.BlockPropertyConditionUtils.addExact;

public final class BlockConditionUtils {

    private BlockConditionUtils() {
    }

    static void addBlocks(final BlockCondition.Builder builder, final JsonObject obj, final Set<Material> blocks) {
        final JsonArray array = new JsonArray();
        blocks.forEach(b -> array.add(b.key().asString()));
        obj.add("blocks", array);
        builder.blocks(blocks);
    }

    static void addProperties(final BlockCondition.Builder builder, final JsonObject obj, final Set<BlockProperty<?>> propertySet) {
        final JsonObject state = new JsonObject();
        final BlockPropertyCondition.Builder propertyBuilder = BlockPropertyCondition.builder();
        propertySet.forEach(p -> addExact(state, propertyBuilder, p));
        obj.add("state", state);
        builder.state(propertyBuilder.build());
    }
}
