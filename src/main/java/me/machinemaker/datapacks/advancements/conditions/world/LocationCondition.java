package me.machinemaker.datapacks.advancements.conditions.world;

import com.google.common.base.Preconditions;
import me.machinemaker.datapacks.advancements.conditions.Condition;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.conditions.block.BlockCondition;
import me.machinemaker.datapacks.advancements.conditions.block.FluidCondition;
import me.machinemaker.datapacks.advancements.conditions.block.LightCondition;
import me.machinemaker.datapacks.advancements.conditions.range.DoubleRange;
import net.kyori.adventure.key.Key;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.structure.Structure;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.NonExtendable
public interface LocationCondition extends Condition.Buildable<LocationCondition, LocationCondition.Builder> {

    @Contract(pure = true)
    static ConditionType<LocationCondition> conditionType() {
        return LocationConditionImpl.TYPE;
    }

    @Contract(value = "_ -> new", pure = true)
    static LocationCondition atLocation(final Location loc) {
        Preconditions.checkArgument(loc.getWorld() != null, "Must be a location with a loaded world");
        return builder()
            .x(DoubleRange.isExactly(loc.x()))
            .y(DoubleRange.isExactly(loc.y()))
            .z(DoubleRange.isExactly(loc.z()))
            .dimension(loc.getWorld().key())
            .build();
    }

    @Contract(value = "_ -> new", pure = true)
    static LocationCondition inWorld(final World world) {
        return builder().dimension(world.key()).build();
    }

    @Contract(value = "_ -> new", pure = true)
    static LocationCondition inBiome(final Biome biome) {
        return builder().biome(biome).build();
    }

    @Contract(value = "_ -> new", pure = true)
    static LocationCondition atStructure(final Structure structure) {
        return builder().structure(structure).build();
    }

    @Contract(value = "-> new", pure = true)
    static Builder builder() {
        return new LocationConditionImpl.BuilderImpl();
    }

    @Contract(pure = true)
    DoubleRange x();

    @Contract(pure = true)
    DoubleRange y();

    @Contract(pure = true)
    DoubleRange z();

    @Contract(pure = true)
    @Nullable Biome biome();

    @Contract(pure = true)
    @Nullable Structure structure();

    @Contract(pure = true)
    @Nullable Key dimension();

    @Contract(pure = true)
    @Nullable Boolean smokey();

    @Contract(pure = true)
    LightCondition light();

    @Contract(pure = true)
    BlockCondition block();

    @Contract(pure = true)
    FluidCondition fluid();

    @ApiStatus.NonExtendable
    interface Builder extends Condition.Builder<LocationCondition> {

        @Contract(pure = true)
        DoubleRange x();

        @Contract(value = "_ -> this", mutates = "this")
        Builder x(DoubleRange x);

        @Contract(pure = true)
        DoubleRange y();

        @Contract(value = "_ -> this", mutates = "this")
        Builder y(DoubleRange y);

        @Contract(pure = true)
        DoubleRange z();

        @Contract(value = "_ -> this", mutates = "this")
        Builder z(DoubleRange z);

        @Contract(pure = true)
        @Nullable Biome biome();

        @Contract(value = "_ -> this", mutates = "this")
        Builder biome(@Nullable Biome biome);

        @Contract(pure = true)
        @Nullable Structure structure();

        @Contract(value = "_ -> this", mutates = "this")
        Builder structure(@Nullable Structure structure);

        @Contract(pure = true)
        @Nullable Key dimension();

        @Contract(value = "_ -> this", mutates = "this")
        Builder dimension(@Nullable Key dimension);

        @Contract(pure = true)
        @Nullable Boolean smokey();

        @Contract(value = "_ -> this", mutates = "this")
        Builder smokey(@Nullable Boolean smokey);

        @Contract(pure = true)
        LightCondition light();

        @Contract(value = "_ -> this", mutates = "this")
        Builder light(LightCondition light);

        @Contract(pure = true)
        BlockCondition block();

        @Contract(value = "_ -> this", mutates = "this")
        Builder block(BlockCondition block);

        @Contract(pure = true)
        FluidCondition fluid();

        @Contract(value = "_ -> this", mutates = "this")
        Builder fluid(FluidCondition fluid);
    }
}
