package me.machinemaker.datapacks.advancements.testing.types;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import me.machinemaker.datapacks.advancements.testing.Provider;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import net.kyori.adventure.key.Key;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.inventory.Recipe;
import oshi.util.Memoizer;

public class RecipeProvider implements Provider<Recipe> {

    private static final Supplier<List<Recipe>> RECIPES = Memoizer.memoize(() -> Lists.newArrayList(Bukkit.recipeIterator()));

    @Override
    public Recipe get() {
        return this.randomElement(RECIPES.get());
    }

    public static class Map implements Provider<Object2BooleanMap<Key>> {

        @Override
        public Object2BooleanMap<Key> get() {
            final Object2BooleanMap<Key> recipeMap = new Object2BooleanOpenHashMap<>();
            for (int i = 0; i < ThreadLocalRandom.current().nextInt(1, 7); i++) {
                recipeMap.put(((Keyed) RandomProviders.RECIPE.get()).key(), ThreadLocalRandom.current().nextBoolean());
            }
            return recipeMap;
        }
    }
}
