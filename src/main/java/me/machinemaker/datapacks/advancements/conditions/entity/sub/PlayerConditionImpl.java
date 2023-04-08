package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import com.google.gson.TypeAdapterFactory;
import io.papermc.paper.statistic.Statistic;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMaps;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import me.machinemaker.datapacks.common.adapters.factories.InterfaceImplAdapterFactory;
import me.machinemaker.datapacks.advancements.conditions.entity.EntityCondition;
import me.machinemaker.datapacks.common.range.IntegerRange;
import net.kyori.adventure.key.Key;
import org.bukkit.GameMode;
import org.checkerframework.checker.nullness.qual.Nullable;

record PlayerConditionImpl(
    IntegerRange level,
    @Nullable GameMode gamemode,
    Map<Statistic<?>, IntegerRange> stats,
    Object2BooleanMap<Key> recipes,
    Map<Key, AdvancementCondition> advancements,
    EntityCondition lookingAt
) implements EntitySubConditionImpl, PlayerCondition {

    static final String TYPE = "player";
    static final TypeAdapterFactory FACTORY = InterfaceImplAdapterFactory.simple(PlayerCondition.class, PlayerConditionImpl.class);

    PlayerConditionImpl {
        stats = Map.copyOf(stats);
        recipes = Object2BooleanMaps.unmodifiable(new Object2BooleanOpenHashMap<>(recipes));
        advancements = Map.copyOf(advancements);
    }

    @Override
    public PlayerCondition.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    public String serializedType() {
        return TYPE;
    }

    static class BuilderImpl implements PlayerCondition.Builder {

        private Map<Statistic<?>, IntegerRange> stats = new LinkedHashMap<>();
        private Object2BooleanMap<Key> recipes = new Object2BooleanOpenHashMap<>();
        private Map<Key, AdvancementCondition> advancements = new LinkedHashMap<>();
        private IntegerRange level = IntegerRange.conditionType().any();
        private @Nullable GameMode gameMode;
        private EntityCondition lookingAt = EntityCondition.conditionType().any();

        BuilderImpl() {
        }

        private BuilderImpl(final PlayerCondition condition) {
            this.level = condition.level();
            this.gameMode = condition.gamemode();
            this.stats = condition.stats();
            this.recipes = condition.recipes();
            this.advancements = condition.advancements();
            this.lookingAt = condition.lookingAt();
        }

        @Override
        public IntegerRange level() {
            return this.level;
        }

        @Override
        public PlayerCondition.Builder level(final IntegerRange level) {
            this.level = level;
            return this;
        }

        @Override
        public @Nullable GameMode gamemode() {
            return this.gameMode;
        }

        @Override
        public PlayerCondition.Builder gamemode(final @Nullable GameMode gamemode) {
            this.gameMode = gamemode;
            return this;
        }

        @Override
        public Map<Statistic<?>, IntegerRange> stats() {
            return this.stats;
        }

        @Override
        public PlayerCondition.Builder addStat(final Statistic<?> stat, final IntegerRange value) {
            this.stats.put(stat, value);
            return this;
        }

        @Override
        public PlayerCondition.Builder stats(final Map<Statistic<?>, IntegerRange> stats) {
            this.stats = stats;
            return this;
        }

        @Override
        public Object2BooleanMap<Key> recipes() {
            return this.recipes;
        }

        @Override
        public PlayerCondition.Builder addRecipe(final Key recipeKey, final boolean unlocked) {
            this.recipes.put(recipeKey, unlocked);
            return this;
        }

        @Override
        public PlayerCondition.Builder recipes(final Object2BooleanMap<Key> recipes) {
            this.recipes = recipes;
            return this;
        }

        @Override
        public Map<Key, AdvancementCondition> advancements() {
            return this.advancements;
        }

        @Override
        public PlayerCondition.Builder addAdvancement(final Key advancement, final AdvancementCondition condition) {
            this.advancements.put(advancement, condition);
            return this;
        }

        @Override
        public PlayerCondition.Builder advancements(final Map<Key, AdvancementCondition> advancements) {
            this.advancements = advancements;
            return this;
        }

        @Override
        public EntityCondition lookingAt() {
            return this.lookingAt;
        }

        @Override
        public PlayerCondition.Builder lookingAt(final EntityCondition lookingAt) {
            this.lookingAt = lookingAt;
            return this;
        }

        @Override
        public PlayerCondition build() {
            return new PlayerConditionImpl(this.level, this.gameMode, this.stats, this.recipes, this.advancements, this.lookingAt);
        }
    }
}
