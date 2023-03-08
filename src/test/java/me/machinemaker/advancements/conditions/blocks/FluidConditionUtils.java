package me.machinemaker.advancements.conditions.blocks;

import com.google.gson.JsonObject;
import io.papermc.paper.world.data.BlockProperty;
import java.util.Set;
import org.bukkit.Fluid;

import static me.machinemaker.advancements.conditions.blocks.BlockPropertyConditionUtils.addExact;

public final class FluidConditionUtils {

    private FluidConditionUtils() {
    }

    static void addFluid(final FluidCondition.Builder builder, final JsonObject obj, final Fluid fluid) {
        obj.addProperty("fluid", fluid.key().asString());
        builder.fluid(fluid);
    }

    static void addProperties(final FluidCondition.Builder builder, final JsonObject obj, final Set<BlockProperty<?>> propertySet) {
        final JsonObject state = new JsonObject();
        final BlockPropertyCondition.Builder propertyBuilder = BlockPropertyCondition.builder();
        propertySet.forEach(p -> addExact(state, propertyBuilder, p));
        obj.add("state", state);
        builder.state(propertyBuilder.build());
    }
}
