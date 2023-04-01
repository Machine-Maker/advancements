package me.machinemaker.datapacks.advancements.conditions.misc;

import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import org.jetbrains.annotations.Nullable;

record NBTConditionImpl(@Nullable BinaryTagHolder tag) implements NBTCondition {

    static final NBTConditionImpl ANY = new NBTConditionImpl(null);
    static final ConditionType<NBTCondition> TYPE = ConditionType.create(NBTCondition.class, ANY, new Adapter());

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "NBTCondition{ANY}";
        }
        return "NBTCondition{" + "tag=" + this.tag + '}';
    }

    private static final class Adapter extends TypeAdapter<NBTCondition> {

        @Override
        public void write(final JsonWriter out, final NBTCondition value) throws IOException {
            if (value == ANY || value.tag() == null) {
                out.nullValue();
            } else {
                out.value(value.tag().string());
            }
        }

        @Override
        public NBTCondition read(final JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return ANY;
            } else if (in.peek() == JsonToken.STRING) {
                return new NBTConditionImpl(BinaryTagHolder.binaryTagHolder(in.nextString()));
            } else {
                throw new JsonParseException("Expected a string or null, got " + in.peek());
            }
        }
    }
}
