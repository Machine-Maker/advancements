package me.machinemaker.datapacks.advancements.testing.types.conditions.entity.sub;

import me.machinemaker.datapacks.advancements.conditions.entity.sub.EntitySubCondition;
import me.machinemaker.datapacks.advancements.testing.Provider;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;

public class EntitySubConditionProvider implements Provider<EntitySubCondition> {

    @Override
    public EntitySubCondition get() {
        return switch (this.integer(10)) {
            case 0 -> RandomProviders.FISHING_HOOK_CONDITION.get();
            case 1 -> RandomProviders.LIGHTNING_BOLT_CONDITION.get();
            case 2 -> RandomProviders.SLIME_CONDITION.get();
            case 3 -> RandomProviders.PLAYER_CONDITION.get();
            default -> RandomProviders.ENTITY_VARIANT_CONDITION.get();
        };
    }
}
