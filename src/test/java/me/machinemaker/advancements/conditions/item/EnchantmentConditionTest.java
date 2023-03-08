package me.machinemaker.advancements.conditions.item;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.conditions.ConditionTest;
import me.machinemaker.advancements.ranges.IntegerRange;
import me.machinemaker.advancements.tests.mocks.DummyEnchantments;
import me.machinemaker.advancements.tests.providers.CompoundProvider;
import me.machinemaker.advancements.tests.providers.RangeProviders;
import org.bukkit.enchantments.Enchantment;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class EnchantmentConditionTest extends ConditionTest<EnchantmentCondition> {

    static {
        DummyEnchantments.setup();
    }

    EnchantmentConditionTest() {
        super(EnchantmentCondition.conditionType());
    }

    @RangeProviders.Ints
    void testSingleEnchantmentCondition(final IntegerRange range) {
        final EnchantmentCondition.Builder builder = EnchantmentCondition.builder();
        builder.enchantment(Enchantment.ARROW_DAMAGE).level(range);
        final JsonObject object = new JsonObject();
        object.addProperty("enchantment", Enchantment.ARROW_DAMAGE.getKey().toString());
        object.add("level", this.toTree(range));
        this.testJsonConversion(builder.build(), object);
    }

    @CompoundProvider({RangeProviders.Ints.Provider.class, RangeProviders.Ints.Provider.class})
    void testMultipleEnchantmentConditions(final IntegerRange range1, final IntegerRange range2) {
        final EnchantmentCondition condition1 = EnchantmentCondition.builder()
            .enchantment(Enchantment.DAMAGE_ALL).level(range1).build();
        final EnchantmentCondition condition2 = EnchantmentCondition.builder()
            .enchantment(null).level(range2).build();
        final JsonArray array = new JsonArray();
        final JsonObject object1 = new JsonObject();
        object1.addProperty("enchantment", Enchantment.DAMAGE_ALL.getKey().toString());
        object1.add("level", this.toTree(range1));
        array.add(object1);
        final JsonObject object2 = new JsonObject();
        object2.add("level", this.toTree(range2));
        array.add(object2);
        final EnchantmentCondition[] conditions = new EnchantmentCondition[]{condition1, condition2};
        this.testJsonConversion(conditions, array, EnchantmentCondition[].class);
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
