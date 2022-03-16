package me.machinemaker.advancements.conditions.item;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.gson.annotations.JsonAdapter;
import io.papermc.paper.potion.Potion;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.GsonBuilderApplicable;
import me.machinemaker.advancements.adapters.util.IgnoreRecordTypeAdapter;
import me.machinemaker.advancements.ranges.IntegerRange;
import me.machinemaker.advancements.tags.ItemTag;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.misc.NBTCondition;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.jetbrains.annotations.Contract;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public record ItemCondition(
        @Nullable ItemTag tag,
        @Nullable Set<Material> items,
        IntegerRange count,
        IntegerRange durability,
        @JsonAdapter(value = EnchantmentCondition.ArrayAdapter.class, nullSafe = false) EnchantmentCondition[] enchantments,
        @JsonAdapter(value = EnchantmentCondition.ArrayAdapter.class, nullSafe = false) EnchantmentCondition[] storedEnchantments,
        @Nullable Potion potion,
        NBTCondition nbt
) implements Condition<ItemCondition> {

    public static final GsonBuilderApplicable BUILDER_APPLICABLE = Adapters.of(Adapters.ITEM_TAG_ADAPTER, Adapters.MATERIAL_ADAPTER, EnchantmentCondition.BUILDER_APPLICABLE, Adapters.POTION_ADAPTER);
    public static final ItemCondition ANY = new ItemCondition(null, null, IntegerRange.ANY, IntegerRange.ANY, EnchantmentCondition.NONE, EnchantmentCondition.NONE, null, NBTCondition.ANY);

    @Contract(value = "_ -> new", pure = true)
    public static ItemCondition forTag(Tag<Material> tag) {
        return new ItemCondition(tag instanceof ItemTag itemTag ? itemTag : new ItemTag(tag), null, IntegerRange.ANY, IntegerRange.ANY, EnchantmentCondition.NONE, EnchantmentCondition.NONE, null, NBTCondition.ANY);
    }

    @Contract(value = "_ -> new", pure = true)
    public static ItemCondition forItems(Material ...items) {
        return forItems(Sets.newHashSet(items));
    }

    @Contract(value = "_ -> new", pure = true)
    public static ItemCondition forItems(Set<Material> items) {
        Preconditions.checkArgument(items.stream().allMatch(Material::isItem), "all elements of items must be actual items");
        return new ItemCondition(null, items, IntegerRange.ANY, IntegerRange.ANY, EnchantmentCondition.NONE, EnchantmentCondition.NONE, null, NBTCondition.ANY);
    }

    @Override
    public ItemCondition any() {
        return ANY;
    }

    // manually define toString, equals, and hashCode due to EnchantmentCondition[]
    @Override
    public String toString() {
        if (this.isAny()) {
            return "ItemCondition{ANY}";
        }
        return "ItemCondition{" +
                "tag=" + this.tag +
                ", items=" + this.items +
                ", count=" + this.count +
                ", durability=" + this.durability +
                ", enchantments=" + Arrays.toString(this.enchantments) +
                ", storedEnchantments=" + Arrays.toString(this.storedEnchantments) +
                ", potion=" + this.potion +
                ", nbt=" + this.nbt +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        ItemCondition condition = (ItemCondition) o;
        return Objects.equals(this.tag, condition.tag) && Objects.equals(this.items, condition.items) && this.count.equals(condition.count) && this.durability.equals(condition.durability) && Arrays.equals(this.enchantments, condition.enchantments) && Arrays.equals(this.storedEnchantments, condition.storedEnchantments) && Objects.equals(this.potion, condition.potion) && this.nbt.equals(condition.nbt);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(this.tag, this.items, this.count, this.durability, this.potion, this.nbt);
        result = 31 * result + Arrays.hashCode(this.enchantments);
        result = 31 * result + Arrays.hashCode(this.storedEnchantments);
        return result;
    }
}
