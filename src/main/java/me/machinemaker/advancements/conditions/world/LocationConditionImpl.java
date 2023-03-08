package me.machinemaker.advancements.conditions.world;

import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.builders.Builders;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.ConditionType;
import me.machinemaker.advancements.conditions.blocks.BlockCondition;
import me.machinemaker.advancements.conditions.blocks.FluidCondition;
import me.machinemaker.advancements.conditions.blocks.LightCondition;
import me.machinemaker.advancements.ranges.DoubleRange;
import net.kyori.adventure.key.Key;
import org.bukkit.block.Biome;
import org.bukkit.generator.structure.Structure;
import org.checkerframework.checker.nullness.qual.Nullable;

record LocationConditionImpl(
    DoubleRange x,
    DoubleRange y,
    DoubleRange z,
    @Nullable Biome biome,
    @Nullable Structure structure,
    @Nullable Key dimension,
    @Nullable Boolean smokey,
    LightCondition light,
    BlockCondition block,
    FluidCondition fluid
) implements LocationCondition {

    static final LocationCondition ANY = new LocationConditionImpl(DoubleRange.conditionType().any(), DoubleRange.conditionType().any(), DoubleRange.conditionType().any(), null, null, null, null, LightCondition.conditionType().any(), BlockCondition.conditionType().any(), FluidCondition.conditionType().any());
    static final ConditionType<LocationCondition> TYPE = ConditionType.create(LocationCondition.class, ANY, );
    public static final GsonBuilderApplicable BUILDER_APPLICABLE = Builders.collection(
        DoubleRange.requiredGson(),
        Adapters.BIOME_ADAPTER,
        Adapters.STRUCTURE_ADAPTER,
        Adapters.NAMESPACED_KEY_ADAPTER,
        BlockCondition.requiredGson(),
        FluidCondition.requiredGson()
    );


    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "LocationCondition{ANY}";
        }
        return "LocationCondition{" +
            "x=" + this.x +
            ", y=" + this.y +
            ", z=" + this.z +
            ", biome=" + this.biome +
            ", structure=" + this.structure +
            ", dimension=" + this.dimension +
            ", smokey=" + this.smokey +
            ", light=" + this.light +
            ", block=" + this.block +
            ", fluid=" + this.fluid +
            '}';
    }
}
