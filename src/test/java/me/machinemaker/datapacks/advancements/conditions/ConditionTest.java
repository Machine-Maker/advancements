package me.machinemaker.datapacks.advancements.conditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.datapacks.GsonTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.EnabledIf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ConditionTest<C extends Condition> extends GsonTest {

    private final ConditionType<C> type;
    private final Condition anyType;
    private final boolean hasAdditionalAnyTests;

    protected ConditionTest(final ConditionType<C> type) {
        this(type, true);
    }

    protected ConditionTest(final ConditionType<C> type, final Condition anyType) {
        this(type, anyType, true);
    }

    protected ConditionTest(final ConditionType<C> type, final boolean hasAdditionalAnyTests) {
        this(type, type.any(), hasAdditionalAnyTests);
    }
    protected ConditionTest(final ConditionType<C> type, final Condition anyType, final boolean hasAdditionalAnyTests) {
        this.type = type;
        this.anyType = anyType;
        this.hasAdditionalAnyTests = hasAdditionalAnyTests;
    }

    protected final C fromTree(final JsonElement element) {
        return this.fromTree(element, this.type.baseType());
    }

    protected final C fromTree(final String json) {
        return this.fromTree(json, this.type.baseType());
    }

    protected final void testJsonConversion(final C condition, final JsonElement obj) {
        assertEquals(obj, this.toTree(condition));
        this.testJsonConversion(condition, obj, this.type.baseType());
    }

    protected void testIsAny(final JsonElement element) {
        this.testIsAny(element, this.anyType);
    }

    protected void testIsAny(final JsonElement element, final Object anyType) {
        checkIsAny(this.fromTree(element), anyType);
    }

    protected void testIsAny(final String json) {
        this.testIsAny(json, this.anyType);
    }

    protected void testIsAny(final String json, final Object anyType) {
        checkIsAny(this.fromTree(json), anyType);
    }

    protected final void checkIsAny(final C condition) {
        checkIsAny(condition, this.anyType);
    }

    private static void checkIsAny(final Condition condition, final Object anyType) {
        assertSame(anyType, condition);
        assertEquals(condition, anyType);
        assertEquals(anyType, condition);
    }

    protected abstract void additionalAnyTests();

    @Nested
    @DisplayName("'ANY' tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class AnyTests {

        @Test
        final void testAnyToNull() {
            assertEquals(ConditionTest.this.type.anyIsNull() ? JsonNull.INSTANCE : new JsonObject(), ConditionTest.this.toTree(ConditionTest.this.anyType));
        }

        @Test
        final void testNullToAny() {
            ConditionTest.this.testIsAny(ConditionTest.this.type.anyIsNull() ? "null" : "{}");
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
