package me.machinemaker.advancements.conditions.entity;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.conditions.ConditionTest;
import me.machinemaker.advancements.tests.random.RandomProviders;
import me.machinemaker.advancements.tests.sources.RandomItemSource;
import me.machinemaker.advancements.tests.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class EntityFlagsConditionTest extends ConditionTest<EntityFlagsCondition> {

    EntityFlagsConditionTest() {
        super(EntityFlagsCondition.conditionType());
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testEntityFlagsCondition(final EntityFlagsCondition condition) {
        final JsonObject obj = new JsonObject();
        this.add(obj, "is_on_fire", condition.isOnFire());
        this.add(obj, "is_crouching", condition.isCrouching());
        this.add(obj, "is_sprinting", condition.isSprinting());
        this.add(obj, "is_swimming", condition.isSwimming());
        this.add(obj, "is_baby", condition.isBaby());
        this.testJsonConversion(condition, obj);
    }

    private static final class Provider extends RandomItemSource<EntityFlagsCondition> {

        Provider() {
            super(RandomProviders.ENTITY_FLAGS_CONDITION);
        }
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject obj = new JsonObject();
        obj.add("is_on_fire", JsonNull.INSTANCE);
        obj.add("is_baby", JsonNull.INSTANCE);
        this.testIsAny(obj);
        this.testIsAny("{\"is_crouching\": null }");
        this.testIsAny("{\"is_swimming\": null, \"is_on_fire\": null }");
    }
}
