package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import com.google.gson.TypeAdapterFactory;
import me.machinemaker.datapacks.advancements.adapters.factories.ConditionAdapterFactory;

record FishingHookConditionImpl(
    boolean isOpenWater
) implements EntitySubConditionImpl, FishingHookCondition {

    static final String TYPE = "fishing_hook";
    static final TypeAdapterFactory FACTORY = ConditionAdapterFactory.record(FishingHookCondition.class, false, null, null,  FishingHookConditionImpl.class);

    @Override
    public String serializedType() {
        return TYPE;
    }
}
