package me.machinemaker.datapacks.advancements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.function.Predicate;
import me.machinemaker.datapacks.advancements.conditions.Condition;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class GsonTestBase {

    public GsonTestBase() {
        final GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        this.gson = builder.create();
    }

    protected Gson gson;

    protected final void registerTypeAdapter(final Type type, final Object adapter) {
        this.gson = this.gson.newBuilder().registerTypeAdapter(type, adapter).create();
    }

    protected JsonObject object(final String jsonString) {
        return this.gson.fromJson(jsonString, JsonObject.class);
    }

    protected JsonElement tree(final Object object) {
        return this.gson.toJsonTree(object);
    }

    protected JsonElement tree(final Object object, final Type type) {
        return this.gson.toJsonTree(object, type);
    }

    protected <T> T fromJson(final JsonElement jsonElement, final Class<T> classOfT) {
        return this.gson.fromJson(jsonElement, classOfT);
    }

    protected <T> T fromJson(final JsonElement jsonElement, final Type type) {
        return this.gson.fromJson(jsonElement, type);
    }

    protected <T> T fromJson(final String json, final Class<T> classOfT) {
        return this.gson.fromJson(json, classOfT);
    }

    protected String toJson(final Object object) {
        return this.gson.toJson(object);
    }

    protected <T extends Condition<? super T>> void anyTest(final Object object, final Class<T> classOfT) {
        this.test(object, classOfT, t -> t == t.any() && t.equals(t.any()) && t.any().equals(t));
    }

    protected <T> void test(final Object object, final Class<T> classOfT, final Predicate<T> test) {
        final T testObject;
        if (object instanceof String string) {
            testObject = this.fromJson(string, classOfT);
        } else if (object instanceof JsonElement jsonElement) {
            testObject = this.fromJson(jsonElement, classOfT);
        } else {
            throw new UnsupportedOperationException(object.getClass() + " is not a supported type");
        }
        assertTrue(test.test(testObject));
    }
}
