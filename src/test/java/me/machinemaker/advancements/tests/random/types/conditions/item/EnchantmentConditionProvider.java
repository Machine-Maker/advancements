package me.machinemaker.advancements.tests.random.types.conditions.item;

import me.machinemaker.advancements.conditions.item.EnchantmentCondition;
import me.machinemaker.advancements.tests.random.Provider;
import me.machinemaker.advancements.tests.random.RandomProviders;
import me.machinemaker.advancements.tests.random.types.conditions.ConditionProvider;

public class EnchantmentConditionProvider extends ConditionProvider<EnchantmentCondition, EnchantmentCondition.Builder> {

    public EnchantmentConditionProvider() {
        super(EnchantmentCondition::builder);
        this.component(() -> RandomProviders.ENCHANTMENT, EnchantmentCondition.Builder::enchantment);
        this.component(() -> RandomProviders.INTEGER_RANGE, EnchantmentCondition.Builder::level);
    }

    public Provider<EnchantmentCondition[]> array() {
        return new Array();
    }

    public final class Array implements Provider<EnchantmentCondition[]> {

        @Override
        public EnchantmentCondition[] get() {
            final EnchantmentCondition[] condition = new EnchantmentCondition[this.integer(1, 4)];
            for (int i = 0; i < condition.length; i++) {
                condition[i] = EnchantmentConditionProvider.this.get();
            }
            return condition;
        }
    }
}
