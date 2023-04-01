package me.machinemaker.datapacks.advancements.conditions.entity;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemSource;
import me.machinemaker.datapacks.testing.sources.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class EntityEquipmentConditionTest extends ConditionTest<EntityEquipmentCondition> {

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
