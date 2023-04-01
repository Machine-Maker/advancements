package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import java.util.Locale;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Rabbit;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.intellij.lang.annotations.Subst;

/**
 * Custom mappings since the API isn't 100% correct
 */
final class EntityVariants {

    private static final BiMap<Object, String> OVERRIDES = ImmutableBiMap.of(
        Parrot.Variant.RED, "red_blue",
        Parrot.Variant.CYAN, "yellow_blue",
        Rabbit.Type.BLACK_AND_WHITE, "white_splotched",
        Rabbit.Type.SALT_AND_PEPPER, "salt",
        Rabbit.Type.THE_KILLER_BUNNY, "evil"
    );

    private EntityVariants() {
    }

    static String getSerializedName(final Object value) {
        if (value instanceof final Keyed keyed) {
            return keyed.key().asString();
        } else if (value instanceof final Enum<?> e) {
            return OVERRIDES.getOrDefault(value, e.name().toLowerCase(Locale.ENGLISH));
        } else {
            throw new IllegalArgumentException(value.toString());
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    static <T> T getDeserializedValue(@Subst("key:value") final String input, final Class<T> type) {
        Preconditions.checkArgument(type.isEnum(), type + " isn't an enum");
        final Class<Enum> enumClass = (Class<Enum>) type;
        if (Keyed.class.isAssignableFrom(type)) {
            return (T) Enum.valueOf(enumClass, Key.key(input).value().toUpperCase(Locale.ENGLISH));
        } else {
            final @Nullable Object o = OVERRIDES.inverse().get(input);
            return (T) (o == null ? Enum.valueOf(enumClass, input.toUpperCase(Locale.ENGLISH)) : o);
        }
    }
}
