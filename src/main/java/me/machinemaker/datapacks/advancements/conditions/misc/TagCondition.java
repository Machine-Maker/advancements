package me.machinemaker.datapacks.advancements.conditions.misc;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.NonExtendable
public interface TagCondition<T extends Keyed> {

    @Contract(value = "_, _ -> new", pure = true)
    static <T extends Keyed> TagCondition<T> create(final Key tag, final boolean expected) {
        return new TagConditionImpl<>(tag, expected);
    }

    @Contract(pure = true)
    Key tag();

    @Contract(pure = true)
    boolean expected();
}
