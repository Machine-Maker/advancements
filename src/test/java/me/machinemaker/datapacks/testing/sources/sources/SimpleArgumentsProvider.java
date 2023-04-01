package me.machinemaker.datapacks.testing.sources.sources;

import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

interface SimpleArgumentsProvider extends ArgumentsProvider {

    Stream<? extends Arguments> arguments();

    @Override
    default Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
        return this.arguments();
    }
}
