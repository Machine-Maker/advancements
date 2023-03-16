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

record IntegerRangeImpl(
    @Nullable Integer min,
    @Nullable Integer max
) implements IntegerRange {

    static final IntegerRange ANY = new IntegerRangeImpl(null, null);
    static final GsonBuilderApplicable REQUIRED_GSON = Builders.typeHierarchy(IntegerRange.class, new Adapter());
    static final ConditionType<IntegerRange> TYPE = ConditionType.create(IntegerRange.class, ANY, IntegerRange::requiredGson);

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
                final JsonObject obj = HELPER.objectFromReader(in);
                final @Nullable Integer min = GsonHelper.getInt(obj, "min", null);
                final @Nullable Integer max = GsonHelper.getInt(obj, "max", null);
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
