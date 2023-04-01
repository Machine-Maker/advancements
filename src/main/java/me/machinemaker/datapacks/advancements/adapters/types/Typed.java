package me.machinemaker.datapacks.advancements.adapters.types;

import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface Typed<T> {

    TypeToken<T> getType();
}
