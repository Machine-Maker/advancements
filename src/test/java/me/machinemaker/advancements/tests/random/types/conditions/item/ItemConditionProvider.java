package me.machinemaker.advancements.tests.random.types.conditions.item;

import java.util.stream.IntStream;
import me.machinemaker.advancements.conditions.item.ItemCondition;
import me.machinemaker.advancements.tags.ItemTag;
import me.machinemaker.advancements.tests.random.RandomProviders;
import me.machinemaker.advancements.tests.random.types.conditions.ConditionProvider;
import org.bukkit.Tag;

public class ItemConditionProvider extends ConditionProvider<ItemCondition, ItemCondition.Builder> {

    public ItemConditionProvider() {
        super(Component.class, ItemCondition::builder);
        this.component(() -> RandomProviders.INTEGER_RANGE, ItemCondition.Builder::count);
        this.component(() -> RandomProviders.INTEGER_RANGE, ItemCondition.Builder::durability);
        this.component(() -> RandomProviders.ENCHANTMENTS_CONDITION, ItemCondition.Builder::enchantments);
        this.component(() -> RandomProviders.ENCHANTMENTS_CONDITION, ItemCondition.Builder::storedEnchantments);
        this.component(() -> RandomProviders.POTION, ItemCondition.Builder::potion);
        this.component(() -> RandomProviders.NBT_CONDITION, ItemCondition.Builder::nbt);
    }

    private enum Component implements ConditionProvider.Component<ItemCondition.Builder> {
        TAG {
            @Override
            public void apply(final ItemCondition.Builder builder) {
                // TODO random tag
                builder.tag(new ItemTag(Tag.ITEMS_COALS));
            }
        },
        ITEMS {
            @Override
            public void apply(final ItemCondition.Builder builder) {
                IntStream.range(0, this.integer(1, 5)).forEach(i -> builder.addItem(RandomProviders.ITEM.get()));
            }
        }
    }
}
