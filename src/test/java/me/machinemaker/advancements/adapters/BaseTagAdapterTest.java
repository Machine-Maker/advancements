package me.machinemaker.advancements.adapters;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;
import me.machinemaker.advancements.GsonTestBase;
import me.machinemaker.advancements.tags.BlockTag;
import me.machinemaker.advancements.tags.EntityTag;
import me.machinemaker.advancements.tags.FluidTag;
import me.machinemaker.advancements.tags.ItemTag;
import org.bukkit.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseTagAdapterTest extends GsonTestBase {

    static Stream<Arguments> args() {
        return Stream.of(
                Arguments.of(BlockTag.class, new Tag[]{new BlockTag(Tag.ACACIA_LOGS), new BlockTag(Tag.BEDS)}),
                Arguments.of(FluidTag.class, new Tag[]{new FluidTag(Tag.FLUIDS_WATER), new FluidTag(Tag.FLUIDS_LAVA)}),
                Arguments.of(ItemTag.class, new Tag[]{new ItemTag(Tag.FOX_FOOD), new ItemTag(Tag.IGNORED_BY_PIGLIN_BABIES)}),
                Arguments.of(EntityTag.class, new Tag[]{new EntityTag(Tag.ENTITY_TYPES_ARROWS), new EntityTag(Tag.ENTITY_TYPES_RAIDERS)})
        );
    }

    @SafeVarargs
    @ParameterizedTest
    @MethodSource("args")
    final <T extends Tag<?>> void testSingle(Class<T> classOfT, T... tags) {
        JsonElement element = new JsonPrimitive(tags[0].getKey().toString());
        assertEquals(toJson(element), toJson(tags[0]));
        assertEquals(tags[0], fromJson(element, classOfT));
    }

    @SafeVarargs
    @ParameterizedTest
    @MethodSource("args")
    final <T extends Tag<?>> void testCollection(Class<T> classOfT, T... tags) {
        Collection<Tag<?>> tagCollection = Sets.newHashSet(tags);
        JsonArray array = new JsonArray();
        tagCollection.forEach(m -> array.add(m.getKey().toString()));
        assertEquals(toJson(tagCollection), toJson(array));
        assertEquals(tagCollection, fromJson(array, TypeToken.getParameterized(Set.class, classOfT).getType()));
    }
}
