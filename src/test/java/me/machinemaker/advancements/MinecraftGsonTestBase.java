package me.machinemaker.advancements;

import me.machinemaker.advancements.mocks.DummyServer;

public abstract class MinecraftGsonTestBase extends GsonTestBase {

    static {
        DummyServer.setup();
    }
}
