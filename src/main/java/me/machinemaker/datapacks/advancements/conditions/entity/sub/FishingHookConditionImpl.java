package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import com.google.gson.TypeAdapterFactory;
import me.machinemaker.datapacks.common.adapters.factories.InterfaceImplAdapterFactory;

record FishingHookConditionImpl(
    boolean isOpenWater
) implements EntitySubConditionImpl, FishingHookCondition {

    static final String TYPE = "fishing_hook";
    static final TypeAdapterFactory FACTORY = InterfaceImplAdapterFactory.simple(FishingHookCondition.class, FishingHookConditionImpl.class);

    @Override
    public String serializedType() {
        return TYPE;
    }
}
