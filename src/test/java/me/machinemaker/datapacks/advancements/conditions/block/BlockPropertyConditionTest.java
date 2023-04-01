package me.machinemaker.datapacks.advancements.conditions.block;

import com.google.gson.JsonObject;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemSource;
import me.machinemaker.datapacks.testing.sources.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

class BlockPropertyConditionTest extends ConditionTest<BlockPropertyCondition> {

    BlockPropertyConditionTest() {
        super(BlockPropertyCondition.conditionType(), false);
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testBlockPropertyCondition(final BlockPropertyCondition condition) {
        final JsonObject obj = new JsonObject();
        condition.properties().forEach(m -> {
            final BlockPropertyConditionImpl.MatcherImpl matcher = (BlockPropertyConditionImpl.MatcherImpl) m;
            obj.add(m.name(), matcher.toJson());
        });
        this.testJsonConversion(condition, obj);
    }

    private static final class Provider extends RandomItemSource<BlockPropertyCondition> {

        Provider() {
            super(RandomProviders.BLOCK_PROPERTY_CONDITION);
        }
    }

    @Override
    protected void additionalAnyTests() {
        // none
    }
}
