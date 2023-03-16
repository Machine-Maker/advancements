package me.machinemaker.advancements.conditions.entity;

import com.google.gson.TypeAdapterFactory;
import me.machinemaker.advancements.adapters.builders.Builders;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.adapters.factories.ConditionAdapterFactory;
import me.machinemaker.advancements.conditions.ConditionType;
import me.machinemaker.advancements.conditions.item.ItemCondition;

public record EntityEquipmentConditionImpl(
    ItemCondition head,
    ItemCondition chest,
    ItemCondition legs,
    ItemCondition feet,
    ItemCondition mainhand,
    ItemCondition offhand
) implements EntityEquipmentCondition {

    static final EntityEquipmentCondition ANY = EntityEquipmentCondition.builder().build();
    static final ConditionType<EntityEquipmentCondition> TYPE = ConditionType.create(EntityEquipmentCondition.class, ANY, EntityEquipmentCondition::requiredGson);
    static final TypeAdapterFactory FACTORY = ConditionAdapterFactory.record(TYPE, EntityEquipmentConditionImpl.class);
    static final GsonBuilderApplicable REQUIRED_GSON = Builders.collection(ItemCondition.requiredGson(), Builders.factory(FACTORY));

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public EntityEquipmentCondition.Builder toBuilder() {
        return new BuilderImpl(this);
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
            ", mainhand=" + this.mainhand +
            ", offhand=" + this.offhand +
            '}';
    }

    static final class BuilderImpl implements EntityEquipmentCondition.Builder {

        private ItemCondition head = ItemCondition.conditionType().any();
        private ItemCondition chest = ItemCondition.conditionType().any();
        private ItemCondition legs = ItemCondition.conditionType().any();
        private ItemCondition feet = ItemCondition.conditionType().any();
        private ItemCondition mainHand = ItemCondition.conditionType().any();
        private ItemCondition offHand = ItemCondition.conditionType().any();

        private BuilderImpl(final EntityEquipmentCondition condition) {
            this.head = condition.head();
            this.chest = condition.chest();
            this.legs = condition.legs();
            this.feet = condition.feet();
            this.mainHand = condition.mainhand();
            this.offHand = condition.offhand();
        }

        BuilderImpl() {
        }

        @Override
        public ItemCondition head() {
            return this.head;
        }

        @Override
        public EntityEquipmentCondition.Builder head(final ItemCondition head) {
            this.head = head;
            return this;
        }

        @Override
        public ItemCondition chest() {
            return this.chest;
        }

        @Override
        public EntityEquipmentCondition.Builder chest(final ItemCondition chest) {
            this.chest = chest;
            return this;
        }

        @Override
        public ItemCondition legs() {
            return this.legs;
        }

        @Override
        public EntityEquipmentCondition.Builder legs(final ItemCondition legs) {
            this.legs = legs;
            return this;
        }

        @Override
        public ItemCondition feet() {
            return this.feet;
        }

        @Override
        public EntityEquipmentCondition.Builder feet(final ItemCondition feet) {
            this.feet = feet;
            return this;
        }

        @Override
        public ItemCondition mainhand() {
            return this.mainHand;
        }

        @Override
        public EntityEquipmentCondition.Builder mainhand(final ItemCondition mainHand) {
            this.mainHand = mainHand;
            return this;
        }

        @Override
        public ItemCondition offhand() {
            return this.offHand;
        }

        @Override
        public EntityEquipmentCondition.Builder offhand(final ItemCondition offHand) {
            this.offHand = offHand;
            return this;
        }

        @Override
        public EntityEquipmentCondition build() {
            return new EntityEquipmentConditionImpl(this.head, this.chest, this.legs, this.feet, this.mainHand, this.offHand);
        }

    }
}
