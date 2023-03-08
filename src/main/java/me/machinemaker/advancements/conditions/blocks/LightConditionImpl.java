package me.machinemaker.advancements.conditions.blocks;

import com.google.gson.TypeAdapterFactory;
import me.machinemaker.advancements.adapters.builders.Builders;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.adapters.factories.ConditionAdapterFactory;
import me.machinemaker.advancements.conditions.ConditionType;
import me.machinemaker.advancements.ranges.IntegerRange;

record LightConditionImpl(IntegerRange light) implements LightCondition {

    static final LightCondition ANY = new LightConditionImpl(IntegerRange.conditionType().any());
    static final ConditionType<LightCondition> TYPE = ConditionType.create(LightCondition.class, ANY, LightCondition::requiredGson);
    static final TypeAdapterFactory FACTORY = new ConditionAdapterFactory<>(TYPE, LightConditionImpl.class);
    static final GsonBuilderApplicable REQUIRED_GSON = Builders.collection(
        Builders.factory(FACTORY),
        IntegerRange.requiredGson()
    );

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
