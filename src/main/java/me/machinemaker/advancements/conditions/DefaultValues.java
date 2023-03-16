package me.machinemaker.advancements.conditions;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import java.util.Map;
import java.util.Objects;
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
import me.machinemaker.advancements.conditions.entity.sub.EntitySubCondition;
import me.machinemaker.advancements.conditions.item.EnchantmentCondition;
import me.machinemaker.advancements.conditions.item.ItemCondition;
import me.machinemaker.advancements.conditions.misc.NBTCondition;
import me.machinemaker.advancements.conditions.world.DistanceCondition;
import me.machinemaker.advancements.conditions.world.LocationCondition;
import me.machinemaker.advancements.conditions.range.DoubleRange;
import me.machinemaker.advancements.conditions.range.IntegerRange;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class DefaultValues {

    private static final Map<TypeToken<?>, Condition<?>> DEFAULT_OBJECT_BY_BASE_TYPE;
    private static final ImmutableMap.Builder<TypeToken<?>, Condition<?>> DEFAULT_OBJECT_BY_BASE_TYPE_BUILDER = ImmutableMap.builder();

    static {
        register(IntegerRange.conditionType());
        register(DoubleRange.conditionType());

        register(BlockCondition.conditionType());
        register(BlockPropertyCondition.conditionType());
        register(FluidCondition.conditionType());
        register(LightCondition.conditionType());

        register(PotionEffectInstanceCondition.conditionType());
        register(PotionEffectsCondition.conditionType());

        register(EntityCondition.conditionType());
        register(EntitySubCondition.conditionType());
        register(EntityEquipmentCondition.conditionType());
        register(EntityFlagsCondition.conditionType());
        register(EntityTypeCondition.conditionType());

        register(EnchantmentCondition.conditionType());
        register(ItemCondition.conditionType());

        register(DistanceCondition.conditionType());
        register(LocationCondition.conditionType());

        register(NBTCondition.conditionType());
        DEFAULT_OBJECT_BY_BASE_TYPE = DEFAULT_OBJECT_BY_BASE_TYPE_BUILDER.build();
    }

    private DefaultValues() {
    }

    private static <C extends Condition<C>> void register(final ConditionType<C> type) {
        DEFAULT_OBJECT_BY_BASE_TYPE_BUILDER.put(TypeToken.get(type.baseType()), type.any());
    }

    public static boolean isDefaulted(final TypeToken<?> type) {
        return DEFAULT_OBJECT_BY_BASE_TYPE.containsKey(type);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getDefaultValue(final TypeToken<T> type) {
        return (T) Objects.requireNonNull(DEFAULT_OBJECT_BY_BASE_TYPE.get(type), type + " does not have a default value registered");
    }
}
