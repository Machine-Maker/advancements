package me.machinemaker.advancements.conditions.world;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import io.papermc.paper.world.structure.ConfiguredStructure;
import me.machinemaker.advancements.GsonTestBase;
import me.machinemaker.advancements.conditions.blocks.BlockCondition;
import me.machinemaker.advancements.conditions.blocks.FluidCondition;
import me.machinemaker.advancements.conditions.blocks.LightCondition;
import me.machinemaker.advancements.ranges.DoubleRange;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.StructureType;
import org.bukkit.World;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationConditionTest extends GsonTestBase {

    @Test
    void testLocationConditionWithPositionAndWorld() {
        World world = Mockito.mock(World.class);
        Mockito.when(world.getKey()).thenReturn(NamespacedKey.minecraft("overworld"));
        LocationCondition condition = LocationCondition.atLocation(new Location(world, 33, 34, 33));
        JsonObject obj = new JsonObject();
        JsonObject position = new JsonObject();
        position.addProperty("x", 33.0);
        position.addProperty("y", 34.0);
        position.addProperty("z", 33.0);
        obj.add("position", position);
        obj.addProperty("dimension", world.getKey().toString());
        assertEquals(obj, tree(condition));
        assertEquals(condition, fromJson(obj, LocationCondition.class));
    }

    @Test
    void testLocationCondition() {
        LocationCondition condition = new LocationCondition(
        DoubleRange.ANY,
        DoubleRange.isBetween(0, 20),
        DoubleRange.isExactly(3),
        null,
        ConfiguredStructure.BURIED_TREASURE,
        NamespacedKey.minecraft("overworld"),
        null,
        LightCondition.ANY,
        BlockCondition.forBlocks(Material.STONE),
        FluidCondition.ANY
        );
        assertEquals(condition, fromJson("{\"position\":{\"y\":{\"min\":0.0,\"max\":20.0},\"z\":3.0},\"feature\":\"minecraft:buried_treasure\",\"dimension\":\"minecraft:overworld\",\"block\":{\"blocks\":[\"minecraft:stone\"]}}", LocationCondition.class));
    }

    @Test
    void testAnyLocationCondition() {
        JsonObject obj = new JsonObject();
        obj.add("position", JsonNull.INSTANCE);
        anyTest(obj, LocationCondition.class);
        anyTest("null", LocationCondition.class);
        anyTest("{ \"position\": { \"x\": null }, \"block\": null }", LocationCondition.class);
    }
}
