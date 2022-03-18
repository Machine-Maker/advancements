package me.machinemaker.advancements.adapters;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import io.papermc.paper.potion.Potion;
import io.papermc.paper.registry.Reference;
import io.papermc.paper.statistics.StatisticType;
import io.papermc.paper.world.structure.ConfiguredStructure;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import me.machinemaker.advancements.adapters.factories.ConditionTypeAdapterFactory;
import me.machinemaker.advancements.adapters.factories.RecordTypeAdapterFactory;
import me.machinemaker.advancements.adapters.types.keyed.KeyedTypeAdapter;
import me.machinemaker.advancements.adapters.types.NamespacedKeyAdapter;
import me.machinemaker.advancements.tags.BlockTag;
import me.machinemaker.advancements.tags.EntityTag;
import me.machinemaker.advancements.tags.FluidTag;
import me.machinemaker.advancements.tags.ItemTag;
import org.bukkit.Fluid;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Tag;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

@DefaultQualifier(NonNull.class)
public final class Adapters {

    private Adapters() {
    }

    private static final List<UnaryOperator<GsonBuilder>> ADAPTERS = new ArrayList<>();

    // Factories
    public static final AdapterFactory RECORD_TYPE_ADAPTER_FACTORY = factory(new RecordTypeAdapterFactory());
    public static final AdapterFactory WRAPPER_TYPE_ADAPTER_FACTORY = factory(new ConditionTypeAdapterFactory());

    // Keyed
    public static final Type<Cat.Type> CAT_TYPE_ADAPTER = keyedAdapter(KeyedTypeAdapter.forEnum(Cat.Type.class));
    public static final Type<EntityType> ENTITY_TYPE_ADAPTER = keyedAdapter(KeyedTypeAdapter.forRegistry(Registry.ENTITY_TYPE, EntityType.class));
    public static final Type<Material> MATERIAL_ADAPTER = keyedAdapter(KeyedTypeAdapter.forRegistry(Registry.MATERIAL, Material.class));
    public static final Type<Fluid> FLUID_ADAPTER = keyedAdapter(KeyedTypeAdapter.forRegistry(Registry.FLUID, Fluid.class));
    public static final Type<Biome> BIOME_ADAPTER = keyedAdapter(KeyedTypeAdapter.forRegistry(Registry.BIOME, Biome.class));
    // public static final Type<StructureType> STRUCTURE_TYPE_ADAPTER = keyedAdapter(KeyedTypeAdapter.forRegistry(Registry.STRUCTURE_TYPE, StructureType.class));
    public static final TypeHierarchy<Reference<ConfiguredStructure>> CONFIGURED_STRUCTURE_ADAPTER = keyedHierarchyAdapter(KeyedTypeAdapter.forReference(Registry.CONFIGURED_STRUCTURE, TypeToken.get(ConfiguredStructure.class)));
    public static final Type<StatisticType<?>> STATISTIC_TYPE_ADAPTER = keyedAdapter(KeyedTypeAdapter.forRegistry(Registry.STATISTIC_TYPES, new TypeToken<StatisticType<?>>() {}));
    public static final TypeHierarchy<Enchantment> ENCHANTMENT_ADAPTER = keyedHierarchyAdapter(KeyedTypeAdapter.forRegistry(Registry.ENCHANTMENT, Enchantment.class));
    public static final TypeHierarchy<PotionEffectType> POTION_EFFECT_TYPE_ADAPTER = keyedHierarchyAdapter(KeyedTypeAdapter.forRegistry(Registry.POTION_EFFECT_TYPE, PotionEffectType.class));
    public static final TypeHierarchy<Potion> POTION_ADAPTER = keyedHierarchyAdapter(KeyedTypeAdapter.forRegistry(Registry.POTION, Potion.class));

    // Misc. types
    public static final Type<NamespacedKey> NAMESPACED_KEY_ADAPTER = adapter(new NamespacedKeyAdapter().nullSafe(), NamespacedKey.class);

    // Tags
    public static final Type<BlockTag> BLOCK_TAG_ADAPTER = adapter(BaseTagAdapter.of(Tag.REGISTRY_BLOCKS, BlockTag::new, Material.class), BlockTag.class);
    public static final Type<FluidTag> FLUID_TAG_ADAPTER = adapter(BaseTagAdapter.of(Tag.REGISTRY_FLUIDS, FluidTag::new, Fluid.class), FluidTag.class);
    public static final Type<ItemTag> ITEM_TAG_ADAPTER = adapter(BaseTagAdapter.of(Tag.REGISTRY_ITEMS, ItemTag::new, Material.class), ItemTag.class);
    public static final Type<EntityTag> ENTITY_TAG_ADAPTER = adapter(BaseTagAdapter.of(Tag.REGISTRY_ENTITY_TYPES, EntityTag::new, EntityType.class), EntityTag.class);

    // fastutil
    public static final Instantiator<Object2BooleanMap<?>> OBJECT_2_BOOLEAN_MAP_INSTANTIATOR = instantiator(new InstanceCreator<Object2BooleanMap<?>>() {
        @Override
        public Object2BooleanMap<?> createInstance(java.lang.reflect.Type type) {
            return new Object2BooleanOpenHashMap<>();
        }
    }, Object2BooleanMap.class);

    public static final Gson GSON = configure(new GsonBuilder()).setVersion(1.0).create();

    private static AdapterFactory factory(TypeAdapterFactory factory) {
        ADAPTERS.add(builder -> builder.registerTypeAdapterFactory(factory));
        return new AdapterFactory(factory);
    }

    private static <A extends Keyed> Type<A> keyedAdapter(KeyedTypeAdapter<A> adapter) {
        return adapter(adapter, adapter.getType().getRawType());
    }

    private static <A> Type<A> adapter(TypeAdapter<A> adapter, Class<A> classOfA) {
        return adapter(adapter, (java.lang.reflect.Type) classOfA);
    }

    private static <A> Type<A> adapter(TypeAdapter<A> adapter, java.lang.reflect.Type typeOfA) {
        ADAPTERS.add(builder -> builder.registerTypeAdapter(typeOfA, adapter));
        return new Type<>(typeOfA, adapter);
    }
    
    private static <A extends Keyed> TypeHierarchy<A> keyedHierarchyAdapter(KeyedTypeAdapter<A> adapter) {
        return hierarchyAdapter(adapter, adapter.getType().getRawType());
    }

    private static <A> TypeHierarchy<A> hierarchyAdapter(TypeAdapter<A> adapter, Class<? super A> type) {
        ADAPTERS.add(builder -> builder.registerTypeHierarchyAdapter(type, adapter));
        return new TypeHierarchy<>(type, adapter);
    }

    private static <A> Instantiator<A> instantiator(InstanceCreator<A> instanceCreator, java.lang.reflect.Type type) {
        ADAPTERS.add(builder -> builder.registerTypeAdapter(type, instanceCreator));
        return new Instantiator<>(type, instanceCreator);
    }

    public static GsonBuilder configure(final GsonBuilder builder) {
        ADAPTERS.forEach(op -> op.apply(builder));
        builder.setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return builder;
    }

    public record Type<T>(java.lang.reflect.Type type, TypeAdapter<T> adapter) implements GsonBuilderApplicable {

        public Type(Class<T> type, TypeAdapter<T> adapter) {
            this((java.lang.reflect.Type) type, adapter);
        }

        @Override
        public void applyTo(GsonBuilder builder) {
            builder.registerTypeAdapter(this.type, this.adapter);
        }
    }

    public record TypeHierarchy<T>(Class<? super T> type, TypeAdapter<T> adapter) implements GsonBuilderApplicable {

        @Override
        public void applyTo(GsonBuilder builder) {
            builder.registerTypeHierarchyAdapter(this.type, this.adapter);
        }
    }

    public record AdapterFactory(TypeAdapterFactory factory) implements GsonBuilderApplicable {

        @Override
        public void applyTo(GsonBuilder builder) {
            builder.registerTypeAdapterFactory(this.factory);
        }
    }

    public record Instantiator<T>(java.lang.reflect.Type type, InstanceCreator<T> instanceCreator) implements GsonBuilderApplicable {

        @Override
        public void applyTo(GsonBuilder builder) {
            builder.registerTypeAdapter(type, instanceCreator);
        }
    }

    record Collection(GsonBuilderApplicable...gsonBuilderApplicables) implements GsonBuilderApplicable {

        @Override
        public void applyTo(GsonBuilder builder) {
            for (GsonBuilderApplicable gsonBuilderApplicable : this.gsonBuilderApplicables) {
                gsonBuilderApplicable.applyTo(builder);
            }
        }
    }

    public static Collection of(GsonBuilderApplicable...gsonBuilderApplicables) {
        return new Collection(gsonBuilderApplicables);
    }

}
