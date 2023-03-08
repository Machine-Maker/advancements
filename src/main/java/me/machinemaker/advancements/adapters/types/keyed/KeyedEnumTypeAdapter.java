package me.machinemaker.advancements.adapters.types.keyed;

import com.google.common.collect.ImmutableMap;
import com.google.gson.reflect.TypeToken;
import java.util.Map;
import java.util.Objects;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.checkerframework.checker.nullness.qual.Nullable;

public class KeyedEnumTypeAdapter<E extends Enum<E> & Keyed> extends KeyedTypeAdapter<E> {

    private final Map<Key, E> map;

    KeyedEnumTypeAdapter(final Class<E> type) {
        super(TypeToken.get(type));
        final ImmutableMap.Builder<Key, E> builder = ImmutableMap.builder();
        for (final E constant : type.getEnumConstants()) {
            builder.put(constant.key(), constant);
        }
        this.map = builder.build();
    }

    @Override
    public E fromKey(final Key key) {
        return Objects.requireNonNull(this.map.get(key), key + " is an unrecognized key for " + this.getType());
    }

    @Override
    public boolean equals(final @Nullable Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        final KeyedEnumTypeAdapter<?> that = (KeyedEnumTypeAdapter<?>) o;

        return this.map.equals(that.map);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + this.map.hashCode();
        return result;
    }
}
