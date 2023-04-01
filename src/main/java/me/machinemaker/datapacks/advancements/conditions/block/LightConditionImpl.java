package me.machinemaker.datapacks.advancements.conditions.block;

import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.conditions.range.IntegerRange;

record LightConditionImpl(IntegerRange light) implements LightCondition {

    static final LightCondition ANY = new LightConditionImpl(IntegerRange.conditionType().any());
    static final ConditionType<LightCondition> TYPE = ConditionType.create(LightCondition.class, ANY, LightConditionImpl.class);

    @Override
    public boolean isAny() {
        return this.equals(ANY);
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
