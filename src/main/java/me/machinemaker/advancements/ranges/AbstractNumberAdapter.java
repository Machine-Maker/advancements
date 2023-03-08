package me.machinemaker.advancements.ranges;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import me.machinemaker.advancements.GsonHelper;

abstract sealed class AbstractNumberAdapter<N extends NumberRange<? extends Number, ?>> extends TypeAdapter<N> permits IntegerRangeImpl.Adapter, DoubleRangeImpl.Adapter {

    protected static final GsonHelper HELPER = new GsonHelper();

    @Override
    public void write(final JsonWriter out, final N value) throws IOException {
        if (value.isAny()) {
            out.nullValue();
        } else if (value.isExact()) {
            out.value(value.min());
        } else {
            out.beginObject();
            if (value.min() != null) {
                out.name("min").value(value.min());
            }
            if (value.max() != null) {
                out.name("max").value(value.max());
            }
            out.endObject();
        }
    }
}
