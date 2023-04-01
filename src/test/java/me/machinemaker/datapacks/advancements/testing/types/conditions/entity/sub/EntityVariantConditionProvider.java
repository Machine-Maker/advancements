package me.machinemaker.datapacks.advancements.testing.types.conditions.entity.sub;

import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.List;
import me.machinemaker.datapacks.advancements.conditions.entity.sub.EntityVariantCondition;
import me.machinemaker.datapacks.advancements.testing.Provider;

public class EntityVariantConditionProvider implements Provider<EntityVariantCondition<?>> {

    @Override
    public EntityVariantCondition<?> get() {
        final EntityVariantCondition.Type<?> type = this.randomElement(EntityVariantCondition.variantTypes());
        return this.create(type);
    }

    private <T> EntityVariantCondition<T> create(final EntityVariantCondition.Type<T> type) {
        Preconditions.checkArgument(type.type().isEnum());
        final List<T> list = Arrays.stream(type.type().getEnumConstants()).toList();
        return type.create(this.randomElement(list));
    }
}
