package me.machinemaker.advancements.conditions.entity;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.conditions.ConditionTest;
import me.machinemaker.advancements.tests.mocks.DummyEffects;
import me.machinemaker.advancements.tests.mocks.DummyEnchantments;
import me.machinemaker.advancements.tests.random.RandomProviders;
import me.machinemaker.advancements.tests.sources.RandomItemSource;
import me.machinemaker.advancements.tests.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class EntityConditionTest extends ConditionTest<EntityCondition> {

    static {
        DummyEffects.setup();
        DummyEnchantments.setup();
    }

    EntityConditionTest() {
        super(EntityCondition.conditionType());
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testEntityCondition(final EntityCondition condition) {
        final JsonObject obj = new JsonObject();
        this.add(obj, "type", condition.type());
        this.add(obj, "distance", condition.distance());
        this.add(obj, "location", condition.location());
        this.add(obj, "stepping_on", condition.steppingOn());
        this.add(obj, "effects", condition.effects());
        this.add(obj, "nbt", condition.nbt());
        this.add(obj, "flags", condition.flags());
        this.add(obj, "equipment", condition.equipment());
        // this.add(obj, "type_specific"); TODO
        this.add(obj, "team", condition.team());
        this.testJsonConversion(condition, obj);
    }

    private static final class Provider extends RandomItemSource<EntityCondition> {

        Provider() {
            super(RandomProviders.ENTITY_CONDITION);
        }
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject obj = new JsonObject();
        obj.add("distance", JsonNull.INSTANCE);
        obj.add("flags", JsonNull.INSTANCE);
        this.testIsAny(obj);
    }
}
