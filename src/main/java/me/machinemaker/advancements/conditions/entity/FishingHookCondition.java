package me.machinemaker.advancements.conditions.entity;

public record FishingHookCondition(boolean isOpenWater) implements EntitySubCondition {

    public static final FishingHookCondition ANY = new FishingHookCondition(false);

    @Override
    public EntitySubCondition any() {
        return ANY;
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
