package me.machinemaker.advancements.conditions;

import com.google.common.collect.Maps;
import com.google.gson.reflect.TypeToken;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import me.machinemaker.advancements.conditions.entity.DamageCondition;
import me.machinemaker.advancements.conditions.entity.DamageSourceCondition;
import me.machinemaker.advancements.conditions.entity.sub.EntitySubCondition;
import me.machinemaker.advancements.conditions.entity.sub.FishingHookCondition;

public final class Conditions {

    private static final Map<TypeToken<?>, Object> CONDITION_REGISTRY = Maps.newHashMap();
    private static final Map<TypeToken<?>, Class<? extends Condition<?>>> TYPE_TO_BASE_MAP = new HashMap<>();

    static {
        // register(FishingHookCondition.ANY);
        register(DamageSourceCondition.ANY);
        register(DamageCondition.ANY);
        // player and lightning bolt must be registered AFTER entity
        // register(PlayerCondition.ANY);
        // register(LightningBoltCondition.ANY);
    }

    private Conditions() {
    }

    @Deprecated(forRemoval = true)
    private static <C extends Condition<? super C>> void register(final C condition) {
        register(condition, Collections.emptySet());
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

    public static <C extends Condition<? super C>> C getDefaultCondition(final Class<C> classOfC) {
        return getDefaultCondition(TypeToken.get(classOfC));
    }

    @SuppressWarnings("unchecked")
    public static <C extends Condition<?>> C getDefaultCondition(final TypeToken<C> typeToken) {
        if (!CONDITION_REGISTRY.containsKey(typeToken)) {
            throw new IllegalArgumentException(typeToken.getRawType() + " doesn't have a registered default condition");
        }
        return (C) CONDITION_REGISTRY.get(typeToken);
    }
}
