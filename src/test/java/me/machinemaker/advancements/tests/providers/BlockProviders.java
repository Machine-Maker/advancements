package me.machinemaker.advancements.tests.providers;

import com.google.common.base.MoreObjects;
import io.papermc.paper.world.data.BlockProperty;
import io.papermc.paper.world.data.EnumBlockProperty;
import io.papermc.paper.world.data.IntegerBlockProperty;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public final class BlockProviders {

    static final Function<BlockProperty<?>, Named<BlockProperty<?>>> PROPERTY_NAMER = blockProperty -> {
        final String name = MoreObjects.toStringHelper(blockProperty)
            .add("name", blockProperty.name())
            .toString();
        return Named.of(name, blockProperty);
    };
    private static final Predicate<BlockProperty<?>> NOT_ENUM_PROPERTIES = Predicate.not(EnumBlockProperty.class::isInstance);
    static final List<BlockProperty<?>> VALID_PROPERTIES = BlockProperty.PROPERTIES.values().stream().filter(NOT_ENUM_PROPERTIES).toList();

    private BlockProviders() {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @ParameterizedTest
    @ArgumentsSource(Properties.Provider.class)
    public @interface Properties {

        class Provider extends RandomItemsProvider<BlockProperty<?>> {

            public Provider() {
                super(VALID_PROPERTIES, (prop, propSet) -> {
                    return propSet.stream().noneMatch(p -> p.name().equals(prop.name()));
                }, PROPERTY_NAMER);
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @ParameterizedTest
    @ArgumentsSource(IntProperties.Provider.class)
    public @interface IntProperties {

        class Provider extends RandomItemsProvider<IntegerBlockProperty> {

            private static final Predicate<BlockProperty<?>> IS_INT_PROPERTY = IntegerBlockProperty.class::isInstance;
            static final List<IntegerBlockProperty> INTEGER_BLOCK_PROPERTIES = VALID_PROPERTIES.stream().filter(IS_INT_PROPERTY).map(IntegerBlockProperty.class::cast).collect(Collectors.toList());

            public Provider() {
                super(INTEGER_BLOCK_PROPERTIES, (prop, propSet) -> {
                    return propSet.stream().noneMatch(p -> p.name().equals(prop.name()));
                }, PROPERTY_NAMER);
            }
        }
    }
}
