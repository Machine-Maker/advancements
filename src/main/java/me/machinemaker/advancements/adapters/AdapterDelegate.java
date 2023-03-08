package me.machinemaker.advancements.adapters;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import me.machinemaker.advancements.conditions.Condition;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AdapterDelegate {

    Class<? extends Condition<?>> value();
}
