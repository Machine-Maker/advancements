package me.machinemaker.advancements.conditions.misc;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import me.machinemaker.advancements.conditions.Condition;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

@JsonAdapter(value = NBTCondition.Adapter.class, nullSafe = false)
public record NBTCondition(@Nullable BinaryTagHolder tag) implements Condition<NBTCondition> {

    public static final NBTCondition ANY = new NBTCondition(null);

    @Override
    public @NotNull NBTCondition any() {
        return ANY;
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "NBTCondition{ANY}";
        }
        return "NBTCondition{" +
                "tag=" + this.tag +
                '}';
    }

    static final class Adapter extends TypeAdapter<NBTCondition> {

        @Override
        public void write(JsonWriter out, NBTCondition value) throws IOException {
            if (value == ANY || value.tag() == null) {
                out.nullValue();
            } else {
                out.value(value.tag().string());
            }
        }

        @Override
        public NBTCondition read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return NBTCondition.ANY;
            } else if (in.peek() == JsonToken.STRING) {
                return new NBTCondition(BinaryTagHolder.of(in.nextString()));
            } else {
                throw new JsonSyntaxException("Expected a string or null, got " + in.peek());
            }
        }
    }
}
