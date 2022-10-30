package me.machinemaker.advancements.conditions.blocks;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.GsonTestBase;
import org.bukkit.Fluid;
import org.bukkit.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FluidConditionTest extends GsonTestBase {

    @Test
    void testFluidConditionWithFluid() {
        FluidCondition condition = FluidCondition.forFluid(Fluid.LAVA);
        JsonObject obj = new JsonObject();
        obj.addProperty("fluid", Fluid.LAVA.getKey().toString());
        assertEquals(obj, tree(condition));
        assertEquals(condition, fromJson(obj, FluidCondition.class));
    }

    @Test
    void testFluidConditionWithFluidTag() {
        FluidCondition condition = FluidCondition.forTag(Tag.FLUIDS_WATER);
        JsonObject obj = new JsonObject();
        obj.addProperty("tag", Tag.FLUIDS_WATER.getKey().toString());
        assertEquals(obj, tree(condition));
        assertEquals(condition, fromJson(obj, FluidCondition.class));
    }

    // TODO block data condition test

    @Test
    void testAnyFluidCondition() {
        JsonObject obj = new JsonObject();
        obj.add("fluid", JsonNull.INSTANCE);
        anyTest(obj, FluidCondition.class);
        anyTest("null", FluidCondition.class);
        anyTest("{ \"tag\": null }", FluidCondition.class);
        anyTest("{ \"fluid\": null }", FluidCondition.class);
    }
}
