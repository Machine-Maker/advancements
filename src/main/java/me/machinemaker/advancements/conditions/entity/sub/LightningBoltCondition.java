package me.machinemaker.advancements.conditions.entity.sub;

import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.entity.EntityCondition;
import me.machinemaker.advancements.conditions.range.IntegerRange;
import me.machinemaker.advancements.util.Buildable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.NonExtendable
public interface LightningBoltCondition extends EntitySubCondition, Buildable<LightningBoltCondition, LightningBoltCondition.Builder> {

    @Contract(value = " -> new", pure = true)
    static Builder builder() {
        return new LightningBoltConditionImpl.BuilderImpl();
    }

    @Contract(pure = true)
    IntegerRange blocksSetOnFire();

    @Contract(pure = true)
    EntityCondition entityStruck();

    @ApiStatus.NonExtendable
    interface Builder extends Condition.Builder<LightningBoltCondition> {

        @Contract(pure = true)
        IntegerRange blocksSetOnFire();

        @Contract(value = "_ -> this", mutates = "this")
        Builder blocksSetOnFire(IntegerRange blocksSetOnFire);

        @Contract(pure = true)
        EntityCondition entityStruck();

        @Contract(value = "_ -> this", mutates = "this")
        Builder entityStruck(EntityCondition entityStruck);
    }
}
