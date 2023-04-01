package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import com.google.gson.JsonObject;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.testing.sources.sources.RandomItemSource;
import me.machinemaker.datapacks.testing.sources.sources.Sources;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class EntityVariantConditionTest extends ConditionTest<EntitySubCondition> {

    EntityVariantConditionTest() {
        super(EntitySubCondition.conditionType(), false);
    }

    @Sources.Config(count = 1000)
    @ArgumentsSource(Provider.class)
    void testEntityVariantConditionTest(final EntityVariantCondition<?> condition) {
        final JsonObject obj = new JsonObject();
        obj.addProperty("variant", EntityVariants.getSerializedName(condition.variant()));
        obj.addProperty("type", ((EntityVariantConditionImpl.TypeImpl<?>) condition.type()).serializedType());
        this.testJsonConversion(condition, obj);
        this.testJsonConversion(condition, obj, EntitySubCondition.class);
    }

    private static final class Provider extends RandomItemSource<EntityVariantCondition<?>> {
        Provider() {
            super(RandomProviders.ENTITY_VARIANT_CONDITION);
        }
    }

    @Override
    protected void additionalAnyTests() {
        //
    }
}
