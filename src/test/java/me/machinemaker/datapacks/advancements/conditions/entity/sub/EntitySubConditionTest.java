package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import java.util.Set;
import me.machinemaker.datapacks.advancements.conditions.ConditionTest;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntitySubConditionTest extends ConditionTest<EntitySubCondition> {

    EntitySubConditionTest() {
        super(EntitySubCondition.conditionType(), false);
    }

    @Test
    void checkAllSubConditionsPresent() {
        final Set<String> apiTypes = EntitySubConditionImpl.TYPES.keySet();
        final Set<String> nmsTypes = EntitySubPredicate.Types.TYPES.keySet();
        assertEquals(apiTypes.size(), nmsTypes.size());
        assertTrue(apiTypes.containsAll(nmsTypes) && nmsTypes.containsAll(apiTypes));
    }

    @Override
    protected void additionalAnyTests() {
        //
    }
}
