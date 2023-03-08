package me.machinemaker.advancements.conditions.world;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import me.machinemaker.advancements.GsonHelper;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.builders.Builders;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.adapters.util.IgnoreRecordTypeAdapter;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.blocks.BlockCondition;
import me.machinemaker.advancements.conditions.blocks.FluidCondition;
import me.machinemaker.advancements.conditions.blocks.LightCondition;
import me.machinemaker.advancements.ranges.DoubleRange;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.structure.Structure;
import org.bukkit.util.Vector;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

public interface LocationCondition extends Condition<LocationCondition> {

    public static final LocationCondition ANY = new LocationCondition(DoubleRange.conditionType().any(), DoubleRange.conditionType().any(), DoubleRange.conditionType().any(), null, null, null, null, LightCondition.conditionType().any(), BlockCondition.conditionType().any(), FluidCondition.conditionType().any());

    @Contract(value = "_ -> new", pure = true)
    public static LocationCondition atLocation(final Location location) {
        Preconditions.checkArgument(location.getWorld() != null, "Must be a location with a loaded world");
        final Vector vector = location.toVector();
        return new LocationCondition(DoubleRange.isExactly(vector.getX()), DoubleRange.isExactly(vector.getY()), DoubleRange.isExactly(vector.getZ()), null, null, location.getWorld().getKey(), null, LightCondition.conditionType().any(), BlockCondition.conditionType().any(), FluidCondition.conditionType().any());
    }

    @Contract(value = "_ -> new", pure = true)
    public static LocationCondition inWorld(final World world) {
        return new LocationCondition(DoubleRange.conditionType().any(), DoubleRange.conditionType().any(), DoubleRange.conditionType().any(), null, null, world.getKey(), null, LightCondition.conditionType().any(), BlockCondition.conditionType().any(), FluidCondition.conditionType().any());
    }

    @Contract(value = "_ -> new", pure = true)
    public static LocationCondition inBiome(final Biome biome) {
        return new LocationCondition(DoubleRange.conditionType().any(), DoubleRange.conditionType().any(), DoubleRange.conditionType().any(), biome, null, null, null, LightCondition.conditionType().any(), BlockCondition.conditionType().any(), FluidCondition.conditionType().any());
    }

    @Contract(value = "_ -> new", pure = true)
    public static LocationCondition atStructure(final Structure type) {
        return new LocationCondition(DoubleRange.conditionType().any(), DoubleRange.conditionType().any(), DoubleRange.conditionType().any(), null, type, null, null, LightCondition.conditionType().any(), BlockCondition.conditionType().any(), FluidCondition.conditionType().any());
    }

    // TODO update this Adapter
    static class Adapter extends TypeAdapter<LocationCondition> {

        private static final GsonHelper HELPER = new GsonHelper(BUILDER_APPLICABLE);

        @Override
        public void write(final JsonWriter out, final LocationCondition value) throws IOException {
            out.beginObject();
            out.name("position");
            if (value.x().isAny() && value.y().isAny() && value.z().isAny()) {
                out.nullValue();
            } else {
                out.beginObject();
                HELPER.toWriter(out, "x", value.x(), DoubleRange.class);
                HELPER.toWriter(out, "y", value.y(), DoubleRange.class);
                HELPER.toWriter(out, "z", value.z(), DoubleRange.class);
                out.endObject();
            }

            HELPER.toWriter(out, "biome", value.biome(), Biome.class);
            HELPER.toWriter(out, "feature", value.feature(), Structure.class);
            HELPER.toWriter(out, "dimension", value.dimension(), NamespacedKey.class);
            out.name("smokey").value(value.smokey());

            HELPER.toWriter(out, "light", value.light(), LightCondition.class);
            HELPER.toWriter(out, "block", value.block(), BlockCondition.class);
            HELPER.toWriter(out, "fluid", value.fluid(), FluidCondition.class);

            out.endObject();
        }

        @Override
        public LocationCondition read(final JsonReader in) throws IOException {
            final JsonObject object = HELPER.objectFromReader(in);
            final JsonElement position = object.get("position");
            final DoubleRange xRange;
            final DoubleRange yRange;
            final DoubleRange zRange;
            if (GsonHelper.isNull(position)) {
                xRange = yRange = zRange = DoubleRange.conditionType().any();
            } else {
                final JsonObject positionObject = position.getAsJsonObject();
                xRange = HELPER.getDefaulted(positionObject, "x", DoubleRange.class);
                yRange = HELPER.getDefaulted(positionObject, "y", DoubleRange.class);
                zRange = HELPER.getDefaulted(positionObject, "z", DoubleRange.class);
            }

            final @Nullable Biome biome = HELPER.getAs(object, "biome", Biome.class, null);
            final @Nullable Structure structure = HELPER.getAs(object, "feature", Structure.class, null);
            final @Nullable NamespacedKey key = HELPER.getAs(object, "dimension", NamespacedKey.class, null);
            final @Nullable Boolean smokey = HELPER.getAs(object, "smokey", Boolean.class, null);

            final LightCondition light = HELPER.getDefaulted(object, "light", LightCondition.class);
            final BlockCondition block = HELPER.getDefaulted(object, "block", BlockCondition.class);
            final FluidCondition fluid = HELPER.getDefaulted(object, "fluid", FluidCondition.class);

            return new LocationCondition(xRange, yRange, zRange, biome, structure, key, smokey, light, block, fluid);
        }
    }

}
