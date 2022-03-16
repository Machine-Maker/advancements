package me.machinemaker.advancements.conditions;

import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import me.machinemaker.advancements.conditions.blocks.BlockCondition;
import me.machinemaker.advancements.conditions.blocks.PropertyCondition;
import me.machinemaker.advancements.conditions.blocks.FluidCondition;
import me.machinemaker.advancements.conditions.blocks.LightCondition;
import me.machinemaker.advancements.conditions.effects.PotionEffectInstanceCondition;
import me.machinemaker.advancements.conditions.effects.PotionEffectsCondition;
import me.machinemaker.advancements.conditions.entity.DamageCondition;
import me.machinemaker.advancements.conditions.entity.DamageSourceCondition;
import me.machinemaker.advancements.conditions.entity.EntityEquipmentCondition;
import me.machinemaker.advancements.conditions.entity.EntityFlagsCondition;
import me.machinemaker.advancements.conditions.entity.EntityTypeCondition;
import me.machinemaker.advancements.conditions.entity.PlayerCondition;
import me.machinemaker.advancements.conditions.entity.EntityCondition;
import me.machinemaker.advancements.conditions.entity.FishingHookCondition;
import me.machinemaker.advancements.conditions.entity.LightningBoltCondition;
import me.machinemaker.advancements.conditions.item.EnchantmentCondition;
import me.machinemaker.advancements.conditions.item.ItemCondition;
import me.machinemaker.advancements.conditions.misc.NBTCondition;
import me.machinemaker.advancements.conditions.world.DistanceCondition;
import me.machinemaker.advancements.conditions.world.LocationCondition;
import me.machinemaker.advancements.ranges.DoubleRange;
import me.machinemaker.advancements.ranges.IntegerRange;

import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public final class Conditions {

    private Conditions() {
    }

    private static final Map<TypeToken<?>, Object> CONDITION_REGISTRY = Maps.newHashMap();

    // public static final Type<DoubleRange> DOUBLE_RANGE_CONDITION = new Type<>()

    static {
        register(DoubleRange.ANY);
        register(IntegerRange.ANY);

        register(BlockCondition.ANY);
        register(PropertyCondition.ANY);
        register(FluidCondition.ANY);
        register(LightCondition.ANY);

        register(EntityEquipmentCondition.ANY);
        register(EntityFlagsCondition.ANY);
        register(EntityTypeCondition.ANY, EntityTypeCondition.types());
        register(FishingHookCondition.ANY);
        register(DamageSourceCondition.ANY);
        register(DamageCondition.ANY);
        register(EntityCondition.ANY, EntityCondition.types());
        // player and lightning bolt must be registered AFTER entity
        register(PlayerCondition.ANY);
        register(LightningBoltCondition.ANY);

        register(EnchantmentCondition.ANY);
        register(ItemCondition.ANY);

        register(PotionEffectInstanceCondition.ANY);
        register(PotionEffectsCondition.ANY);

        register(NBTCondition.ANY);

        register(DistanceCondition.ANY);
        register(LocationCondition.ANY);
    }

    private static <C extends Condition<? super C>> void register(C condition) {
        register(condition, Collections.emptySet());
    }

    private static <C extends Condition<? super C>> void register(C condition, Collection<Class<? extends C>> otherClasses) {
        CONDITION_REGISTRY.put(TypeToken.get(condition.getClass()), condition);
        for (Class<?> otherClass : otherClasses) {
            CONDITION_REGISTRY.put(TypeToken.get(otherClass), condition);
        }
    }

    public static boolean isRegistered(TypeToken<?> typeToken) {
        return CONDITION_REGISTRY.containsKey(typeToken);
    }

    public static <C extends Condition<? super C>> C getDefaultCondition(Class<C> classOfC) {
        return getDefaultCondition(TypeToken.get(classOfC));
    }

    @SuppressWarnings("unchecked")
    public static <C extends Condition<?>> C getDefaultCondition(TypeToken<C> typeToken) {
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

    public static void init() {
        // load classes
    }


    // public record Type<C>(@NotNull TypeToken<C> type, @NotNull C anyInstance, @NotNull GsonBuilderApplicable gsonBuilderApplicable, @NotNull Class<? super C> @NotNull...otherTypes) {
    //     @SafeVarargs
    //     public Type {
    //     }
    // }
}
