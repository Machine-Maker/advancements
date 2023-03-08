package me.machinemaker.advancements.conditions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.EnabledIf;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ConditionTest<C extends Condition<C>> {

    private final ConditionType<C> type;
    private final Gson gson;
    private final boolean hasAdditionalAnyTests;

    protected ConditionTest(final ConditionType<C> type) {
        this(type, true);
    }

    protected ConditionTest(final ConditionType<C> type, final boolean hasAdditionalAnyTests) {
        this.type = type;
        final GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        this.type.requiredGson().applyTo(builder);
        this.gson = builder.create();
        this.hasAdditionalAnyTests = hasAdditionalAnyTests;
    }

    protected final JsonElement toTree(final Object object) {
        return this.gson.toJsonTree(object);
    }

    protected final JsonElement toTree(final Object object, final Class<?> typeOfObj) {
        return this.gson.toJsonTree(object, typeOfObj);
    }

    protected final C fromTree(final JsonElement element) {
        return this.fromTree(element, this.type.baseType());
    }

    protected final <T> T fromTree(final JsonElement element, final Class<T> typeOfT) {
        return this.gson.fromJson(element, typeOfT);
    }

    protected final C fromTree(final String json) {
        return this.fromTree(json, this.type.baseType());
    }

    protected final <T> T fromTree(final String json, final Class<T> typeOfT) {
        return this.gson.fromJson(json, typeOfT);
    }

    protected final void testJsonConversion(final C condition, final JsonElement obj) {
        this.testJsonConversion(condition, obj, this.type.baseType());
    }

    protected final <T> void testJsonConversion(final T object, final JsonElement obj, final Class<T> typeOfT) {
        assertEquals(obj, this.toTree(object));
        assertEquals(obj, this.toTree(object, typeOfT));

        if (object.getClass().isArray()) {
            assertArrayEquals((Object[]) object, (Object[]) this.gson.fromJson(obj, typeOfT));
        } else {
            assertEquals(object, this.fromTree(obj, typeOfT));
        }
    }

    protected void testIsAny(final JsonElement element) {
        this.checkIsAny(this.fromTree(element));
    }

    protected void testIsAny(final String json) {
        this.checkIsAny(this.fromTree(json));
    }

    protected final void checkIsAny(final C condition) {
        assertSame(this.type.any(), condition);
        assertEquals(condition, this.type.any());
        assertEquals(this.type.any(), condition);
    }

    protected abstract void additionalAnyTests();

    @Nested
    @DisplayName("'ANY' tests")
    class AnyTests {

        @Test
        final void testAnyToNull() {
            assertEquals(JsonNull.INSTANCE, ConditionTest.this.toTree(ConditionTest.this.type.any()));
        }

        @Test
        final void testNullToAny() {
            ConditionTest.this.testIsAny("null");
        }

        @Test
        @EnabledIf("hasAdditionalTests")
        final void testAdditionalAnys() {
            ConditionTest.this.additionalAnyTests();
        }

        final boolean hasAdditionalTests() {
            return ConditionTest.this.hasAdditionalAnyTests;
        }
    }

}
