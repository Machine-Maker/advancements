package me.machinemaker.advancements.conditions.entity;

import com.google.gson.JsonPrimitive;
import me.machinemaker.advancements.conditions.ConditionTest;
import me.machinemaker.advancements.tests.random.RandomProviders;
import me.machinemaker.advancements.tests.sources.RandomItemSource;
import me.machinemaker.advancements.tests.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class EntityTypeConditionTest extends ConditionTest<EntityTypeCondition> {

    EntityTypeConditionTest() {
        super(EntityTypeCondition.conditionType(), false);
    }


    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testEntityTypeCondition(final EntityTypeCondition condition) {
        final String str = condition instanceof final EntityTypeCondition.Tag tag ? '#' + tag.tag().key().asString() : ((EntityTypeCondition.Type) condition).type().key().asString();
        this.testJsonConversion(condition, new JsonPrimitive(str));
    }

    private static final class Provider extends RandomItemSource<EntityTypeCondition> {

        Provider() {
            super(RandomProviders.ENTITY_TYPE_CONDITION);
        }
    }

    @Override
    protected void additionalAnyTests() {
    }
}
