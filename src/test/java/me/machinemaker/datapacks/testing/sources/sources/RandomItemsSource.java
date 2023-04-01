package me.machinemaker.datapacks.testing.sources.sources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.machinemaker.datapacks.advancements.testing.Provider;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.support.AnnotationConsumer;

import static me.machinemaker.datapacks.testing.sources.sources.RandomItemSource.defaultExcluder;

public abstract class RandomItemsSource<T> implements SimpleArgumentsProvider, AnnotationConsumer<Sources.Config> {

    private final Provider<T> provider;
    private int maxSize = 1;
    private int count = 1;
    private final BiPredicate<T, Collection<T>> excluder;
    private final @Nullable Function<? super T, ? extends Named<? super T>> namer;

    public RandomItemsSource(final Provider<T> provider) {
        this(provider, defaultExcluder());
    }

    public RandomItemsSource(final Provider<T> provider, final BiPredicate<T, Collection<T>> excluder) {
        this(provider, excluder, null);
    }

    public RandomItemsSource(final Provider<T> provider, final @Nullable Function<? super T, ? extends Named<? super T>> namer) {
        this(provider, defaultExcluder(), namer);
    }

    public RandomItemsSource(final Provider<T> provider, final BiPredicate<T, Collection<T>> excluder, final @Nullable Function<? super T, ? extends Named<? super T>> namer) {
        this.provider = provider;
        this.excluder = excluder;
        this.namer = namer;
    }


    @Override
    public void accept(final Sources.Config config) {
        this.maxSize = config.maxSize();
        this.count = config.count();
    }

    @Override
    public Stream<? extends Arguments> arguments() {
        return randomSetsOfItems(this.provider, this.maxSize, this.count, this.excluder, this.namer)
            .stream()
            .map(Arguments::of);
    }

    private static <T> List<?> randomSetsOfItems(final Provider<? extends T> provider, final int maxSize, final int count, final BiPredicate<T, Collection<T>> predicate, final @Nullable Function<? super T, ? extends Named<? super T>> namer) {
        final List<List<?>> randomSets = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            randomSets.add(RandomItemSource.randomItems(provider, ThreadLocalRandom.current().nextInt(maxSize) + 1, predicate, namer));
        }
        return randomSets
            .stream()
            .map(list -> Named.of(
                list.toString(),
                list.stream()
                    .map(item -> item instanceof Named<?> named ? named.getPayload() : item)
                    .collect(Collectors.toList())
            ))
            .toList();
    }
}
