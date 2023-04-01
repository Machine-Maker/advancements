package me.machinemaker.datapacks.advancements.testing.types;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import me.machinemaker.datapacks.advancements.conditions.entity.sub.AdvancementCondition;
import me.machinemaker.datapacks.advancements.testing.Provider;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.advancement.Advancement;
import oshi.util.Memoizer;

public class AdvancementMapProvider implements Provider<Map<Key, AdvancementCondition>> {

    private static final Supplier<List<Advancement>> ADVANCEMENTS = Memoizer.memoize(() -> Lists.newArrayList(Bukkit.advancementIterator()));

    @Override
    public Map<Key, AdvancementCondition> get() {
        final Map<Key, AdvancementCondition> map = new HashMap<>();
        for (int i = 0; i < this.integer(1, 10); i++) {
            final Advancement random = this.randomElement(ADVANCEMENTS.get());
            final List<String> criteria = new ArrayList<>(random.getCriteria());
            if (this.integer(3) < 2) {
                Collections.shuffle(criteria, ThreadLocalRandom.current());
                final Object2BooleanMap<String> criteriaMap = new Object2BooleanOpenHashMap<>();
                for (final String c : criteria.subList(0, ThreadLocalRandom.current().nextInt(0, criteria.size()))) {
                    criteriaMap.put(c, this.bool());
                }
                map.put(random.key(), AdvancementCondition.criteria(criteriaMap));
            } else {
                map.put(random.key(), AdvancementCondition.done(this.bool()));
            }
        }
        return map;
    }
}
