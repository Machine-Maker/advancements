package me.machinemaker.datapacks.advancements.conditions.block;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemSource;
import me.machinemaker.datapacks.testing.sources.sources.Sources;
import org.bukkit.Material;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BlockConditionTest extends ConditionTest<BlockCondition> {

    BlockConditionTest() {
        super(BlockCondition.conditionType());
    }

    @Test
    void testBlockConditionWithBlocksFail() {
        assertThrows(IllegalArgumentException.class, () -> BlockCondition.forBlocks(Material.DIAMOND_AXE));
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testBlockCondition(final BlockCondition condition) {
        final JsonObject obj = new JsonObject();
        this.add(obj, "tag", condition.tag());
        this.add(obj, "blocks", condition.blocks());
        this.add(obj, "state", condition.state());
        this.add(obj, "nbt", condition.nbt());
        this.testJsonConversion(condition, obj);
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

    private static class Provider extends RandomItemSource<BlockCondition> {

        Provider() {
            super(RandomProviders.BLOCK_CONDITION);
        }
    }
}
