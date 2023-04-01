package me.machinemaker.datapacks.advancements.conditions;

import com.google.common.collect.ImmutableMap;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import me.machinemaker.datapacks.advancements.conditions.block.BlockCondition;
import me.machinemaker.datapacks.advancements.conditions.block.BlockPropertyCondition;
import me.machinemaker.datapacks.advancements.conditions.block.FluidCondition;
import me.machinemaker.datapacks.advancements.conditions.block.LightCondition;
import me.machinemaker.datapacks.advancements.conditions.effect.PotionEffectInstanceCondition;
import me.machinemaker.datapacks.advancements.conditions.effect.PotionEffectsCondition;
import me.machinemaker.datapacks.advancements.conditions.entity.EntityCondition;
import me.machinemaker.datapacks.advancements.conditions.entity.EntityEquipmentCondition;
import me.machinemaker.datapacks.advancements.conditions.entity.EntityFlagsCondition;
import me.machinemaker.datapacks.advancements.conditions.entity.EntityTypeCondition;
import me.machinemaker.datapacks.advancements.conditions.entity.sub.EntitySubCondition;
import me.machinemaker.datapacks.advancements.conditions.item.EnchantmentCondition;
import me.machinemaker.datapacks.advancements.conditions.item.ItemCondition;
import me.machinemaker.datapacks.advancements.conditions.misc.NBTCondition;
import me.machinemaker.datapacks.advancements.conditions.range.DoubleRange;
import me.machinemaker.datapacks.advancements.conditions.range.IntegerRange;
import me.machinemaker.datapacks.advancements.conditions.world.DistanceCondition;
import me.machinemaker.datapacks.advancements.conditions.world.LocationCondition;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class Conditions {

    private static final List<ConditionType<?>> CONDITION_TYPES = new ArrayList<>();
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

    private Conditions() {
    }

    private static <C extends Condition<C>> void register(final ConditionType<C> type) {
        DEFAULT_OBJECT_BY_BASE_TYPE_BUILDER.put(TypeToken.get(type.baseType()), type.any());
        CONDITION_TYPES.add(type);
    }

    public static void applyToGsonBuilder(final GsonBuilder builder) {
        for (final ConditionType<?> type : CONDITION_TYPES) {
            ((ConditionTypeImpl<?>) type).builderConsumer().accept(builder);
        }
    }

    public static boolean isDefaulted(final TypeToken<?> type) {
        return DEFAULT_OBJECT_BY_BASE_TYPE.containsKey(type);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getDefaultValue(final TypeToken<T> type) {
        return (T) Objects.requireNonNull(DEFAULT_OBJECT_BY_BASE_TYPE.get(type), type + " does not have a default value registered");
    }
}
