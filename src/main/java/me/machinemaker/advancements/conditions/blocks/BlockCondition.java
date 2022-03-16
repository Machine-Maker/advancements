package me.machinemaker.advancements.conditions.blocks;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.misc.NBTCondition;
import me.machinemaker.advancements.tags.BlockTag;
import me.machinemaker.advancements.util.Buildable;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

import java.util.HashSet;
import java.util.Set;

public record BlockCondition(
        @Nullable BlockTag tag,
        @Nullable Set<Material> blocks,
        PropertyCondition state,
        NBTCondition nbt
) implements Condition<BlockCondition>, Buildable<BlockCondition, BlockCondition.Builder> {

    public static final GsonBuilderApplicable BUILDER_APPLICABLE = Adapters.of(Adapters.MATERIAL_ADAPTER, Adapters.BLOCK_TAG_ADAPTER);
    public static final BlockCondition ANY = new BlockCondition(null, null, PropertyCondition.ANY, NBTCondition.ANY);

    @Contract(value = "_ -> new", pure = true)
    public static BlockCondition forTag(Tag<Material> tag) {
        return new BlockCondition(tag instanceof BlockTag blockTag ? blockTag : new BlockTag(tag), null, PropertyCondition.ANY, NBTCondition.ANY);
    }

    /**
     * Creates a new block condition from an array of blocks.
     *
     * @param blocks must all pass {@link Material#isBlock()}
     * @return a new block condition
     * @throws IllegalArgumentException if any of the materials isn't a block
     */
    @Contract(value = "_ -> new", pure = true)
    public static BlockCondition forBlocks(Material ...blocks) {
        return forBlocks(Sets.newHashSet(blocks));
    }

    /**
     * Creates a new block condition from a set of blocks.
     *
     * @param blocks must all pass {@link Material#isBlock()}
     * @return a new block condition
     * @throws IllegalArgumentException if any of the materials isn't a block
     */
    @Contract(value = "_ -> new", pure = true)
    public static BlockCondition forBlocks(Set<Material> blocks) {
        Preconditions.checkArgument(blocks.stream().allMatch(Material::isBlock), "Cannot have a material that isn't a block");
        return new BlockCondition(null, blocks, PropertyCondition.ANY, NBTCondition.ANY);
    }

    @Override
    public BlockCondition any() {
        return ANY;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "BlockCondition{ANY}";
        }
        return "BlockCondition{" +
                "tag=" + this.tag +
                ", blocks=" + this.blocks +
                ", state=" + this.state +
                ", nbt=" + this.nbt +
                '}';
    }

    @Contract(value = "-> new", pure = true)
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements Condition.Builder<BlockCondition> {

        private @Nullable BlockTag tag;
        private @Nullable Set<Material> blocks;
        private PropertyCondition state;
        private NBTCondition nbt;

        private Builder() {
            this.tag = null;
            this.blocks = null;
            this.state = PropertyCondition.ANY;
            this.nbt = NBTCondition.ANY;
        }

        private Builder(BlockCondition condition) {
            this.tag = condition.tag;
            this.blocks = condition.blocks != null ? new HashSet<>(condition.blocks) : null;
            this.state = condition.state;
            this.nbt = condition.nbt;
        }

        public @Nullable BlockTag tag() {
            return this.tag;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder tag(@Nullable BlockTag tag) {
            this.tag = tag;
            return this;
        }

        public @Nullable Set<Material> blocks() {
            return this.blocks;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder blocks(@Nullable Set<Material> blocks) {
            Preconditions.checkArgument(blocks == null || blocks.stream().allMatch(Material::isBlock), "Cannot have a material that isn't a block");
            this.blocks = blocks;
            return this;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder blocks(Material ...blocks) {
            return this.blocks(Sets.newHashSet(blocks));
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder addBlock(Material block) {
            Preconditions.checkArgument(block.isBlock(), "Cannot add a material that isn't a block");
            if (this.blocks == null) {
                this.blocks = new HashSet<>();
            }
            this.blocks.add(block);
            return this;
        }

        public PropertyCondition state() {
            return this.state;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder state(PropertyCondition state) {
            this.state = state;
            return this;
        }

        public NBTCondition nbt() {
            return this.nbt;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder nbt(NBTCondition nbt) {
            this.nbt = nbt;
            return this;
        }

        @Override
        public BlockCondition build() {
            return new BlockCondition(this.tag, this.blocks, this.state, this.nbt);
        }
    }
}
