package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import com.google.gson.TypeAdapterFactory;
import me.machinemaker.datapacks.common.adapters.factories.InterfaceImplAdapterFactory;
import me.machinemaker.datapacks.common.range.IntegerRange;

record SlimeConditionImpl(
    IntegerRange size
) implements EntitySubConditionImpl, SlimeCondition {

    static final String TYPE = "slime";
    static final TypeAdapterFactory FACTORY = InterfaceImplAdapterFactory.simple(SlimeCondition.class, SlimeConditionImpl.class);

    @Override
    public String serializedType() {
        return TYPE;
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "SlimeCondition{ANY}";
        }
        return "SlimeCondition{" +
            "size=" + this.size +
            '}';
    }
}
