package me.machinemaker.advancements.conditions.item;

import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import me.machinemaker.advancements.GsonHelper;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.GsonBuilderApplicable;
import me.machinemaker.advancements.ranges.IntegerRange;
import me.machinemaker.advancements.conditions.Condition;
import org.bukkit.enchantments.Enchantment;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.qual.DefaultQualifier;
import org.jetbrains.annotations.Contract;

import java.io.IOException;
import java.util.Map;

public record EnchantmentCondition(@Nullable Enchantment enchantment, IntegerRange level) implements Condition<EnchantmentCondition> {

    public static final GsonBuilderApplicable BUILDER_APPLICABLE = Adapters.ENCHANTMENT_ADAPTER;
    public static final EnchantmentCondition ANY = new EnchantmentCondition(null, IntegerRange.ANY);
    public static final EnchantmentCondition[] NONE = new EnchantmentCondition[0];

    @Contract(value = "_ -> new", pure = true)
    public static EnchantmentCondition[] from(Map<Enchantment, Integer> map) {
        EnchantmentCondition[] conditions = new EnchantmentCondition[map.size()];
        var iterator = map.entrySet().iterator();
        for (int i = 0; i < conditions.length; i++) {
            var entry = iterator.next();
            conditions[i] = new EnchantmentCondition(entry.getKey(), IntegerRange.isExactly(entry.getValue()));
        }
        return conditions;
    }

    @Override
    public EnchantmentCondition any() {
        return ANY;
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

    static class ArrayAdapter extends TypeAdapter<EnchantmentCondition[]> {

        private static final TypeToken<EnchantmentCondition[]> TOKEN = TypeToken.get(EnchantmentCondition[].class);
        private static final GsonHelper HELPER = new GsonHelper(BUILDER_APPLICABLE);

        @Override
        public void write(JsonWriter out, EnchantmentCondition @Nullable [] value) throws IOException {
            if (value == null || value.length == 0) {
                out.nullValue();
            } else {
                ArrayTypeAdapter.FACTORY.create(HELPER.gson(), TOKEN).write(out, value);
            }
        }

        @Override
        public EnchantmentCondition[] read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return NONE;
            }
            return ArrayTypeAdapter.FACTORY.create(HELPER.gson(), TOKEN).read(in);
        }
    }
}
