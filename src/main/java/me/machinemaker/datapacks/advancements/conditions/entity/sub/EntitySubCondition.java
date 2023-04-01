package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import me.machinemaker.datapacks.advancements.conditions.Condition;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.NonExtendable
public interface EntitySubCondition extends Condition<EntitySubCondition> {

    @Contract(pure = true)
    static ConditionType<EntitySubCondition> conditionType() {
        return EntitySubConditionImpl.TYPE;
    }
}
