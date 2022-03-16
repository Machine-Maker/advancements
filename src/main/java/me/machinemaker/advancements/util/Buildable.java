package me.machinemaker.advancements.util;

import org.jetbrains.annotations.Contract;

public interface Buildable<T, B extends Buildable.Builder<T>> {

    @Contract(value = "-> new", pure = true)
    B toBuilder();

    interface Builder<T> {

        @Contract(value = "-> new", pure = true)
        T build();
    }
}
