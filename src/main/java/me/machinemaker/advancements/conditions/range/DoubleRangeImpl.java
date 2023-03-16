package me.machinemaker.advancements.conditions.range;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.IOException;
import me.machinemaker.advancements.GsonHelper;
import me.machinemaker.advancements.adapters.builders.Builders;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.ConditionType;
import org.checkerframework.checker.nullness.qual.Nullable;

record DoubleRangeImpl(
    @Nullable Double min,
    @Nullable Double max
) implements DoubleRange {

    static final DoubleRange ANY = new DoubleRangeImpl(null, null);
    static final GsonBuilderApplicable REQUIRED_GSON = Builders.typeHierarchy(DoubleRange.class, new Adapter());
    static final ConditionType<DoubleRange> TYPE = ConditionType.create(DoubleRange.class, ANY, DoubleRange::requiredGson);

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
                final JsonObject obj = HELPER.objectFromReader(in);
                final @Nullable Double min = GsonHelper.getDouble(obj, "min", null);
                final @Nullable Double max = GsonHelper.getDouble(obj, "max", null);
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
