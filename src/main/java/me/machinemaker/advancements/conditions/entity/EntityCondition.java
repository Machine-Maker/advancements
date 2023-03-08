package me.machinemaker.advancements.conditions.entity;

import java.util.Objects;
import me.machinemaker.advancements.adapters.builders.Builders;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.ConditionType;
import me.machinemaker.advancements.conditions.effects.PotionEffectsCondition;
import me.machinemaker.advancements.conditions.misc.NBTCondition;
import me.machinemaker.advancements.conditions.world.DistanceCondition;
import me.machinemaker.advancements.conditions.world.LocationCondition;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

public final class EntityCondition implements Condition<EntityCondition> {

    public static final GsonBuilderApplicable BUILDER_APPLICABLE = Builders.collection(EntityTypeCondition.BUILDER_APPLICABLE, LocationCondition.BUILDER_APPLICABLE);
    public static final EntityCondition ANY = new EntityCondition(EntityTypeCondition.ANY, DistanceCondition.conditionType().any(), LocationCondition.ANY, LocationCondition.ANY, PotionEffectsCondition.ANY, NBTCondition.conditionType().any(), EntityFlagsCondition.ANY, EntityEquipmentCondition.ANY, EntitySubCondition.ANY, null);

    private final EntityTypeCondition type;
    private final DistanceCondition distance;
    private final LocationCondition location;
    private final LocationCondition steppingOn;
    private final PotionEffectsCondition effects;
    private final NBTCondition nbt;
    private final EntityFlagsCondition flags;
    private final EntityEquipmentCondition equipment;
    private final EntitySubCondition typeSpecific;
    private final EntityCondition vehicle;
    private final EntityCondition passenger;
    private final EntityCondition targetedEntity;
    private final @Nullable String team;

    public EntityCondition(final EntityTypeCondition type, final DistanceCondition distance, final LocationCondition location, final LocationCondition steppingOn, final PotionEffectsCondition effects, final NBTCondition nbt, final EntityFlagsCondition flags, final EntityEquipmentCondition equipment, final EntitySubCondition typeSpecific, final EntityCondition vehicle, final EntityCondition passenger, final EntityCondition targetedEntity, final @Nullable String team) {
        this.type = type;
        this.distance = distance;
        this.location = location;
        this.steppingOn = steppingOn;
        this.effects = effects;
        this.nbt = nbt;
        this.flags = flags;
        this.equipment = equipment;
        this.typeSpecific = typeSpecific;
        this.vehicle = vehicle;
        this.passenger = passenger;
        this.targetedEntity = targetedEntity;
        this.team = team;
    }

    private EntityCondition(final EntityTypeCondition type, final DistanceCondition distance, final LocationCondition location, final LocationCondition steppingOn, final PotionEffectsCondition effects, final NBTCondition nbt, final EntityFlagsCondition flags, final EntityEquipmentCondition equipment, final EntitySubCondition typeSpecific, final @Nullable String team) {
        this.type = type;
        this.distance = distance;
        this.location = location;
        this.steppingOn = steppingOn;
        this.effects = effects;
        this.nbt = nbt;
        this.flags = flags;
        this.equipment = equipment;
        this.typeSpecific = typeSpecific;
        this.vehicle = this;
        this.passenger = this;
        this.targetedEntity = this;
        this.team = team;
    }

    @Contract(value = " -> new", pure = true)
    static EntityCondition.Builder builder() {
        return new EntityCondition.Builder();
    }

    @Override
    public EntityCondition any() {
        return ANY;
    }

    public EntityTypeCondition type() {
        return this.type;
    }

    public DistanceCondition distance() {
        return this.distance;
    }

    public LocationCondition location() {
        return this.location;
    }

    public LocationCondition steppingOn() {
        return this.steppingOn;
    }

    public PotionEffectsCondition effects() {
        return this.effects;
    }

    public NBTCondition nbt() {
        return this.nbt;
    }

    public EntityFlagsCondition flags() {
        return this.flags;
    }

    public EntityEquipmentCondition equipment() {
        return this.equipment;
    }

    public EntitySubCondition typeSpecific() {
        return this.typeSpecific;
    }

    public EntityCondition vehicle() {
        return this.vehicle;
    }

    public EntityCondition passenger() {
        return this.passenger;
    }

    public EntityCondition targetedEntity() {
        return this.targetedEntity;
    }

    public @Nullable String team() {
        return this.team;
    }

    @Override
    public boolean equals(final @Nullable Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        final var that = (EntityCondition) obj;
        return Objects.equals(this.type, that.type) &&
            Objects.equals(this.distance, that.distance) &&
            Objects.equals(this.location, that.location) &&
            Objects.equals(this.steppingOn, that.steppingOn) &&
            Objects.equals(this.effects, that.effects) &&
            Objects.equals(this.nbt, that.nbt) &&
            Objects.equals(this.flags, that.flags) &&
            Objects.equals(this.equipment, that.equipment) &&
            Objects.equals(this.typeSpecific, that.typeSpecific) &&
            Objects.equals(this.vehicle, that.vehicle) &&
            Objects.equals(this.passenger, that.passenger) &&
            Objects.equals(this.targetedEntity, that.targetedEntity) &&
            Objects.equals(this.team, that.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.distance, this.location, this.steppingOn, this.effects, this.nbt, this.flags, this.equipment, this.typeSpecific, this.vehicle, this.passenger, this.targetedEntity, this.team);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "EntityCondition{ANY}";
        }
        return "EntityCondition{" +
            "type=" + this.type +
            ", distance=" + this.distance +
            ", location=" + this.location +
            ", steppingOn=" + this.steppingOn +
            ", effects=" + this.effects +
            ", nbt=" + this.nbt +
            ", flags=" + this.flags +
            ", equipment=" + this.equipment +
            ", typeSpecific=" + this.typeSpecific +
            ", vehicle=" + this.vehicle +
            ", passenger=" + this.passenger +
            ", targetedEntity=" + this.targetedEntity +
            ", team='" + this.team + '\'' +
            '}';
    }

    public static final class Builder implements Condition.Builder<EntityCondition> {

        private EntityTypeCondition type = EntityTypeCondition.ANY;
        private DistanceCondition distance = DistanceCondition.conditionType().any();
        private LocationCondition location = LocationCondition.ANY;
        private LocationCondition steppingOn = LocationCondition.ANY;
        private PotionEffectsCondition effects = PotionEffectsCondition.ANY;
        private NBTCondition nbt = NBTCondition.conditionType().any();
        private EntityFlagsCondition flags = EntityFlagsCondition.ANY;
        private EntityEquipmentCondition equipment = EntityEquipmentCondition.ANY;
        private EntitySubCondition typeSpecific = EntitySubCondition.ANY;
        private EntityCondition vehicle = EntityCondition.ANY;
        private EntityCondition passenger = EntityCondition.ANY;
        private EntityCondition targetedEntity = EntityCondition.ANY;
        private @Nullable String team;

        private Builder() {
        }

        private Builder(final EntityCondition other) {
            this.type = other.type();
            this.distance = other.distance();
            this.location = other.location();
            this.steppingOn = other.steppingOn();
            this.effects = other.effects();
            this.nbt = other.nbt();
            this.flags = other.flags();
            this.equipment = other.equipment();
            this.typeSpecific = other.typeSpecific();
            this.vehicle = other.vehicle();
            this.passenger = other.passenger();
            this.targetedEntity = other.targetedEntity();
            this.team = other.team();
        }

        public EntityTypeCondition type() {
            return this.type;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder type(final EntityTypeCondition type) {
            this.type = type;
            return this;
        }

        public DistanceCondition distance() {
            return this.distance;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder distance(final DistanceCondition distance) {
            this.distance = distance;
            return this;
        }

        public LocationCondition location() {
            return this.location;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder location(final LocationCondition location) {
            this.location = location;
            return this;
        }

        public LocationCondition steppingOn() {
            return this.steppingOn;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder steppingOn(final LocationCondition steppingOn) {
            this.steppingOn = steppingOn;
            return this;
        }

        public PotionEffectsCondition effects() {
            return this.effects;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder effects(final PotionEffectsCondition effects) {
            this.effects = effects;
            return this;
        }

        public NBTCondition nbt() {
            return this.nbt;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder nbt(final NBTCondition nbt) {
            this.nbt = nbt;
            return this;
        }

        public EntityFlagsCondition flags() {
            return this.flags;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder flags(final EntityFlagsCondition flags) {
            this.flags = flags;
            return this;
        }

        public EntityEquipmentCondition equipment() {
            return this.equipment;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder equipment(final EntityEquipmentCondition equipment) {
            this.equipment = equipment;
            return this;
        }

        public EntitySubCondition typeSpecific() {
            return this.typeSpecific;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder typeSpecific(final EntitySubCondition typeSpecific) {
            this.typeSpecific = typeSpecific;
            return this;
        }

        public EntityCondition vehicle() {
            return this.vehicle;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder vehicle(final EntityCondition vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public EntityCondition passenger() {
            return this.passenger;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder passenger(final EntityCondition passenger) {
            this.passenger = passenger;
            return this;
        }

        public EntityCondition targetedEntity() {
            return this.targetedEntity;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder targetedEntity(final EntityCondition targetedEntity) {
            this.targetedEntity = targetedEntity;
            return this;
        }

        public @Nullable String team() {
            return this.team;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder team(final @Nullable String team) {
            this.team = team;
            return this;
        }

        @Override
        public EntityCondition build() {
            return new EntityCondition(this.type, this.distance, this.location, this.steppingOn, this.effects, this.nbt, this.flags, this.equipment, this.typeSpecific, this.vehicle, this.passenger, this.targetedEntity, this.team);
        }
    }
}
