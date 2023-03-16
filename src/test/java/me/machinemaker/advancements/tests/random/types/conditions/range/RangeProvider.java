package me.machinemaker.advancements.tests.random.types.conditions.range;

import java.util.concurrent.ThreadLocalRandom;
import me.machinemaker.advancements.conditions.range.DoubleRange;
import me.machinemaker.advancements.conditions.range.IntegerRange;
import me.machinemaker.advancements.conditions.range.NumberRange;
import me.machinemaker.advancements.tests.random.Provider;

public abstract class RangeProvider<N extends Number, R extends NumberRange<N, R>> implements Provider<R> {

    abstract N randomNumber();

    abstract R between(N value1, N value2);

    abstract R atLeast(N min);

    abstract R atMost(N max);

    abstract R exactly(N value);

    @Override
    public R get() {
        return switch (this.integer(4)) {
            case 0 -> this.between(this.randomNumber(), this.randomNumber());
            case 1 -> this.atLeast(this.randomNumber());
            case 2 -> this.atMost(this.randomNumber());
            case 3 -> this.exactly(this.randomNumber());
            default -> throw new IllegalStateException();
        };
    }

    public static final class Ints extends RangeProvider<Integer, IntegerRange> {

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

    public static final class Doubles extends RangeProvider<Double, DoubleRange> {

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
