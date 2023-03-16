package me.machinemaker.advancements.conditions.misc;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import me.machinemaker.advancements.GsonTestBase;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NBTConditionTest extends ConditionTest<NBTCondition> {

    private static final Codec<CompoundBinaryTag, String, IOException, IOException> CODEC = Codec.codec(TagStringIO.get()::asCompound, TagStringIO.get()::asString);

    private static CompoundBinaryTag forPattern(final int color, final String pattern) {
        return CompoundBinaryTag.from(Map.of("Color", IntBinaryTag.of(color), "Pattern", StringBinaryTag.of(pattern)));
    }

    private static NBTCondition generateCondition() throws IOException {
        final CompoundBinaryTag tag = CompoundBinaryTag.builder().put(
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
        return NBTCondition.create(BinaryTagHolder.encode(tag, CODEC));
    }

    NBTConditionTest() {
        super(NBTCondition.conditionType(), false);
    }

    @Test
    void testNBTCondition() throws IOException {
        final NBTCondition condition = generateCondition();
        final @Nullable BinaryTagHolder holder = condition.tag();
        assertNotNull(holder);
        final JsonElement primitive = new JsonPrimitive(holder.string());
        this.testJsonConversion(condition, primitive);
    }

    @Override
    protected void additionalAnyTests() {
    }
}
