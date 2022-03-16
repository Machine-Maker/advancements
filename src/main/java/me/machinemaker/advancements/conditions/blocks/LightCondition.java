package me.machinemaker.advancements.conditions.blocks;

import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.ranges.IntegerRange;
import org.jetbrains.annotations.NotNull;

public record LightCondition(IntegerRange light) implements Condition<LightCondition> {

    public static final LightCondition ANY = new LightCondition(IntegerRange.ANY);

    @Override
    public LightCondition any() {
        return ANY;
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "LightCondition{ANY}";
        }
        return "LightCondition{" +
                "light=" + this.light +
                '}';
    }
}
