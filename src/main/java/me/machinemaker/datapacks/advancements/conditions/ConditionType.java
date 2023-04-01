package me.machinemaker.datapacks.advancements.conditions;

import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import java.util.function.Consumer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

public interface ConditionType<C extends Condition<? super C>> {

    @ApiStatus.Internal
    static <C extends Condition<? super C>> ConditionType<C> create(final Class<C> baseType, final C any, final Object adapter) {
        return new ConditionTypeImpl<>(baseType, any, builder -> builder.registerTypeHierarchyAdapter(baseType, adapter), true);
    }

    @ApiStatus.Internal
    static <C extends Condition<? super C>> ConditionType<C> create(final Class<C> baseType, final C any, final TypeAdapterFactory factory) {
        return new ConditionTypeImpl<>(baseType, any, builder -> builder.registerTypeAdapterFactory(factory), true);
    }

    @ApiStatus.Internal
    static <C extends Condition<? super C>> ConditionType<C> create(final Class<C> baseType, final C any, final Consumer<GsonBuilder> builderConsumer) {
        return new ConditionTypeImpl<>(baseType, any, builderConsumer, true);
    }

    @ApiStatus.Internal
    static <C extends Condition<? super C>, R extends C> ConditionType<C> create(final Class<C> baseType, final C any, final Class<R> implType) {
        return create(baseType, any, implType, true);
    }

    @ApiStatus.Internal
    static <C extends Condition<? super C>, R extends C> ConditionType<C> create(final Class<C> baseType, final C any, final Class<R> implType, final Consumer<GsonBuilder> builderConsumer) {
        return create(baseType, any, implType, builderConsumer, true);
    }

    @ApiStatus.Internal
    static <C extends Condition<? super C>, R extends C> ConditionType<C> create(final Class<C> baseType, final C any, final Class<R> implType, final boolean anyIsNull) {
        return create(baseType, any, implType, ignored -> {}, anyIsNull);
    }

    @ApiStatus.Internal
    private static <C extends Condition<? super C>, R extends C> ConditionType<C> create(final Class<C> baseType, final C any, final Class<R> implType, final Consumer<GsonBuilder> builderConsumer, final boolean anyIsNull) {
        return new ConditionTypeImpl<>(baseType, any, implType, builderConsumer, anyIsNull);
    }

    @Contract(pure = true)
    C any();

    @Contract(pure = true)
    @ApiStatus.Internal
    boolean anyIsNull();

    @Contract(pure = true)
    Class<C> baseType();
}
