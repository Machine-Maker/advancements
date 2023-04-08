package me.machinemaker.datapacks.toremove.tags;

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
 * Tag for items.
 */
public record ItemTag(Tag<Material> tag) implements Tag<Material>, Predicate<Material> {

    public ItemTag(Tag<Material> tag) {
        this.tag = Bukkit.getTag(Tag.REGISTRY_ITEMS, tag.getKey(), Material.class);
    }

    /**
     * @throws IllegalArgumentException if the item is not a block
     */
    @Override
    public boolean isTagged(Material item) {
        Preconditions.checkArgument(item.isItem(), item + " is not an item!");
        return this.tag.isTagged(item);
    }

    @Override
    public boolean test(Material material) {
        return material.isItem() && this.isTagged(material);
    }

    @Override
    public Set<Material> getValues() {
        return this.tag.getValues();
    }

    @Override
    public NamespacedKey getKey() {
        return this.tag.getKey();
    }
}
