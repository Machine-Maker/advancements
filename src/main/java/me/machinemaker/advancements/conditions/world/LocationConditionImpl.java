package me.machinemaker.advancements.conditions.world;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.builders.Builders;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.ConditionType;
import me.machinemaker.advancements.conditions.block.BlockCondition;
import me.machinemaker.advancements.conditions.block.FluidCondition;
import me.machinemaker.advancements.conditions.block.LightCondition;
import me.machinemaker.advancements.conditions.range.DoubleRange;
import net.kyori.adventure.key.Key;
import org.bukkit.block.Biome;
import org.bukkit.generator.structure.Structure;
import org.checkerframework.checker.nullness.qual.Nullable;

import static me.machinemaker.advancements.util.GsonUtils.deserializeCondition;
import static me.machinemaker.advancements.util.GsonUtils.deserializeFrom;
import static me.machinemaker.advancements.util.GsonUtils.isNull;

record LocationConditionImpl(
    DoubleRange x,
    DoubleRange y,
    DoubleRange z,
    @Nullable Biome biome,
    @Nullable Structure structure,
    @Nullable Key dimension,
    @Nullable Boolean smokey,
    LightCondition light,
    BlockCondition block,
    FluidCondition fluid
) implements LocationCondition {

    static final LocationCondition ANY = new LocationConditionImpl(DoubleRange.conditionType().any(), DoubleRange.conditionType().any(), DoubleRange.conditionType().any(), null, null, null, null, LightCondition.conditionType().any(), BlockCondition.conditionType().any(), FluidCondition.conditionType().any());
    static final ConditionType<LocationCondition> TYPE = ConditionType.create(LocationCondition.class, ANY, LocationCondition::requiredGson);
    static final GsonBuilderApplicable REQUIRED_GSON = Builders.collection(
        DoubleRange.requiredGson(),
        Adapters.BIOME_ADAPTER,
        Adapters.STRUCTURE_ADAPTER,
        Adapters.KEY_ADAPTER,
        LightCondition.requiredGson(),
        BlockCondition.requiredGson(),
        FluidCondition.requiredGson(),
        Builders.typeHierarchy(LocationCondition.class, new Adapter())
    );

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    //<editor-fold desc="toString" defaultstate="collapsed">
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
            ", structure=" + this.structure +
            ", dimension=" + this.dimension +
            ", smokey=" + this.smokey +
            ", light=" + this.light +
            ", block=" + this.block +
            ", fluid=" + this.fluid +
            '}';
    }
    //</editor-fold>

    //<editor-fold desc="BuilderImpl" defaultstate="collapsed">
    static final class BuilderImpl implements LocationCondition.Builder {

        private DoubleRange x = DoubleRange.conditionType().any();
        private DoubleRange y = DoubleRange.conditionType().any();
        private DoubleRange z = DoubleRange.conditionType().any();
        private @Nullable Biome biome = null;
        private @Nullable Structure structure = null;
        private @Nullable Key dimension = null;
        private @Nullable Boolean smokey = null;
        private LightCondition light = LightCondition.conditionType().any();
        private BlockCondition block = BlockCondition.conditionType().any();
        private FluidCondition fluid = FluidCondition.conditionType().any();

        BuilderImpl() {
        }

        BuilderImpl(final LocationCondition condition) {
            this.x = condition.x();
            this.y = condition.y();
            this.z = condition.z();
            this.biome = condition.biome();
            this.structure = condition.structure();
            this.dimension = condition.dimension();
            this.smokey = condition.smokey();
            this.light = condition.light();
            this.block = condition.block();
            this.fluid = condition.fluid();
        }

        @Override
        public DoubleRange x() {
            return this.x;
        }

        @Override
        public LocationCondition.Builder x(final DoubleRange x) {
            this.x = x;
            return this;
        }

        @Override
        public DoubleRange y() {
            return this.y;
        }

        @Override
        public LocationCondition.Builder y(final DoubleRange y) {
            this.y = y;
            return this;
        }

        @Override
        public DoubleRange z() {
            return this.z;
        }

        @Override
        public LocationCondition.Builder z(final DoubleRange z) {
            this.z = z;
            return this;
        }

        @Override
        public @Nullable Biome biome() {
            return this.biome;
        }

        @Override
        public LocationCondition.Builder biome(final @Nullable Biome biome) {
            this.biome = biome;
            return this;
        }

        @Override
        public @Nullable Structure structure() {
            return this.structure;
        }

        @Override
        public LocationCondition.Builder structure(final @Nullable Structure structure) {
            this.structure = structure;
            return this;
        }

        @Override
        public @Nullable Key dimension() {
            return this.dimension;
        }

        @Override
        public LocationCondition.Builder dimension(final @Nullable Key dimension) {
            this.dimension = dimension;
            return this;
        }

        @Override
        public @Nullable Boolean smokey() {
            return this.smokey;
        }

        @Override
        public LocationCondition.Builder smokey(final @Nullable Boolean smokey) {
            this.smokey = smokey;
            return this;
        }

        @Override
        public LightCondition light() {
            return this.light;
        }

        @Override
        public LocationCondition.Builder light(final LightCondition light) {
            this.light = light;
            return this;
        }

        @Override
        public BlockCondition block() {
            return this.block;
        }

        @Override
        public LocationCondition.Builder block(final BlockCondition block) {
            this.block = block;
            return this;
        }

        @Override
        public FluidCondition fluid() {
            return this.fluid;
        }

        @Override
        public LocationCondition.Builder fluid(final FluidCondition fluid) {
            this.fluid = fluid;
            return this;
        }

        @Override
        public LocationCondition build() {
            return new LocationConditionImpl(this.x, this.y, this.z, this.biome, this.structure, this.dimension, this.smokey, this.light, this.block, this.fluid);
        }
    }
    //</editor-fold>

    static final class Adapter extends TypeAdapter<LocationCondition> {

        private static final GsonBuilderApplicable GSON_REQUIRED = Builders.collection(
            DoubleRange.requiredGson(),
            Adapters.BIOME_ADAPTER,
            Adapters.STRUCTURE_ADAPTER,
            Adapters.KEY_ADAPTER,
            LightCondition.requiredGson(),
            BlockCondition.requiredGson(),
            FluidCondition.requiredGson()
        );
        static final Gson GSON;
        static {
            final GsonBuilder builder = new GsonBuilder();
            GSON_REQUIRED.applyTo(builder);
            GSON = builder.create();
        }

        @Override
        public void write(final JsonWriter out, final LocationCondition src) throws IOException {
            if (src.isAny()) {
                out.nullValue();
                return;
            }
            final JsonObject obj = new JsonObject();
            if (!src.x().isAny() || !src.y().isAny() || !src.z().isAny()) {
                final JsonObject position = new JsonObject();
                position.add("x", GSON.toJsonTree(src.x()));
                position.add("y", GSON.toJsonTree(src.y()));
                position.add("z", GSON.toJsonTree(src.z()));
                obj.add("position", position);
            }
            obj.add("biome", GSON.toJsonTree(src.biome(), Biome.class));
            obj.add("structure", GSON.toJsonTree(src.structure(), Structure.class));
            obj.add("dimension", GSON.toJsonTree(src.dimension(), Key.class));
            obj.addProperty("smokey", src.smokey());

            obj.add("light", GSON.toJsonTree(src.light()));
            obj.add("block", GSON.toJsonTree(src.block()));
            obj.add("fluid", GSON.toJsonTree(src.fluid()));
            Streams.write(obj, out);
        }

        @Override
        public LocationCondition read(final JsonReader in) {
            final JsonElement json = Streams.parse(in);
            if (json.isJsonNull()) {
                return ANY;
            }
            if (!(json instanceof JsonObject object)) {
                throw new JsonParseException("Expected object, got " + json);
            }
            final DoubleRange xRange;
            final DoubleRange yRange;
            final DoubleRange zRange;
            if (isNull(object, "position")) {
                xRange = yRange = zRange = DoubleRange.conditionType().any();
            } else {
                final JsonObject position = object.getAsJsonObject("position");
                xRange = deserializeCondition(GSON, position, "x", DoubleRange.class);
                yRange = deserializeCondition(GSON, position, "y", DoubleRange.class);
                zRange = deserializeCondition(GSON, position, "z", DoubleRange.class);
            }

            final @Nullable Biome biome = deserializeFrom(GSON, object, "biome", Biome.class);
            final @Nullable Structure structure = deserializeFrom(GSON, object, "structure", Structure.class);
            final @Nullable Key dimension = deserializeFrom(GSON, object, "dimension", Key.class);
            final @Nullable Boolean smokey = deserializeFrom(GSON, object, "smokey", Boolean.class);

            final LightCondition light = deserializeCondition(GSON, object, "light", LightCondition.class);
            final BlockCondition block = deserializeCondition(GSON, object, "block", BlockCondition.class);
            final FluidCondition fluid = deserializeCondition(GSON, object, "fluid", FluidCondition.class);

            final LocationCondition condition = new LocationConditionImpl(xRange, yRange, zRange, biome, structure, dimension, smokey, light, block, fluid);
            if (condition.isAny()) {
                return ANY;
            }
            return condition;
        }
    }
}
