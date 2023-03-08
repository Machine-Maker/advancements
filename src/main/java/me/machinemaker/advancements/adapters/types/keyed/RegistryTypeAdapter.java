package me.machinemaker.advancements.adapters.types.keyed;

import com.google.gson.reflect.TypeToken;
import net.kyori.adventure.key.Key;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class RegistryTypeAdapter<T extends Keyed> extends KeyedTypeAdapter<T> {

    private final Registry<T> registry;

    RegistryTypeAdapter(final TypeToken<T> type, final Registry<T> registry) {
        super(type);
        this.registry = registry;
    }

    @Override
    public @Nullable T fromKey(final Key key) {
        final NamespacedKey nsKey = key instanceof NamespacedKey ns ? ns : new NamespacedKey(key.namespace(), key.value());
        return this.registry.get(nsKey);
    }

    @Override
    public boolean equals(final @Nullable Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final RegistryTypeAdapter<?> that = (RegistryTypeAdapter<?>) o;

        return this.registry.equals(that.registry);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + this.registry.hashCode();
        return result;
    }
}
