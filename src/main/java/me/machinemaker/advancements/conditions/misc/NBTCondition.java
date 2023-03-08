package me.machinemaker.advancements.conditions.misc;

import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.ConditionType;
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
    static GsonBuilderApplicable requiredGson() {
        return NBTConditionImpl.REQUIRED_GSON;
    }

    @Contract(pure = true)
    @Nullable BinaryTagHolder tag();

}
