package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemSource;
import me.machinemaker.datapacks.testing.sources.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LightningBoltConditionTest extends ConditionTest<EntitySubCondition> {

    LightningBoltConditionTest() {
        super(EntitySubCondition.conditionType());
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testLightningBoltCondition(final LightningBoltCondition condition) {
        final JsonObject obj = new JsonObject();
        this.add(obj, "blocks_set_on_fire", condition.blocksSetOnFire());
        this.add(obj, "entity_struck", condition.entityStruck());
        obj.addProperty("type", "lightning");
        this.testJsonConversion(condition, obj);
        this.testJsonConversion(condition, obj, EntitySubCondition.class);
    }


    private static final class Provider extends RandomItemSource<LightningBoltCondition> {

        Provider() {
            super(RandomProviders.LIGHTNING_BOLT_CONDITION);
        }
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject obj = new JsonObject();
        obj.add("blocks_set_on_fire", JsonNull.INSTANCE);
        obj.addProperty("type", "lightning");
        assertTrue(this.fromTree(obj) instanceof LightningBoltCondition);
        assertTrue(this.fromTree("{ \"entity_struck\": null, \"type\": \"lightning\" }") instanceof LightningBoltCondition);
    }
}
