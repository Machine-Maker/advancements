package me.machinemaker.datapacks.advancements.testing.types.conditions.entity.sub;

import me.machinemaker.datapacks.advancements.conditions.entity.sub.PlayerCondition;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.advancements.testing.types.conditions.ConditionProvider;

public class PlayerConditionProvider extends ConditionProvider<PlayerCondition, PlayerCondition.Builder> {

    public PlayerConditionProvider() {
        super(PlayerCondition::builder);
        this.component(() -> RandomProviders.INTEGER_RANGE, PlayerCondition.Builder::level);
        this.component(() -> RandomProviders.GAME_MODE, PlayerCondition.Builder::gamemode);
        this.component(() -> RandomProviders.STATISTIC_MAP_PROVIDER, PlayerCondition.Builder::stats);
        this.component(() -> RandomProviders.RECIPE_MAP, PlayerCondition.Builder::recipes);
        this.component(() -> RandomProviders.ADVANCEMENT_MAP_PROVIDER, PlayerCondition.Builder::advancements);
        this.component(() -> RandomProviders.ENTITY_CONDITION, PlayerCondition.Builder::lookingAt);
    }
}
