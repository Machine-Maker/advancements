package me.machinemaker.advancements.conditions.entity;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.conditions.ConditionTest;
import me.machinemaker.advancements.tests.mocks.DummyEnchantments;
import me.machinemaker.advancements.tests.random.RandomProviders;
import me.machinemaker.advancements.tests.sources.RandomItemSource;
import me.machinemaker.advancements.tests.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class EntityEquipmentConditionTest extends ConditionTest<EntityEquipmentCondition> {

    static {
        DummyEnchantments.setup();
    }

    EntityEquipmentConditionTest() {
        super(EntityEquipmentCondition.conditionType());
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testEntityEquipmentCondition(final EntityEquipmentCondition condition) {
        final JsonObject obj = new JsonObject();
        this.add(obj, "head", condition.head());
        this.add(obj, "chest", condition.chest());
        this.add(obj, "legs", condition.legs());
        this.add(obj, "feet", condition.feet());
        this.add(obj, "mainhand", condition.mainhand());
        this.add(obj, "offhand", condition.offhand());

    }

    private static final class Provider extends RandomItemSource<EntityEquipmentCondition> {

        Provider() {
            super(RandomProviders.ENTITY_EQUIPMENT_CONDITION);
        }
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject obj = new JsonObject();
        obj.add("chest", JsonNull.INSTANCE);
        this.testIsAny(obj);
        this.testIsAny("{ \"legs\": null, \"chest\":  null }");
    }
}
