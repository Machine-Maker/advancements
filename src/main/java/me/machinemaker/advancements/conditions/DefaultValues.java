package me.machinemaker.advancements.conditions;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import java.util.Map;
import java.util.Objects;
import me.machinemaker.advancements.conditions.blocks.BlockCondition;
import me.machinemaker.advancements.conditions.blocks.BlockPropertyCondition;
import me.machinemaker.advancements.conditions.blocks.FluidCondition;
import me.machinemaker.advancements.conditions.blocks.LightCondition;
import me.machinemaker.advancements.conditions.item.EnchantmentCondition;
import me.machinemaker.advancements.conditions.item.ItemCondition;
import me.machinemaker.advancements.conditions.misc.NBTCondition;
import me.machinemaker.advancements.conditions.world.DistanceCondition;
import me.machinemaker.advancements.ranges.DoubleRange;
import me.machinemaker.advancements.ranges.IntegerRange;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class DefaultValues {

    private static final Map<TypeToken<?>, Condition<?>> DEFAULT_OBJECT_BY_BASE_TYPE;
    private static final ImmutableMap.Builder<TypeToken<?>, Condition<?>> DEFAULT_OBJECT_BY_BASE_TYPE_BUILDER = ImmutableMap.builder();

    static {
        register(IntegerRange.class, IntegerRange.conditionType());
        register(DoubleRange.class, DoubleRange.conditionType());

        register(BlockCondition.class, BlockCondition.conditionType());
        register(BlockPropertyCondition.class, BlockPropertyCondition.conditionType());
        register(FluidCondition.class, FluidCondition.conditionType());
        register(LightCondition.class, LightCondition.conditionType());

        register(DistanceCondition.class, DistanceCondition.conditionType());

        register(EnchantmentCondition.class, EnchantmentCondition.conditionType());
        register(ItemCondition.class, ItemCondition.conditionType());

        register(NBTCondition.class, NBTCondition.conditionType());
        DEFAULT_OBJECT_BY_BASE_TYPE = DEFAULT_OBJECT_BY_BASE_TYPE_BUILDER.build();
    }

    private DefaultValues() {
    }

    private static <C extends Condition<C>> void register(final Class<C> baseClass, final ConditionType<C> type) {
        DEFAULT_OBJECT_BY_BASE_TYPE_BUILDER.put(TypeToken.get(baseClass), type.any());
    }

    // public static boolean isDefaulted(final Class<?> clazz) {
    //     return DEFAULT_OBJECT_BY_BASE_TYPE.containsKey(TypeToken.get(clazz));
    // }

    public static boolean isDefaulted(final TypeToken<?> type) {
        return DEFAULT_OBJECT_BY_BASE_TYPE.containsKey(type);
    }

    // @SuppressWarnings("unchecked")
    // public static <T> T getDefaultValue(final Class<T> baseClass) {
    //     return (T) Objects.requireNonNull(DEFAULT_OBJECT_BY_BASE_TYPE.get(TypeToken.get(baseClass)), baseClass + " does not have a default value registered");
    // }

    @SuppressWarnings("unchecked")
    public static <T> T getDefaultValue(final TypeToken<T> type) {
        return (T) Objects.requireNonNull(DEFAULT_OBJECT_BY_BASE_TYPE.get(type), type + " does not have a default value registered");
    }
}
