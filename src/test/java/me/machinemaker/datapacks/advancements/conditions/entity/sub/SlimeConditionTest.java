package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import com.google.gson.JsonObject;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemSource;
import me.machinemaker.datapacks.testing.sources.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class SlimeConditionTest extends ConditionTest<EntitySubCondition> {

    SlimeConditionTest() {
        super(EntitySubCondition.conditionType(), false);
    }

    @Sources.Config(count = 100)
    @ArgumentsSource(Provider.class)
    void testSlimeCondition(final SlimeCondition condition) {
        final JsonObject obj = new JsonObject();
        this.add(obj, "size", condition.size());
        obj.addProperty("type", "slime");
        this.testJsonConversion(condition, obj);
        this.testJsonConversion(condition, obj, EntitySubCondition.class);
    }

    private static final class Provider extends RandomItemSource<SlimeCondition> {
        Provider() {
            super(RandomProviders.SLIME_CONDITION);
        }
    }

    @Override
    protected void additionalAnyTests() {
        //
    }
}
