package me.machinemaker.advancements.tests.random.types.conditions.block;

import java.util.stream.IntStream;
import me.machinemaker.advancements.conditions.block.BlockCondition;
import me.machinemaker.advancements.tags.BlockTag;
import me.machinemaker.advancements.tests.random.RandomProviders;
import me.machinemaker.advancements.tests.random.types.conditions.ConditionProvider;
import org.bukkit.Tag;

public class BlockConditionProvider extends ConditionProvider<BlockCondition, BlockCondition.Builder> {

    public BlockConditionProvider() {
        super(Component.class, BlockCondition::builder);
        this.component(() -> RandomProviders.NBT_CONDITION, BlockCondition.Builder::nbt);
        this.component(() -> RandomProviders.BLOCK_PROPERTY_CONDITION, BlockCondition.Builder::state);
    }

    private enum Component implements ConditionProvider.Component<BlockCondition.Builder> {
        BLOCKS {
            @Override
            public void apply(final BlockCondition.Builder builder) {
                IntStream.range(0, this.integer(1, 5)).forEach(i -> builder.addBlock(RandomProviders.BLOCK.get()));
            }
        },
        BLOCK_TAG {
            @Override
            public void apply(final BlockCondition.Builder builder) {
                // TODO better random tags
                builder.tag(new BlockTag(Tag.ANVIL));
            }
        },
    }
}
