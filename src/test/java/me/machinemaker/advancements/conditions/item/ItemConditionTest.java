package me.machinemaker.advancements.conditions.item;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import io.papermc.paper.potion.Potion;
import java.util.Set;
import me.machinemaker.advancements.conditions.ConditionTest;
import me.machinemaker.advancements.ranges.IntegerRange;
import me.machinemaker.advancements.tests.mocks.DummyEnchantments;
import me.machinemaker.advancements.tests.providers.CompoundProvider;
import me.machinemaker.advancements.tests.providers.MaterialProviders;
import me.machinemaker.advancements.tests.providers.Providers;
import me.machinemaker.advancements.tests.providers.RangeProviders;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.junit.jupiter.api.Test;

import static me.machinemaker.advancements.conditions.item.ItemConditionUtils.addItems;

class ItemConditionTest extends ConditionTest<ItemCondition> {

    static {
        DummyEnchantments.setup();
    }

    ItemConditionTest() {
        super(ItemCondition.conditionType());
    }

    @Test
    void testItemConditionTag() {
        final ItemCondition condition = ItemCondition.forTag(Tag.FENCES);
        final JsonObject object = new JsonObject();
        object.addProperty("tag", Tag.FENCES.getKey().toString());
        this.testJsonConversion(condition, object);
    }

    @Providers.Config(maxSize = 4, count = 3)
    @CompoundProvider({MaterialProviders.Items.Provider.class, RangeProviders.Ints.Provider.class})
    void testItemConditionItems(final Set<Material> itemSet, final IntegerRange range) {
        final ItemCondition.Builder builder = ItemCondition.builder();
        final JsonObject obj = new JsonObject();
        addItems(builder, obj, itemSet);
        builder.count(range);
        obj.add("count", this.toTree(range));
        builder.potion(Potion.LEAPING.value());
        obj.addProperty("potion", Potion.LEAPING.key().asString());
        this.testJsonConversion(builder.build(), obj);
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject object = new JsonObject();
        object.add("items", JsonNull.INSTANCE);
        object.add("count", JsonNull.INSTANCE);
        this.testIsAny(object);
        this.testIsAny("{ \"enchantments\": null, \"potion\": null, \"count\": { \"min\":  null } }");
    }

    // @Test
    // void testItemConditionEnchantments() {
    //     final EnchantmentCondition[] conditions = new EnchantmentCondition[1];
    //     conditions[0] = new EnchantmentCondition(Enchantment.ARROW_DAMAGE, IntegerRange.isExactly(2));
    //     final ItemCondition condition = new ItemCondition(null, null, IntegerRange.conditionType().any(), IntegerRange.conditionType().any(), conditions, EnchantmentCondition.NONE, null, NBTCondition.conditionType().any());
    //     final JsonObject obj = new JsonObject();
    //     final JsonArray array = new JsonArray();
    //     final JsonObject ench1 = new JsonObject();
    //     ench1.addProperty("enchantment", "minecraft:power");
    //     ench1.addProperty("level", 2);
    //     array.add(ench1);
    //     obj.add("enchantments", array);
    //     assertEquals(obj, this.tree(condition));
    //     assertEquals(condition, this.fromJson(obj, ItemCondition.class));
    // }

    // TODO more tests probably
}
