package me.machinemaker.advancements.conditions.item;

import java.util.Iterator;
import java.util.Map;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.ConditionType;
import me.machinemaker.advancements.conditions.range.IntegerRange;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

@ApiStatus.NonExtendable
public interface EnchantmentCondition extends Condition.Buildable<EnchantmentCondition, EnchantmentCondition.Builder> {

    @Contract(pure = true)
    static ConditionType<EnchantmentCondition> conditionType() {
        return EnchantmentConditionImpl.TYPE;
    }

    @Contract(value = "_ -> new", pure = true)
    static EnchantmentCondition[] from(final Map<Enchantment, Integer> map) {
        final EnchantmentCondition[] conditions = new EnchantmentConditionImpl[map.size()];
        final Iterator<Map.Entry<Enchantment, Integer>> iterator = map.entrySet().iterator();
        for (int i = 0; i < conditions.length; i++) {
            final Map.Entry<Enchantment, Integer> entry = iterator.next();
            conditions[i] = new EnchantmentConditionImpl(entry.getKey(), IntegerRange.isExactly(entry.getValue()));
        }
        return conditions;
    }

    @Contract(value = "-> new", pure = true)
    static Builder builder() {
        return new EnchantmentConditionImpl.BuilderImpl();
    }

    @Contract(pure = true)
    static GsonBuilderApplicable requiredGson() {
        return EnchantmentConditionImpl.REQUIRED_GSON;
    }

    @Contract(pure = true)
    @Nullable Enchantment enchantment();

    @Contract(pure = true)
    IntegerRange level();

    @ApiStatus.NonExtendable
    interface Builder extends Condition.Builder<EnchantmentCondition> {

        @Contract(pure = true)
        @Nullable Enchantment enchantment();

        @Contract(value = "_ -> this", mutates = "this")
        Builder enchantment(@Nullable Enchantment enchantment);

        @Contract(pure = true)
        IntegerRange level();

        @Contract(value = "_ -> this", mutates = "this")
        Builder level(IntegerRange level);
    }
}
