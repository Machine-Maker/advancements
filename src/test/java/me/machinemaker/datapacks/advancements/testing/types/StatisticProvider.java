package me.machinemaker.datapacks.advancements.testing.types;

import com.google.common.collect.Lists;
import io.papermc.paper.statistic.CustomStatistic;
import io.papermc.paper.statistic.Statistic;
import io.papermc.paper.statistic.StatisticType;
import java.util.LinkedHashMap;
import java.util.List;
import me.machinemaker.datapacks.common.range.IntegerRange;
import me.machinemaker.datapacks.advancements.testing.Provider;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import org.bukkit.Material;
import org.bukkit.Registry;
import org.bukkit.entity.EntityType;

public class StatisticProvider implements Provider<Statistic<?>> {

    private static final List<StatisticType<Material>> BLOCK_TYPES = List.of(StatisticType.BLOCK_MINED);
    private static final List<StatisticType<Material>> ITEM_TYPES = List.of(
        StatisticType.ITEM_BROKEN,
        StatisticType.ITEM_CRAFTED,
        StatisticType.ITEM_DROPPED,
        StatisticType.ITEM_USED,
        StatisticType.ITEM_PICKED_UP
    );
    private static final List<StatisticType<EntityType>> ENTITY_TYPES = List.of(
        StatisticType.ENTITY_KILLED,
        StatisticType.ENTITY_KILLED_BY
    );
    private static final List<CustomStatistic> CUSTOM_STATISTICS = Lists.newArrayList(Registry.CUSTOM_STATISTIC.iterator());

    @Override
    public Statistic<?> get() {
        return switch (this.integer(4)) {
            case 0 -> this.randomElement(BLOCK_TYPES).of(RandomProviders.BLOCK.get());
            case 1 -> this.randomElement(ITEM_TYPES).of(RandomProviders.ITEM.get());
            case 2 -> this.randomElement(ENTITY_TYPES).of(RandomProviders.ENTITY_TYPE.get());
            case 3 -> StatisticType.CUSTOM_STATS.of(this.randomElement(CUSTOM_STATISTICS));
            default -> throw new IllegalStateException();
        };
    }

    public static class Map implements Provider<java.util.Map<Statistic<?>, IntegerRange>> {

        @Override
        public java.util.Map<Statistic<?>, IntegerRange> get() {
            final java.util.Map<Statistic<?>, IntegerRange> map = new LinkedHashMap<>();
            for (int i = 0; i < this.integer(1, 10); i++) {
                map.put(RandomProviders.STATISTIC_PROVIDER.get(), RandomProviders.INTEGER_RANGE.get());
            }
            return map;
        }
    }
}
