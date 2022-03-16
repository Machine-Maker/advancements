package me.machinemaker.advancements;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.conditions.Condition;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.Type;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class GsonTestBase {

    protected Gson GSON = Adapters.configure(new GsonBuilder()).setPrettyPrinting().create();

    protected void registerTypeAdapter(Type type, Object adapter) {
        GSON = GSON.newBuilder().registerTypeAdapter(type, adapter).create();
    }

    protected JsonObject object(String jsonString) {
        return GSON.fromJson(jsonString, JsonObject.class);
    }

    protected JsonElement tree(Object object) {
        return GSON.toJsonTree(object);
    }

    protected JsonElement tree(Object object, Type type) {
        return GSON.toJsonTree(object, type);
    }

    protected <T> T fromJson(JsonElement jsonElement, Class<T> classOfT) {
        return GSON.fromJson(jsonElement, classOfT);
    }

    protected <T> T fromJson(JsonElement jsonElement, Type type) {
        return GSON.fromJson(jsonElement, type);
    }

    protected <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }

    protected String toJson(Object object) {
        return GSON.toJson(object);
    }

    protected <T extends Condition<? super T>> void anyTest(Object object, Class<T> classOfT) {
        test(object, classOfT, t -> t == t.any());
    }

    protected <T> void test(Object object, Class<T> classOfT, Predicate<T> test) {
        T testObject;
        if (object instanceof String string) {
            testObject = fromJson(string, classOfT);
        } else if (object instanceof JsonElement jsonElement) {
            testObject = fromJson(jsonElement, classOfT);
        } else {
            throw new UnsupportedOperationException(object.getClass() + " is not a supported type");
        }
        assertTrue(test.test(testObject));
    }
}
