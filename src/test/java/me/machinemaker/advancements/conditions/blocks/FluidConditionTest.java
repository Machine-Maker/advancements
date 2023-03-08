package me.machinemaker.advancements.conditions.blocks;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import io.papermc.paper.world.data.BlockProperty;
import java.util.Set;
import me.machinemaker.advancements.conditions.ConditionTest;
import me.machinemaker.advancements.tests.providers.BlockProviders;
import me.machinemaker.advancements.tests.providers.CompoundProvider;
import me.machinemaker.advancements.tests.providers.MaterialProviders;
import me.machinemaker.advancements.tests.providers.Providers;
import org.bukkit.Fluid;
import org.bukkit.Tag;
import org.junit.jupiter.api.Test;

import static me.machinemaker.advancements.conditions.blocks.FluidConditionUtils.addFluid;
import static me.machinemaker.advancements.conditions.blocks.FluidConditionUtils.addProperties;

class FluidConditionTest extends ConditionTest<FluidCondition> {

    FluidConditionTest() {
        super(FluidCondition.conditionType());
    }

    @Providers.Config(count = 3)
    @MaterialProviders.Fluid
    void testFluidConditionWithFluid(final Fluid fluid) {
        final FluidCondition.Builder builder = FluidCondition.builder();
        final JsonObject obj = new JsonObject();
        addFluid(builder, obj, fluid);
        this.testJsonConversion(builder.build(), obj);
    }

    @Test
    void testFluidConditionWithFluidTag() {
        final FluidCondition condition = FluidCondition.forTag(Tag.FLUIDS_WATER);
        final JsonObject obj = new JsonObject();
        obj.addProperty("tag", Tag.FLUIDS_WATER.getKey().toString());
        this.testJsonConversion(condition, obj);
    }

    @Providers.Config(maxSize = 4, count = 3)
    @CompoundProvider({BlockProviders.Properties.Provider.class, MaterialProviders.Fluid.Provider.class})
    void testFluidConditionWithBlockProperty(final Set<BlockProperty<?>> propertySet, final Fluid fluid) {
        final FluidCondition.Builder builder = FluidCondition.builder();
        final JsonObject obj = new JsonObject();
        addFluid(builder, obj, fluid);
        addProperties(builder, obj, propertySet);
        this.testJsonConversion(builder.build(), obj);
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject obj = new JsonObject();
        obj.add("fluid", JsonNull.INSTANCE);
        this.testIsAny(obj);
        this.testIsAny("{ \"tag\": null }");
        this.testIsAny("{ \"fluid\": null }");
    }
}
