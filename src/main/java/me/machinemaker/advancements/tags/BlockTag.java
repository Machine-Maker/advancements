package me.machinemaker.advancements.tags;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;

import java.util.Set;
import java.util.function.Predicate;

/**
 * Wrapper for {@code Tag<Material>} to simplify working with
 * gson adapters.
 * <p>
 * Tag for blocks.
 */
public record BlockTag(Tag<Material> tag) implements Tag<Material>, Predicate<Material> {

    public BlockTag(Tag<Material> tag) {
        this.tag = Bukkit.getTag(Tag.REGISTRY_BLOCKS, tag.getKey(), Material.class);
    }

    /**
     * @throws IllegalArgumentException if the item is not a block
     */
    @Override
    public boolean isTagged(Material item) {
        Preconditions.checkArgument(item.isBlock(), item + " is not a block!");
        return tag.isTagged(item);
    }

    @Override
    public boolean test(Material material) {
        return material.isBlock() && this.isTagged(material);
    }

    @Override
    public Set<Material> getValues() {
        return this.tag.getValues();
    }

    @Override
    public NamespacedKey getKey() {
        return tag.getKey();
    }
}
