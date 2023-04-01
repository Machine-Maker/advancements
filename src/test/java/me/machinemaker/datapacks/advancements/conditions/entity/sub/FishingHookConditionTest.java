package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemSource;
import me.machinemaker.datapacks.testing.sources.sources.Sources;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FishingHookConditionTest extends ConditionTest<EntitySubCondition> {

    FishingHookConditionTest() {
        super(EntitySubCondition.conditionType());
    }

    @Sources.Config(count = 100)
    @ArgumentsSource(Provider.class)
    void testFishingHookCondition(final FishingHookCondition condition) {
        final JsonObject obj = new JsonObject();
        obj.addProperty("is_open_water", condition.isOpenWater());
        obj.addProperty("type", "fishing_hook");
        this.testJsonConversion(condition, obj);
        this.testJsonConversion(condition, obj, EntitySubCondition.class);
    }

    private static final class Provider extends RandomItemSource<FishingHookCondition> {

        Provider() {
            super(RandomProviders.FISHING_HOOK_CONDITION);
        }
    }

    @Override
    protected void additionalAnyTests() {
        final JsonObject obj = new JsonObject();
        obj.add("is_open_water", JsonNull.INSTANCE);
        obj.addProperty("type", "fishing_hook");
        assertTrue(this.fromTree(obj) instanceof FishingHookCondition);
        assertTrue(this.fromTree("{ \"is_open_water\":  false, \"type\": \"fishing_hook\" }") instanceof FishingHookCondition);
    }
}
