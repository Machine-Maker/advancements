// package me.machinemaker.advancements.conditions.entity;
//
// import me.machinemaker.advancements.adapters.Adapters;
// import me.machinemaker.advancements.adapters.GsonBuilderApplicable;
// import me.machinemaker.advancements.conditions.Condition;
// import me.machinemaker.advancements.conditions.effects.PotionEffectsCondition;
// import me.machinemaker.advancements.conditions.misc.NBTCondition;
// import me.machinemaker.advancements.conditions.world.DistanceCondition;
// import me.machinemaker.advancements.conditions.world.LocationCondition;
// import me.machinemaker.advancements.util.Buildable;
// import org.bukkit.entity.Cat;
// import org.checkerframework.checker.nullness.qual.Nullable;
// import org.jetbrains.annotations.ApiStatus;
//
// import java.util.Collection;
// import java.util.Objects;
// import java.util.Set;
//
// public interface EntityCondition extends Condition<EntityCondition>, Buildable<EntityCondition, EntityCondition2.Builder> {
//
//     GsonBuilderApplicable BUILDER_APPLICABLE = Adapters.of(EntityTypeCondition.BUILDER_APPLICABLE, LocationCondition.BUILDER_APPLICABLE);
//     EntityCondition ANY = new Impl(
//             EntityTypeCondition.ANY,
//             DistanceCondition.ANY,
//             LocationCondition.ANY,
//             LocationCondition.ANY,
//             PotionEffectsCondition.ANY,
//             NBTCondition.ANY,
//             EntityFlagsCondition.ANY,
//             EntityEquipmentCondition.ANY,
//             EntitySubCondition.ANY,
//             // PlayerCondition.ANY,
//             // FishingHookCondition.ANY,
//             // LightningBoltCondition.ANY,
//             null,
//             null
//     );
//
//     EntityTypeCondition type();
//
//     DistanceCondition distance();
//
//     LocationCondition location();
//
//     LocationCondition steppingOn();
//
//     PotionEffectsCondition effects();
//
//     NBTCondition nbt();
//
//     EntityFlagsCondition flags();
//
//     EntityEquipmentCondition equipment();
//
//     // PlayerCondition player();
//     //
//     // FishingHookCondition fishingHook();
//     //
//     // LightningBoltCondition lightningBolt();
//     EntitySubCondition subPredicate();
//
//     EntityCondition vehicle();
//
//     EntityCondition passenger();
//
//     EntityCondition targetedEntity();
//
//     @Nullable String team();
//
//     // @SerializedName("catType") Cat.@Nullable Type catType();
//
//     @Override
//     default EntityCondition any() {
//         return ANY;
//     }
//
//     @ApiStatus.Internal
//     static Collection<Class<? extends EntityCondition>> types() {
//         return Set.of(EntityCondition.class, Impl.class);
//     }
//
//     //<editor-fold defaultstate="collapsed" desc="Impl">
//     @SuppressWarnings("DuplicatedCode") // need second ctor to set "this" in ctor
//     final class Impl implements EntityCondition {
//         private final EntityTypeCondition type;
//         private final DistanceCondition distance;
//         private final LocationCondition location;
//         private final LocationCondition steppingOn;
//         private final PotionEffectsCondition effects;
//         private final NBTCondition nbt;
//         private final EntityFlagsCondition flags;
//         private final EntityEquipmentCondition equipment;
//         // private final PlayerCondition player;
//         // private final FishingHookCondition fishingHook;
//         // private final LightningBoltCondition lightningBolt;
//         private final EntitySubCondition subPredicate;
//         private final EntityCondition vehicle;
//         private final EntityCondition passenger;
//         private final EntityCondition targetedEntity;
//         private final @Nullable String team;
//         // @SerializedName("catType")
//         // private final Cat.@Nullable Type catType;
//
//         private Impl(
//                 EntityTypeCondition type,
//                 DistanceCondition distance,
//                 LocationCondition location,
//                 LocationCondition steppingOn,
//                 PotionEffectsCondition effects,
//                 NBTCondition nbt,
//                 EntityFlagsCondition flags,
//                 EntityEquipmentCondition equipment,
//                 // PlayerCondition player,
//                 // FishingHookCondition fishingHook,
//                 // LightningBoltCondition lightningBolt,
//                 EntitySubCondition subPredicate,
//                 EntityCondition vehicle,
//                 EntityCondition passenger,
//                 EntityCondition targetedEntity,
//                 @Nullable String team
//                 // Cat.@Nullable Type catType
//         ) {
//             this.type = type;
//             this.distance = distance;
//             this.location = location;
//             this.steppingOn = steppingOn;
//             this.effects = effects;
//             this.nbt = nbt;
//             this.flags = flags;
//             this.equipment = equipment;
//             // this.player = player;
//             // this.fishingHook = fishingHook;
//             // this.lightningBolt = lightningBolt;
//             this.subPredicate = subPredicate;
//             this.vehicle = vehicle;
//             this.passenger = passenger;
//             this.targetedEntity = targetedEntity;
//             this.team = team;
//             // this.catType = catType;
//         }
//
//         //<editor-fold defaultstate="collapsed" desc="duplicate ctor">
//         private Impl(
//                 EntityTypeCondition type,
//                 DistanceCondition distance,
//                 LocationCondition location,
//                 LocationCondition steppingOn,
//                 PotionEffectsCondition effects,
//                 NBTCondition nbt,
//                 EntityFlagsCondition flags,
//                 EntityEquipmentCondition equipment,
//                 EntitySubCondition subPredicate,
//                 // PlayerCondition player,
//                 // FishingHookCondition fishingHook,
//                 // LightningBoltCondition lightningBolt,
//                 @Nullable String team,
//                 Cat.@Nullable Type catType
//         ) {
//             this.type = type;
//             this.distance = distance;
//             this.location = location;
//             this.steppingOn = steppingOn;
//             this.effects = effects;
//             this.nbt = nbt;
//             this.flags = flags;
//             this.equipment = equipment;
//             this.subPredicate = subPredicate;
//             // this.player = player;
//             // this.fishingHook = fishingHook;
//             // this.lightningBolt = lightningBolt;
//             this.vehicle = this;
//             this.passenger = this;
//             this.targetedEntity = this;
//             this.team = team;
//             // this.catType = catType;
//         }
//         //</editor-fold>
//
//         @Override
//         public EntityCondition2.Builder toBuilder() {
//             return new EntityCondition2.Builder(this);
//         }
//
//         @Override
//         public EntityTypeCondition type() {
//             return this.type;
//         }
//
//         @Override
//         public DistanceCondition distance() {
//             return this.distance;
//         }
//
//         @Override
//         public LocationCondition location() {
//             return this.location;
//         }
//
//         @Override
//         public LocationCondition steppingOn() {
//             return this.steppingOn;
//         }
//
//         @Override
//         public PotionEffectsCondition effects() {
//             return this.effects;
//         }
//
//         @Override
//         public NBTCondition nbt() {
//             return this.nbt;
//         }
//
//         @Override
//         public EntityFlagsCondition flags() {
//             return this.flags;
//         }
//
//         @Override
//         public EntityEquipmentCondition equipment() {
//             return this.equipment;
//         }
//
//         @Override
//         public EntitySubCondition subPredicate() {
//             return this.subPredicate;
//         }
//
//         @Override
//         public EntityCondition vehicle() {
//             return this.vehicle;
//         }
//
//         @Override
//         public EntityCondition passenger() {
//             return this.passenger;
//         }
//
//         @Override
//         public EntityCondition targetedEntity() {
//             return this.targetedEntity;
//         }
//
//         @Override
//         public @Nullable String team() {
//             return this.team;
//         }
//
//         // @Override
//         // @SerializedName("catType")
//         // public Cat.@Nullable Type catType() {
//         //     return this.catType;
//         // }
//
//         @Override
//         public boolean equals(Object o) {
//             if (this == o) return true;
//             if (!(o instanceof EntityCondition that)) return false;
//             return this.type.equals(that.type()) && this.distance.equals(that.distance()) && this.location.equals(that.location()) && this.steppingOn.equals(that.steppingOn()) && this.effects.equals(that.effects()) && this.nbt.equals(that.nbt()) && this.flags.equals(that.flags()) && this.equipment.equals(that.equipment()) && /* this.player.equals(that.player()) && this.fishingHook.equals(that.fishingHook()) && this.lightningBolt.equals(that.lightningBolt()) */ this.subPredicate.equals(that.subPredicate()) && this.vehicle.equals(that.vehicle()) && this.passenger.equals(that.passenger()) && this.targetedEntity.equals(that.targetedEntity()) && Objects.equals(this.team, that.team())/*  && this.catType == that.catType() */;
//         }
//
//         @Override
//         public int hashCode() {
//             return Objects.hash(this.type, this.distance, this.location, this.steppingOn, this.effects, this.nbt, this.flags, this.equipment, /* this.player, this.fishingHook, this.lightningBolt */ this.subPredicate, this.vehicle, this.passenger, this.targetedEntity, this.team/* , this.catType */);
//         }
//
//         @Override
//         public String toString() {
//             if (this.isAny()) {
//                 return "EntityCondition{ANY}";
//             }
//             return "EntityCondition{" +
//                     "type=" + this.type +
//                     ", distance=" + this.distance +
//                     ", location=" + this.location +
//                     ", steppingOn=" + this.steppingOn +
//                     ", effects=" + this.effects +
//                     ", nbt=" + this.nbt +
//                     ", flags=" + this.flags +
//                     ", equipment=" + this.equipment +
//                     // ", player=" + this.player +
//                     // ", fishingHook=" + this.fishingHook +
//                     // ", lightningBolt=" + this.lightningBolt +
//                     ", subPredicate=" + this.subPredicate +
//                     ", vehicle=" + this.vehicle +
//                     ", passenger=" + this.passenger +
//                     ", targetedEntity=" + this.targetedEntity +
//                     ", team='" + this.team + '\'' +
//                     // ", catType=" + this.catType +
//                     '}';
//         }
//
//         // static EntityCondition delegate(Supplier<EntityCondition> supplier) {
//         //     return (EntityCondition) Proxy.newProxyInstance(EntityCondition.class.getClassLoader(), new Class[]{EntityCondition.class}, new ConditionDelegate<>(supplier));
//         // }
//     }
//     //</editor-fold>
// }
