package me.machinemaker.advancements.tests.random.types.conditions.block;

import me.machinemaker.advancements.conditions.block.FluidCondition;
import me.machinemaker.advancements.tags.FluidTag;
import me.machinemaker.advancements.tests.random.RandomProviders;
import me.machinemaker.advancements.tests.random.types.conditions.ConditionProvider;
import org.bukkit.Tag;

public class FluidConditionProvider extends ConditionProvider<FluidCondition, FluidCondition.Builder> {

    public FluidConditionProvider() {
        super(Component.class, FluidCondition::builder);
    }

    private enum Component implements ConditionProvider.Component<FluidCondition.Builder> {
        FLUID {
            @Override
            public void apply(final FluidCondition.Builder builder) {
                builder.fluid(RandomProviders.FLUID.get());
            }
        },
        FLUID_TAG {
            @Override
            public void apply(final FluidCondition.Builder builder) {
                // TODO better random tags
                builder.tag(new FluidTag(Tag.FLUIDS_LAVA));
            }
        },
        BLOCK_PROPERTY_CONDITION {
            @Override
            public void apply(final FluidCondition.Builder builder) {
                builder.state(RandomProviders.BLOCK_PROPERTY_CONDITION.get());
            }
        }
    }
}
