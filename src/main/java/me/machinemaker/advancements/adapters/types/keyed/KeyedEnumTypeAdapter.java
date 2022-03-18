package me.machinemaker.advancements.adapters.types.keyed;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Map;
import java.util.Objects;

public class KeyedEnumTypeAdapter<E extends Enum<E> & Keyed> extends KeyedTypeAdapter<E> {

    private final Map<NamespacedKey, E> map;

    KeyedEnumTypeAdapter(Class<E> type) {
        super(TypeToken.get(type));
        final ImmutableMap.Builder<NamespacedKey, E> builder = ImmutableMap.builder();
        for (E constant : type.getEnumConstants()) {
            builder.put(constant.getKey(), constant);
        }
        this.map = builder.build();
    }

    @Override
    public E fromKey(NamespacedKey key) {
        return Objects.requireNonNull(this.map.get(key), key + " is an unrecognized key for " + this.getType());
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        KeyedEnumTypeAdapter<?> that = (KeyedEnumTypeAdapter<?>) o;

        return map.equals(that.map);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + map.hashCode();
        return result;
    }
}
