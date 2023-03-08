package me.machinemaker.advancements.tests.providers;

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

abstract class RandomItemProvider<T> implements ArgumentsProvider, AnnotationConsumer<Providers.Config> {

    private final List<T> items;
    private int count = 1;
    private final BiPredicate<T, Set<T>> excluder;
    private final @Nullable Function<? super T, ? extends Named<? super T>> namer;

    protected RandomItemProvider(final List<T> items) {
        this(items, defaultExcluder());
    }

    protected RandomItemProvider(final List<T> items, final BiPredicate<T, Set<T>> excluder) {
        this(items, excluder, null);
    }

    protected RandomItemProvider(final List<T> items, final @Nullable Function<? super T, ? extends Named<? super T>> namer) {
        this(items, defaultExcluder(), namer);
    }

    protected RandomItemProvider(final List<T> items, final BiPredicate<T, Set<T>> excluder, final @Nullable Function<? super T, ? extends Named<? super T>> namer) {
        this.items = items;
        this.excluder = excluder;
        this.namer = namer;
    }

    @Override
    public void accept(final Providers.Config config) {
        this.count = config.count();
    }

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
        return randomItems(this.items, this.count, this.excluder, this.namer)
            .stream()
            .map(Arguments::of);
    }

    public static <T> Set<?> randomItems(final List<T> items, final int count, final BiPredicate<T, Set<T>> predicate, final @Nullable Function<? super T, ? extends Named<? super T>> namer) {
        if (items.size() < count) {
            throw new IllegalArgumentException("count: " + count + " must be bigger than items size: " + items.size());
        }
        final Set<T> randomItems = new LinkedHashSet<>();
        int iterations = 0;
        while (randomItems.size() < count) {
            if (iterations > 1000) {
                throw new IllegalStateException("Detected possible infinite loop");
            }
            final T possibleItem = items.get(ThreadLocalRandom.current().nextInt(items.size()));
            if (predicate.test(possibleItem, randomItems)) {
                randomItems.add(possibleItem);
            }
            iterations++;
        }
        return randomItems.stream().map(namer != null ? namer : Function.identity()).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    static <T> BiPredicate<T, Set<T>> defaultExcluder() {
        return (t, setOfT) -> true;
    }
}
