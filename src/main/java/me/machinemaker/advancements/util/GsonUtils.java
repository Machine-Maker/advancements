package me.machinemaker.advancements.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Objects;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.DefaultValues;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.Internal
public final class GsonUtils {

    private GsonUtils() {
    }

    public static boolean isNull(final @Nullable JsonElement element) {
        return element == null || element.isJsonNull();
    }

    public static boolean isNull(final JsonObject object, final String property) {
        return isNull(object.get(property));
    }

    @Contract("_, _, !null -> !null")
    public static @Nullable String getString(final JsonObject object, final String property, final @Nullable String def) {
        if (isNull(object, property)) {
            return def;
        }
        return object.get(property).getAsString();
    }

    public static <C extends Condition<C>> C deserializeCondition(final Gson gson, final JsonObject obj, final String key, final Class<C> classOfC) {
        if (isNull(obj, key)) {
            return DefaultValues.getDefaultValue(TypeToken.get(classOfC));
        }
        return Objects.requireNonNull(deserializeFrom(gson, obj, key, classOfC));
    }

    public static <T> @Nullable T deserializeFrom(final Gson gson, final JsonObject obj, final String key, final Class<T> typeOfT) {
        return deserializeFrom(gson, obj, key, (Type) typeOfT);
    }

    public static <T> @Nullable T deserializeFrom(final Gson gson, final JsonObject obj, final String key, final Type typeOfT) {
        if (isNull(obj, key)) {
            return null;
        }
        return Objects.requireNonNull(gson.fromJson(obj.get(key), typeOfT));
    }

    public static <T> void out(final Gson gson, final String key, final T value, final JsonWriter writer) throws IOException {
        out(gson, key, value, value.getClass(), writer);
    }

    public static <T> void out(final Gson gson, final String key, final @Nullable T value, final Class<? extends T> classOfT, final JsonWriter writer) throws IOException {
        writer.name(key);
        gson.toJson(value, classOfT, writer);
    }
}
