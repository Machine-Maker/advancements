package me.machinemaker.datapacks.advancements.conditions.misc;

import com.google.gson.TypeAdapterFactory;
import me.machinemaker.datapacks.advancements.adapters.factories.ConditionAdapterFactory;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;

record TagConditionImpl<T extends Keyed>(
    Key tag,
    boolean expected
) implements TagCondition<T>{

    static final TypeAdapterFactory FACTORY = ConditionAdapterFactory.record(TagCondition.class, false, null, null, TagConditionImpl.class);

}
