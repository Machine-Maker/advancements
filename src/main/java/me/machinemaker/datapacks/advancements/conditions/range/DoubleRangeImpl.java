package me.machinemaker.datapacks.advancements.conditions.range;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.utils.GsonUtils;
import org.checkerframework.checker.nullness.qual.Nullable;

record DoubleRangeImpl(
    @Nullable Double min,
    @Nullable Double max
) implements DoubleRange {

    static final DoubleRange ANY = new DoubleRangeImpl(null, null);
    static final ConditionType<DoubleRange> TYPE = ConditionType.create(DoubleRange.class, ANY, new Adapter());

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "DoubleRange{ANY}";
        }
        return "DoubleRange{" +
            "min=" + this.min +
            ", max=" + this.max +
            '}';
    }

    static final class Adapter extends AbstractNumberAdapter<DoubleRange> {

        @Override
        public DoubleRange read(final JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return ANY;
            } else if (in.peek() == JsonToken.NUMBER) {
                return DoubleRange.isExactly(in.nextDouble());
            } else if (in.peek() == JsonToken.BEGIN_OBJECT) {
                final JsonObject obj = Streams.parse(in).getAsJsonObject();
                final @Nullable Double min = GsonUtils.getDouble(obj, "min", null);
                final @Nullable Double max = GsonUtils.getDouble(obj, "max", null);
                if (min == null && max == null) {
                    return ANY;
                }
                return new DoubleRangeImpl(min, max);
            } else {
                throw new JsonParseException(in.peek() + " was not expected for DoubleRange");
            }
        }
    }
}
