package me.machinemaker.datapacks;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import me.machinemaker.datapacks.advancements.adapters.Adapters;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class GsonTest {

    protected final Gson gson;

    protected GsonTest() {
        this.gson = Adapters.createBuilder()
            .setPrettyPrinting()
            .create();
    }

    protected final JsonElement toTree(final @Nullable Object object) {
        return this.gson.toJsonTree(object);
    }

    protected final JsonElement toTree(final Object object, final Type typeOfObj) {
        return this.gson.toJsonTree(object, typeOfObj);
    }

    protected final <T> T fromTree(final JsonElement element, final Class<T> typeOfT) {
        return this.gson.fromJson(element, typeOfT);
    }

    protected final <T> T fromTree(final String json, final Class<T> typeOfT) {
        return this.gson.fromJson(json, typeOfT);
    }

    protected void add(final JsonObject obj, final String key, final @Nullable Object value) {
        final JsonElement element = this.toTree(value);
        if (!element.isJsonNull()) {
            obj.add(key, element);
        }
    }

    protected void add(final JsonObject obj, final String key, final @Nullable Object value, final Type type) {
        final JsonElement element = this.toTree(value, type);
        if (!element.isJsonNull()) {
            obj.add(key, element);
        }
    }

    protected final <T> void testJsonConversion(final T object, final JsonElement obj, final Class<T> typeOfT) {
        this.testJsonConversion(object, obj, (Type) typeOfT);
    }

    protected final <T> void testJsonConversion(final T object, final JsonElement obj, final Type typeOfT) {
        assertEquals(obj, this.toTree(object, typeOfT));

        if (object.getClass().isArray()) {
            assertArrayEquals((Object[]) object, (Object[]) this.gson.fromJson(obj, typeOfT));
        } else {
            assertEquals(object, this.gson.fromJson(obj, typeOfT));
        }
    }

}
