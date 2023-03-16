package me.machinemaker.advancements.conditions;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.GsonTest;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.EnabledIf;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ConditionTest<C extends Condition<C>> extends GsonTest {

    private final ConditionType<C> type;
    private final boolean hasAdditionalAnyTests;

    protected ConditionTest(final ConditionType<C> type) {
        this(type, true);
    }

    protected ConditionTest(final ConditionType<C> type, final boolean hasAdditionalAnyTests) {
        this.type = type;
        this.hasAdditionalAnyTests = hasAdditionalAnyTests;
    }

    @Override
    protected final void modifyBuilder(final GsonBuilder builder) {
        this.type.requiredGson().applyTo(builder);
        super.modifyBuilder(builder);
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
            assertEquals(ConditionTest.this.type.anyIsNull() ? JsonNull.INSTANCE : new JsonObject(), ConditionTest.this.toTree(ConditionTest.this.type.any()));
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
