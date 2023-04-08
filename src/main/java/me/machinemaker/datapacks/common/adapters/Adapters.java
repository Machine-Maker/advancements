package me.machinemaker.datapacks.common.adapters;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;
import io.papermc.paper.potion.Potion;
import io.papermc.paper.statistic.CustomStatistic;
import io.papermc.paper.statistic.StatisticType;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.lang.reflect.Type;
import me.machinemaker.datapacks.advancements.adapters.types.GameModeAdapter;
import me.machinemaker.datapacks.advancements.adapters.types.KeyAdapter;
import me.machinemaker.datapacks.advancements.adapters.types.Typed;
import me.machinemaker.datapacks.advancements.conditions.Conditions;
import me.machinemaker.datapacks.advancements.conditions.misc.TagCondition;
import me.machinemaker.datapacks.toremove.tags.BlockTag;
import me.machinemaker.datapacks.toremove.tags.EntityTag;
import me.machinemaker.datapacks.toremove.tags.FluidTag;
import me.machinemaker.datapacks.toremove.tags.ItemTag;
import org.bukkit.Fluid;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.Tag;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.structure.Structure;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

import static me.machinemaker.datapacks.advancements.adapters.types.BaseTagAdapter.tag;
import static me.machinemaker.datapacks.advancements.adapters.types.keyed.KeyedTypeAdapter.registry;

@ApiStatus.Internal
public final class Adapters {

    private static final GsonBuilder BUILDER = new GsonBuilder()
        .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);

    static {
        // registry
        typeAdapter(registry(Registry.ENTITY_TYPE, EntityType.class));
        typeAdapter(registry(Registry.MATERIAL, Material.class));
        typeAdapter(registry(Registry.FLUID, Fluid.class));
        typeAdapter(registry(Registry.BIOME, Biome.class));
        typeHierarchyAdapter(registry(Registry.STRUCTURE, Structure.class));
        typeAdapter(registry(Registry.STATISTIC_TYPES, new TypeToken<StatisticType<?>>() {}));
        typeAdapter(registry(Registry.CUSTOM_STATISTICS, CustomStatistic.class));
        typeHierarchyAdapter(registry(Registry.ENCHANTMENT, Enchantment.class));
        typeHierarchyAdapter(registry(Registry.POTION, Potion.class));
        typeHierarchyAdapter(registry(Registry.POTION_EFFECT_TYPE, PotionEffectType.class));
        // tags
        typeAdapter(tag(Tag.REGISTRY_BLOCKS, BlockTag::new, Material.class, BlockTag.class));
        typeAdapter(tag(Tag.REGISTRY_FLUIDS, FluidTag::new, Fluid.class, FluidTag.class));
        typeAdapter(tag(Tag.REGISTRY_ITEMS, ItemTag::new, Material.class, ItemTag.class));
        typeAdapter(tag(Tag.REGISTRY_ENTITY_TYPES, EntityTag::new, EntityType.class, EntityTag.class));
        // misc
        typeHierarchyAdapter(new KeyAdapter());
        typeAdapter(GameMode.class, GameModeAdapter.INSTANCE);
        typeAdapter(Object2BooleanMap.class, (InstanceCreator<Object>) type -> new Object2BooleanOpenHashMap<>());
        BUILDER.registerTypeAdapterFactory(TagCondition.FACTORY);
        Conditions.applyToGsonBuilder(BUILDER);
    }

    private Adapters() {
    }

    private static void typeAdapter(final Typed<?> adapter) {
        typeAdapter(adapter.getType().getType(), adapter);
    }

    private static void typeAdapter(final Type type, final Object adapter) {
        BUILDER.registerTypeAdapter(type, adapter);
    }

    private static void typeHierarchyAdapter(final Typed<?> adapter) {
        typeHierarchyAdapter(adapter.getType().getRawType(), adapter);
    }

    private static void typeHierarchyAdapter(final Class<?> type, final Object adapter) {
        BUILDER.registerTypeHierarchyAdapter(type, adapter);
    }

    @Contract("-> new")
    public static GsonBuilder createBuilder() {
        return BUILDER.create().newBuilder();
    }
}
