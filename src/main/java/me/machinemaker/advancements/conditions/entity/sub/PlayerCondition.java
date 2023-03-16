package me.machinemaker.advancements.conditions.entity.sub;

import io.papermc.paper.statistic.Statistic;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import java.util.Map;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.entity.EntityCondition;
import me.machinemaker.advancements.conditions.range.IntegerRange;
import me.machinemaker.advancements.util.Buildable;
import net.kyori.adventure.key.Key;
import org.bukkit.GameMode;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;

@ApiStatus.NonExtendable
public interface PlayerCondition extends EntitySubCondition, Buildable<PlayerCondition, PlayerCondition.Builder> {

    static Builder builder() {
        return new PlayerConditionImpl.BuilderImpl();
    }

    @Contract(pure = true)
    IntegerRange level();

    @Contract(pure = true)
    @Nullable GameMode gamemode();

    @Contract(pure = true)
    @Unmodifiable Map<Statistic<?>, IntegerRange> stats();

    @Contract(pure = true)
    @Unmodifiable Object2BooleanMap<Key> recipes();

    @Contract(pure = true)
    @Unmodifiable Map<Key, AdvancementCondition> advancements();

    @Contract(pure = true)
    EntityCondition lookingAt();

    @ApiStatus.NonExtendable
    interface Builder extends Condition.Builder<PlayerCondition> {

        @Contract(pure = true)
        IntegerRange level();

        @Contract(value = "_ -> this", mutates = "this")
        Builder level(IntegerRange level);

        @Contract(pure = true)
        @Nullable GameMode gamemode();

        @Contract(value = "_ -> this", mutates = "this")
        Builder gamemode(@Nullable GameMode gamemode);

        @Contract(pure = true)
        Map<Statistic<?>, IntegerRange> stats();

        @Contract(value = "_, _ -> this", mutates = "this")
        Builder addStat(Statistic<?> statistic, IntegerRange range);

        @Contract(value = "_ -> this", mutates = "this")
        Builder stats(Map<Statistic<?>, IntegerRange> stats);

        @Contract(pure = true)
        Object2BooleanMap<Key> recipes();

        @Contract(value = "_, _ -> this", mutates = "this")
        Builder addRecipe(Key recipe, boolean unlocked);

        @Contract(value = "_ -> this", mutates = "this")
        Builder recipes(Object2BooleanMap<Key> recipes);

        @Contract(pure = true)
        Map<Key, AdvancementCondition> advancements();

        @Contract(value = "_, _ -> this", mutates = "this")
        Builder addAdvancement(Key advancement, AdvancementCondition condition);

        @Contract(value = "_ -> this", mutates = "this")
        Builder advancements(Map<Key, AdvancementCondition> advancements);

        @Contract(pure = true)
        EntityCondition lookingAt();

        @Contract(value = "_ -> this", mutates = "this")
        Builder lookingAt(EntityCondition lookingAt);
    }
}
