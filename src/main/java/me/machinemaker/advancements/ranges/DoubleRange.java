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
@JsonAdapter(value = DoubleRange.Adapter.class, nullSafe = false)
public record DoubleRange(@Nullable Double min, @Nullable Double max) implements NumberRange<Double> {

    public static final DoubleRange ANY = new DoubleRange(null, null);

    @Contract("_ -> new")
    public static DoubleRange isExactly(double value) {
        return new DoubleRange(value, value);
    }

    @Contract("_, _ -> new")
    public static DoubleRange isBetween(double min, double max) {
        return new DoubleRange(min, max);
    }

    @Contract("_ -> new")
    public static DoubleRange isAtLeast(double min) {
        return new DoubleRange(min, null);
    }

    @Contract("_ -> new")
    public static DoubleRange isAtMost(double max) {
        return new DoubleRange(null, max);
    }

    @Override
    public NumberRange<Double> any() {
        return ANY;
    }

    static final class Adapter extends NumberRange.Adapter<DoubleRange> {

        @Override
        public DoubleRange read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NUMBER) {
                return DoubleRange.isExactly(in.nextDouble());
            } else if (in.peek() == JsonToken.BEGIN_OBJECT) {
                JsonObject obj = HELPER.objectFromReader(in);
                final @Nullable Double min = GsonHelper.getDouble(obj, "min", null);
                final @Nullable Double max = GsonHelper.getDouble(obj, "max", null);
                return new DoubleRange(min, max);
            } else {
                throw new JsonParseException(in.peek() + " was not expected for DoubleRange");
            }
        }
    }

}
