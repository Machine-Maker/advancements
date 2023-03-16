package me.machinemaker.advancements.conditions.entity.sub;

import com.google.gson.TypeAdapterFactory;
import me.machinemaker.advancements.adapters.builders.Builders;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.adapters.factories.ConditionAdapterFactory;
import me.machinemaker.advancements.conditions.ConditionType;

record FishingHookConditionImpl(
    boolean isOpenWater
) implements EntitySubConditionImpl, FishingHookCondition {

    static final FishingHookCondition ANY = new FishingHookConditionImpl(false);
    static final ConditionType<FishingHookCondition> INTERNAL_TYPE = ConditionType.create(FishingHookCondition.class, ANY, FishingHookConditionImpl::requiredGson);
    static final TypeAdapterFactory FACTORY = ConditionAdapterFactory.record(INTERNAL_TYPE, FishingHookConditionImpl.class);
    static final GsonBuilderApplicable REQUIRED_GSON = Builders.factory(FACTORY);

    private static GsonBuilderApplicable requiredGson() {
        return REQUIRED_GSON;
    }

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public String toString() {
        if (this.equals(ANY)) {
            return "FishingHookCondition{ANY}";
        }
        return "FishingHookCondition{" +
            "isOpenWater=" + this.isOpenWater +
            '}';
    }
}
