package me.machinemaker.advancements.conditions.entity;

import com.google.gson.annotations.SerializedName;
import me.machinemaker.advancements.adapters.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.item.ItemCondition;
import me.machinemaker.advancements.util.Buildable;
import org.jetbrains.annotations.Contract;

public record EntityEquipmentCondition(
        ItemCondition head,
        ItemCondition chest,
        ItemCondition legs,
        ItemCondition feet,
        @SerializedName("mainhand") ItemCondition mainHand,
        @SerializedName("offhand") ItemCondition offHand
) implements Condition<EntityEquipmentCondition>, Buildable<EntityEquipmentCondition, EntityEquipmentCondition.Builder> {

    public static final GsonBuilderApplicable BUILDER_APPLICABLE = ItemCondition.BUILDER_APPLICABLE;
    public static final EntityEquipmentCondition ANY = new EntityEquipmentCondition(ItemCondition.ANY, ItemCondition.ANY, ItemCondition.ANY, ItemCondition.ANY, ItemCondition.ANY, ItemCondition.ANY);

    @Contract(value = "_ -> new", pure = true)
    public static EntityEquipmentCondition forHead(ItemCondition head) {
        return new EntityEquipmentCondition(head, ItemCondition.ANY, ItemCondition.ANY, ItemCondition.ANY, ItemCondition.ANY, ItemCondition.ANY);
    }

    @Contract(value = "_ -> new", pure = true)
    public static EntityEquipmentCondition forChest(ItemCondition chest) {
        return new EntityEquipmentCondition(ItemCondition.ANY, chest, ItemCondition.ANY, ItemCondition.ANY, ItemCondition.ANY, ItemCondition.ANY);
    }

    @Contract(value = "_ -> new", pure = true)
    public static EntityEquipmentCondition forLegs(ItemCondition legs) {
        return new EntityEquipmentCondition(ItemCondition.ANY, ItemCondition.ANY, legs, ItemCondition.ANY, ItemCondition.ANY, ItemCondition.ANY);
    }

    @Contract(value = "_ -> new", pure = true)
    public static EntityEquipmentCondition forFeet(ItemCondition feet) {
        return new EntityEquipmentCondition(ItemCondition.ANY, ItemCondition.ANY, ItemCondition.ANY, feet, ItemCondition.ANY, ItemCondition.ANY);
    }

    @Contract(value = "_, _ -> new", pure = true)
    public static EntityEquipmentCondition forHands(ItemCondition mainHand, ItemCondition offHand) {
        return new EntityEquipmentCondition(ItemCondition.ANY, ItemCondition.ANY, ItemCondition.ANY, ItemCondition.ANY, mainHand, offHand);
    }

    // TODO from EntityEquipment factory method

    @Override
    public EntityEquipmentCondition any() {
        return ANY;
    }

    @Override
    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "EntityEquipmentCondition{ANY}";
        }
        return "EntityEquipmentCondition{" +
                "head=" + this.head +
                ", chest=" + this.chest +
                ", legs=" + this.legs +
                ", feet=" + this.feet +
                ", mainHand=" + this.mainHand +
                ", offHand=" + this.offHand +
                '}';
    }

    @Contract(value = "_ -> new", pure = true)
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder implements Condition.Builder<EntityEquipmentCondition> {

        private ItemCondition head = ItemCondition.ANY;
        private ItemCondition chest = ItemCondition.ANY;
        private ItemCondition legs = ItemCondition.ANY;
        private ItemCondition feet = ItemCondition.ANY;
        private ItemCondition mainHand = ItemCondition.ANY;
        private ItemCondition offHand = ItemCondition.ANY;

        private Builder(EntityEquipmentCondition condition) {
            this.head = condition.head;
            this.chest = condition.chest;
            this.legs = condition.legs;
            this.feet = condition.feet;
            this.mainHand = condition.mainHand;
            this.offHand = condition.offHand;
        }

        private Builder() {
        }

        public ItemCondition head() {
            return this.head;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder head(ItemCondition head) {
            this.head = head;
            return this;
        }

        public ItemCondition chest() {
            return this.chest;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder chest(ItemCondition chest) {
            this.chest = chest;
            return this;
        }

        public ItemCondition legs() {
            return this.legs;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder legs(ItemCondition legs) {
            this.legs = legs;
            return this;
        }

        public ItemCondition feet() {
            return this.feet;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder feet(ItemCondition feet) {
            this.feet = feet;
            return this;
        }

        public ItemCondition mainHand() {
            return this.mainHand;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder mainHand(ItemCondition mainHand) {
            this.mainHand = mainHand;
            return this;
        }

        public ItemCondition offHand() {
            return this.offHand;
        }

        @Contract(value = "_ -> this", mutates = "this")
        public Builder offHand(ItemCondition offHand) {
            this.offHand = offHand;
            return this;
        }

        @Override
        public EntityEquipmentCondition build() {
            return new EntityEquipmentCondition(this.head, this.chest, this.legs, this.feet, this.mainHand, this.offHand);
        }

    }
}
