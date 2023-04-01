package me.machinemaker.datapacks.advancements.conditions.misc;

import me.machinemaker.datapacks.advancements.conditions.Condition;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import net.kyori.adventure.nbt.api.BinaryTagHolder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

public interface NBTCondition extends Condition<NBTCondition> {

    @Contract(pure = true)
    static ConditionType<NBTCondition> conditionType() {
        return NBTConditionImpl.TYPE;
    }

    @Contract(value = "_ -> new", pure = true)
    static NBTCondition create(final BinaryTagHolder tagHolder) {
        return new NBTConditionImpl(tagHolder);
    }

    @Contract(pure = true)
    @Nullable BinaryTagHolder tag();

}
