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
import org.bukkit.Material;
import org.bukkit.Tag;
import org.junit.jupiter.api.Test;

import static me.machinemaker.advancements.conditions.blocks.BlockConditionUtils.addBlocks;
import static me.machinemaker.advancements.conditions.blocks.BlockConditionUtils.addProperties;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BlockConditionTest extends ConditionTest<BlockCondition> {

    BlockConditionTest() {
        super(BlockCondition.conditionType());
    }

    @Test
    void testBlockConditionWithBlocksFail() {
        assertThrows(IllegalArgumentException.class, () -> BlockCondition.forBlocks(Material.DIAMOND_AXE));
    }

    @Providers.Config(maxSize = 5, count = 3)
    @MaterialProviders.Blocks
    void testBlockConditionWithBlocks(final Set<Material> blocks) {
        final BlockCondition.Builder builder = BlockCondition.builder();
        final JsonObject obj = new JsonObject();
        addBlocks(builder, obj, blocks);
        this.testJsonConversion(builder.build(), obj);
    }

    @Test
    void testBlockConditionWithBlockTag() {
        final BlockCondition condition = BlockCondition.forTag(Tag.ACACIA_LOGS);
        final JsonObject obj = new JsonObject();
        obj.addProperty("tag", Tag.ACACIA_LOGS.getKey().toString());
        this.testJsonConversion(condition, obj);
    }

    @Providers.Config(maxSize = 5, count = 3)
    @CompoundProvider({BlockProviders.Properties.Provider.class, MaterialProviders.Blocks.Provider.class})
    void testBlockConditionWithBlockProperty(final Set<BlockProperty<?>> propertySet, final Set<Material> blocks) {
        final BlockCondition.Builder builder = BlockCondition.builder();
        final JsonObject obj = new JsonObject();
        addBlocks(builder, obj, blocks);
        addProperties(builder, obj, propertySet);
        this.testJsonConversion(builder.build(), obj);
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject obj = new JsonObject();
        obj.add("blocks", JsonNull.INSTANCE);
        this.testIsAny(obj);
        this.testIsAny("{ \"blocks\": null }");
        this.testIsAny("{ \"tag\": null }");
        this.testIsAny("{ \"blocks\": null, \"nbt\": null }");
    }

}
