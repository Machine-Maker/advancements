package me.machinemaker.datapacks.testing.extensions;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.craftbukkit.v1_19_R2.tag.CraftBlockTag;
import org.bukkit.craftbukkit.v1_19_R2.tag.CraftEntityTag;
import org.bukkit.craftbukkit.v1_19_R2.tag.CraftFluidTag;
import org.bukkit.craftbukkit.v1_19_R2.tag.CraftItemTag;
import org.bukkit.craftbukkit.v1_19_R2.util.CraftNamespacedKey;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.mockito.invocation.InvocationOnMock;

final class Tags {

    private Tags() {
    }

    static @Nullable Tag<?> getTag(final InvocationOnMock invocation, final Map<String, Map<ResourceLocation, Tag<?>>> tagMap) {
        final String registry = invocation.getArgument(0);
        final ResourceLocation key = CraftNamespacedKey.toMinecraft(invocation.getArgument(1));
        final Class<?> clazz = invocation.getArgument(2);
        return tagMap.get(registry).computeIfAbsent(key, ignored -> createTag(registry, key, clazz));
    }

    private static @Nullable Tag<?> createTag(final String registry, final ResourceLocation key, final Class<?> clazz) {
        switch (registry) {
            case Tag.REGISTRY_BLOCKS -> {
                Preconditions.checkArgument(clazz == Material.class, "Block namespace must have material type");
                final TagKey<Block> blockTagKey = TagKey.create(Registries.BLOCK, key);
                if (BuiltInRegistries.BLOCK.getTag(blockTagKey).isPresent()) {
                    return new CraftBlockTag(BuiltInRegistries.BLOCK, blockTagKey);
                }
            }
            case Tag.REGISTRY_ITEMS -> {
                Preconditions.checkArgument(clazz == Material.class, "Item namespace must have material type");
                final TagKey<Item> itemTagKey = TagKey.create(Registries.ITEM, key);
                if (BuiltInRegistries.ITEM.getTag(itemTagKey).isPresent()) {
                    return new CraftItemTag(BuiltInRegistries.ITEM, itemTagKey);
                }
            }
            case Tag.REGISTRY_FLUIDS -> {
                Preconditions.checkArgument(clazz == org.bukkit.Fluid.class, "Fluid namespace must have fluid type");
                final TagKey<Fluid> fluidTagKey = TagKey.create(Registries.FLUID, key);
                if (BuiltInRegistries.FLUID.getTag(fluidTagKey).isPresent()) {
                    return new CraftFluidTag(BuiltInRegistries.FLUID, fluidTagKey);
                }
            }
            case Tag.REGISTRY_ENTITY_TYPES -> {
                Preconditions.checkArgument(clazz == org.bukkit.entity.EntityType.class, "Entity type namespace must have entity type");
                final TagKey<EntityType<?>> entityTagKey = TagKey.create(Registries.ENTITY_TYPE, key);
                if (BuiltInRegistries.ENTITY_TYPE.getTag(entityTagKey).isPresent()) {
                    return new CraftEntityTag(BuiltInRegistries.ENTITY_TYPE, entityTagKey);
                }
            }
            // Paper start
            case Tag.REGISTRY_GAME_EVENTS -> {
                Preconditions.checkArgument(clazz == org.bukkit.GameEvent.class, "Game Event namespace must have GameEvent type");
                final TagKey<GameEvent> gameEventTagKey = TagKey.create(net.minecraft.core.registries.Registries.GAME_EVENT, key);
                if (net.minecraft.core.registries.BuiltInRegistries.GAME_EVENT.getTag(gameEventTagKey).isPresent()) {
                    return new io.papermc.paper.CraftGameEventTag(BuiltInRegistries.GAME_EVENT, gameEventTagKey);
                }
            }
            // Paper end
            default -> throw new IllegalArgumentException();
        }
        return null;
    }

    static Map<String, Map<ResourceLocation, Tag<?>>> createTagMap() {
        final Map<String, Map<ResourceLocation, Tag<?>>> tagMap = new HashMap<>();
        tagMap.put(Tag.REGISTRY_BLOCKS, new HashMap<>());
        tagMap.put(Tag.REGISTRY_ITEMS, new HashMap<>());
        tagMap.put(Tag.REGISTRY_FLUIDS, new HashMap<>());
        tagMap.put(Tag.REGISTRY_ENTITY_TYPES, new HashMap<>());
        tagMap.put(Tag.REGISTRY_GAME_EVENTS, new HashMap<>());
        return tagMap;
    }
}
