package me.machinemaker.advancements.conditions.entity;

import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.ConditionType;
import me.machinemaker.advancements.conditions.item.ItemCondition;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

@ApiStatus.NonExtendable
public interface EntityEquipmentCondition extends Condition.Buildable<EntityEquipmentCondition, EntityEquipmentCondition.Builder> {

    @Contract(pure = true)
    static ConditionType<EntityEquipmentCondition> conditionType() {
        return EntityEquipmentConditionImpl.TYPE;
    }

    @Contract(value = "_ -> new", pure = true)
    static EntityEquipmentCondition forHead(final ItemCondition head) {
        return builder().head(head).build();
    }

    @Contract(value = "_ -> new", pure = true)
    static EntityEquipmentCondition forChest(final ItemCondition chest) {
        return builder().chest(chest).build();
    }

    @Contract(value = "_ -> new", pure = true)
    static EntityEquipmentCondition forLegs(final ItemCondition legs) {
        return builder().legs(legs).build();
    }

    @Contract(value = "_ -> new", pure = true)
    static EntityEquipmentCondition forFeet(final ItemCondition feet) {
        return builder().feet(feet).build();
    }

    @Contract(value = "_, _ -> new", pure = true)
    static EntityEquipmentCondition forHands(final ItemCondition mainHand, final ItemCondition offHand) {
        return builder().mainhand(mainHand).offhand(offHand).build();
    }

    // TODO from EntityEquipment factory method

    @Contract(value = "-> new", pure = true)
    static Builder builder() {
        return new EntityEquipmentConditionImpl.BuilderImpl();
    }

    @Contract(pure = true)
    static GsonBuilderApplicable requiredGson() {
        return EntityEquipmentConditionImpl.REQUIRED_GSON;
    }

    @Contract(pure = true)
    ItemCondition head();

    @Contract(pure = true)
    ItemCondition chest();

    @Contract(pure = true)
    ItemCondition legs();

    @Contract(pure = true)
    ItemCondition feet();

    @Contract(pure = true)
    ItemCondition mainhand();

    @Contract(pure = true)
    ItemCondition offhand();


    @ApiStatus.NonExtendable
    interface Builder extends Condition.Builder<EntityEquipmentCondition> {

        @Contract(pure = true)
        ItemCondition head();

        @Contract(value = "_ -> this", mutates = "this")
        Builder head(ItemCondition head);

        @Contract(pure = true)
        ItemCondition chest();

        @Contract(value = "_ -> this", mutates = "this")
        Builder chest(ItemCondition chest);

        @Contract(pure = true)
        ItemCondition legs();

        @Contract(value = "_ -> this", mutates = "this")
        Builder legs(ItemCondition legs);

        @Contract(pure = true)
        ItemCondition feet();

        @Contract(value = "_ -> this", mutates = "this")
        Builder feet(ItemCondition feet);

        @Contract(pure = true)
        ItemCondition mainhand();

        @Contract(value = "_ -> this", mutates = "this")
        Builder mainhand(ItemCondition mainhand);

        @Contract(pure = true)
        ItemCondition offhand();

        @Contract(value = "_ -> this", mutates = "this")
        Builder offhand(ItemCondition offhand);
    }

}
