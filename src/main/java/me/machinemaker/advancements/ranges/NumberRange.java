package me.machinemaker.advancements.ranges;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import me.machinemaker.advancements.GsonHelper;
import me.machinemaker.advancements.conditions.Condition;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.util.Objects;

public sealed interface NumberRange<N extends Number> extends Condition<NumberRange<N>> permits DoubleRange, IntegerRange {

    @Nullable N min();

    @Nullable N max();

    @Override
    default boolean isAny() {
        return this.min() == null && this.max() == null;
    }

    default boolean isExact() {
        return this.min() != null && this.max() != null && Objects.equals(this.min(), this.max());
    }

    abstract sealed class Adapter<N extends NumberRange<? extends Number>> extends TypeAdapter<N> permits IntegerRange.Adapter, DoubleRange.Adapter {

        protected static final GsonHelper HELPER = new GsonHelper();

        @Override
        public void write(JsonWriter out, N value) throws IOException {
            // WrapperTypeAdapterFactory should have already filtered out null and isAny
            if (value.isExact()) {
                out.value(value.min());
            } else {
                out.beginObject();
                boolean wrote = false;
                if (value.min() != null) {
                    out.name("min").value(value.min());
                    wrote = true;
                }
                if (value.max() != null) {
                    out.name("max").value(value.max());
                    wrote = true;
                }
                if (!wrote) { // TODO maybe unneeded
                    throw new IllegalStateException(value + " didnt have a min or max set");
                }
                out.endObject();
            }
        }
    }
}
