package me.machinemaker.advancements.junit;

import me.machinemaker.advancements.mocks.DummyServer;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstanceFactoryContext;
import org.junit.jupiter.api.extension.TestInstancePreConstructCallback;

public class ServerExtension implements TestInstancePreConstructCallback {

    @Override
    public void preConstructTestInstance(final TestInstanceFactoryContext factoryContext, final ExtensionContext context) {
        DummyServer.setup();
    }
}
