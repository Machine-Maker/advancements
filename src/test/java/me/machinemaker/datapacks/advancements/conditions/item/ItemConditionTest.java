package me.machinemaker.datapacks.advancements.conditions.item;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemSource;
import me.machinemaker.datapacks.testing.sources.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class ItemConditionTest extends ConditionTest<ItemCondition> {

    ItemConditionTest() {
        super(ItemCondition.conditionType());
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testItemCondition(final ItemCondition condition) {
        final JsonObject obj = new JsonObject();
        this.add(obj, "tag", condition.tag());
        this.add(obj, "items", condition.items());
        this.add(obj, "count", condition.count());
        this.add(obj, "durability", condition.durability());
        this.add(obj, "enchantments", condition.enchantments());
        this.add(obj, "stored_enchantments", condition.storedEnchantments());
        this.add(obj, "potion", condition.potion());
        this.add(obj, "nbt", condition.nbt());
        this.testJsonConversion(condition, obj);
    }

    private static final class Provider extends RandomItemSource<ItemCondition> {

        Provider() {
            super(RandomProviders.ITEM_CONDITION);
        }
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject object = new JsonObject();
        object.add("items", JsonNull.INSTANCE);
        object.add("count", JsonNull.INSTANCE);
        this.testIsAny(object);
        this.testIsAny("{ \"enchantments\": null, \"potion\": null, \"count\": { \"min\":  null } }");
    }
}
