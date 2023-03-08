package me.machinemaker.advancements.conditions;

import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import me.machinemaker.advancements.adapters.AdapterDelegate;
import me.machinemaker.advancements.conditions.blocks.BlockCondition;
import me.machinemaker.advancements.conditions.blocks.FluidCondition;
import me.machinemaker.advancements.conditions.blocks.LightCondition;
import me.machinemaker.advancements.conditions.blocks.BlockPropertyCondition;
import me.machinemaker.advancements.conditions.effects.PotionEffectInstanceCondition;
import me.machinemaker.advancements.conditions.effects.PotionEffectsCondition;
import me.machinemaker.advancements.conditions.entity.DamageCondition;
import me.machinemaker.advancements.conditions.entity.DamageSourceCondition;
import me.machinemaker.advancements.conditions.entity.EntityCondition;
import me.machinemaker.advancements.conditions.entity.EntityEquipmentCondition;
import me.machinemaker.advancements.conditions.entity.EntityFlagsCondition;
import me.machinemaker.advancements.conditions.entity.EntitySubCondition;
import me.machinemaker.advancements.conditions.entity.EntityTypeCondition;
import me.machinemaker.advancements.conditions.entity.FishingHookCondition;
import me.machinemaker.advancements.conditions.item.EnchantmentCondition;
import me.machinemaker.advancements.conditions.item.ItemCondition;
import me.machinemaker.advancements.conditions.misc.NBTCondition;
import me.machinemaker.advancements.conditions.world.DistanceCondition;
import me.machinemaker.advancements.conditions.world.LocationCondition;
import me.machinemaker.advancements.ranges.DoubleRange;
import me.machinemaker.advancements.ranges.IntegerRange;

public final class Conditions {

    private static final Map<TypeToken<?>, Object> CONDITION_REGISTRY = Maps.newHashMap();
    private static final Map<TypeToken<?>, Class<? extends Condition<?>>> TYPE_TO_BASE_MAP = new HashMap<>();

    static {
        register(EntityEquipmentCondition.ANY);
        register(EntityFlagsCondition.ANY);
        register(EntityTypeCondition.ANY, EntityTypeCondition.types());
        register(EntitySubCondition.ANY, EntitySubCondition.TYPES.keySet(), EntitySubCondition.class);
        register(FishingHookCondition.ANY); // while still an entity sub condition, it has its own ANY
        // register(FishingHookCondition.ANY);
        register(DamageSourceCondition.ANY);
        register(DamageCondition.ANY);
        register(EntityCondition.ANY/* , EntityCondition.types() */);
        // player and lightning bolt must be registered AFTER entity
        // register(PlayerCondition.ANY);
        // register(LightningBoltCondition.ANY);

        // register(EnchantmentCondition.ANY);
        // register(ItemCondition.ANY);

        register(PotionEffectInstanceCondition.ANY);
        register(PotionEffectsCondition.ANY);

        register(LocationCondition.ANY);
    }

    private Conditions() {
    }

    @Deprecated(forRemoval = true)
    private static <C extends Condition<? super C>> void register(final C condition) {
        register(condition, Collections.emptySet());
    }

    @SafeVarargs
    @Deprecated(forRemoval = true)
    private static <C extends Condition<? super C>> void register(final C condition, final Class<? extends C>... moreOtherClasses) {
        register(condition, Collections.emptySet(), moreOtherClasses);
    }

    private static <C extends Condition<? super C>> void registerCustom(final Class<C> baseType, final C any) {
        register(any, baseType);
    }

    private static <C extends Condition<? super C>> void registerNew(final Class<C> baseType, final C any) {
        TYPE_TO_BASE_MAP.put(TypeToken.get(any.getClass()), baseType);
        TYPE_TO_BASE_MAP.put(TypeToken.get(baseType), baseType);
        register(any, baseType);
    }

    @SafeVarargs
    @Deprecated(forRemoval = true)
    private static <C extends Condition<? super C>> void register(final C condition, final Collection<Class<? extends C>> otherClasses, final Class<? extends C>... moreOtherClasses) {
        CONDITION_REGISTRY.put(TypeToken.get(condition.getClass()), condition);
        for (final Class<?> otherClass : otherClasses) {
            CONDITION_REGISTRY.put(TypeToken.get(otherClass), condition);
        }
        for (final Class<? extends C> otherClass : moreOtherClasses) {
            CONDITION_REGISTRY.put(TypeToken.get(otherClass), condition);
        }
    }

    public static boolean isRegistered(final TypeToken<?> typeToken) {
        return CONDITION_REGISTRY.containsKey(typeToken);
    }

    public static <C extends Condition<? super C>> C getDefaultCondition(final Class<C> classOfC) {
        return getDefaultCondition(TypeToken.get(classOfC));
    }

    @SuppressWarnings("unchecked")
    public static <C extends Condition<?>> C getDefaultCondition(final TypeToken<C> typeToken) {
        if (!CONDITION_REGISTRY.containsKey(typeToken)) {
            throw new IllegalArgumentException(typeToken.getRawType() + " doesn't have a registered default condition");
        }
        final C condition = (C) CONDITION_REGISTRY.get(typeToken);
        if (Proxy.isProxyClass(condition.getClass())) {
            if (condition instanceof EntityCondition) {
                return (C) EntityCondition.ANY;
            }
        }
        return (C) CONDITION_REGISTRY.get(typeToken);
    }

    @SuppressWarnings("unchecked")
    public static <C extends Condition<C>> TypeToken<? extends C> getAdapterType(final TypeToken<C> type) {
        return TypeToken.get((Class<? extends C>) Objects.requireNonNull(TYPE_TO_BASE_MAP.get(type)).getAnnotation(AdapterDelegate.class).value());
    }

    public static boolean shouldConvertType(final TypeToken<?> type) {
        return TYPE_TO_BASE_MAP.containsKey(type);
    }
}
