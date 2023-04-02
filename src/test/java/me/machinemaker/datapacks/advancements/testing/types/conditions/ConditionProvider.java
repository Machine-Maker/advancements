package me.machinemaker.datapacks.advancements.testing.types.conditions;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import me.machinemaker.datapacks.advancements.conditions.Condition;
import me.machinemaker.datapacks.advancements.testing.Provider;
import oshi.util.Memoizer;

public abstract class ConditionProvider<C extends Condition, B extends Condition.Builder<C>> implements Provider<C> {

    private final ImmutableList.Builder<Component<B>> conditionsBuilder = ImmutableList.builder();
    private final Supplier<List<Component<B>>> conditions = Memoizer.memoize(() -> new ArrayList<>(this.conditionsBuilder.build()));
    private final Supplier<? extends B> builderCreator;

    protected ConditionProvider(final Class<? extends Component<B>> componentEnumClass, final Supplier<? extends B> builderCreator) {
        Preconditions.checkArgument(componentEnumClass.isEnum(), componentEnumClass + " is not an enum");
        for (final Component<B> constant : componentEnumClass.getEnumConstants()) {
            this.conditionsBuilder.add(constant);
        }
        this.builderCreator = builderCreator;
    }

    protected ConditionProvider(final Supplier<? extends B> builderCreator) {
        this.builderCreator = builderCreator;
    }

    protected final <T> void component(final Supplier<Provider<T>> provider, final BiConsumer<B, T> builderMethod) {
        this.conditionsBuilder.add(new SimpleComponent<>(provider, builderMethod));
    }

    private List<Component<B>> random() {
        Collections.shuffle(this.conditions.get(), ThreadLocalRandom.current());
        return new ArrayList<>(this.conditions.get().subList(0, ThreadLocalRandom.current().nextInt(1, this.conditions.get().size())));
    }

    @Override
    public C get() {
        int counter = 0;
        while (counter < 1000) {
            final B builder = this.builderCreator.get();
            final List<Component<B>> components = this.random();
            components.forEach(c -> c.apply(builder));
            final C built = builder.build();
            if (!built.isAny()) {
                return built;
            }
            counter++;
        }
        throw new IllegalStateException("Possible infinite loop");
    }

    public interface Component<B extends Condition.Builder<?>> {

        void apply(B builder);

        default int integer() {
            return ThreadLocalRandom.current().nextInt();
        }

        default int integer(final int bound) {
            return ThreadLocalRandom.current().nextInt(bound);
        }

        default int integer(final int origin, final int bound) {
            return ThreadLocalRandom.current().nextInt(origin, bound);
        }

        default <A> A randomElement(final List<A> list) {
            return list.get(this.integer(list.size()));
        }
    }

    private record SimpleComponent<T, B extends Condition.Builder<?>>(
        Supplier<Provider<T>> provider,
        BiConsumer<B, T> builderMethod
    ) implements Component<B> {

        @Override
        public void apply(final B builder) {
            this.builderMethod.accept(builder, this.provider.get().get());
        }
    }
}
