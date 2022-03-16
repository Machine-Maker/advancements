package me.machinemaker.advancements.conditions.blocks;

import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.tags.FluidTag;
import me.machinemaker.advancements.util.Buildable;
import org.bukkit.Fluid;
import org.bukkit.Tag;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

public record FluidCondition(
        @Nullable FluidTag tag,
        @Nullable Fluid fluid,
        PropertyCondition state
) implements Condition<FluidCondition>, Buildable<FluidCondition, FluidCondition.Builder> {

    public static final GsonBuilderApplicable BUILDER_APPLICABLE = Adapters.of(Adapters.FLUID_TAG_ADAPTER, Adapters.FLUID_ADAPTER);
    public static final FluidCondition ANY = new FluidCondition(null, null, PropertyCondition.ANY);

    @Contract(value = "_ -> new", pure = true)
    public static FluidCondition forFluid(Fluid fluid) {
        return new FluidCondition(null, fluid, PropertyCondition.ANY);
    }

    @Contract(value = "_ -> new", pure = true)
    public static FluidCondition forTag(Tag<Fluid> tag) {
        return new FluidCondition(tag instanceof FluidTag fluidTag ? fluidTag : new FluidTag(tag), null, PropertyCondition.ANY);
    }

    @Override
    public FluidCondition any() {
        return ANY;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "FluidCondition{ANY}";
        }
        return "FluidCondition{" +
                "tag=" + this.tag +
                ", fluid=" + this.fluid +
                ", state=" + this.state +
                '}';
    }

    @Contract(value = "_ -> new", pure = true)
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements Condition.Builder<FluidCondition> {
        private @Nullable FluidTag tag;
        private @Nullable Fluid fluid;
        private PropertyCondition state = PropertyCondition.ANY;

        private Builder() {
        }

        private Builder(FluidCondition condition) {
            this.tag = condition.tag;
            this.fluid = condition.fluid;
            this.state = condition.state;
        }

        public @Nullable FluidTag tag() {
            return this.tag;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder tag(@Nullable FluidTag tag) {
            this.tag = tag;
            return this;
        }

        public @Nullable Fluid fluid() {
            return this.fluid;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder fluid(@Nullable Fluid fluid) {
            this.fluid = fluid;
            return this;
        }

        public PropertyCondition state() {
            return this.state;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder state(PropertyCondition state) {
            this.state = state;
            return this;
        }

        @Override
        public FluidCondition build() {
            return new FluidCondition(this.tag, this.fluid, this.state);
        }
    }
}
