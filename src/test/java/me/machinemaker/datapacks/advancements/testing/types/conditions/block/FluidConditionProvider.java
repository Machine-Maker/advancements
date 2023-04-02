package me.machinemaker.datapacks.advancements.testing.types.conditions.block;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import me.machinemaker.datapacks.advancements.conditions.block.FluidCondition;
import me.machinemaker.datapacks.advancements.tags.FluidTag;
import me.machinemaker.datapacks.advancements.testing.types.conditions.ConditionProvider;
import me.machinemaker.datapacks.advancements.testing.RandomProviders;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import oshi.util.Memoizer;

import static me.machinemaker.datapacks.utils.TestUtils.fromResourceLocation;

public class FluidConditionProvider extends ConditionProvider<FluidCondition, FluidCondition.Builder> {

    private static final Supplier<List<TagKey<Fluid>>> FLUID_TAGS = Memoizer.memoize(() -> BuiltInRegistries.FLUID.getTagNames().toList());


    public FluidConditionProvider() {
        super(Component.class, FluidCondition::builder);
        this.component(() -> RandomProviders.BLOCK_PROPERTY_CONDITION, FluidCondition.Builder::state);
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
                final NamespacedKey randomTagKey = fromResourceLocation(this.randomElement(FLUID_TAGS.get()).location());
                final Tag<org.bukkit.Fluid> randomTag = Objects.requireNonNull(Bukkit.getTag(Tag.REGISTRY_FLUIDS, randomTagKey, org.bukkit.Fluid.class));
                builder.tag(new FluidTag(randomTag));
            }
        },
    }
}
