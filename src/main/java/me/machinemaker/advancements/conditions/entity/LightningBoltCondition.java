package me.machinemaker.advancements.conditions.entity;

import me.machinemaker.advancements.adapters.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.ranges.IntegerRange;
import me.machinemaker.advancements.util.Buildable;
import org.jetbrains.annotations.Contract;

// TODO tests
public record LightningBoltCondition(IntegerRange blocksSetOnFire, EntityCondition entityStruck) implements Condition<LightningBoltCondition>, Buildable<LightningBoltCondition, LightningBoltCondition.Builder> {

    public static final LightningBoltCondition ANY = new Builder().build();
    public static final GsonBuilderApplicable BUILDER_APPLICABLE = EntityCondition.BUILDER_APPLICABLE; // must be declared AFTER the ANY field

    @Override
    public LightningBoltCondition any() {
        return ANY;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "LightningBoltCondition{ANY}";
        }
        return "LightningBoltCondition{" +
                "blocksSetOnFire=" + this.blocksSetOnFire +
                ", entityStruck=" + this.entityStruck +
                '}';
    }

    @Contract(value = " -> new", pure = true)
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements Condition.Builder<LightningBoltCondition> {

        private IntegerRange blocksSetOnFire = IntegerRange.ANY;
        private EntityCondition entityStruck = EntityCondition.Impl.delegate(() -> EntityCondition.ANY);
        
        private Builder() {}
        
        public Builder(LightningBoltCondition condition) {
            this.blocksSetOnFire = condition.blocksSetOnFire;
            this.entityStruck = condition.entityStruck;
        }

        public IntegerRange blocksSetOnFire() {
            return this.blocksSetOnFire;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder blocksSetOnFire(IntegerRange blocksSetOnFire) {
            this.blocksSetOnFire = blocksSetOnFire;
            return this;
        }

        public EntityCondition entityStruck() {
            return this.entityStruck;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder entityStruck(EntityCondition entityStruck) {
            this.entityStruck = entityStruck;
            return this;
        }

        @Override
        public LightningBoltCondition build() {
            return new LightningBoltCondition(this.blocksSetOnFire, this.entityStruck);
        }
    }
}
