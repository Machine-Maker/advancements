package me.machinemaker.datapacks.advancements.testing.types.conditions.misc;

import java.io.IOException;
import me.machinemaker.datapacks.advancements.conditions.misc.NBTCondition;
import me.machinemaker.datapacks.advancements.testing.Provider;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.DoubleBinaryTag;
import net.kyori.adventure.nbt.TagStringIO;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import net.kyori.adventure.util.Codec;

public class NBTConditionProvider implements Provider<NBTCondition> {

    private static final Codec<CompoundBinaryTag, String, IOException, IOException> CODEC = Codec.codec(TagStringIO.get()::asCompound, TagStringIO.get()::asString);

    @Override
    public NBTCondition get() {
        final CompoundBinaryTag test = CompoundBinaryTag.builder()
            .put("test", DoubleBinaryTag.of(4.2d)).build();
        try {
            return NBTCondition.create(BinaryTagHolder.encode(test, CODEC));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
