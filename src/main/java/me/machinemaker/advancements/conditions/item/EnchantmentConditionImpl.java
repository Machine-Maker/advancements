package me.machinemaker.advancements.conditions.item;

import com.google.common.base.Suppliers;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.function.Supplier;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.builders.Builders;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.adapters.factories.ConditionAdapterFactory;
import me.machinemaker.advancements.conditions.ConditionType;
import me.machinemaker.advancements.conditions.range.IntegerRange;
import org.bukkit.enchantments.Enchantment;
import org.checkerframework.checker.nullness.qual.Nullable;

record EnchantmentConditionImpl(@Nullable Enchantment enchantment, IntegerRange level) implements EnchantmentCondition {

    static final EnchantmentCondition ANY = new EnchantmentConditionImpl(null, IntegerRange.conditionType().any());
    static final EnchantmentCondition[] NONE = new EnchantmentConditionImpl[0];
    static final ConditionType<EnchantmentCondition> TYPE = ConditionType.create(EnchantmentCondition.class, ANY, EnchantmentCondition::requiredGson);
    static final TypeAdapterFactory FACTORY = ConditionAdapterFactory.record(TYPE, EnchantmentConditionImpl.class);
    static final GsonBuilderApplicable REQUIRED_GSON = Builders.collection(
        Adapters.ENCHANTMENT_ADAPTER,
        IntegerRange.requiredGson(),
        Builders.factory(FACTORY),
        Builders.typeHierarchy(EnchantmentCondition[].class, new ArrayAdapter())
    );

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public EnchantmentCondition.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "EnchantmentCondition{ANY}";
        }
        return "EnchantmentCondition{" +
            "enchantment=" + this.enchantment +
            ", level=" + this.level +
            '}';
    }

    static final class BuilderImpl implements EnchantmentCondition.Builder {

        private @Nullable Enchantment enchantment = null;
        private IntegerRange level = IntegerRange.conditionType().any();

        BuilderImpl() {
        }

        BuilderImpl(final EnchantmentCondition condition) {
            this.enchantment = condition.enchantment();
            this.level = condition.level();
        }

        @Override
        public @Nullable Enchantment enchantment() {
            return this.enchantment;
        }

        @Override
        public EnchantmentCondition.Builder enchantment(final @Nullable Enchantment enchantment) {
            this.enchantment = enchantment;
            return this;
        }

        @Override
        public IntegerRange level() {
            return this.level;
        }

        @Override
        public EnchantmentCondition.Builder level(final IntegerRange level) {
            this.level = level;
            return this;
        }

        @Override
        public EnchantmentCondition build() {
            return new EnchantmentConditionImpl(this.enchantment, this.level);
        }
    }

    static class ArrayAdapter extends TypeAdapter<EnchantmentCondition[]> {

        private static final Supplier<TypeAdapter<EnchantmentCondition[]>> ADAPTER = Suppliers.memoize(() -> {
            final GsonBuilder builder = new GsonBuilder();
            REQUIRED_GSON.applyTo(builder);
            return ArrayTypeAdapter.FACTORY.create(builder.create(), TypeToken.get(EnchantmentCondition[].class));
        });

        @Override
        public void write(final JsonWriter out, final EnchantmentCondition @Nullable [] value) throws IOException {
            if (value == null || value.length == 0) {
                out.nullValue();
            } else {
                ADAPTER.get().write(out, value);
            }
        }

        @Override
        public EnchantmentCondition[] read(final JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return NONE;
            }
            return ADAPTER.get().read(in);
        }
    }
}
