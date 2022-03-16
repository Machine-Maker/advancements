package me.machinemaker.advancements.conditions.world;

import me.machinemaker.advancements.ranges.DoubleRange;
import me.machinemaker.advancements.conditions.Condition;

public record DistanceCondition(DoubleRange x, DoubleRange y, DoubleRange z, DoubleRange horizontal, DoubleRange absolute) implements Condition<DistanceCondition> {

    public static final DistanceCondition ANY = new DistanceCondition(DoubleRange.ANY, DoubleRange.ANY, DoubleRange.ANY, DoubleRange.ANY, DoubleRange.ANY);

    @Override
    public DistanceCondition any() {
        return ANY;
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "DistanceCondition{ANY}";
        }
        return "DistanceCondition{" +
                "x=" + this.x +
                ", y=" + this.y +
                ", z=" + this.z +
                ", horizontal=" + this.horizontal +
                ", absolute=" + this.absolute +
                '}';
    }
}
