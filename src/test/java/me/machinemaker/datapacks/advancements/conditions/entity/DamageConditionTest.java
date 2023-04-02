package me.machinemaker.datapacks.advancements.conditions.entity;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemSource;
import me.machinemaker.datapacks.testing.sources.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class DamageConditionTest extends ConditionTest<DamageCondition> {

    DamageConditionTest() {
        super(DamageCondition.conditionType());
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testDamageCondition(final DamageCondition condition) {
        final JsonObject obj = new JsonObject();
        this.add(obj, "dealt", condition.dealtDamage());
        this.add(obj, "taken", condition.takenDamage());
        this.add(obj, "source_entity", condition.sourceEntity());
        this.add(obj, "blocked", condition.blocked());
        this.add(obj, "type", condition.type());
        this.testJsonConversion(condition, obj);
    }

    private static final class Provider extends RandomItemSource<DamageCondition> {

        Provider() {
            super(RandomProviders.DAMAGE_CONDITION);
        }
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject obj = new JsonObject();
        obj.add("dealt", JsonNull.INSTANCE);
        obj.add("type", JsonNull.INSTANCE);
        this.testIsAny(obj);
        this.testIsAny("{ \"blocked\": null, \"source_entity\": null }");
    }
}
