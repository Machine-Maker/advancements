package me.machinemaker.advancements.conditions.entity.sub;

import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.ConditionType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.NonExtendable
public interface EntitySubCondition extends Condition<EntitySubCondition> {

    @Contract(pure = true)
    static ConditionType<EntitySubCondition> conditionType() {
        return EntitySubConditionImpl.TYPE;
    }

    @Contract(pure = true)
    static GsonBuilderApplicable requiredGson() {
        return EntitySubConditionImpl.REQUIRED_GSON;
    }
}
