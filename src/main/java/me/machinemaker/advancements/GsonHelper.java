package me.machinemaker.advancements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.Conditions;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;

public class GsonHelper {

    public static boolean isNull(@Nullable JsonElement element) {
        return element == null || element.isJsonNull();
    }

    public static boolean isNull(@NotNull JsonObject object, @NotNull String property) {
        return isNull(object.get(property));
    }

    public static int getInt(@NotNull JsonObject object, @NotNull String property) {
        if (isNull(object, property)) {
            nullException(object, property);
        }
        return object.get(property).getAsInt();
    }

    @Contract("_, _, !null -> null")
    public static @Nullable Integer getInt(@NotNull JsonObject object, @NotNull String property, @Nullable Integer def) {
        if (isNull(object, property)) {
            return def;
        }
        return object.get(property).getAsInt();
    }

    public static double getDouble(@NotNull JsonObject object, @NotNull String property) {
        if (isNull(object, property)) {
            nullException(object, property);
        }
        return object.get(property).getAsDouble();
    }

    @Contract("_, _, !null -> !null")
    public static @Nullable Double getDouble(@NotNull JsonObject object, @NotNull String property, @Nullable Double def) {
        if (isNull(object, property)) {
            return def;
        }
        return object.get(property).getAsDouble();
    }

    public static boolean getBoolean(@NotNull JsonObject object, @NotNull String property) {
        if (isNull(object, property)) {
            nullException(object, property);
        }
        return object.get(property).getAsBoolean();
    }

    @Contract("_, _, !null -> !null")
    public static @Nullable Boolean getBoolean(@NotNull JsonObject object, @NotNull String property, @Nullable Boolean def) {
        if (isNull(object, property)) {
            return def;
        }
        return object.get(property).getAsBoolean();
    }

    public static String getString(@NotNull JsonObject object, @NotNull String property) {
        if (isNull(object, property)) {
            nullException(object, property);
        }
        return object.get(property).getAsString();
    }

    public static @Nullable String getString(@NotNull JsonObject object, @NotNull String property, @Nullable String def) {
        if (isNull(object, property)) {
            return def;
        }
        return object.get(property).getAsString();
    }

    public @NotNull JsonElement get(@NotNull JsonObject object, @NotNull String property) {
        if (isNull(object, property)) {
            nullException(object, property);
        }
        return object.get(property);
    }

    public @NotNull <T extends JsonElement> T get(@NotNull JsonObject object, @NotNull String property, @NotNull Class<T> classOfT) {
        if (isNull(object, property)) {
            nullException(object, property);
        }
        JsonElement element = object.get(property);
        if (classOfT.isInstance(element)) {
            return classOfT.cast(element);
        }
        throw new JsonParseException(property + " on " + object + " is " + element + ", not an instance of " + classOfT.getSimpleName());
    }

    @Contract("_, _, !null -> !null")
    public @Nullable JsonElement get(@NotNull JsonObject object, @NotNull String property, @Nullable JsonElement def) {
        if (isNull(object, property)) {
            return def;
        }
        return object.get(property);
    }

    @Contract("_, _ -> fail")
    private static void nullException(@NotNull JsonObject object, @NotNull String property) {
        throw new JsonParseException(property + " on " + object + " was null");
    }

    private final GsonBuilder builder;
    @Nullable
    private Gson gson;

    public GsonHelper(GsonBuilderApplicable applicable) {
        this();
        applicable.applyTo(this.builder);
    }

    public GsonHelper() {
        this.builder = new GsonBuilder()
                .setVersion(1.0)
                .registerTypeAdapterFactory(Adapters.RECORD_TYPE_ADAPTER_FACTORY.factory())
                .registerTypeAdapterFactory(Adapters.WRAPPER_TYPE_ADAPTER_FACTORY.factory());
    }

    public GsonHelper(GsonBuilder builder) {
        this.builder = builder;
    }

    @Contract(value = "_, _ -> this", mutates = "this")
    public @NotNull GsonHelper registerTypeAdapter(@NotNull Type type, @NotNull Object typeAdapter) {
        this.checkGson();
        this.builder.registerTypeAdapter(type, typeAdapter);
        return this;
    }

    @Contract(value = "_ -> this", mutates = "this")
    public @NotNull GsonHelper registerTypeAdapterFactory(@NotNull TypeAdapterFactory factory) {
        this.checkGson();
        this.builder.registerTypeAdapterFactory(factory);
        return this;
    }

    @Contract(value = "_, _ -> this", mutates = "this")
    public @NotNull GsonHelper registerTypeHierarchyAdapter(@NotNull Class<?> baseType, @NotNull Object typeAdapter) {
        this.checkGson();
        this.builder.registerTypeHierarchyAdapter(baseType, typeAdapter);
        return this;
    }

    private void checkGson() {
        if (this.gson != null) {
            throw new IllegalStateException("Already built");
        }
    }

    public @NotNull Gson gson() {
        if (this.gson == null) {
            this.gson = this.builder.create();
        }
        return this.gson;
    }

    public <T> @NotNull T getAs(@NotNull JsonObject object, @NotNull String property, @NotNull Class<T> classOfT) {
        return this.getAs(object, property, (Type) classOfT);
    }

    public <T> @NotNull T getAs(@NotNull JsonObject object, @NotNull String property, @NotNull Type typeOfT) {
        if (isNull(object, property)) {
            nullException(object, property);
        }
        return this.requireNonNull(this.gson().fromJson(object.get(property), typeOfT), object, property, typeOfT);
    }

    @Contract("_, _, _, !null -> !null")
    public <T> @Nullable T getAs(@NotNull JsonObject object, @NotNull String property, @NotNull Class<T> classOfT, @Nullable T def) {
        return this.getAs(object, property, (Type) classOfT, def);
    }

    @Contract("_, _, _, !null -> !null")
    public <T> @Nullable T getAs(@NotNull JsonObject object, @NotNull String property, @NotNull Type typeOfT, @Nullable T def) {
        if (isNull(object, property)) {
            return def;
        }
        return this.requireNonNull(this.gson().fromJson(object.get(property), typeOfT), object, property, typeOfT);
    }

    public <C extends Condition<? super C>> @NotNull C getDefaulted(@NotNull JsonObject object, @NotNull String property, @NotNull Class<C> classOfC) {
        if (isNull(object, property)) {
            return Conditions.getDefaultCondition(classOfC);
        }
        return this.getAs(object, property, classOfC);
    }

    private <T> T requireNonNull(@Nullable T value, @NotNull JsonObject object, @NotNull String property, @NotNull Type typeOfT) {
        if (value == null) {
            throw new JsonParseException(object.get(property) + " parsed to type " + typeOfT + " was null");
        }
        return value;
    }

    public @NotNull String toJsonTreeString(@NotNull Object object) {
        return this.toJsonTree(object).getAsString();
    }

    public int toJsonTreeInt(@NotNull Object object) {
        return this.toJsonTree(object).getAsInt();
    }

    public double toJsonTreeDouble(@NotNull Object object) {
        return this.toJsonTree(object).getAsDouble();
    }

    public @NotNull JsonElement toJsonTree(@NotNull Object object) {
        return this.toJsonTree(object, object.getClass());
    }

    public <T> @NotNull JsonElement toJsonTree(@Nullable T object, @NotNull Class<T> classOfT) {
        return this.toJsonTree(object, (Type) classOfT);
    }

    public <T> @NotNull JsonElement toJsonTree(@Nullable T object, @NotNull Type typeOfT) {
        return this.gson().toJsonTree(object, typeOfT);
    }

    public <T> @NotNull T fromJsonTree(@NotNull JsonElement element, @NotNull Class<T> classOfT) {
        return this.fromJsonTree(element, (Type) classOfT);
    }

    public <T> @NotNull T fromJsonTree(@NotNull JsonElement element, @NotNull Type typeOfT) {
        return this.gson().fromJson(element, typeOfT);
    }

    public <T> void toWriter(@NotNull JsonWriter writer, @NotNull String property, @Nullable T object, @NotNull Class<T> classOfT) throws IOException {
        writer.name(property);
        this.toWriter(writer, object, classOfT);
    }

    public <T> void toWriter(@NotNull JsonWriter writer, @Nullable T object, @NotNull Class<T> classOfT) {
        this.gson().toJson(object, classOfT, writer);
    }

    public void toWriter(@NotNull JsonWriter writer, @NotNull JsonElement element) {
        this.gson().toJson(element, writer);
    }

    public JsonObject objectFromReader(@NotNull JsonReader reader) {
        return this.fromReader(reader, JsonObject.class);
    }

    public JsonArray arrayFromReader(@NotNull JsonReader reader) {
        return this.fromReader(reader, JsonArray.class);
    }

    public <T> T fromReader(@NotNull JsonReader reader, @NotNull Class<T> classOfT) {
        return this.fromReader(reader, (Type) classOfT);
    }

    public <T> T fromReader(@NotNull JsonReader reader, @NotNull Type typeOfT) {
        return this.gson().fromJson(reader, typeOfT);
    }
}
