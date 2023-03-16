package me.machinemaker.advancements.tests.random.types.conditions.block;

import io.papermc.paper.world.data.BlockProperty;
import io.papermc.paper.world.data.IntegerBlockProperty;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import me.machinemaker.advancements.conditions.block.BlockPropertyCondition;
import me.machinemaker.advancements.tests.random.Provider;
import me.machinemaker.advancements.tests.random.RandomProviders;

public class BlockPropertyConditionProvider implements Provider<BlockPropertyCondition> {

    @Override
    public BlockPropertyCondition get() {
        final BlockPropertyCondition.Builder builder = BlockPropertyCondition.builder();
        for (int i = 0; i < ThreadLocalRandom.current().nextInt(1, 4); i++) {
            if (ThreadLocalRandom.current().nextBoolean()) {
                final BlockProperty<?> property = RandomProviders.BLOCK_PROPERTY.get();
                if (builder.properties().stream().noneMatch(m -> m.name().equals(property.name()))) {
                    addExact(builder, property);
                }
            } else {
                final IntegerBlockProperty property = RandomProviders.INTEGER_BLOCK_PROPERTY.get();
                if (builder.properties().stream().noneMatch(m -> m.name().equals(property.name()))) {
                    addRanged(builder, property);
                }
            }
        }
        return builder.build();
    }

    private static <T extends Comparable<T>> void addExact(final BlockPropertyCondition.Builder builder, final BlockProperty<T> property) {
        builder.addExact(property, randomInSet(property.values()));
    }

    private static void addRanged(final BlockPropertyCondition.Builder builder, final IntegerBlockProperty property) {
        builder.addRanged(property, property.min(), property.max());
    }

    private static <T> T randomInSet(final Set<T> items) {
        int i = 0;
        final int idx = ThreadLocalRandom.current().nextInt(items.size());
        for (final T currentItem : items) {
            if (i == idx) {
                return currentItem;
            }
            i++;
        }
        throw new IllegalStateException();
    }
}
