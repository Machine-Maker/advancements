package me.machinemaker.datapacks.advancements.conditions.entity;

import java.util.Objects;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.conditions.effect.PotionEffectsCondition;
import me.machinemaker.datapacks.advancements.conditions.entity.sub.EntitySubCondition;
import me.machinemaker.datapacks.advancements.conditions.misc.NBTCondition;
import me.machinemaker.datapacks.advancements.conditions.world.DistanceCondition;
import me.machinemaker.datapacks.advancements.conditions.world.LocationCondition;
import org.checkerframework.checker.nullness.qual.Nullable;

// can't be a record because the ANY instance needs to reference itself
final class EntityConditionImpl implements EntityCondition {

    static final EntityCondition ANY = new EntityConditionImpl(EntityTypeCondition.conditionType().any(), DistanceCondition.conditionType().any(), LocationCondition.conditionType().any(), LocationCondition.conditionType().any(), PotionEffectsCondition.conditionType().any(), NBTCondition.conditionType().any(), EntityFlagsCondition.conditionType().any(), EntityEquipmentCondition.conditionType().any(), EntitySubCondition.conditionType().any(), null);
    static final ConditionType<EntityCondition> TYPE = ConditionType.create(EntityCondition.class, ANY, EntityConditionImpl.class);

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

    //<editor-fold desc="Constructors" defaultstate="collapsed">
    private EntityConditionImpl(final EntityTypeCondition type, final DistanceCondition distance, final LocationCondition location, final LocationCondition steppingOn, final PotionEffectsCondition effects, final NBTCondition nbt, final EntityFlagsCondition flags, final EntityEquipmentCondition equipment, final EntitySubCondition typeSpecific, final EntityCondition vehicle, final EntityCondition passenger, final EntityCondition targetedEntity, final @Nullable String team) {
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

    private EntityConditionImpl(final EntityTypeCondition type, final DistanceCondition distance, final LocationCondition location, final LocationCondition steppingOn, final PotionEffectsCondition effects, final NBTCondition nbt, final EntityFlagsCondition flags, final EntityEquipmentCondition equipment, final EntitySubCondition typeSpecific, final @Nullable String team) {
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
    //</editor-fold>

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    public EntityTypeCondition type() {
        return this.type;
    }

    @Override
    public DistanceCondition distance() {
        return this.distance;
    }

    @Override
    public LocationCondition location() {
        return this.location;
    }

    @Override
    public LocationCondition steppingOn() {
        return this.steppingOn;
    }

    @Override
    public PotionEffectsCondition effects() {
        return this.effects;
    }

    @Override
    public NBTCondition nbt() {
        return this.nbt;
    }

    @Override
    public EntityFlagsCondition flags() {
        return this.flags;
    }

    @Override
    public EntityEquipmentCondition equipment() {
        return this.equipment;
    }

    @Override
    public EntitySubCondition typeSpecific() {
        return this.typeSpecific;
    }

    @Override
    public EntityCondition vehicle() {
        return this.vehicle;
    }

    @Override
    public EntityCondition passenger() {
        return this.passenger;
    }

    @Override
    public EntityCondition targetedEntity() {
        return this.targetedEntity;
    }

    @Override
    public @Nullable String team() {
        return this.team;
    }

    //<editor-fold desc="equals, hashCode, toString" defaultstate="collapsed">
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        final var that = (EntityConditionImpl) obj;
        return Objects.equals(this.type, that.type) &&
            Objects.equals(this.distance, that.distance) &&
            Objects.equals(this.location, that.location) &&
            Objects.equals(this.steppingOn, that.steppingOn) &&
            Objects.equals(this.effects, that.effects) &&
            Objects.equals(this.nbt, that.nbt) &&
            Objects.equals(this.flags, that.flags) &&
            Objects.equals(this.equipment, that.equipment) &&
            // Objects.equals(this.typeSpecific, that.typeSpecific) &&
            Objects.equals(this.vehicle, that.vehicle) &&
            Objects.equals(this.passenger, that.passenger) &&
            Objects.equals(this.targetedEntity, that.targetedEntity) &&
            Objects.equals(this.team, that.team);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.distance, this.location, this.steppingOn, this.effects, this.nbt, this.flags, this.equipment, /*this.typeSpecific,*/ this.vehicle, this.passenger, this.targetedEntity, this.team);
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
            // ", typeSpecific=" + this.typeSpecific +
            ", vehicle=" + this.vehicle +
            ", passenger=" + this.passenger +
            ", targetedEntity=" + this.targetedEntity +
            ", team='" + this.team + '\'' +
            '}';
    }
    //</editor-fold>

    static final class BuilderImpl implements EntityCondition.Builder {

        private EntityTypeCondition type = EntityTypeCondition.conditionType().any();
        private DistanceCondition distance = DistanceCondition.conditionType().any();
        private LocationCondition location = LocationCondition.conditionType().any();
        private LocationCondition steppingOn = LocationCondition.conditionType().any();
        private PotionEffectsCondition effects = PotionEffectsCondition.conditionType().any();
        private NBTCondition nbt = NBTCondition.conditionType().any();
        private EntityFlagsCondition flags = EntityFlagsCondition.conditionType().any();
        private EntityEquipmentCondition equipment = EntityEquipmentCondition.conditionType().any();
        private EntitySubCondition typeSpecific = EntitySubCondition.conditionType().any();
        private EntityCondition vehicle = ANY;
        private EntityCondition passenger = ANY;
        private EntityCondition targetedEntity = ANY;
        private @Nullable String team;

        BuilderImpl() {
        }

        private BuilderImpl(final EntityCondition other) {
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

        @Override
        public EntityTypeCondition type() {
            return this.type;
        }

        @Override
        public EntityCondition.Builder type(final EntityTypeCondition type) {
            this.type = type;
            return this;
        }

        @Override
        public DistanceCondition distance() {
            return this.distance;
        }

        @Override
        public EntityCondition.Builder distance(final DistanceCondition distance) {
            this.distance = distance;
            return this;
        }

        @Override
        public LocationCondition location() {
            return this.location;
        }

        @Override
        public EntityCondition.Builder location(final LocationCondition location) {
            this.location = location;
            return this;
        }

        @Override
        public LocationCondition steppingOn() {
            return this.steppingOn;
        }

        @Override
        public EntityCondition.Builder steppingOn(final LocationCondition steppingOn) {
            this.steppingOn = steppingOn;
            return this;
        }

        @Override
        public PotionEffectsCondition effects() {
            return this.effects;
        }

        @Override
        public EntityCondition.Builder effects(final PotionEffectsCondition effects) {
            this.effects = effects;
            return this;
        }

        @Override
        public NBTCondition nbt() {
            return this.nbt;
        }

        @Override
        public EntityCondition.Builder nbt(final NBTCondition nbt) {
            this.nbt = nbt;
            return this;
        }

        @Override
        public EntityFlagsCondition flags() {
            return this.flags;
        }

        @Override
        public EntityCondition.Builder flags(final EntityFlagsCondition flags) {
            this.flags = flags;
            return this;
        }

        @Override
        public EntityEquipmentCondition equipment() {
            return this.equipment;
        }

        @Override
        public EntityCondition.Builder equipment(final EntityEquipmentCondition equipment) {
            this.equipment = equipment;
            return this;
        }

        @Override
        public EntitySubCondition typeSpecific() {
            return this.typeSpecific;
        }

        @Override
        public EntityCondition.Builder typeSpecific(final EntitySubCondition typeSpecific) {
            this.typeSpecific = typeSpecific;
            return this;
        }

        @Override
        public EntityCondition vehicle() {
            return this.vehicle;
        }

        @Override
        public EntityCondition.Builder vehicle(final EntityCondition vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        @Override
        public EntityCondition passenger() {
            return this.passenger;
        }

        @Override
        public EntityCondition.Builder passenger(final EntityCondition passenger) {
            this.passenger = passenger;
            return this;
        }

        @Override
        public EntityCondition targetedEntity() {
            return this.targetedEntity;
        }

        @Override
        public EntityCondition.Builder targetedEntity(final EntityCondition targetedEntity) {
            this.targetedEntity = targetedEntity;
            return this;
        }

        @Override
        public @Nullable String team() {
            return this.team;
        }

        @Override
        public EntityCondition.Builder team(final @Nullable String team) {
            this.team = team;
            return this;
        }

        @Override
        public EntityCondition build() {
            return new EntityConditionImpl(this.type, this.distance, this.location, this.steppingOn, this.effects, this.nbt, this.flags, this.equipment, this.typeSpecific, this.vehicle, this.passenger, this.targetedEntity, this.team);
        }
    }
}
