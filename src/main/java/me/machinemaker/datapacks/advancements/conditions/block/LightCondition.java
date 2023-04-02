package me.machinemaker.datapacks.advancements.conditions.block;

import me.machinemaker.datapacks.advancements.conditions.Condition;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.conditions.range.IntegerRange;
import org.jetbrains.annotations.Contract;

public interface LightCondition extends Condition {

    @Contract(pure = true)
    static ConditionType<LightCondition> conditionType() {
        return LightConditionImpl.TYPE;
    }

    @Contract(value = "_ -> new", pure = true)
    static LightCondition create(final IntegerRange light) {
        return new LightConditionImpl(light);
    }

    @Contract(pure = true)
    IntegerRange light();
}
