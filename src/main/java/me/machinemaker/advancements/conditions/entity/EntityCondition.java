package me.machinemaker.advancements.conditions.entity;

import com.google.gson.annotations.SerializedName;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.ConditionDelegate;
import me.machinemaker.advancements.conditions.effects.PotionEffectsCondition;
import me.machinemaker.advancements.conditions.misc.NBTCondition;
import me.machinemaker.advancements.conditions.world.DistanceCondition;
import me.machinemaker.advancements.conditions.world.LocationCondition;
import me.machinemaker.advancements.util.Buildable;
import org.bukkit.entity.Cat;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

public interface EntityCondition extends Condition<EntityCondition>, Buildable<EntityCondition, EntityCondition.Builder> {

    GsonBuilderApplicable BUILDER_APPLICABLE = Adapters.of(EntityTypeCondition.BUILDER_APPLICABLE, LocationCondition.BUILDER_APPLICABLE);
    EntityCondition ANY = new Impl(
            EntityTypeCondition.ANY,
            DistanceCondition.ANY,
            LocationCondition.ANY,
            LocationCondition.ANY,
            PotionEffectsCondition.ANY,
            NBTCondition.ANY,
            EntityFlagsCondition.ANY,
            EntityEquipmentCondition.ANY,
            PlayerCondition.ANY,
            FishingHookCondition.ANY,
            LightningBoltCondition.ANY,
            null,
            null
    );

    EntityTypeCondition type();

    DistanceCondition distance();

    LocationCondition location();

    LocationCondition steppingOn();

    PotionEffectsCondition effects();

    NBTCondition nbt();

    EntityFlagsCondition flags();

    EntityEquipmentCondition equipment();

    PlayerCondition player();

    FishingHookCondition fishingHook();

    LightningBoltCondition lightningBolt();

    EntityCondition vehicle();

    EntityCondition passenger();

    EntityCondition targetedEntity();

    @Nullable String team();

    @SerializedName("catType") Cat.@Nullable Type catType();

    @Override
    default EntityCondition any() {
        return ANY;
    }

    @Contract(value = " -> new", pure = true)
    static Builder builder() {
        return new Builder();
    }

    @ApiStatus.Internal
    static Collection<Class<? extends EntityCondition>> types() {
        return Set.of(EntityCondition.class, Impl.class);
    }

    final class Builder implements Condition.Builder<EntityCondition> {

        private EntityTypeCondition type = EntityTypeCondition.ANY;
        private DistanceCondition distance = DistanceCondition.ANY;
        private LocationCondition location = LocationCondition.ANY;
        private LocationCondition steppingOn = LocationCondition.ANY;
        private PotionEffectsCondition effects = PotionEffectsCondition.ANY;
        private NBTCondition nbt = NBTCondition.ANY;
        private EntityFlagsCondition flags = EntityFlagsCondition.ANY;
        private EntityEquipmentCondition equipment = EntityEquipmentCondition.ANY;
        private PlayerCondition player = PlayerCondition.ANY;
        private FishingHookCondition fishingHook = FishingHookCondition.ANY;
        private LightningBoltCondition lightningBolt = LightningBoltCondition.ANY;
        private EntityCondition vehicle = EntityCondition.ANY;
        private EntityCondition passenger = EntityCondition.ANY;
        private EntityCondition targetedEntity = EntityCondition.ANY;
        private @Nullable String team;
        private Cat.@Nullable Type catType;

        private Builder() {
        }

        private Builder(EntityCondition other) {
            this.type = other.type();
            this.distance = other.distance();
            this.location = other.location();
            this.steppingOn = other.steppingOn();
            this.effects = other.effects();
            this.nbt = other.nbt();
            this.flags = other.flags();
            this.equipment = other.equipment();
            this.player = other.player();
            this.fishingHook = other.fishingHook();
            this.lightningBolt = other.lightningBolt();
            this.vehicle = other.vehicle();
            this.passenger = other.passenger();
            this.targetedEntity = other.targetedEntity();
            this.team = other.team();
            this.catType = other.catType();
        }

        public EntityTypeCondition type() {
            return this.type;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder type(EntityTypeCondition type) {
            this.type = type;
            return this;
        }

        public DistanceCondition distance() {
            return this.distance;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder distance(DistanceCondition distance) {
            this.distance = distance;
            return this;
        }

        public LocationCondition location() {
            return this.location;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder location(LocationCondition location) {
            this.location = location;
            return this;
        }

        public LocationCondition steppingOn() {
            return this.steppingOn;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder steppingOn(LocationCondition steppingOn) {
            this.steppingOn = steppingOn;
            return this;
        }

        public PotionEffectsCondition effects() {
            return this.effects;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder effects(PotionEffectsCondition effects) {
            this.effects = effects;
            return this;
        }

        public NBTCondition nbt() {
            return this.nbt;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder nbt(NBTCondition nbt) {
            this.nbt = nbt;
            return this;
        }

        public EntityFlagsCondition flags() {
            return this.flags;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder flags(EntityFlagsCondition flags) {
            this.flags = flags;
            return this;
        }

        public EntityEquipmentCondition equipment() {
            return this.equipment;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder equipment(EntityEquipmentCondition equipment) {
            this.equipment = equipment;
            return this;
        }

        public PlayerCondition player() {
            return this.player;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder player(PlayerCondition player) {
            this.player = player;
            return this;
        }

        public FishingHookCondition fishingHook() {
            return this.fishingHook;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder fishingHook(FishingHookCondition fishingHook) {
            this.fishingHook = fishingHook;
            return this;
        }

        public LightningBoltCondition lightningBolt() {
            return this.lightningBolt;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder lightningBolt(LightningBoltCondition lightningBolt) {
            this.lightningBolt = lightningBolt;
            return this;
        }

        public EntityCondition vehicle() {
            return this.vehicle;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder vehicle(EntityCondition vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public EntityCondition passenger() {
            return this.passenger;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder passenger(EntityCondition passenger) {
            this.passenger = passenger;
            return this;
        }

        public EntityCondition targetedEntity() {
            return this.targetedEntity;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder targetedEntity(EntityCondition targetedEntity) {
            this.targetedEntity = targetedEntity;
            return this;
        }

        public @Nullable String team() {
            return this.team;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder team(@Nullable String team) {
            this.team = team;
            return this;
        }

        public Cat.@Nullable Type catType() {
            return this.catType;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder catType(Cat.@Nullable Type catType) {
            this.catType = catType;
            return this;
        }

        @Override
        public EntityCondition build() {
            return new Impl(this.type,
                    this.distance,
                    this.location,
                    this.steppingOn,
                    this.effects,
                    this.nbt,
                    this.flags,
                    this.equipment,
                    this.player,
                    this.fishingHook,
                    this.lightningBolt,
                    this.vehicle,
                    this.passenger,
                    this.targetedEntity,
                    this.team,
                    this.catType
            );
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Impl">
    @SuppressWarnings("DuplicatedCode") // need second ctor to set "this" in ctor
    final class Impl implements EntityCondition {
        private final EntityTypeCondition type;
        private final DistanceCondition distance;
        private final LocationCondition location;
        private final LocationCondition steppingOn;
        private final PotionEffectsCondition effects;
        private final NBTCondition nbt;
        private final EntityFlagsCondition flags;
        private final EntityEquipmentCondition equipment;
        private final PlayerCondition player;
        private final FishingHookCondition fishingHook;
        private final LightningBoltCondition lightningBolt;
        private final EntityCondition vehicle;
        private final EntityCondition passenger;
        private final EntityCondition targetedEntity;
        private final @Nullable String team;
        @SerializedName("catType")
        private final Cat.@Nullable Type catType;

        private Impl(
                EntityTypeCondition type,
                DistanceCondition distance,
                LocationCondition location,
                LocationCondition steppingOn,
                PotionEffectsCondition effects,
                NBTCondition nbt,
                EntityFlagsCondition flags,
                EntityEquipmentCondition equipment,
                PlayerCondition player,
                FishingHookCondition fishingHook,
                LightningBoltCondition lightningBolt,
                EntityCondition vehicle,
                EntityCondition passenger,
                EntityCondition targetedEntity,
                @Nullable String team,
                Cat.@Nullable Type catType
        ) {
            this.type = type;
            this.distance = distance;
            this.location = location;
            this.steppingOn = steppingOn;
            this.effects = effects;
            this.nbt = nbt;
            this.flags = flags;
            this.equipment = equipment;
            this.player = player;
            this.fishingHook = fishingHook;
            this.lightningBolt = lightningBolt;
            this.vehicle = vehicle;
            this.passenger = passenger;
            this.targetedEntity = targetedEntity;
            this.team = team;
            this.catType = catType;
        }

        //<editor-fold defaultstate="collapsed" desc="duplicate ctor">
        private Impl(
                EntityTypeCondition type,
                DistanceCondition distance,
                LocationCondition location,
                LocationCondition steppingOn,
                PotionEffectsCondition effects,
                NBTCondition nbt,
                EntityFlagsCondition flags,
                EntityEquipmentCondition equipment,
                PlayerCondition player,
                FishingHookCondition fishingHook,
                LightningBoltCondition lightningBolt,
                @Nullable String team,
                Cat.@Nullable Type catType
        ) {
            this.type = type;
            this.distance = distance;
            this.location = location;
            this.steppingOn = steppingOn;
            this.effects = effects;
            this.nbt = nbt;
            this.flags = flags;
            this.equipment = equipment;
            this.player = player;
            this.fishingHook = fishingHook;
            this.lightningBolt = lightningBolt;
            this.vehicle = this;
            this.passenger = this;
            this.targetedEntity = this;
            this.team = team;
            this.catType = catType;
        }
        //</editor-fold>

        @Override
        public EntityCondition.Builder toBuilder() {
            return new EntityCondition.Builder(this);
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
        public PlayerCondition player() {
            return this.player;
        }

        @Override
        public FishingHookCondition fishingHook() {
            return this.fishingHook;
        }

        @Override
        public LightningBoltCondition lightningBolt() {
            return this.lightningBolt;
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

        @Override
        @SerializedName("catType")
        public Cat.@Nullable Type catType() {
            return this.catType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof EntityCondition that)) return false;
            return this.type.equals(that.type()) && this.distance.equals(that.distance()) && this.location.equals(that.location()) && this.steppingOn.equals(that.steppingOn()) && this.effects.equals(that.effects()) && this.nbt.equals(that.nbt()) && this.flags.equals(that.flags()) && this.equipment.equals(that.equipment()) && this.player.equals(that.player()) && this.fishingHook.equals(that.fishingHook()) && this.lightningBolt.equals(that.lightningBolt()) && this.vehicle.equals(that.vehicle()) && this.passenger.equals(that.passenger()) && this.targetedEntity.equals(that.targetedEntity()) && Objects.equals(this.team, that.team()) && this.catType == that.catType();
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.type, this.distance, this.location, this.steppingOn, this.effects, this.nbt, this.flags, this.equipment, this.player, this.fishingHook, this.lightningBolt, this.vehicle, this.passenger, this.targetedEntity, this.team, this.catType);
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
                    ", player=" + this.player +
                    ", fishingHook=" + this.fishingHook +
                    ", lightningBolt=" + this.lightningBolt +
                    ", vehicle=" + this.vehicle +
                    ", passenger=" + this.passenger +
                    ", targetedEntity=" + this.targetedEntity +
                    ", team='" + this.team + '\'' +
                    ", catType=" + this.catType +
                    '}';
        }

        static EntityCondition delegate(Supplier<EntityCondition> supplier) {
            return (EntityCondition) Proxy.newProxyInstance(EntityCondition.class.getClassLoader(), new Class[]{EntityCondition.class}, new ConditionDelegate<>(supplier));
        }
    }
    //</editor-fold>
}
