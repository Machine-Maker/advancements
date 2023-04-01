package me.machinemaker.datapacks.testing.sources.sources;

import com.google.common.collect.Streams;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.support.AnnotationConsumer;
import org.junit.jupiter.params.support.AnnotationConsumerInitializer;
import org.junit.platform.commons.util.ReflectionUtils;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@ParameterizedTest
@ArgumentsSource(CompoundProvider.Provider.class)
public @interface CompoundProvider {

    Class<? extends ArgumentsProvider>[] value();

    class Provider implements ArgumentsProvider, AnnotationConsumer<CompoundProvider> {

        private final List<ArgumentsProvider> providers = new ArrayList<>();

        @Override
        public void accept(final CompoundProvider compound) {
            for (final Class<? extends ArgumentsProvider> aClass : compound.value()) {
                this.providers.add(ReflectionUtils.newInstance(aClass));
            }
        }

        @SuppressWarnings("UnstableApiUsage")
        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext context) throws Exception {
            @Nullable Stream<? extends Arguments> arguments = null;
            for (final ArgumentsProvider provider : this.providers) {
                AnnotationConsumerInitializer.initialize(context.getRequiredTestMethod(), provider);
                if (arguments == null) {
                    arguments = provider.provideArguments(context);
                } else {
                    arguments = Streams.zip(arguments, provider.provideArguments(context), (aggTo, toAdd) -> {
                        final Object[] agg = aggTo.get();
                        final Object[] add = toAdd.get();
                        final Object[] newArgs = new Object[agg.length + add.length];
                        System.arraycopy(agg, 0, newArgs, 0, agg.length);
                        System.arraycopy(add, 0, newArgs, agg.length, add.length);
                        return arguments(newArgs);
                    });
                }
            }
            return Objects.requireNonNull(arguments);
        }
    }
}
