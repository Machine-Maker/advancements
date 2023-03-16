package me.machinemaker.advancements.tests.random;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public interface Provider<T> {

    T get();

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
