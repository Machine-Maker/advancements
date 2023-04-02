package me.machinemaker.datapacks.advancements.testing.types.conditions.item;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import me.machinemaker.datapacks.advancements.conditions.item.ItemCondition;
import me.machinemaker.datapacks.advancements.tags.ItemTag;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.advancements.testing.types.conditions.ConditionProvider;
import me.machinemaker.datapacks.utils.TestUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import oshi.util.Memoizer;

import static me.machinemaker.datapacks.utils.TestUtils.fromResourceLocation;

public class ItemConditionProvider extends ConditionProvider<ItemCondition, ItemCondition.Builder> {

    private static final Supplier<List<TagKey<Item>>> ITEM_TAGS = Memoizer.memoize(() -> BuiltInRegistries.ITEM.getTagNames().toList());

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
                final NamespacedKey randomTagKey = fromResourceLocation(this.randomElement(ITEM_TAGS.get()).location());
                final Tag<Material> randomTag = Objects.requireNonNull(Bukkit.getTag(Tag.REGISTRY_ITEMS, randomTagKey, Material.class));
                builder.tag(new ItemTag(randomTag));
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
