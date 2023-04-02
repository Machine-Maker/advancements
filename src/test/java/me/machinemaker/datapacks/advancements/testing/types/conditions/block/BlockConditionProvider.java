package me.machinemaker.datapacks.advancements.testing.types.conditions.block;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import me.machinemaker.datapacks.advancements.conditions.block.BlockCondition;
import me.machinemaker.datapacks.advancements.tags.BlockTag;
import me.machinemaker.datapacks.advancements.testing.types.conditions.ConditionProvider;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import oshi.util.Memoizer;

import static me.machinemaker.datapacks.utils.TestUtils.fromResourceLocation;

public class BlockConditionProvider extends ConditionProvider<BlockCondition, BlockCondition.Builder> {

    private static final Supplier<List<TagKey<Block>>> BLOCK_TAGS = Memoizer.memoize(() -> BuiltInRegistries.BLOCK.getTagNames().toList());

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
                final NamespacedKey randomTagKey = fromResourceLocation(this.randomElement(BLOCK_TAGS.get()).location());
                final Tag<Material> randomTag = Objects.requireNonNull(Bukkit.getTag(Tag.REGISTRY_BLOCKS, randomTagKey, Material.class));
                builder.tag(new BlockTag(randomTag));
            }
        },
    }
}
