package me.machinemaker.advancements.adapters.types;

import com.google.gson.reflect.TypeToken;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class RegistryTypeAdapter<T extends Keyed> extends KeyedTypeAdapter<T> {

    private final Registry<T> registry;

    RegistryTypeAdapter(TypeToken<T> type, Registry<T> registry) {
        super(type);
        this.registry = registry;
    }

    @Override
    public @Nullable T fromKey(NamespacedKey key) {
        return this.registry.get(key);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RegistryTypeAdapter<?> that = (RegistryTypeAdapter<?>) o;

        return registry.equals(that.registry);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + registry.hashCode();
        return result;
    }
}
