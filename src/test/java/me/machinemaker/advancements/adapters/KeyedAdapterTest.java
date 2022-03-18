package me.machinemaker.advancements.adapters;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import io.papermc.paper.registry.Reference;
import io.papermc.paper.world.structure.ConfiguredStructure;
import me.machinemaker.advancements.GsonTestBase;
import me.machinemaker.advancements.MinecraftGsonTestBase;
import me.machinemaker.advancements.mocks.DummyEffects;
import me.machinemaker.advancements.mocks.DummyEnchantments;
import me.machinemaker.advancements.mocks.DummyServer;
import org.bukkit.Fluid;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.StructureType;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffectType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class KeyedAdapterTest extends GsonTestBase {

    static {
        DummyServer.setup();
        DummyEnchantments.setup();
        DummyEffects.setup();
    }

    // @BeforeAll
    // void beforeAll() {
    //     GSON = GSON.newBuilder().registerTypeAdapter(Adapters.CONFIGURED_STRUCTURE_ADAPTER.type(), Adapters.CONFIGURED_STRUCTURE_ADAPTER.adapter()).create();
    // }

    @SafeVarargs
    @ParameterizedTest
    @MethodSource("args")
    final <T extends Keyed> void testSingle(Type typeOfT, T... keyed) {
        JsonElement element = new JsonPrimitive(keyed[0].getKey().toString());
        assertEquals(toJson(element), toJson(keyed[0]));
        assertEquals(keyed[0], fromJson(element, typeOfT));
    }

    @SafeVarargs
    @ParameterizedTest
    @MethodSource("args")
    final <T extends Keyed> void testCollection(Type typeOfT, T... keyed) {
        Collection<Keyed> keyedCollection = Sets.newHashSet(keyed);
        JsonArray array = new JsonArray();
        keyedCollection.forEach(m -> array.add(m.getKey().toString()));
        assertEquals(toJson(keyedCollection), toJson(array));
        assertEquals(keyedCollection, fromJson(array, TypeToken.getParameterized(Set.class, typeOfT).getType()));
    }

    static Stream<Arguments> args() {
        return Stream.of(
                Arguments.of(Material.class, new Keyed[]{Material.ANVIL, Material.DIAMOND, Material.CHEST}),
                Arguments.of(Fluid.class, new Keyed[]{Fluid.FLOWING_LAVA, Fluid.WATER}),
                Arguments.of(Biome.class, new Keyed[]{Biome.BADLANDS, Biome.BEACH}),
                Arguments.of(new TypeToken<Reference<ConfiguredStructure>>() {}.getType(), new Keyed[]{ConfiguredStructure.END_CITY, ConfiguredStructure.NETHER_FOSSIL}),
                Arguments.of(Enchantment.class, new Keyed[]{Enchantment.ARROW_DAMAGE, Enchantment.DAMAGE_ALL}),
                Arguments.of(PotionEffectType.class, new Keyed[]{PotionEffectType.ABSORPTION, PotionEffectType.FAST_DIGGING})
        );
    }
}
