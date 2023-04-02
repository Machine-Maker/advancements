package me.machinemaker.datapacks.advancements.adapters.types.keyed;

import com.google.gson.reflect.TypeToken;
import net.kyori.adventure.key.Key;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class RegistryTypeAdapter<T extends Keyed> extends KeyedTypeAdapter<T> {

    private final Registry<T> registry;

    RegistryTypeAdapter(final TypeToken<T> type, final Registry<T> registry) {
        super(type);
        this.registry = registry;
    }

    @Override
    public @Nullable T fromKey(final Key key) {
        final NamespacedKey nsKey = key instanceof final NamespacedKey ns ? ns : new NamespacedKey(key.namespace(), key.value());
        return this.registry.get(nsKey);
    }
}
