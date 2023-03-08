package me.machinemaker.advancements.conditions.entity;

import com.google.gson.annotations.JsonAdapter;
import io.papermc.paper.statistic.Statistic;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.builders.Builders;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.adapters.factories.NonNullMapAdapterFactory;
import me.machinemaker.advancements.adapters.maps.StatisticMapAdapter;
import me.machinemaker.advancements.adapters.types.GameModeAdapter;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.ranges.IntegerRange;
import me.machinemaker.advancements.util.Buildable;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.checkerframework.checker.nullness.qual.Nullable;

public record PlayerCondition(
    IntegerRange level,
    @Nullable @JsonAdapter(GameModeAdapter.class) GameMode gamemode,
    @JsonAdapter(value = StatisticMapAdapter.class, nullSafe = false) Map<Statistic<?>, IntegerRange> stats,
    @JsonAdapter(value = NonNullMapAdapterFactory.class, nullSafe = false) Object2BooleanMap<NamespacedKey> recipes,
    @JsonAdapter(value = NonNullMapAdapterFactory.class, nullSafe = false) Map<NamespacedKey, AdvancementCondition> advancements,
    EntityCondition lookingAt
) implements EntitySubCondition, Buildable<PlayerCondition, PlayerCondition.Builder> {

    public static final GsonBuilderApplicable BUILDER_APPLICABLE = Builders.collection(EntityCondition.BUILDER_APPLICABLE);

    public static final PlayerCondition ANY = new Builder().build();

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public PlayerCondition.Builder toBuilder() {
        return new Builder(
            this.level,
            this.gamemode,
            this.stats,
            this.recipes,
            this.advancements,
            this.lookingAt
        );
    }

    @Override
    public EntitySubCondition any() {
        return ANY;
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "PlayerCondition{ANY}";
        }
        return "PlayerCondition{" +
            "level=" + this.level +
            ", gamemode=" + this.gamemode +
            ", stats=" + this.stats +
            ", recipes=" + this.recipes +
            ", advancements=" + this.advancements +
            ", lookingAt=" + this.lookingAt +
            '}';
    }

    public static class Builder implements Condition.Builder<PlayerCondition> {

        private final Map<Statistic<?>, IntegerRange> stats;
        private final Object2BooleanMap<NamespacedKey> recipes;
        private final Map<NamespacedKey, AdvancementCondition> advancements;
        private IntegerRange level = IntegerRange.conditionType().any();
        private @Nullable GameMode gameMode;
        private EntityCondition lookingAt = EntityCondition.ANY;
        // private EntityCondition lookingAt = EntityCondition.Impl.delegate(() -> EntityCondition.ANY); // delegate need to resolve circular static field issue

        private Builder() {
            this.stats = new LinkedHashMap<>();
            this.recipes = new Object2BooleanOpenHashMap<>();
            this.advancements = new LinkedHashMap<>();
        }

        private Builder(final IntegerRange level, final @Nullable GameMode gameMode, final Map<Statistic<?>, IntegerRange> stats, final Object2BooleanMap<NamespacedKey> recipes, final Map<NamespacedKey, AdvancementCondition> advancements, final EntityCondition lookingAt) {
            this.level = level;
            this.gameMode = gameMode;
            this.stats = stats;
            this.recipes = recipes;
            this.advancements = advancements;
            this.lookingAt = lookingAt;
        }

        public IntegerRange level() {
            return this.level;
        }

        public Builder level(final IntegerRange level) {
            this.level = level;
            return this;
        }

        public @Nullable GameMode gamemode() {
            return this.gameMode;
        }

        public Builder gameMode(final @Nullable GameMode gameMode) {
            this.gameMode = gameMode;
            return this;
        }

        public Map<Statistic<?>, IntegerRange> stats() {
            return this.stats;
        }

        public Builder addStat(final Statistic<?> stat, final IntegerRange value) {
            this.stats.put(stat, value);
            return this;
        }

        public Object2BooleanMap<NamespacedKey> recipes() {
            return this.recipes;
        }

        public Builder addRecipe(final NamespacedKey recipeKey, final boolean unlocked) {
            this.recipes.put(recipeKey, unlocked);
            return this;
        }

        public Map<NamespacedKey, AdvancementCondition> advancements() {
            return this.advancements;
        }

        public Builder checkAdvancementDone(final NamespacedKey advancementKey, final boolean done) {
            this.advancements.put(advancementKey, AdvancementCondition.done(done));
            return this;
        }

        public Builder checkAdvancementCriteria(final NamespacedKey advancementKey, final Object2BooleanMap<String> criteria) {
            this.advancements.put(advancementKey, AdvancementCondition.criteria(criteria));
            return this;
        }

        public EntityCondition lookingAt() {
            return this.lookingAt;
        }

        public Builder lookingAt(final EntityCondition lookingAt) {
            this.lookingAt = lookingAt;
            return this;
        }

        @Override
        public PlayerCondition build() {
            return new PlayerCondition(this.level, this.gameMode, this.stats, this.recipes, this.advancements, this.lookingAt);
        }
    }
}
