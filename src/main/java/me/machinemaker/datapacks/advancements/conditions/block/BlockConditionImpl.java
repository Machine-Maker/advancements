package me.machinemaker.datapacks.advancements.conditions.block;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.conditions.misc.NBTCondition;
import me.machinemaker.datapacks.advancements.tags.BlockTag;
import me.machinemaker.datapacks.advancements.utils.Util;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

record BlockConditionImpl(
    @Nullable BlockTag tag,
    @Nullable Set<Material> blocks,
    BlockPropertyCondition state,
    NBTCondition nbt
) implements BlockCondition {

    static final BlockConditionImpl ANY = new BlockConditionImpl(null, null, BlockPropertyCondition.conditionType().any(), NBTCondition.conditionType().any());
    static final ConditionType<BlockCondition> TYPE = ConditionType.create(BlockCondition.class, ANY, BlockConditionImpl.class);

    BlockConditionImpl {
        blocks = Util.nullableCopy(blocks);
    }

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public BlockCondition.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "BlockCondition{ANY}";
        }
        return "BlockCondition{" + "tag=" + this.tag + ", blocks=" + this.blocks + ", state=" + this.state + ", nbt=" + this.nbt + '}';
    }

    static final class BuilderImpl implements BlockCondition.Builder {

        private @Nullable BlockTag tag;
        private @Nullable Set<Material> blocks;
        private BlockPropertyCondition state;
        private NBTCondition nbt;

        BuilderImpl() {
            this.tag = null;
            this.blocks = null;
            this.state = BlockPropertyCondition.conditionType().any();
            this.nbt = NBTCondition.conditionType().any();
        }

        BuilderImpl(final BlockCondition condition) {
            this.tag = condition.tag();
            this.blocks = condition.blocks() != null ? new HashSet<>(condition.blocks()) : null;
            this.state = condition.state();
            this.nbt = condition.nbt();
        }

        @Override
        public @Nullable BlockTag tag() {
            return this.tag;
        }

        @Override
        public BlockCondition.Builder tag(final @Nullable BlockTag tag) {
            this.tag = tag;
            return this;
        }

        @Override
        public @Nullable @UnmodifiableView Set<Material> blocks() {
            return this.blocks == null ? null : Collections.unmodifiableSet(this.blocks);
        }

        @Override
        public BlockCondition.Builder blocks(final @Nullable Set<Material> blocks) {
            Preconditions.checkArgument(blocks == null || blocks.stream().allMatch(Material::isBlock), "Cannot have a material that isn't a block");
            this.blocks = blocks;
            return this;
        }

        @Override
        public BlockCondition.Builder blocks(final Material... blocks) {
            return this.blocks(Sets.newHashSet(blocks));
        }

        @Override
        public BlockCondition.Builder addBlock(final Material block) {
            Preconditions.checkArgument(block.isBlock(), "Cannot add a material that isn't a block");
            if (this.blocks == null) {
                this.blocks = new HashSet<>();
            }
            this.blocks.add(block);
            return this;
        }

        @Override
        public BlockPropertyCondition state() {
            return this.state;
        }

        @Override
        public BlockCondition.Builder state(final BlockPropertyCondition state) {
            this.state = state;
            return this;
        }

        public NBTCondition nbt() {
            return this.nbt;
        }

        public BlockCondition.Builder nbt(final NBTCondition nbt) {
            this.nbt = nbt;
            return this;
        }

        @Override
        public BlockCondition build() {
            return new BlockConditionImpl(this.tag, this.blocks, this.state, this.nbt);
        }

    }

}
