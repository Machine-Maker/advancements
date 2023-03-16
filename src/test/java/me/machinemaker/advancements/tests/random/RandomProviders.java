package me.machinemaker.advancements.tests.random;

import io.papermc.paper.potion.Potion;
import io.papermc.paper.statistic.Statistic;
import io.papermc.paper.world.data.BlockProperty;
import io.papermc.paper.world.data.EnumBlockProperty;
import io.papermc.paper.world.data.IntegerBlockProperty;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import me.machinemaker.advancements.conditions.block.BlockCondition;
import me.machinemaker.advancements.conditions.block.BlockPropertyCondition;
import me.machinemaker.advancements.conditions.block.FluidCondition;
import me.machinemaker.advancements.conditions.block.LightCondition;
import me.machinemaker.advancements.conditions.effect.PotionEffectInstanceCondition;
import me.machinemaker.advancements.conditions.effect.PotionEffectsCondition;
import me.machinemaker.advancements.conditions.entity.EntityCondition;
import me.machinemaker.advancements.conditions.entity.EntityEquipmentCondition;
import me.machinemaker.advancements.conditions.entity.EntityFlagsCondition;
import me.machinemaker.advancements.conditions.entity.EntityTypeCondition;
import me.machinemaker.advancements.conditions.item.EnchantmentCondition;
import me.machinemaker.advancements.conditions.item.ItemCondition;
import me.machinemaker.advancements.conditions.misc.NBTCondition;
import me.machinemaker.advancements.conditions.world.DistanceCondition;
import me.machinemaker.advancements.conditions.world.LocationCondition;
import me.machinemaker.advancements.conditions.range.DoubleRange;
import me.machinemaker.advancements.conditions.range.IntegerRange;
import me.machinemaker.advancements.tests.random.types.StatisticProvider;
import me.machinemaker.advancements.tests.random.types.conditions.effect.PotionEffectsConditionProvider;
import me.machinemaker.advancements.tests.random.types.conditions.entity.EntityConditionProvider;
import me.machinemaker.advancements.tests.random.types.conditions.entity.EntityEquipmentConditionProvider;
import me.machinemaker.advancements.tests.random.types.conditions.entity.EntityFlagsConditionProvider;
import me.machinemaker.advancements.tests.random.types.conditions.entity.EntityTypeConditionProvider;
import me.machinemaker.advancements.tests.random.types.conditions.range.RangeProvider;
import me.machinemaker.advancements.tests.random.types.conditions.block.BlockConditionProvider;
import me.machinemaker.advancements.tests.random.types.conditions.block.BlockPropertyConditionProvider;
import me.machinemaker.advancements.tests.random.types.conditions.block.FluidConditionProvider;
import me.machinemaker.advancements.tests.random.types.conditions.block.LightConditionProvider;
import me.machinemaker.advancements.tests.random.types.conditions.effect.PotionEffectInstanceConditionProvider;
import me.machinemaker.advancements.tests.random.types.conditions.item.EnchantmentConditionProvider;
import me.machinemaker.advancements.tests.random.types.conditions.item.ItemConditionProvider;
import me.machinemaker.advancements.tests.random.types.conditions.misc.NBTConditionProvider;
import me.machinemaker.advancements.tests.random.types.conditions.world.DistanceConditionProvider;
import me.machinemaker.advancements.tests.random.types.conditions.world.LocationConditionProvider;
import net.kyori.adventure.key.Key;
import net.minecraft.Util;
import org.bukkit.Fluid;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.structure.Structure;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class RandomProviders {

    private static final class Lists {
        private static final Predicate<Material> NOT_LEGACY = Predicate.not(Material::isLegacy);
        private static final List<Material> BLOCKS = Arrays.stream(Material.values()).filter(NOT_LEGACY.and(Material::isBlock)).toList();
        private static final List<Material> ITEMS = Arrays.stream(Material.values()).filter(NOT_LEGACY.and(Material::isItem)).toList();

        private static final List<Fluid> FLUIDS = Arrays.stream(Fluid.values()).toList();
        private static final List<EntityType> ENTITY_TYPES = Arrays.stream(EntityType.values()).filter(et -> et != EntityType.UNKNOWN).toList();
        private static final List<Biome> BIOMES = Arrays.stream(Biome.values()).filter(b -> b != Biome.CUSTOM).toList();
        private static final List<Structure> STRUCTURES = com.google.common.collect.Lists.newArrayList(Util.make(() -> {
            Structure.ANCIENT_CITY.getKey();
            return Registry.STRUCTURE.iterator();
        }));
        private static final List<Enchantment> ENCHANTMENTS = List.of(Enchantment.ARROW_DAMAGE, Enchantment.DAMAGE_ALL); // TODO add more via user-dev
        // private static final List<Potion> POTIONS = com.google.common.collect.Lists.newArrayList(Util.make(() -> {
        //     Potion.AWKWARD.getKey();
        //     return Registry.POTION.iterator();
        // }));
        private static final List<Potion> POTIONS = List.of(Potion.AWKWARD.value(), Potion.LEAPING.value(), Potion.INVISIBILITY.value());
        private static final List<PotionEffectType> POTION_EFFECT_TYPES = List.of(PotionEffectType.ABSORPTION, PotionEffectType.FAST_DIGGING);

        private static final Predicate<BlockProperty<?>> NOT_ENUM_PROPERTIES = Predicate.not(EnumBlockProperty.class::isInstance);
        private static final List<BlockProperty<?>> VALID_PROPERTIES = BlockProperty.PROPERTIES.values().stream().filter(NOT_ENUM_PROPERTIES).toList();
        private static final Predicate<BlockProperty<?>> IS_INT_PROPERTY = IntegerBlockProperty.class::isInstance;
        private static final List<IntegerBlockProperty> INTEGER_BLOCK_PROPERTIES = VALID_PROPERTIES.stream().filter(IS_INT_PROPERTY).map(IntegerBlockProperty.class::cast).toList();

    }

    public static final Provider<@Nullable Boolean> BOOLEAN = new ListProvider<>(List.of(true, false), true);

    public static final Provider<Material> BLOCK = new ListProvider<>(Lists.BLOCKS);
    public static final Provider<Material> ITEM = new ListProvider<>(Lists.ITEMS);
    public static final Provider<@Nullable Fluid> FLUID = new ListProvider<>(Lists.FLUIDS, true);
    public static final Provider<EntityType> ENTITY_TYPE = new ListProvider<>(Lists.ENTITY_TYPES);
    public static final Provider<@Nullable Biome> BIOME = new ListProvider<>(Lists.BIOMES, true);
    public static final Provider<@Nullable Structure> STRUCTURE = new ListProvider<>(Lists.STRUCTURES, true);
    public static final Provider<@Nullable Enchantment> ENCHANTMENT = new ListProvider<>(Lists.ENCHANTMENTS, true);
    public static final Provider<@Nullable Potion> POTION = new ListProvider<>(Lists.POTIONS, true);
    public static final Provider<PotionEffectType> POTION_EFFECT_TYPE = new ListProvider<>(Lists.POTION_EFFECT_TYPES);

    public static final Provider<Statistic<?>> STATISTIC_PROVIDER = new StatisticProvider();
    public static final Provider<BlockProperty<?>> BLOCK_PROPERTY = new ListProvider<>(Lists.VALID_PROPERTIES);
    public static final Provider<IntegerBlockProperty> INTEGER_BLOCK_PROPERTY = new ListProvider<>(Lists.INTEGER_BLOCK_PROPERTIES);

    public static final Provider<Key> DIMENSION = new ListProvider<>(List.of(Key.key("minecraft:overworld"), Key.key("minecraft:the_nether"), Key.key("minecraft:the_end")));
    // conditions

    // block
    public static final Provider<BlockPropertyCondition> BLOCK_PROPERTY_CONDITION = new BlockPropertyConditionProvider();
    public static final Provider<BlockCondition> BLOCK_CONDITION = new BlockConditionProvider();
    public static final Provider<FluidCondition> FLUID_CONDITION = new FluidConditionProvider();
    public static final Provider<LightCondition> LIGHT_CONDITION = new LightConditionProvider();

    // effect
    public static final Provider<PotionEffectInstanceCondition> POTION_EFFECT_INSTANCE_CONDITION = new PotionEffectInstanceConditionProvider();
    public static final Provider<PotionEffectsCondition> POTION_EFFECTS_CONDITION = new PotionEffectsConditionProvider();

    // entity
    public static final Provider<EntityCondition> ENTITY_CONDITION = new EntityConditionProvider();
    public static final Provider<EntityEquipmentCondition> ENTITY_EQUIPMENT_CONDITION = new EntityEquipmentConditionProvider();
    public static final Provider<EntityFlagsCondition> ENTITY_FLAGS_CONDITION = new EntityFlagsConditionProvider();
    public static final Provider<EntityTypeCondition> ENTITY_TYPE_CONDITION = new EntityTypeConditionProvider();

    // item
    public static final Provider<EnchantmentCondition> ENCHANTMENT_CONDITION = new EnchantmentConditionProvider();
    public static final Provider<EnchantmentCondition[]> ENCHANTMENTS_CONDITION = new EnchantmentConditionProvider().array();
    public static final Provider<ItemCondition> ITEM_CONDITION = new ItemConditionProvider();

    // misc
    public static final Provider<NBTCondition> NBT_CONDITION = new NBTConditionProvider();

    // range
    public static final Provider<IntegerRange> INTEGER_RANGE = new RangeProvider.Ints();
    public static final Provider<DoubleRange> DOUBLE_RANGE = new RangeProvider.Doubles();

    // world
    public static final Provider<DistanceCondition> DISTANCE_CONDITION = new DistanceConditionProvider();
    public static final Provider<LocationCondition> LOCATION_CONDITION = new LocationConditionProvider();


    private RandomProviders() {
    }
}
