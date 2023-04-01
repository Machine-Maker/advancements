package me.machinemaker.datapacks.advancements.tags;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.entity.EntityType;

import java.util.Set;
import java.util.function.Predicate;

/**
 * Wrapper for {@code Tag<EntityType>} to simplify working with gson adapters.
 * <p>
 * Tag for entities.
 */
public record EntityTag(Tag<EntityType> tag) implements Tag<EntityType>, Predicate<EntityType> {

    public EntityTag(Tag<EntityType> tag) {
        this.tag = Bukkit.getTag(Tag.REGISTRY_ENTITY_TYPES, tag.getKey(), EntityType.class);
    }

    @Override
    public boolean isTagged(EntityType entityType) {
        return this.tag.isTagged(entityType);
    }

    @Override
    public boolean test(EntityType entityType) {
        return this.isTagged(entityType);
    }

    @Override
    public Set<EntityType> getValues() {
        return this.tag.getValues();
    }

    @Override
    public NamespacedKey getKey() {
        return this.tag.getKey();
    }
}
