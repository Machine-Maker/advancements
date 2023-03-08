package me.machinemaker.advancements.tests.providers;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.support.AnnotationConsumer;

import static me.machinemaker.advancements.tests.providers.RandomItemProvider.defaultExcluder;

abstract class RandomItemsProvider<T> implements ArgumentsProvider, AnnotationConsumer<Providers.Config> {

    private final List<T> items;
    private int maxSize = 1;
    private int count = 1;
    private final BiPredicate<T, Set<T>> excluder;
    private final @Nullable Function<? super T, ? extends Named<? super T>> namer;

    public RandomItemsProvider(final List<T> items) {
        this(items, defaultExcluder());
    }

    public RandomItemsProvider(final List<T> items, final BiPredicate<T, Set<T>> excluder) {
        this(items, excluder, null);
    }

    public RandomItemsProvider(final List<T> items, final @Nullable Function<? super T, ? extends Named<? super T>> namer) {
        this(items, defaultExcluder(), namer);
    }

    public RandomItemsProvider(final List<T> items, final BiPredicate<T, Set<T>> excluder, final @Nullable Function<? super T, ? extends Named<? super T>> namer) {
        this.items = items;
        this.excluder = excluder;
        this.namer = namer;
    }


    @Override
    public void accept(final Providers.Config config) {
        this.maxSize = config.maxSize();
        this.count = config.count();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
        return randomSetsOfItems(this.items, this.maxSize, this.count, this.excluder, this.namer)
            .stream()
            .map(Arguments::of);
    }

    public static <T> List<?> randomSetsOfItems(final List<T> items, final int maxSize, final int count, final BiPredicate<T, Set<T>> predicate, final @Nullable Function<? super T, ? extends Named<? super T>> namer) {
        if (items.size() < maxSize) {
            throw new IllegalArgumentException("count: " + count + " must be bigger than items size: " + items.size());
        }
        final List<Set<?>> randomSets = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            randomSets.add(RandomItemProvider.randomItems(items, ThreadLocalRandom.current().nextInt(maxSize) + 1, predicate, namer));
        }
        return randomSets
            .stream()
            .map(set -> Named.of(
                set.toString(),
                set.stream()
                    .map(item -> item instanceof Named<?> named ? named.getPayload() : item)
                    .collect(Collectors.toCollection(LinkedHashSet::new))
            ))
            .toList();
    }
}
