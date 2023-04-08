package me.machinemaker.datapacks.advancements.conditions.misc;

import com.google.gson.TypeAdapterFactory;
import me.machinemaker.datapacks.common.adapters.factories.InterfaceImplAdapterFactory;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;

record TagConditionImpl<T extends Keyed>(
    Key tag,
    boolean expected
) implements TagCondition<T>{

    static final TypeAdapterFactory FACTORY = InterfaceImplAdapterFactory.simple(TagCondition.class, TagConditionImpl.class);

}
