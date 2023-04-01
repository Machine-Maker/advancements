package me.machinemaker.datapacks.testing.sources.sources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import me.machinemaker.datapacks.advancements.testing.Provider;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.support.AnnotationConsumer;

public abstract class RandomItemSource<T> implements SimpleArgumentsProvider, AnnotationConsumer<Sources.Config> {

    private final Provider<T> provider;
    private int count = 1;
    private final BiPredicate<T, Collection<T>> excluder;
    private final @Nullable Function<? super T, ? extends Named<? super T>> namer;

    protected RandomItemSource(final Provider<T> provider) {
        this(provider, defaultExcluder());
    }

    protected RandomItemSource(final Provider<T> provider, final BiPredicate<T, Collection<T>> excluder) {
        this(provider, excluder, null);
    }

    protected RandomItemSource(final Provider<T> provider, final @Nullable Function<? super T, ? extends Named<? super T>> namer) {
        this(provider, defaultExcluder(), namer);
    }

    protected RandomItemSource(final Provider<T> provider, final BiPredicate<T, Collection<T>> excluder, final @Nullable Function<? super T, ? extends Named<? super T>> namer) {
        this.provider = provider;
        this.excluder = excluder;
        this.namer = namer;
    }

    @Override
    public void accept(final Sources.Config config) {
        this.count = config.count();
    }

    @Override
    public Stream<? extends Arguments> arguments() {
        return randomItems(this.provider, this.count, this.excluder, this.namer)
            .stream()
            .map(Arguments::of);
    }

    public static <T> List<?> randomItems(final Provider<? extends T> provider, final int count, final BiPredicate<T, Collection<T>> predicate, final @Nullable Function<? super T, ? extends Named<? super T>> namer) {
        final List<T> randomItems = new ArrayList<>();
        int iterations = 0;
        while (randomItems.size() < count) {
            if (iterations > 1000) {
                throw new IllegalStateException("Detected possible infinite loop");
            }
            final T possibleItem = provider.get();
            if (predicate.test(possibleItem, randomItems)) {
                randomItems.add(possibleItem);
            }
            iterations++;
        }
        return randomItems.stream().map(namer != null ? namer : Function.identity()).collect(Collectors.toList());
    }

    static <T> BiPredicate<T, Collection<T>> defaultExcluder() {
        return (t, setOfT) -> true;
    }
}
