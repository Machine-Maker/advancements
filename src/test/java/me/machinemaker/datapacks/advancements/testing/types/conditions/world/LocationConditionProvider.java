package me.machinemaker.datapacks.advancements.testing.types.conditions.world;

import me.machinemaker.datapacks.advancements.conditions.world.LocationCondition;
import me.machinemaker.datapacks.advancements.testing.types.conditions.ConditionProvider;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;

public class LocationConditionProvider extends ConditionProvider<LocationCondition, LocationCondition.Builder> {

    public LocationConditionProvider() {
        super(LocationCondition::builder);
        this.component(() -> RandomProviders.DOUBLE_RANGE, LocationCondition.Builder::x);
        this.component(() -> RandomProviders.DOUBLE_RANGE, LocationCondition.Builder::y);
        this.component(() -> RandomProviders.DOUBLE_RANGE, LocationCondition.Builder::z);
        this.component(() -> RandomProviders.BIOME, LocationCondition.Builder::biome);
        this.component(() -> RandomProviders.STRUCTURE, LocationCondition.Builder::structure);
        this.component(() -> RandomProviders.DIMENSION, LocationCondition.Builder::dimension);
        this.component(() -> RandomProviders.BOOLEAN, LocationCondition.Builder::smokey);
        this.component(() -> RandomProviders.LIGHT_CONDITION, LocationCondition.Builder::light);
        this.component(() -> RandomProviders.BLOCK_CONDITION, LocationCondition.Builder::block);
        this.component(() -> RandomProviders.FLUID_CONDITION, LocationCondition.Builder::fluid);
    }
}
