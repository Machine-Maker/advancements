package me.machinemaker.advancements.adapters;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import io.papermc.paper.potion.Potion;
import io.papermc.paper.statistic.CustomStatistic;
import io.papermc.paper.statistic.StatisticType;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;
import me.machinemaker.advancements.adapters.builders.Builders;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.adapters.types.GameModeAdapter;
import me.machinemaker.advancements.adapters.types.KeyAdapter;
import me.machinemaker.advancements.adapters.types.keyed.KeyedTypeAdapter;
import me.machinemaker.advancements.tags.BlockTag;
import me.machinemaker.advancements.tags.EntityTag;
import me.machinemaker.advancements.tags.FluidTag;
import me.machinemaker.advancements.tags.ItemTag;
import net.kyori.adventure.key.Key;
import org.bukkit.Fluid;
import org.bukkit.GameMode;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.Tag;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.structure.Structure;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.jetbrains.annotations.ApiStatus;

@DefaultQualifier(NonNull.class)
@ApiStatus.Internal
public final class Adapters {

    private static final List<UnaryOperator<GsonBuilder>> ADAPTERS = new ArrayList<>();
    // Keyed
    public static final GsonBuilderApplicable CAT_TYPE_ADAPTER = keyedAdapter(KeyedTypeAdapter.forEnum(Cat.Type.class));
    public static final GsonBuilderApplicable ENTITY_TYPE_ADAPTER = keyedAdapter(KeyedTypeAdapter.forRegistry(Registry.ENTITY_TYPE, EntityType.class));
    public static final GsonBuilderApplicable MATERIAL_ADAPTER = keyedAdapter(KeyedTypeAdapter.forRegistry(Registry.MATERIAL, Material.class));
    public static final GsonBuilderApplicable FLUID_ADAPTER = keyedAdapter(KeyedTypeAdapter.forRegistry(Registry.FLUID, Fluid.class));
    public static final GsonBuilderApplicable BIOME_ADAPTER = keyedAdapter(KeyedTypeAdapter.forRegistry(Registry.BIOME, Biome.class));
    public static final GsonBuilderApplicable STRUCTURE_ADAPTER = keyedHierarchyAdapter(KeyedTypeAdapter.forRegistry(Registry.STRUCTURE, TypeToken.get(Structure.class)));
    public static final GsonBuilderApplicable STATISTIC_TYPE_ADAPTER = keyedAdapter(KeyedTypeAdapter.forRegistry(Registry.STATISTIC_TYPES, new TypeToken<StatisticType<?>>() {}));
    public static final GsonBuilderApplicable CUSTOM_STAT_TYPE_ADAPTER = keyedAdapter(KeyedTypeAdapter.forRegistry(Registry.CUSTOM_STATISTICS, CustomStatistic.class));
    public static final GsonBuilderApplicable ENCHANTMENT_ADAPTER = keyedHierarchyAdapter(KeyedTypeAdapter.forRegistry(Registry.ENCHANTMENT, Enchantment.class));
    public static final GsonBuilderApplicable POTION_ADAPTER = keyedHierarchyAdapter(KeyedTypeAdapter.forRegistry(Registry.POTION, Potion.class));
    // Misc. types
    public static final GsonBuilderApplicable KEY_ADAPTER = hierarchyAdapter(new KeyAdapter(), Key.class);
    public static final GsonBuilderApplicable GAME_MODE_ADAPTER = adapter(new GameModeAdapter(), GameMode.class);
    // Tags
    public static final GsonBuilderApplicable BLOCK_TAG_ADAPTER = adapter(BaseTagAdapter.of(Tag.REGISTRY_BLOCKS, BlockTag::new, Material.class), BlockTag.class);
    public static final GsonBuilderApplicable FLUID_TAG_ADAPTER = adapter(BaseTagAdapter.of(Tag.REGISTRY_FLUIDS, FluidTag::new, Fluid.class), FluidTag.class);
    public static final GsonBuilderApplicable ITEM_TAG_ADAPTER = adapter(BaseTagAdapter.of(Tag.REGISTRY_ITEMS, ItemTag::new, Material.class), ItemTag.class);
    public static final GsonBuilderApplicable ENTITY_TAG_ADAPTER = adapter(BaseTagAdapter.of(Tag.REGISTRY_ENTITY_TYPES, EntityTag::new, EntityType.class), EntityTag.class);
    // fastutil
    public static final GsonBuilderApplicable OBJECT_2_BOOLEAN_MAP_INSTANTIATOR = instantiator(type -> new Object2BooleanOpenHashMap<>(), Object2BooleanMap.class);

    private Adapters() {
    }

    private static <A extends Keyed> GsonBuilderApplicable keyedAdapter(final KeyedTypeAdapter<A> adapter) {
        return adapter(adapter, adapter.getType());
    }

    private static <A> GsonBuilderApplicable adapter(final TypeAdapter<A> adapter, final Class<A> classOfA) {
        return adapter(adapter, TypeToken.get(classOfA));
    }

    private static <A> GsonBuilderApplicable adapter(final TypeAdapter<A> adapter, final TypeToken<A> typeOfA) {
        ADAPTERS.add(builder -> builder.registerTypeAdapter(typeOfA.getType(), adapter));
        return Builders.type(typeOfA, adapter);
    }

    private static <A extends Keyed> GsonBuilderApplicable keyedHierarchyAdapter(final KeyedTypeAdapter<A> adapter) {
        return hierarchyAdapter(adapter, adapter.getType().getRawType());
    }

    private static <A> GsonBuilderApplicable hierarchyAdapter(final TypeAdapter<A> adapter, final Class<? super A> type) {
        ADAPTERS.add(builder -> builder.registerTypeHierarchyAdapter(type, adapter));
        return Builders.typeHierarchy(type, adapter);
    }

    private static <A> GsonBuilderApplicable instantiator(final InstanceCreator<A> instanceCreator, final Class<A> type) {
        ADAPTERS.add(builder -> builder.registerTypeAdapter(type, instanceCreator));
        return Builders.instantiator(type, instanceCreator);
    }
}
