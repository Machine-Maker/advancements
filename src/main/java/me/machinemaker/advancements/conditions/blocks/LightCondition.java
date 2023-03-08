package me.machinemaker.advancements.conditions.blocks;

import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.ConditionType;
import me.machinemaker.advancements.ranges.IntegerRange;
import org.jetbrains.annotations.Contract;

public interface LightCondition extends Condition<LightCondition> {

    @Contract(pure = true)
    static ConditionType<LightCondition> conditionType() {
        return LightConditionImpl.TYPE;
    }

    @Contract(value = "_ -> new", pure = true)
    static LightCondition create(final IntegerRange light) {
        return new LightConditionImpl(light);
    }

    @Contract(pure = true)
    static GsonBuilderApplicable requiredGson() {
        return LightConditionImpl.REQUIRED_GSON;
    }

    @Contract(pure = true)
    IntegerRange light();
}
