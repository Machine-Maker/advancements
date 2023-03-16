package me.machinemaker.advancements.adapters.util;

import com.google.common.base.Suppliers;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import java.util.function.Supplier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public record GsonContext(Supplier<Gson> gson) implements JsonSerializationContext, JsonDeserializationContext {

    public GsonContext(final Gson gson) {
        this(Suppliers.ofInstance(gson));
    }

    @Override
    public <T> T deserialize(final JsonElement json, final Type typeOfT) throws JsonParseException {
        return this.gson.get().fromJson(json, typeOfT);
    }

    @Override
    public JsonElement serialize(final Object src) {
        return this.gson.get().toJsonTree(src);
    }

    @Override
    public JsonElement serialize(final Object src, final Type typeOfSrc) {
        return this.gson.get().toJsonTree(src, typeOfSrc);
    }
}
