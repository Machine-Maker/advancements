package me.machinemaker.advancements.conditions.entity;

import me.machinemaker.advancements.conditions.Condition;

public record FishingHookCondition(boolean isOpenWater) implements Condition<FishingHookCondition> {

    public static final FishingHookCondition ANY = new FishingHookCondition(false);

    @Override
    public FishingHookCondition any() {
        return ANY;
    }

    @Override
    public String toString() {
        return "FishingHookCondition{" +
                "isOpenWater=" + this.isOpenWater +
                '}';
    }
}
