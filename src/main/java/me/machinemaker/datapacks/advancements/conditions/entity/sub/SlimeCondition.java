package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import me.machinemaker.datapacks.advancements.conditions.range.IntegerRange;
import org.jetbrains.annotations.Contract;

public interface SlimeCondition extends EntitySubCondition {

    @Contract(value = "_ -> new", pure = true)
    static SlimeCondition create(final IntegerRange size) {
        return new SlimeConditionImpl(size);
    }

    @Contract(pure = true)
    IntegerRange size();
}
