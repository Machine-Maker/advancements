package me.machinemaker.advancements.tests.sources;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.params.ParameterizedTest;

public final class Sources {

    private Sources() {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @ParameterizedTest
    public @interface Config {

        /**
         * Max size of the collection.
         * (only used for {@link RandomItemsSource}
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
