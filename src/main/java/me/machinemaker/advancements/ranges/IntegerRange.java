package me.machinemaker.advancements.ranges;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import me.machinemaker.advancements.GsonHelper;
import me.machinemaker.advancements.adapters.util.IgnoreRecordTypeAdapter;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;

import java.io.IOException;

@IgnoreRecordTypeAdapter
@JsonAdapter(value = IntegerRange.Adapter.class, nullSafe = false)
public record IntegerRange(@Nullable Integer min, @Nullable Integer max) implements NumberRange<Integer> {

    public static final IntegerRange ANY = new IntegerRange(null, null);

    @Contract("_ -> new")
    public static IntegerRange isExactly(int value) {
        return new IntegerRange(value, value);
    }

    @Contract("_, _ -> new")
    public static IntegerRange isBetween(int min, int max) {
        return new IntegerRange(min, max);
    }

    @Contract("_ -> new")
    public static IntegerRange isAtLeast(int min) {
        return new IntegerRange(min, null);
    }

    @Contract("_ -> new")
    public static IntegerRange isAtMost(int max) {
        return new IntegerRange(null, max);
    }

    @Override
    public NumberRange<Integer> any() {
        return ANY;
    }

    static final class Adapter extends NumberRange.Adapter<IntegerRange> {

        @Override
        public IntegerRange read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NUMBER) {
                return IntegerRange.isExactly(in.nextInt());
            } else if (in.peek() == JsonToken.BEGIN_OBJECT) {
                JsonObject obj = HELPER.objectFromReader(in);
                final @Nullable Integer min = GsonHelper.getInt(obj, "min", null);
                final @Nullable Integer max = GsonHelper.getInt(obj, "max", null);
                return new IntegerRange(min, max);
            } else {
                throw new JsonParseException(in.peek() + " was not expected for IntegerRange");
            }
        }
    }
}
