package me.machinemaker.advancements.tests.providers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class Providers {

    private Providers() {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    public @interface Config {

        /**
         * Max size of the collection.
         * (only used for {@link RandomItemsProvider}
         *
         * @return the max collection size
         */
        int maxSize() default 1;

        /**
         * The number of elements to test.
         *
         * @return the element count
         */
        int count() default 1;
    }
}
