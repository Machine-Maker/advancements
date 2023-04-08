package me.machinemaker.datapacks.advancements.conditions.item;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.common.range.IntegerRange;
import org.bukkit.enchantments.Enchantment;
import org.checkerframework.checker.nullness.qual.Nullable;

record EnchantmentConditionImpl(@Nullable Enchantment enchantment, IntegerRange level) implements EnchantmentCondition {

    static final EnchantmentCondition ANY = new EnchantmentConditionImpl(null, IntegerRange.conditionType().any());
    static final EnchantmentCondition[] NONE = new EnchantmentConditionImpl[0];
    static final ConditionType<EnchantmentCondition> TYPE = ConditionType.create(EnchantmentCondition.class, ANY, EnchantmentConditionImpl.class, builder -> {
        builder.registerTypeAdapterFactory(new ArrayAdapterFactory());
    });

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

    static class ArrayAdapterFactory implements TypeAdapterFactory {

        @SuppressWarnings("unchecked")
        @Override
        public <T> @Nullable TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
            if (!EnchantmentCondition[].class.isAssignableFrom(type.getRawType())) {
                return null;
            }

            final TypeAdapter<EnchantmentCondition[]> adapterDelegate = ArrayTypeAdapter.FACTORY.create(gson, TypeToken.get(EnchantmentCondition[].class));

            return (TypeAdapter<T>) new TypeAdapter<EnchantmentCondition[]>() {
                @Override
                public void write(final JsonWriter out, final EnchantmentCondition @Nullable [] value) throws IOException {
                    if (value == null || value.length == 0) {
                        out.nullValue();
                    } else {
                        adapterDelegate.write(out, value);
                    }
                }

                @Override
                public EnchantmentCondition[] read(final JsonReader in) throws IOException {
                    if (in.peek() == JsonToken.NULL) {
                        in.nextNull();
                        return NONE;
                    }
                    return adapterDelegate.read(in);
                }
            };
        }
    }
}
