package me.machinemaker.datapacks.advancements.conditions.entity;

import me.machinemaker.datapacks.advancements.conditions.Condition;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.conditions.effect.PotionEffectsCondition;
import me.machinemaker.datapacks.advancements.conditions.entity.sub.EntitySubCondition;
import me.machinemaker.datapacks.advancements.conditions.misc.NBTCondition;
import me.machinemaker.datapacks.advancements.conditions.world.DistanceCondition;
import me.machinemaker.datapacks.advancements.conditions.world.LocationCondition;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

public interface EntityCondition extends Condition.Buildable<EntityCondition, EntityCondition.Builder> {

    @Contract(pure = true)
    static ConditionType<EntityCondition> conditionType() {
        return EntityConditionImpl.TYPE;
    }

    @Contract(value = " -> new", pure = true)
    static EntityCondition.Builder builder() {
        return new EntityConditionImpl.BuilderImpl();
    }

    @Contract(pure = true)
    EntityTypeCondition type();

    @Contract(pure = true)
    DistanceCondition distance();

    @Contract(pure = true)
    LocationCondition location();

    @Contract(pure = true)
    LocationCondition steppingOn();

    @Contract(pure = true)
    PotionEffectsCondition effects();

    @Contract(pure = true)
    NBTCondition nbt();

    @Contract(pure = true)
    EntityFlagsCondition flags();

    @Contract(pure = true)
    EntityEquipmentCondition equipment();

    @Contract(pure = true)
    EntitySubCondition typeSpecific();

    @Contract(pure = true)
    EntityCondition vehicle();

    @Contract(pure = true)
    EntityCondition passenger();

    @Contract(pure = true)
    EntityCondition targetedEntity();

    @Contract(pure = true)
    @Nullable String team();

    interface Builder extends Condition.Builder<EntityCondition> {

        @Contract(pure = true)
        EntityTypeCondition type();

        @Contract(value = "_ -> this", mutates = "this")
        Builder type(final EntityTypeCondition type);

        @Contract(pure = true)
        DistanceCondition distance();

        @Contract(value = "_ -> this", mutates = "this")
        Builder distance(final DistanceCondition distance);

        @Contract(pure = true)
        LocationCondition location();

        @Contract(value = "_ -> this", mutates = "this")
        Builder location(final LocationCondition location);

        @Contract(pure = true)
        LocationCondition steppingOn();

        @Contract(value = "_ -> this", mutates = "this")
        Builder steppingOn(final LocationCondition steppingOn);

        @Contract(pure = true)
        PotionEffectsCondition effects();

        @Contract(value = "_ -> this", mutates = "this")
        Builder effects(final PotionEffectsCondition effects);

        @Contract(pure = true)
        NBTCondition nbt();

        @Contract(value = "_ -> this", mutates = "this")
        Builder nbt(final NBTCondition nbt);

        @Contract(pure = true)
        EntityFlagsCondition flags();

        @Contract(value = "_ -> this", mutates = "this")
        Builder flags(final EntityFlagsCondition flags);

        @Contract(pure = true)
        EntityEquipmentCondition equipment();

        @Contract(value = "_ -> this", mutates = "this")
        Builder equipment(final EntityEquipmentCondition equipment);

        @Contract(pure = true)
        EntitySubCondition typeSpecific();

        @Contract(value = "_ -> this", mutates = "this")
        Builder typeSpecific(final EntitySubCondition typeSpecific);

        @Contract(pure = true)
        EntityCondition vehicle();

        @Contract(value = "_ -> this", mutates = "this")
        Builder vehicle(final EntityCondition vehicle);

        @Contract(pure = true)
        EntityCondition passenger();

        @Contract(value = "_ -> this", mutates = "this")
        Builder passenger(final EntityCondition passenger);

        @Contract(pure = true)
        EntityCondition targetedEntity();

        @Contract(value = "_ -> this", mutates = "this")
        Builder targetedEntity(final EntityCondition targetedEntity);

        @Contract(pure = true)
        @Nullable String team();

        @Contract(value = "_ -> this", mutates = "this")
        Builder team(final @Nullable String team);
    }
}
