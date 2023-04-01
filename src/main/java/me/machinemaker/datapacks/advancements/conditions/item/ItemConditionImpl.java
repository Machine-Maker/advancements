package me.machinemaker.datapacks.advancements.conditions.item;

import com.google.common.base.Preconditions;
import io.papermc.paper.potion.Potion;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.conditions.misc.NBTCondition;
import me.machinemaker.datapacks.advancements.conditions.range.IntegerRange;
import me.machinemaker.datapacks.advancements.tags.ItemTag;
import me.machinemaker.datapacks.advancements.utils.Util;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.Nullable;

record ItemConditionImpl(
    @Nullable ItemTag tag,
    @Nullable Set<Material> items,
    IntegerRange count,
    IntegerRange durability,
    EnchantmentCondition[] enchantments,
    EnchantmentCondition[] storedEnchantments,
    @Nullable Potion potion,
    NBTCondition nbt
) implements ItemCondition {

    static final ItemCondition ANY = new ItemConditionImpl(
        null,
        null,
        IntegerRange.conditionType().any(),
        IntegerRange.conditionType().any(),
        EnchantmentConditionImpl.NONE,
        EnchantmentConditionImpl.NONE,
        null,
        NBTCondition.conditionType().any()
    );
    static final ConditionType<ItemCondition> TYPE = ConditionType.create(ItemCondition.class, ANY, ItemConditionImpl.class);

    ItemConditionImpl {
        items = Util.nullableCopy(items);
    }

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public ItemCondition.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    // manually define toString, equals, and hashCode due to EnchantmentCondition[]
    //<editor-fold desc="Manually defined toString, equals, and hashCode" defaultstate="collapsed">
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
    public boolean equals(final @Nullable Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        final ItemConditionImpl that = (ItemConditionImpl) o;
        return Objects.equals(this.tag, that.tag) && Objects.equals(this.items, that.items) && this.count.equals(that.count) && this.durability.equals(that.durability) && Arrays.equals(this.enchantments, that.enchantments) && Arrays.equals(this.storedEnchantments, that.storedEnchantments) && Objects.equals(this.potion, that.potion) && this.nbt.equals(that.nbt);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(this.tag, this.items, this.count, this.durability, this.potion, this.nbt);
        result = 31 * result + Arrays.hashCode(this.enchantments);
        result = 31 * result + Arrays.hashCode(this.storedEnchantments);
        return result;
    }
    //</editor-fold>

    static final class BuilderImpl implements ItemCondition.Builder {

        private @Nullable ItemTag tag = null;
        private @Nullable Set<Material> items = null;
        private IntegerRange count = IntegerRange.conditionType().any();
        private IntegerRange durability = IntegerRange.conditionType().any();
        private EnchantmentCondition[] enchantments = EnchantmentConditionImpl.NONE;
        private EnchantmentCondition[] storedEnchantments = EnchantmentConditionImpl.NONE;
        private @Nullable Potion potion = null;
        private NBTCondition nbt = NBTCondition.conditionType().any();

        BuilderImpl() {
        }

        BuilderImpl(final ItemCondition condition) {
            this.tag = condition.tag();
            this.items = condition.items();
            this.count = condition.count();
            this.durability = condition.durability();
            this.enchantments = condition.enchantments();
            this.storedEnchantments = condition.storedEnchantments();
            this.potion = condition.potion();
            this.nbt = condition.nbt();
        }

        @Override
        public @Nullable ItemTag tag() {
            return this.tag;
        }

        @Override
        public ItemCondition.Builder tag(final @Nullable ItemTag tag) {
            this.tag = tag;
            return this;
        }

        @Override
        public @Nullable Set<Material> items() {
            return this.items;
        }

        @Override
        public ItemCondition.Builder addItem(final Material item) {
            Preconditions.checkArgument(item.isItem(), "Cannot add a material that isn't an item");
            if (this.items == null) {
                this.items = new HashSet<>();
            }
            this.items.add(item);
            return this;
        }

        @Override
        public ItemCondition.Builder items(final @Nullable Set<Material> items) {
            this.items = items;
            return this;
        }

        @Override
        public IntegerRange count() {
            return this.count;
        }

        @Override
        public ItemCondition.Builder count(final IntegerRange count) {
            this.count = count;
            return this;
        }

        @Override
        public IntegerRange durability() {
            return this.durability;
        }

        @Override
        public ItemCondition.Builder durability(final IntegerRange durability) {
            this.durability = durability;
            return this;
        }

        @Override
        public EnchantmentCondition[] enchantments() {
            return this.enchantments;
        }

        @Override
        public ItemCondition.Builder enchantments(final EnchantmentCondition[] enchantments) {
            this.enchantments = enchantments;
            return this;
        }

        @Override
        public EnchantmentCondition[] storedEnchantments() {
            return this.storedEnchantments;
        }

        @Override
        public ItemCondition.Builder storedEnchantments(final EnchantmentCondition[] storedEnchantments) {
            this.storedEnchantments = storedEnchantments;
            return this;
        }

        @Override
        public @Nullable Potion potion() {
            return this.potion;
        }

        @Override
        public ItemCondition.Builder potion(final @Nullable Potion potion) {
            this.potion = potion;
            return this;
        }

        @Override
        public NBTCondition nbt() {
            return this.nbt;
        }

        @Override
        public ItemCondition.Builder nbt(final NBTCondition nbt) {
            this.nbt = nbt;
            return this;
        }

        @Override
        public ItemCondition build() {
            return new ItemConditionImpl(this.tag, this.items, this.count, this.durability, this.enchantments, this.storedEnchantments, this.potion, this.nbt);
        }
    }
}
