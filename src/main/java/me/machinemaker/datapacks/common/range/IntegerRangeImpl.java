package me.machinemaker.datapacks.common.range;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.common.adapters.utils.GsonUtils;
import org.checkerframework.checker.nullness.qual.Nullable;

record IntegerRangeImpl(
    @Nullable Integer min,
    @Nullable Integer max
) implements IntegerRange {

    static final IntegerRange ANY = new IntegerRangeImpl(null, null);
    static final ConditionType<IntegerRange> TYPE = ConditionType.create(IntegerRange.class, ANY, new Adapter());

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "IntegerRange{ANY}";
        }
        return "IntegerRange{" +
            "min=" + this.min +
            ", max=" + this.max +
            '}';
    }

    static final class Adapter extends AbstractNumberAdapter<IntegerRange> {

        @Override
        public IntegerRange read(final JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return ANY;
            } else if (in.peek() == JsonToken.NUMBER) {
                return IntegerRange.isExactly(in.nextInt());
            } else if (in.peek() == JsonToken.BEGIN_OBJECT) {
                final JsonObject obj = Streams.parse(in).getAsJsonObject();
                final @Nullable Integer min = GsonUtils.getInt(obj, "min", null);
                final @Nullable Integer max = GsonUtils.getInt(obj, "max", null);
                if (min == null && max == null) {
                    return ANY;
                }
                return new IntegerRangeImpl(min, max);
            } else {
                throw new JsonParseException(in.peek() + " was not expected for IntegerRange");
            }
        }
    }
}
