package me.machinemaker.advancements.adapters.util;

import me.machinemaker.advancements.adapters.factories.RecordTypeAdapterFactory;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate records with this to skip the {@link RecordTypeAdapterFactory}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface IgnoreRecordTypeAdapter {
}
