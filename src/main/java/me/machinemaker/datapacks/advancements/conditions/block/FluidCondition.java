package me.machinemaker.datapacks.advancements.conditions.block;

import me.machinemaker.datapacks.advancements.conditions.Condition;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.toremove.tags.FluidTag;
import org.bukkit.Fluid;
import org.bukkit.Tag;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

public interface FluidCondition extends Condition.Buildable<FluidCondition, FluidCondition.Builder> {

    @Contract(pure = true)
    static ConditionType<FluidCondition> conditionType() {
        return FluidConditionImpl.TYPE;
    }

    @Contract(value = "_ -> new", pure = true)
    static FluidCondition forFluid(final Fluid fluid) {
        return new FluidConditionImpl(null, fluid, BlockPropertyCondition.conditionType().any());
    }

    @Contract(value = "_ -> new", pure = true)
    static FluidCondition forTag(final Tag<Fluid> tag) {
        return new FluidConditionImpl(tag instanceof FluidTag fluidTag ? fluidTag : new FluidTag(tag), null, BlockPropertyCondition.conditionType().any());
    }

    @Contract(value = "-> new", pure = true)
    static Builder builder() {
        return new FluidConditionImpl.BuilderImpl();
    }

    @Contract(pure = true)
    @Nullable FluidTag tag();

    @Contract(pure = true)
    @Nullable Fluid fluid();

    @Contract(pure = true)
    BlockPropertyCondition state();

    interface Builder extends Condition.Builder<FluidCondition> {

        @Contract(pure = true)
        @Nullable FluidTag tag();

        @Contract(value = "_ -> this", mutates = "this")
        Builder tag(@Nullable FluidTag tag);

        @Contract(pure = true)
        @Nullable Fluid fluid();

        @Contract(value = "_ -> this", mutates = "this")
        Builder fluid(@Nullable Fluid fluid);

        @Contract(pure = true)
        BlockPropertyCondition state();

        @Contract(value = "_ -> this", mutates = "this")
        Builder state(BlockPropertyCondition state);
    }
}
