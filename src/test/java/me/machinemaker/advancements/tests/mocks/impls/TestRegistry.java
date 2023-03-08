package me.machinemaker.advancements.tests.mocks.impls;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.checkerframework.checker.nullness.qual.Nullable;

public class TestRegistry<T extends Keyed> implements Registry<T> {

    private final Function<NamespacedKey, T> fakeCreator;
    private final Map<NamespacedKey, T> cache = new HashMap<>();

    public TestRegistry(final Function<NamespacedKey, T> fakeCreator) {
        this.fakeCreator = fakeCreator;
    }

    @Override
    public @Nullable T get(final NamespacedKey key) {
        return this.cache.computeIfAbsent(key, this.fakeCreator);
    }

    @Override
    public Iterator<T> iterator() {
        return this.cache.values().iterator();
    }
}
