package me.machinemaker.datapacks.advancements.testing.types.conditions.entity;

import com.google.common.base.Suppliers;
import me.machinemaker.datapacks.advancements.conditions.entity.DamageSourceCondition;
import me.machinemaker.datapacks.advancements.conditions.misc.TagCondition;
import me.machinemaker.datapacks.advancements.testing.Provider;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import me.machinemaker.datapacks.advancements.testing.types.conditions.ConditionProvider;
import me.machinemaker.datapacks.testing.extensions.ServerBootstrap;
import net.kyori.adventure.key.Key;
import net.minecraft.core.registries.Registries;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class DamageSourceConditionProvider extends ConditionProvider<DamageSourceCondition, DamageSourceCondition.Builder> {

    @SuppressWarnings("PatternValidation")
    private static final Supplier<List<Key>> DAMAGE_TYPE_TAGS = Suppliers.memoize(() -> {
        return ServerBootstrap.REGISTRY_ACCESS.registryOrThrow(Registries.DAMAGE_TYPE).getTagNames()
                .map(key -> Key.key(key.location().toString()))
                .collect(Collectors.toList());
    });

    public DamageSourceConditionProvider() {
        super(DamageSourceCondition::builder);
        this.component(DamageTagsProvider::new, DamageSourceCondition.Builder::tags);
        this.component(() -> RandomProviders.ENTITY_CONDITION, DamageSourceCondition.Builder::directEntity);
        this.component(() -> RandomProviders.ENTITY_CONDITION, DamageSourceCondition.Builder::sourceEntity);
    }

    private static class DamageTagsProvider implements Provider<List<TagCondition<Key>>> {

        @Override
        public List<TagCondition<Key>> get() {
            Collections.shuffle(DAMAGE_TYPE_TAGS.get());
            return DAMAGE_TYPE_TAGS.get().subList(0, this.integer(1, DAMAGE_TYPE_TAGS.get().size())).stream()
                    .map(key -> TagCondition.<Key>create(key, this.bool()))
                    .collect(Collectors.toList());
        }
    }
}
