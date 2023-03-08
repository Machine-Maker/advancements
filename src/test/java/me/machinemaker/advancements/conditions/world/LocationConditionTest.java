package me.machinemaker.advancements.conditions.world;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.GsonTestBase;
import me.machinemaker.advancements.conditions.blocks.BlockCondition;
import me.machinemaker.advancements.conditions.blocks.FluidCondition;
import me.machinemaker.advancements.conditions.blocks.LightCondition;
import me.machinemaker.advancements.ranges.DoubleRange;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.generator.structure.Structure;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationConditionTest extends GsonTestBase {

    @Test
    void testLocationConditionWithPositionAndWorld() {
        final World world = Mockito.mock(World.class);
        Mockito.when(world.getKey()).thenReturn(NamespacedKey.minecraft("overworld"));
        final LocationCondition condition = LocationCondition.atLocation(new Location(world, 33, 34, 33));
        final JsonObject obj = new JsonObject();
        final JsonObject position = new JsonObject();
        position.addProperty("x", 33.0);
        position.addProperty("y", 34.0);
        position.addProperty("z", 33.0);
        obj.add("position", position);
        obj.addProperty("dimension", world.getKey().toString());
        assertEquals(obj, this.tree(condition));
        assertEquals(condition, this.fromJson(obj, LocationCondition.class));
    }

    @Test
    void testLocationCondition() {
        final LocationCondition condition = new LocationCondition(
            DoubleRange.conditionType().any(),
            DoubleRange.isBetween(0, 20),
            DoubleRange.isExactly(3),
            null,
            Structure.BURIED_TREASURE,
            NamespacedKey.minecraft("overworld"),
            null,
            LightCondition.conditionType().any(),
            BlockCondition.forBlocks(Material.STONE),
            FluidCondition.conditionType().any()
        );
        assertEquals(condition, this.fromJson("{\"position\":{\"y\":{\"min\":0.0,\"max\":20.0},\"z\":3.0},\"feature\":\"minecraft:buried_treasure\",\"dimension\":\"minecraft:overworld\",\"block\":{\"blocks\":[\"minecraft:stone\"]}}", LocationCondition.class));
    }

    @Test
    void testAnyLocationCondition() {
        final JsonObject obj = new JsonObject();
        obj.add("position", JsonNull.INSTANCE);
        this.anyTest(obj, LocationCondition.class);
        this.anyTest("null", LocationCondition.class);
        this.anyTest("{ \"position\": { \"x\": null }, \"block\": null }", LocationCondition.class);
    }
}
