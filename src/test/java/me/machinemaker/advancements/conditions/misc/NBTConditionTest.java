package me.machinemaker.advancements.conditions.misc;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import me.machinemaker.advancements.conditions.ConditionTest;
import net.kyori.adventure.nbt.BinaryTagTypes;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.IntBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import net.kyori.adventure.nbt.StringBinaryTag;
import net.kyori.adventure.nbt.TagStringIO;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.util.Codec;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NBTConditionTest extends ConditionTest<NBTCondition> {

    private static final Codec<CompoundBinaryTag, String, IOException, IOException> CODEC = Codec.of(TagStringIO.get()::asCompound, TagStringIO.get()::asString);

    @Test
    @Override
    protected void testCondition() throws IOException {
        final NBTCondition condition = this.generateCondition();
        final @Nullable BinaryTagHolder holder = condition.tag();
        assertNotNull(holder);
        final CompoundBinaryTag tag = holder.get(CODEC);

        CompoundBinaryTag expectedTag = TagStringIO.get().asCompound("{BlockEntityTag:{Patterns:[{Color:9,Pattern:\"mr\"},{Color:8,Pattern:\"bs\"},{Color:7,Pattern:\"cs\"},{Color:8,Pattern:\"bo\"},{Color:15,Pattern:\"ms\"},{Color:8,Pattern:\"hh\"},{Color:8,Pattern:\"mc\"},{Color:15,Pattern:\"bo\"}]},HideFlags:32,display:{Name:\u0027{\"color\":\"gold\",\"translate\":\"block.minecraft.ominous_banner\"}\u0027}}");
        assertEquals(expectedTag, tag);
        assertEquals(condition, new NBTCondition(BinaryTagHolder.encode(expectedTag, CODEC)));
    }

    @Test
    @Override
    protected void testAnyCondition() {
        testIsAny("null");
        testIsAny(JsonNull.INSTANCE);
        assertThrows(JsonParseException.class, () -> testIsAny("{}"));
    }

    @Override
    public NBTCondition generateCondition() throws IOException {
        CompoundBinaryTag tag = CompoundBinaryTag.builder().put(
                        "BlockEntityTag", CompoundBinaryTag.builder()
                                .put(
                                        "Patterns", ListBinaryTag.of(BinaryTagTypes.COMPOUND, List.of(
                                                forPattern(9, "mr"),
                                                forPattern(8, "bs"),
                                                forPattern(7, "cs"),
                                                forPattern(8, "bo"),
                                                forPattern(15, "ms"),
                                                forPattern(8, "hh"),
                                                forPattern(8, "mc"),
                                                forPattern(15, "bo")
                                        )))
                                .build()
                )
                .putInt("HideFlags", 32)
                .put("display", CompoundBinaryTag.builder().putString("Name", GsonComponentSerializer.gson().serialize(Component.translatable("block.minecraft.ominous_banner", NamedTextColor.GOLD))).build())
                .build();
        return new NBTCondition(BinaryTagHolder.encode(tag, CODEC));
    }

    private static CompoundBinaryTag forPattern(int color, String pattern) {
        return CompoundBinaryTag.from(Map.of("Color", IntBinaryTag.of(color), "Pattern", StringBinaryTag.of(pattern)));
    }
}
