package me.machinemaker.advancements.adapters.types.keyed;

import com.google.gson.reflect.TypeToken;
import io.papermc.paper.registry.Reference;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ReferenceTypeAdapter<T extends Keyed> extends KeyedTypeAdapter<Reference<T>> {

    private final Registry<T> registry;

    ReferenceTypeAdapter(TypeToken<Reference<T>> type, Registry<T> registry) {
        super(type);
        this.registry = registry;
    }

    @Override
    public @Nullable Reference<T> fromKey(NamespacedKey key) {
        return Reference.create(this.registry, key);
    }
}
