package me.machinemaker.datapacks.advancements.testing;

import io.papermc.paper.potion.Potion;
import io.papermc.paper.statistic.Statistic;
import io.papermc.paper.world.data.BlockProperty;
import io.papermc.paper.world.data.EnumBlockProperty;
import io.papermc.paper.world.data.IntegerBlockProperty;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import me.machinemaker.datapacks.advancements.conditions.block.BlockCondition;
import me.machinemaker.datapacks.advancements.conditions.block.BlockPropertyCondition;
import me.machinemaker.datapacks.advancements.conditions.block.FluidCondition;
import me.machinemaker.datapacks.advancements.conditions.block.LightCondition;
import me.machinemaker.datapacks.advancements.conditions.effect.PotionEffectInstanceCondition;
import me.machinemaker.datapacks.advancements.conditions.effect.PotionEffectsCondition;
import me.machinemaker.datapacks.advancements.conditions.entity.*;
import me.machinemaker.datapacks.advancements.conditions.entity.sub.AdvancementCondition;
import me.machinemaker.datapacks.advancements.conditions.entity.sub.EntitySubCondition;
import me.machinemaker.datapacks.advancements.conditions.entity.sub.EntityVariantCondition;
import me.machinemaker.datapacks.advancements.conditions.entity.sub.FishingHookCondition;
import me.machinemaker.datapacks.advancements.conditions.entity.sub.LightningBoltCondition;
import me.machinemaker.datapacks.advancements.conditions.entity.sub.PlayerCondition;
import me.machinemaker.datapacks.advancements.conditions.entity.sub.SlimeCondition;
import me.machinemaker.datapacks.advancements.conditions.item.EnchantmentCondition;
import me.machinemaker.datapacks.advancements.conditions.item.ItemCondition;
import me.machinemaker.datapacks.advancements.conditions.misc.NBTCondition;
import me.machinemaker.datapacks.advancements.conditions.range.DoubleRange;
import me.machinemaker.datapacks.advancements.conditions.range.IntegerRange;
import me.machinemaker.datapacks.advancements.conditions.world.DistanceCondition;
import me.machinemaker.datapacks.advancements.conditions.world.LocationCondition;
import me.machinemaker.datapacks.advancements.testing.types.AdvancementMapProvider;
import me.machinemaker.datapacks.advancements.testing.types.RecipeProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.block.BlockConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.block.BlockPropertyConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.block.FluidConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.block.LightConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.effect.PotionEffectInstanceConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.effect.PotionEffectsConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.entity.*;
import me.machinemaker.datapacks.advancements.testing.types.conditions.entity.sub.EntitySubConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.entity.sub.EntityVariantConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.entity.sub.FishingHookConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.entity.sub.LightningBoltConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.entity.sub.PlayerConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.entity.sub.SlimeConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.item.EnchantmentConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.item.ItemConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.misc.NBTConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.range.RangeProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.world.DistanceConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.conditions.world.LocationConditionProvider;
import me.machinemaker.datapacks.advancements.testing.types.StatisticProvider;
import net.kyori.adventure.key.Key;
import org.bukkit.Fluid;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.structure.Structure;
import org.bukkit.inventory.Recipe;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.nullness.qual.Nullable;

import static com.google.common.collect.Lists.newArrayList;

public final class RandomProviders {

    private static final class Lists {
        private static final Predicate<Material> NOT_LEGACY = Predicate.not(Material::isLegacy);
        private static final List<Material> BLOCKS = Arrays.stream(Material.values()).filter(NOT_LEGACY.and(Material::isBlock)).toList();
        private static final List<Material> ITEMS = Arrays.stream(Material.values()).filter(NOT_LEGACY.and(Material::isItem)).toList();

        private static final List<Fluid> FLUIDS = Arrays.stream(Fluid.values()).toList();
        private static final List<EntityType> ENTITY_TYPES = newArrayList(Registry.ENTITY_TYPE);
        private static final List<Biome> BIOMES = Arrays.stream(Biome.values()).filter(b -> b != Biome.CUSTOM).toList();
        private static final List<Structure> STRUCTURES = newArrayList(Registry.STRUCTURE);
        private static final List<Enchantment> ENCHANTMENTS = newArrayList(Registry.ENCHANTMENT);
        private static final List<Potion> POTIONS = newArrayList(Registry.POTION);
        private static final List<PotionEffectType> POTION_EFFECT_TYPES = newArrayList(Registry.POTION_EFFECT_TYPE);
        private static final List<GameMode> GAME_MODES = newArrayList(GameMode.values());

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
    public static final Provider<GameMode> GAME_MODE = new ListProvider<>(Lists.GAME_MODES);

    public static final Provider<Recipe> RECIPE = new RecipeProvider();
    public static final Provider<Object2BooleanMap<Key>> RECIPE_MAP = new RecipeProvider.Map();

    public static final Provider<Statistic<?>> STATISTIC_PROVIDER = new StatisticProvider();
    public static final Provider<Map<Statistic<?>, IntegerRange>> STATISTIC_MAP_PROVIDER = new StatisticProvider.Map();
    public static final Provider<BlockProperty<?>> BLOCK_PROPERTY = new ListProvider<>(Lists.VALID_PROPERTIES);
    public static final Provider<IntegerBlockProperty> INTEGER_BLOCK_PROPERTY = new ListProvider<>(Lists.INTEGER_BLOCK_PROPERTIES);

    public static final Provider<Map<Key, AdvancementCondition>> ADVANCEMENT_MAP_PROVIDER = new AdvancementMapProvider();

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
    public static final Provider<DamageSourceCondition> DAMAGE_SOURCE_CONDITION = new DamageSourceConditionProvider();
    public static final Provider<DamageCondition> DAMAGE_CONDITION = new DamageConditionProvider();
    // entity sub condition
    public static final Provider<PlayerCondition> PLAYER_CONDITION = new PlayerConditionProvider();
    public static final Provider<LightningBoltCondition> LIGHTNING_BOLT_CONDITION = new LightningBoltConditionProvider();
    public static final Provider<FishingHookCondition> FISHING_HOOK_CONDITION = new FishingHookConditionProvider();
    public static final Provider<SlimeCondition> SLIME_CONDITION = new SlimeConditionProvider();
    public static final Provider<EntityVariantCondition<?>> ENTITY_VARIANT_CONDITION = new EntityVariantConditionProvider();
    public static final Provider<EntitySubCondition> ENTITY_SUB_CONDITION = new EntitySubConditionProvider();

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
