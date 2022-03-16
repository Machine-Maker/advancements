package me.machinemaker.advancements.conditions;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import me.machinemaker.advancements.GsonHelper;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.lang.reflect.ParameterizedType;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ConditionTest<C extends Condition<? super C>> {

    @MonotonicNonNull private GsonHelper helper;

    @BeforeAll
    protected void before() {
        final GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        this.setupGson(builder);
        this.helper = new GsonHelper(builder);
    }

    protected void setupGson(GsonBuilder builder) { }

    @SuppressWarnings("unchecked")
    public final Class<C> conditionType() {
        if (this.getClass().getGenericSuperclass() instanceof ParameterizedType parameterizedType) {
            return (Class<C>) parameterizedType.getActualTypeArguments()[0];
        }
        throw new IllegalStateException();
    }

    protected abstract void testCondition() throws Exception;

    protected abstract void testAnyCondition() throws Exception;

    public abstract C generateCondition() throws Exception;

    protected final void testIsAny(Object object) {
        this.test(object, this.conditionType(), c -> c == c.any() && c.equals(c.any())); // check both types of equality
    }

    protected final <T> void test(Object object, Class<T> classOfT, Predicate<T> test) {
        final T testObject;
        if (object instanceof String string) {
            testObject = this.helper.gson().fromJson(string, classOfT);
        } else if (object instanceof JsonElement jsonElement) {
            testObject = this.helper.gson().fromJson(jsonElement, classOfT);
        } else {
            throw new UnsupportedOperationException(object.getClass() + " is not a supported type");
        }
        assertTrue(test.test(testObject));
    }
}
