package me.machinemaker.advancements.tags;

import org.bukkit.Bukkit;
import org.bukkit.Fluid;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;

import java.util.Set;
import java.util.function.Predicate;

/**
 * Wrapper for {@code Tag<Fluid>} to simplify working with
 * gson adapters.
 * <p>
 * Tag for fluids.
 */
public record FluidTag(Tag<Fluid> tag) implements Tag<Fluid>, Predicate<Fluid> {

    public FluidTag(Tag<Fluid> tag) {
        this.tag = Bukkit.getTag(Tag.REGISTRY_FLUIDS, tag.getKey(), Fluid.class);
    }

    @Override
    public boolean isTagged(Fluid fluid) {
        return this.tag.isTagged(fluid);
    }

    @Override
    public boolean test(Fluid fluid) {
        return this.isTagged(fluid);
    }

    @Override
    public Set<Fluid> getValues() {
        return this.tag.getValues();
    }

    @Override
    public NamespacedKey getKey() {
        return this.tag.getKey();
    }
}
