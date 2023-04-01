package me.machinemaker.datapacks.advancements.conditions;

import com.google.gson.GsonBuilder;
import java.util.function.Consumer;
import me.machinemaker.datapacks.advancements.adapters.factories.ConditionAdapterFactory;

final class ConditionTypeImpl<C extends Condition<? super C>> implements ConditionType<C> {

    private final Class<C> baseType;
    private final C any;
    private final Consumer<GsonBuilder> builderConsumer;
    private final boolean anyIsNull;

    ConditionTypeImpl(final Class<C> baseType, final C any, final Consumer<GsonBuilder> builderConsumer, final boolean anyIsNull) {
        this.baseType = baseType;
        this.any = any;
        this.builderConsumer = builderConsumer;
        this.anyIsNull = anyIsNull;
    }

    <R extends C> ConditionTypeImpl(final Class<C> baseType, final C any, final Class<R> implType, final Consumer<GsonBuilder> builderConsumer, final boolean anyIsNull) {
        this.baseType = baseType;
        this.any = any;
        this.builderConsumer = builder -> {
            if (implType.isRecord()) {
                builder.registerTypeAdapterFactory(ConditionAdapterFactory.record(this, implType));
            } else {
                builder.registerTypeAdapterFactory(ConditionAdapterFactory.type(this, implType));
            }
            builderConsumer.accept(builder);
        };
        this.anyIsNull = anyIsNull;
    }

    @Override
    public Class<C> baseType() {
        return this.baseType;
    }

    @Override
    public C any() {
        return this.any;
    }

    public Consumer<GsonBuilder> builderConsumer() {
        return this.builderConsumer;
    }

    @Override
    public boolean anyIsNull() {
        return this.anyIsNull;
    }
}
