package me.machinemaker.advancements.adapters.types.keyed;

import com.google.gson.reflect.TypeToken;
import io.papermc.paper.registry.Reference;
import net.kyori.adventure.key.Key;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ReferenceTypeAdapter<T extends Keyed> extends KeyedTypeAdapter<Reference<T>> {

    private final Registry<T> registry;

    ReferenceTypeAdapter(final TypeToken<Reference<T>> type, final Registry<T> registry) {
        super(type);
        this.registry = registry;
    }

    @Override
    public @Nullable Reference<T> fromKey(final Key key) {
        final NamespacedKey nsKey = key instanceof NamespacedKey ns ? ns : new NamespacedKey(key.namespace(), key.value());
        return Reference.create(this.registry, nsKey);
    }
}
