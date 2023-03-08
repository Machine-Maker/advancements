package me.machinemaker.advancements.adapters;

import com.google.common.collect.Sets;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;
import me.machinemaker.advancements.GsonTestBase;
import me.machinemaker.advancements.tests.mocks.DummyEffects;
import me.machinemaker.advancements.tests.mocks.DummyEnchantments;
import me.machinemaker.advancements.tests.mocks.DummyServer;
import org.bukkit.Fluid;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.generator.structure.Structure;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class KeyedAdapterTest extends GsonTestBase {

    static {
        DummyServer.setup();
        DummyEnchantments.setup();
        DummyEffects.setup();
    }

    static Stream<Arguments> args() {
        return Stream.of(
            arguments(Material.class, new Keyed[]{Material.ANVIL, Material.DIAMOND, Material.CHEST}),
            arguments(Fluid.class, new Keyed[]{Fluid.FLOWING_LAVA, Fluid.WATER}),
            arguments(Biome.class, new Keyed[]{Biome.BADLANDS, Biome.BEACH}),
            arguments(new TypeToken<Structure>() {}.getType(), new Keyed[]{Structure.END_CITY, Structure.NETHER_FOSSIL}),
            arguments(Enchantment.class, new Keyed[]{Enchantment.ARROW_DAMAGE, Enchantment.DAMAGE_ALL}),
            arguments(PotionEffectType.class, new Keyed[]{PotionEffectType.ABSORPTION, PotionEffectType.FAST_DIGGING})
        );
    }

    @SafeVarargs
    @ParameterizedTest
    @MethodSource("args")
    final <T extends Keyed> void testSingle(final Type typeOfT, final T... keyed) {
        final JsonElement element = new JsonPrimitive(keyed[0].getKey().toString());
        assertEquals(this.toJson(element), this.toJson(keyed[0]));
        assertEquals(keyed[0], this.fromJson(element, typeOfT));
    }

    @SafeVarargs
    @ParameterizedTest
    @MethodSource("args")
    final <T extends Keyed> void testCollection(final Type typeOfT, final T... keyed) {
        final Collection<Keyed> keyedCollection = Sets.newHashSet(keyed);
        final JsonArray array = new JsonArray();
        keyedCollection.forEach(m -> array.add(m.getKey().toString()));
        assertEquals(this.toJson(keyedCollection), this.toJson(array));
        assertEquals(keyedCollection, this.fromJson(array, TypeToken.getParameterized(Set.class, typeOfT).getType()));
    }
}
