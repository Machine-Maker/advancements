package me.machinemaker.datapacks.advancements.conditions.item;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import io.papermc.paper.potion.Potion;
import java.util.Set;
import me.machinemaker.datapacks.advancements.conditions.Condition;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.conditions.misc.NBTCondition;
import me.machinemaker.datapacks.advancements.conditions.range.IntegerRange;
import me.machinemaker.datapacks.advancements.tags.ItemTag;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

public interface ItemCondition extends Condition.Buildable<ItemCondition, ItemCondition.Builder> {

    @Contract(pure = true)
    static ConditionType<ItemCondition> conditionType() {
        return ItemConditionImpl.TYPE;
    }

    @Contract(value = "_ -> new", pure = true)
    static ItemCondition forTag(final Tag<Material> tag) {
        return new ItemConditionImpl(tag instanceof ItemTag itemTag ? itemTag : new ItemTag(tag), null, IntegerRange.conditionType().any(), IntegerRange.conditionType().any(), EnchantmentConditionImpl.NONE, EnchantmentConditionImpl.NONE, null, NBTCondition.conditionType().any());
    }

    @Contract(value = "_ -> new", pure = true)
    static ItemCondition forItems(final Material... items) {
        return forItems(Sets.newHashSet(items));
    }

    @Contract(value = "_ -> new", pure = true)
    static ItemCondition forItems(final Set<Material> items) {
        Preconditions.checkArgument(items.stream().allMatch(Material::isItem), "all elements of items must be actual items");
        return new ItemConditionImpl(null, items, IntegerRange.conditionType().any(), IntegerRange.conditionType().any(), EnchantmentConditionImpl.NONE, EnchantmentConditionImpl.NONE, null, NBTCondition.conditionType().any());
    }

    @Contract(value = "-> new", pure = true)
    static Builder builder() {
        return new ItemConditionImpl.BuilderImpl();
    }

    @Contract(pure = true)
    @Nullable ItemTag tag();

    @Contract(pure = true)
    @Unmodifiable @Nullable Set<Material> items();

    @Contract(pure = true)
    IntegerRange count();

    @Contract(pure = true)
    IntegerRange durability();

    @Contract(pure = true)
    EnchantmentCondition[] enchantments();

    @Contract(pure = true)
    EnchantmentCondition[] storedEnchantments();

    @Nullable Potion potion();

    @Contract(pure = true)
    NBTCondition nbt();

    interface Builder extends Condition.Builder<ItemCondition> {

        @Contract(pure = true)
        @Nullable ItemTag tag();

        @Contract(value = "_ -> this", mutates = "this")
        Builder tag(@Nullable ItemTag tag);

        @Contract(pure = true)

        @Nullable Set<Material> items();

        @Contract(value = "_ -> this", mutates = "this")
        Builder addItem(Material item);

        @Contract(value = "_ -> this", mutates = "this")
        Builder items(@Nullable Set<Material> items);

        @Contract(pure = true)
        IntegerRange count();

        @Contract(value = "_ -> this", mutates = "this")
        Builder count(IntegerRange count);

        @Contract(pure = true)
        IntegerRange durability();

        @Contract(value = "_ -> this", mutates = "this")
        Builder durability(IntegerRange durability);

        @Contract(pure = true)
        EnchantmentCondition[] enchantments();

        @Contract(value = "_ -> this", mutates = "this")
        Builder enchantments(EnchantmentCondition[] enchantments);

        @Contract(pure = true)
        EnchantmentCondition[] storedEnchantments();

        @Contract(value = "_ -> this", mutates = "this")
        Builder storedEnchantments(EnchantmentCondition[] storedEnchantments);

        @Contract(pure = true)
        @Nullable Potion potion();

        @Contract(value = "_ -> this", mutates = "this")
        Builder potion(@Nullable Potion potion);

        @Contract(pure = true)
        NBTCondition nbt();

        @Contract(value = "_ -> this", mutates = "this")
        Builder nbt(NBTCondition nbt);

    }
}
