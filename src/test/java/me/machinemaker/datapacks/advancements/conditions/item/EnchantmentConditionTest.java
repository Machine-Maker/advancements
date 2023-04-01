package me.machinemaker.datapacks.advancements.conditions.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import java.util.List;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemSource;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemsSource;
import me.machinemaker.datapacks.testing.sources.sources.Sources;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EnchantmentConditionTest extends ConditionTest<EnchantmentCondition> {

    EnchantmentConditionTest() {
        super(EnchantmentCondition.conditionType());
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(SingleProvider.class)
    void testSingleEnchantmentCondition(final EnchantmentCondition condition) {
        final JsonObject obj = this.create(condition);
        this.testJsonConversion(condition, obj);
    }

    private JsonObject create(final EnchantmentCondition condition) {
        final JsonObject obj = new JsonObject();
        this.add(obj, "enchantment", condition.enchantment());
        this.add(obj, "level", condition.level());
        return obj;
    }

    @Sources.Config(count = 1000, maxSize = 3)
    @ArgumentsSource(MultipleProvider.class)
    void testMultipleEnchantmentCondition(final List<EnchantmentCondition> conditions) {
        final JsonArray array = new JsonArray();
        conditions.forEach(c -> {
            array.add(this.create(c));
        });
        this.testJsonConversion(conditions.toArray(EnchantmentCondition[]::new), array, EnchantmentCondition[].class);
    }

    private static final class SingleProvider extends RandomItemSource<EnchantmentCondition> {

        SingleProvider() {
            super(RandomProviders.ENCHANTMENT_CONDITION);
        }
    }

    private static final class MultipleProvider extends RandomItemsSource<EnchantmentCondition> {

        MultipleProvider() {
            super(RandomProviders.ENCHANTMENT_CONDITION);
        }
    }

    @Test
    void testNoneEnchantmentCondition() {
        final EnchantmentCondition[] none = new EnchantmentConditionImpl[0];
        assertArrayEquals(none, this.fromTree("[]", EnchantmentCondition[].class));
        assertArrayEquals(none, this.fromTree(JsonNull.INSTANCE, EnchantmentCondition[].class));
        assertEquals(JsonNull.INSTANCE, this.toTree(none, EnchantmentCondition[].class));
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject object = new JsonObject();
        object.add("enchantment", JsonNull.INSTANCE);
        this.testIsAny(object);
        this.testIsAny("{ \"enchantment\": null }");
        this.testIsAny("{ \"level\": { \"min\": null } }");

        final JsonArray array = new JsonArray();
        array.add(JsonNull.INSTANCE);
        final JsonObject object1 = new JsonObject();
        object1.add("level", JsonNull.INSTANCE);
        array.add(object1);

        this.testAllAny(this.fromTree(array, EnchantmentCondition[].class));
        this.testAllAny(this.fromTree("[ null, null, null ]", EnchantmentCondition[].class));
        this.testAllAny(this.fromTree("[ {}, null ]", EnchantmentCondition[].class));
        this.testAllAny(this.fromTree("[ null, { \"level\": null } ]", EnchantmentCondition[].class));
    }

    private void testAllAny(final EnchantmentCondition[] conditions) {
        for (final EnchantmentCondition condition : conditions) {
            this.checkIsAny(condition);
        }
    }
}
