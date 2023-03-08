package me.machinemaker.advancements.conditions.blocks;

import io.papermc.paper.world.data.BlockProperty;
import java.util.List;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.ConditionType;
import me.machinemaker.advancements.util.Buildable;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

public interface BlockPropertyCondition extends Condition<BlockPropertyCondition>, Buildable<BlockPropertyCondition, BlockPropertyCondition.Builder> {

    @Contract(pure = true)
    static ConditionType<BlockPropertyCondition> conditionType() {
        return BlockPropertyConditionImpl.TYPE;
    }

    @Contract(value = "_ -> new", pure = true)
    static BlockPropertyCondition create(final List<Matcher> matchers) {
        return new BlockPropertyConditionImpl(matchers);
    }

    @Contract(value = "-> new", pure = true)
    static Builder builder() {
        return new BlockPropertyConditionImpl.BuilderImpl();
    }

    @Contract(pure = true)
    static GsonBuilderApplicable requiredGson() {
        return BlockPropertyConditionImpl.REQUIRED_GSON;
    }

    @Contract(pure = true)
    @Unmodifiable List<Matcher> properties();

    sealed interface Matcher permits RangedMatcher, ExactMatcher {

        @Contract(pure = true)
        String name();
    }

    sealed interface ExactMatcher extends Matcher permits BlockPropertyConditionImpl.ExactMatcherImpl {

        @Contract(pure = true)
        String value();
    }

    sealed interface RangedMatcher extends Matcher permits BlockPropertyConditionImpl.RangedMatcherImpl {

        @Contract(pure = true)
        String min();

        @Contract(pure = true)
        String max();
    }

    interface Builder extends Condition.Builder<BlockPropertyCondition> {

        @Contract(pure = true)
        @UnmodifiableView List<Matcher> properties();

        @Contract(value = "_ -> this", mutates = "this")
        Builder add(final Matcher matcher);

        @Contract(value = "_, _ -> this", mutates = "this")
        <T extends Comparable<T>> Builder addExact(final BlockProperty<T> property, final T value);

        @Contract(value = "_, _, _ -> this", mutates = "this")
        <T extends Comparable<T>> Builder addRanged(final BlockProperty<T> property, final @Nullable T min, final @Nullable T max);
    }
}
