package me.machinemaker.advancements.tests.providers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import me.machinemaker.advancements.ranges.DoubleRange;
import me.machinemaker.advancements.ranges.IntegerRange;
import me.machinemaker.advancements.ranges.NumberRange;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public final class RangeProviders {

    private RangeProviders() {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @ParameterizedTest
    @ArgumentsSource(Ints.Provider.class)
    public @interface Ints {

        class Provider implements NumberRangeArgumentsProvider<Integer, IntegerRange> {

            @Override
            public Integer randomNumber() {
                return ThreadLocalRandom.current().nextInt(50_000);
            }

            @Override
            public IntegerRange between(final Integer value1, final Integer value2) {
                return IntegerRange.isBetween(Math.min(value1, value2), Math.max(value1, value2));
            }

            @Override
            public IntegerRange atLeast(final Integer min) {
                return IntegerRange.isAtLeast(min);
            }

            @Override
            public IntegerRange atMost(final Integer max) {
                return IntegerRange.isAtMost(max);
            }

            @Override
            public IntegerRange exactly(final Integer value) {
                return IntegerRange.isExactly(value);
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @ParameterizedTest
    @ArgumentsSource(Doubles.Provider.class)
    public @interface Doubles {

        class Provider implements NumberRangeArgumentsProvider<Double, DoubleRange> {

            @Override
            public Double randomNumber() {
                return ThreadLocalRandom.current().nextDouble(50_000);
            }

            @Override
            public DoubleRange between(final Double value1, final Double value2) {
                return DoubleRange.isBetween(Math.min(value1, value2), Math.max(value1, value2));
            }

            @Override
            public DoubleRange atLeast(final Double min) {
                return DoubleRange.isAtLeast(min);
            }

            @Override
            public DoubleRange atMost(final Double max) {
                return DoubleRange.isAtMost(max);
            }

            @Override
            public DoubleRange exactly(final Double value) {
                return DoubleRange.isExactly(value);
            }
        }
    }

    interface NumberRangeArgumentsProvider<N extends Number, R extends NumberRange<N, R>> extends ArgumentsProvider {

        N randomNumber();

        R between(N value1, N value2);

        R atLeast(N min);

        R atMost(N max);

        R exactly(N value);

        @Override
        default Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
            final List<R> ranges = new ArrayList<>(List.of(
                this.between(this.randomNumber(), this.randomNumber()),
                this.atLeast(this.randomNumber()),
                this.atMost(this.randomNumber()),
                this.exactly(this.randomNumber())
            ));
            Collections.shuffle(ranges);
            return ranges.stream().map(Arguments::of);
        }
    }
}
