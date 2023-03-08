package me.machinemaker.advancements.conditions.blocks;

import com.google.gson.JsonObject;
import io.papermc.paper.world.data.BlockProperty;
import io.papermc.paper.world.data.IntegerBlockProperty;
import java.util.Set;
import me.machinemaker.advancements.conditions.ConditionTest;
import me.machinemaker.advancements.tests.providers.BlockProviders;
import me.machinemaker.advancements.tests.providers.Providers;

import static me.machinemaker.advancements.conditions.blocks.BlockPropertyConditionUtils.addExact;
import static me.machinemaker.advancements.conditions.blocks.BlockPropertyConditionUtils.addRanged;

class BlockPropertyConditionTest extends ConditionTest<BlockPropertyCondition> {

    BlockPropertyConditionTest() {
        super(BlockPropertyCondition.conditionType(), false);
    }

    @Providers.Config(maxSize = 3, count = 3)
    @BlockProviders.Properties
    void testBlockPropertyConditionExact(final Set<BlockProperty<?>> propertySet) {
        final BlockPropertyCondition.Builder builder = BlockPropertyCondition.builder();
        final JsonObject obj = new JsonObject();
        propertySet.forEach(p -> addExact(obj, builder, p));
        this.testJsonConversion(builder.build(), obj);
    }

    @Providers.Config(maxSize = 3, count = 3)
    @BlockProviders.IntProperties
    void testBlockPropertyConditionRanged(final Set<IntegerBlockProperty> propertySet) {
        final BlockPropertyCondition.Builder builder = BlockPropertyCondition.builder();
        final JsonObject obj = new JsonObject();
        propertySet.forEach(p -> addRanged(obj, builder, p));
        this.testJsonConversion(builder.build(), obj);
    }

    @Override
    protected void additionalAnyTests() {
        // none
    }
}
