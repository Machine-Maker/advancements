package me.machinemaker.datapacks.advancements.conditions.block;

import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.tags.FluidTag;
import org.bukkit.Fluid;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

record FluidConditionImpl(
    @Nullable FluidTag tag,
    @Nullable Fluid fluid,
    BlockPropertyCondition state
) implements FluidCondition {

    static final FluidCondition ANY = new FluidConditionImpl(null, null, BlockPropertyCondition.conditionType().any());
    static final ConditionType<FluidCondition> TYPE = ConditionType.create(FluidCondition.class, ANY, FluidConditionImpl.class);

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "FluidCondition{ANY}";
        }
        return "FluidCondition{" + "tag=" + this.tag + ", fluid=" + this.fluid + ", state=" + this.state + '}';
    }

    static final class BuilderImpl implements FluidCondition.Builder {

        private @Nullable FluidTag tag;
        private @Nullable Fluid fluid;
        private BlockPropertyCondition state = BlockPropertyCondition.conditionType().any();

        BuilderImpl() {
        }

        BuilderImpl(final FluidCondition condition) {
            this.tag = condition.tag();
            this.fluid = condition.fluid();
            this.state = condition.state();
        }

        public @Nullable FluidTag tag() {
            return this.tag;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public FluidCondition.Builder tag(final @Nullable FluidTag tag) {
            this.tag = tag;
            return this;
        }

        public @Nullable Fluid fluid() {
            return this.fluid;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public FluidCondition.Builder fluid(final @Nullable Fluid fluid) {
            this.fluid = fluid;
            return this;
        }

        public BlockPropertyCondition state() {
            return this.state;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public FluidCondition.Builder state(final BlockPropertyCondition state) {
            this.state = state;
            return this;
        }

        @Override
        public FluidCondition build() {
            return new FluidConditionImpl(this.tag, this.fluid, this.state);
        }
    }
}
