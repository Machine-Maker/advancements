package me.machinemaker.datapacks.advancements.adapters;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;
import me.machinemaker.datapacks.GsonTest;
import me.machinemaker.datapacks.toremove.tags.BlockTag;
import me.machinemaker.datapacks.toremove.tags.EntityTag;
import me.machinemaker.datapacks.toremove.tags.FluidTag;
import me.machinemaker.datapacks.toremove.tags.ItemTag;
import org.bukkit.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BaseTagAdapterTest extends GsonTest {

    static Stream<Arguments> args() {
        return Stream.of(Arguments.of(BlockTag.class, new Tag[]{new BlockTag(Tag.ACACIA_LOGS), new BlockTag(Tag.BEDS)}), Arguments.of(FluidTag.class, new Tag[]{new FluidTag(Tag.FLUIDS_WATER), new FluidTag(Tag.FLUIDS_LAVA)}), Arguments.of(ItemTag.class, new Tag[]{new ItemTag(Tag.FOX_FOOD), new ItemTag(Tag.IGNORED_BY_PIGLIN_BABIES)}), Arguments.of(EntityTag.class, new Tag[]{new EntityTag(Tag.ENTITY_TYPES_ARROWS), new EntityTag(Tag.ENTITY_TYPES_RAIDERS)}));
    }

    @SafeVarargs
    @ParameterizedTest
    @MethodSource("args")
    final <T extends Tag<?>> void testSingle(final Class<T> classOfT, final T... tags) {
        final JsonElement element = new JsonPrimitive(tags[0].getKey().toString());
        this.testJsonConversion(tags[0], element, classOfT);
    }

    @SafeVarargs
    @ParameterizedTest
    @MethodSource("args")
    final <T extends Tag<?>> void testCollection(final Class<T> classOfT, final T... tags) {
        final Collection<Tag<?>> tagCollection = Sets.newHashSet(tags);
        final JsonArray array = new JsonArray();
        tagCollection.forEach(m -> array.add(m.getKey().toString()));
        this.testJsonConversion(tagCollection, array, TypeToken.getParameterized(Set.class, classOfT).getType());
    }
}
