package me.machinemaker.advancements;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class GsonTest {

    protected final Gson gson;

    protected GsonTest(final GsonBuilderApplicable...applicables) {
        final GsonBuilder builder = new GsonBuilder()
            .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setPrettyPrinting();
        for (final GsonBuilderApplicable applicable : applicables) {
            applicable.applyTo(builder);
        }
        this.modifyBuilder(builder);
        this.gson = builder.create();
    }

    protected void modifyBuilder(final GsonBuilder builder) {
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
