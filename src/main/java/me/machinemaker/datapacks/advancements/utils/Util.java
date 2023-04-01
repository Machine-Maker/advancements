package me.machinemaker.datapacks.advancements.utils;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class Util {

    private Util() {
    }

    public static <T> @Nullable Set<T> nullableCopy(final @Nullable Set<T> set) {
        return set == null ? null : Collections.unmodifiableSet(new LinkedHashSet<>(set));
    }
}
