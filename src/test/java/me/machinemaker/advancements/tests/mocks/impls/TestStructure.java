package me.machinemaker.advancements.tests.mocks.impls;

import org.bukkit.NamespacedKey;
import org.bukkit.generator.structure.Structure;
import org.bukkit.generator.structure.StructureType;

public class TestStructure extends Structure {

    private final NamespacedKey key;

    public TestStructure(final NamespacedKey namespacedKey) {
        this.key = namespacedKey;
    }


    @Override
    public StructureType getStructureType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public NamespacedKey getKey() {
        return this.key;
    }
}
