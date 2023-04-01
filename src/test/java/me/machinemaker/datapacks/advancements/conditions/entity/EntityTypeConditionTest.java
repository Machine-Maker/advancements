package me.machinemaker.datapacks.advancements.conditions.entity;

import com.google.gson.JsonPrimitive;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemSource;
import me.machinemaker.datapacks.testing.sources.sources.Sources;
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
