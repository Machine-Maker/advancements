package me.machinemaker.datapacks.advancements.conditions.block;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import java.util.Set;
import me.machinemaker.datapacks.advancements.conditions.Condition;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.conditions.misc.NBTCondition;
import me.machinemaker.datapacks.toremove.tags.BlockTag;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

public interface BlockCondition extends Condition.Buildable<BlockCondition, BlockCondition.Builder> {

    @Contract(pure = true)
    static ConditionType<BlockCondition> conditionType() {
        return BlockConditionImpl.TYPE;
    }

    @Contract(value = "_ -> new", pure = true)
    static BlockCondition forTag(final Tag<Material> tag) {
        return new BlockConditionImpl(tag instanceof final BlockTag blockTag ? blockTag : new BlockTag(tag), null, BlockPropertyCondition.conditionType().any(), NBTCondition.conditionType().any());
    }

    /**
     * Creates a new block condition from an array of blocks.
     *
     * @param blocks must all pass {@link Material#isBlock()}
     * @return a new block condition
     * @throws IllegalArgumentException if any of the materials isn't a block
     */
    @Contract(value = "_ -> new", pure = true)
    static BlockCondition forBlocks(final Material... blocks) {
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
    static BlockCondition forBlocks(final Set<Material> blocks) {
        Preconditions.checkArgument(blocks.stream().allMatch(Material::isBlock), "Cannot have a material that isn't a block");
        return new BlockConditionImpl(null, blocks, BlockPropertyCondition.conditionType().any(), NBTCondition.conditionType().any());
    }

    @Contract(value = "-> new", pure = true)
    static Builder builder() {
        return new BlockConditionImpl.BuilderImpl();
    }

    @Contract(pure = true)
    @Nullable BlockTag tag();

    @Contract(pure = true)
    @Unmodifiable @Nullable Set<Material> blocks();

    @Contract(pure = true)
    BlockPropertyCondition state();

    @Contract(pure = true)
    NBTCondition nbt();

    interface Builder extends Condition.Builder<BlockCondition> {

        @Contract(pure = true)
        @Nullable BlockTag tag();

        @Contract(value = "_ -> this", mutates = "this")
        Builder tag(final @Nullable BlockTag tag);

        @Contract(pure = true)
        @Nullable @UnmodifiableView Set<Material> blocks();

        @Contract(value = "_ -> this", mutates = "this")
        Builder blocks(final @Nullable Set<Material> blocks);

        @Contract(value = "_ -> this", mutates = "this")
        Builder blocks(final Material... blocks);

        @Contract(value = "_ -> this", mutates = "this")
        Builder addBlock(final Material block);

        @Contract(pure = true)
        BlockPropertyCondition state();

        @Contract(value = "_ -> this", mutates = "this")
        Builder state(final BlockPropertyCondition state);

        @Contract(pure = true)
        NBTCondition nbt();

        @Contract(value = "_ -> this", mutates = "this")
        Builder nbt(final NBTCondition nbt);
    }
}
