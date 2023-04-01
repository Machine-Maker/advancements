package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import io.papermc.paper.statistic.Statistic;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import java.util.Map;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import me.machinemaker.datapacks.advancements.conditions.range.IntegerRange;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemSource;
import me.machinemaker.datapacks.testing.sources.sources.Sources;
import net.kyori.adventure.key.Key;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PlayerConditionTest extends ConditionTest<EntitySubCondition> {

    static final TypeToken<Object2BooleanMap<Key>> RECIPE_MAP_TYPE = new TypeToken<Object2BooleanMap<Key>>() {};
    static final TypeToken<Map<Statistic<?>, IntegerRange>> STAT_MAP_TYPE = new TypeToken<Map<Statistic<?>, IntegerRange>>() {};

    PlayerConditionTest() {
        super(EntitySubCondition.conditionType());
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(RecipeMapProvider.class)
    void testRecipeMap(final Object2BooleanMap<Key> recipeMap) {
        final JsonObject obj = new JsonObject();
        recipeMap.forEach((key, bool) -> {
            obj.addProperty(key.asString(), bool);
        });
        this.testJsonConversion(recipeMap, obj, RECIPE_MAP_TYPE.getType());
    }

    private static final class RecipeMapProvider extends RandomItemSource<Object2BooleanMap<Key>> {

        RecipeMapProvider() {
            super(RandomProviders.RECIPE_MAP);
        }
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testPlayerCondition(final PlayerCondition condition) {
        final JsonObject obj = new JsonObject();
        this.add(obj, "level", condition.level());
        this.add(obj, "gamemode", condition.gamemode());
        this.add(obj, "stats", condition.stats(), STAT_MAP_TYPE.getType());
        this.add(obj, "recipes", condition.recipes());
        this.add(obj, "advancements", condition.advancements());
        this.add(obj, "looking_at", condition.lookingAt());
        obj.addProperty("type", "player");
        this.testJsonConversion(condition, obj);
        this.testJsonConversion(condition, obj, EntitySubCondition.class);
    }

    private static final class Provider extends RandomItemSource<PlayerCondition> {

        Provider() {
            super(RandomProviders.PLAYER_CONDITION);
        }
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject object = new JsonObject();
        object.add("gamemode", JsonNull.INSTANCE);
        object.add("recipes", JsonNull.INSTANCE);
        object.addProperty("type", "player");
        assertTrue(this.fromTree(object) instanceof PlayerCondition);
        assertTrue(this.fromTree("{ \"advancements\": null, \"type\": \"player\" }") instanceof PlayerCondition);
    }
}
