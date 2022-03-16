package me.machinemaker.advancements.conditions.world;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import me.machinemaker.advancements.GsonHelper;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.GsonBuilderApplicable;
import me.machinemaker.advancements.adapters.util.IgnoreRecordTypeAdapter;
import me.machinemaker.advancements.conditions.blocks.BlockCondition;
import me.machinemaker.advancements.ranges.DoubleRange;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.blocks.FluidCondition;
import me.machinemaker.advancements.conditions.blocks.LightCondition;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.StructureType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.util.Vector;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;

@IgnoreRecordTypeAdapter
@JsonAdapter(value = LocationCondition.Adapter.class, nullSafe = false)
public record LocationCondition(
        DoubleRange x,
        DoubleRange y,
        DoubleRange z,
        @Nullable Biome biome,
        @Nullable StructureType feature,
        @Nullable NamespacedKey dimension,
        @Nullable Boolean smokey,
        LightCondition light,
        BlockCondition block,
        FluidCondition fluid
) implements Condition<LocationCondition> {

    public static final GsonBuilderApplicable BUILDER_APPLICABLE = Adapters.of(Adapters.BIOME_ADAPTER, Adapters.STRUCTURE_TYPE_ADAPTER, Adapters.NAMESPACED_KEY_ADAPTER, BlockCondition.BUILDER_APPLICABLE, FluidCondition.BUILDER_APPLICABLE);
    public static final LocationCondition ANY = new LocationCondition(DoubleRange.ANY, DoubleRange.ANY, DoubleRange.ANY, null, null, null, null, LightCondition.ANY, BlockCondition.ANY, FluidCondition.ANY);

    public static LocationCondition atLocation(Location location) {
        Preconditions.checkArgument(location.getWorld() != null, "Must be a location with a loaded world");
        Vector vector = location.toVector();
        return new LocationCondition(DoubleRange.isExactly(vector.getX()), DoubleRange.isExactly(vector.getY()), DoubleRange.isExactly(vector.getZ()), null, null, location.getWorld().getKey(), null, LightCondition.ANY, BlockCondition.ANY, FluidCondition.ANY);
    }

    public static LocationCondition inWorld(World world) {
        return new LocationCondition(DoubleRange.ANY, DoubleRange.ANY, DoubleRange.ANY, null, null, world.getKey(), null, LightCondition.ANY, BlockCondition.ANY, FluidCondition.ANY);
    }

    public static LocationCondition inBiome(Biome biome) {
        return new LocationCondition(DoubleRange.ANY, DoubleRange.ANY, DoubleRange.ANY, biome, null, null, null, LightCondition.ANY, BlockCondition.ANY, FluidCondition.ANY);
    }

    public static LocationCondition atStructure(StructureType type) {
        return new LocationCondition(DoubleRange.ANY, DoubleRange.ANY, DoubleRange.ANY, null, type, null, null, LightCondition.ANY, BlockCondition.ANY, FluidCondition.ANY);
    }

    @Override
    public LocationCondition any() {
        return ANY;
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "LocationCondition{ANY}";
        }
        return "LocationCondition{" +
                "x=" + this.x +
                ", y=" + this.y +
                ", z=" + this.z +
                ", biome=" + this.biome +
                ", feature=" + this.feature +
                ", dimension=" + this.dimension +
                ", smokey=" + this.smokey +
                ", light=" + this.light +
                ", block=" + this.block +
                ", fluid=" + this.fluid +
                '}';
    }

    // TODO update this Adapter
    static class Adapter extends TypeAdapter<LocationCondition> {

        private static final GsonHelper HELPER = new GsonHelper(BUILDER_APPLICABLE);

        @Override
        public void write(JsonWriter out, LocationCondition value) throws IOException {
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
            HELPER.toWriter(out, "feature", value.feature(), StructureType.class);
            HELPER.toWriter(out, "dimension", value.dimension(), NamespacedKey.class);
            out.name("smokey").value(value.smokey());

            HELPER.toWriter(out, "light", value.light(), LightCondition.class);
            HELPER.toWriter(out, "block", value.block(), BlockCondition.class);
            HELPER.toWriter(out, "fluid", value.fluid(), FluidCondition.class);

            out.endObject();
        }

        @Override
        public LocationCondition read(JsonReader in) throws IOException {
            JsonObject object = HELPER.objectFromReader(in);
            JsonElement position = object.get("position");
            DoubleRange xRange;
            DoubleRange yRange;
            DoubleRange zRange;
            if (GsonHelper.isNull(position)) {
                xRange = yRange = zRange = DoubleRange.ANY;
            } else {
                JsonObject positionObject = position.getAsJsonObject();
                xRange = HELPER.getDefaulted(positionObject, "x", DoubleRange.class);
                yRange = HELPER.getDefaulted(positionObject, "y", DoubleRange.class);
                zRange = HELPER.getDefaulted(positionObject, "z", DoubleRange.class);
            }

            final @Nullable Biome biome = HELPER.getAs(object, "biome", Biome.class, null);
            final @Nullable StructureType feature = HELPER.getAs(object, "feature", StructureType.class, null);
            final @Nullable NamespacedKey key = HELPER.getAs(object, "dimension", NamespacedKey.class, null);
            final @Nullable Boolean smokey = HELPER.getAs(object, "smokey", Boolean.class, null);

            LightCondition light = HELPER.getDefaulted(object, "light", LightCondition.class);
            BlockCondition block = HELPER.getDefaulted(object, "block", BlockCondition.class);
            FluidCondition fluid = HELPER.getDefaulted(object, "fluid", FluidCondition.class);

            return new LocationCondition(xRange, yRange, zRange, biome, feature, key, smokey, light, block, fluid);
        }
    }

}
