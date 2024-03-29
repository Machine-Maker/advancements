package me.machinemaker.datapacks.advancements.conditions.effect;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import me.machinemaker.datapacks.common.adapters.factories.HierarchyAdapterFactory;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.common.adapters.utils.GsonUtils;
import net.kyori.adventure.key.Key;
import org.bukkit.NamespacedKey;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.intellij.lang.annotations.Subst;

record PotionEffectsConditionImpl(
    Map<PotionEffectType, PotionEffectInstanceCondition> effects
) implements PotionEffectsCondition {

    static final PotionEffectsCondition ANY = new PotionEffectsConditionImpl(Collections.emptyMap());
    static final ConditionType<PotionEffectsCondition> TYPE = ConditionType.create(PotionEffectsCondition.class, ANY, new AdapterFactory());

    PotionEffectsConditionImpl {
        effects = Map.copyOf(effects);
    }

    @Override
    public boolean isAny() {
        return this.equals(ANY);
    }

    @Override
    public PotionEffectsCondition.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    public String toString() {
        if (this.isAny()) {
            return "PotionEffectsCondition{ANY}";
        }
        return "PotionEffectsCondition{" +
            "effects=" + this.effects +
            '}';
    }

    static final class BuilderImpl implements PotionEffectsCondition.Builder {

        private Map<PotionEffectType, PotionEffectInstanceCondition> effects = new HashMap<>();

        BuilderImpl() {
        }

        BuilderImpl(final PotionEffectsCondition condition) {
            this.effects = new HashMap<>(condition.effects());
        }

        @Override
        public Map<PotionEffectType, PotionEffectInstanceCondition> effects() {
            return this.effects;
        }

        @Override
        public PotionEffectsCondition.Builder addEffectType(final PotionEffectType type) {
            this.effects.put(type, PotionEffectInstanceCondition.conditionType().any());
            return this;
        }

        @Override
        public PotionEffectsCondition.Builder addEffectType(final PotionEffectType type, final PotionEffectInstanceCondition condition) {
            this.effects.put(type, condition);
            return this;
        }

        @Override
        public PotionEffectsCondition.Builder addEffect(final PotionEffect effect) {
            this.effects.put(effect.getType(), PotionEffectInstanceCondition.fromPotionEffect(effect));
            return this;
        }

        @Override
        public PotionEffectsCondition.Builder effects(final Map<PotionEffectType, PotionEffectInstanceCondition> effects) {
            this.effects = effects;
            return this;
        }

        @Override
        public PotionEffectsCondition build() {
            return new PotionEffectsConditionImpl(this.effects);
        }
    }

    static final class AdapterFactory extends HierarchyAdapterFactory<PotionEffectsCondition> {

        AdapterFactory() {
            super(PotionEffectsCondition.class);
        }

        @Override
        public void write(final Gson gson, final JsonWriter out, final PotionEffectsCondition value) throws IOException {
            if (value.isAny()) {
                out.nullValue();
                return;
            }
            out.beginObject();
            for (final PotionEffectType type : value.effects().keySet()) {
                final PotionEffectInstanceCondition condition = value.effects().get(type);
                GsonUtils.out(gson, type.key().asString(), condition, out);
            }
            out.endObject();
        }

        @Override
        public PotionEffectsCondition read(final Gson gson, final JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return ANY;
            }
            if (in.peek() != JsonToken.BEGIN_OBJECT) {
                throw new JsonParseException("Expected %s, got %s".formatted(JsonToken.BEGIN_OBJECT, in.peek()));
            }
            final Map<PotionEffectType, PotionEffectInstanceCondition> effects = new HashMap<>();
            in.beginObject();
            while (in.hasNext()) {
                @Subst("minecraft:speed") final String string = in.nextName();
                final Key key = Key.key(string);
                final PotionEffectType type = Objects.requireNonNull(PotionEffectType.getByKey(new NamespacedKey(key.namespace(), key.value())));
                final PotionEffectInstanceCondition condition = gson.fromJson(in, PotionEffectInstanceCondition.class);
                effects.put(type, condition);
            }
            in.endObject();
            if (effects.isEmpty()) {
                return ANY;
            }
            return new PotionEffectsConditionImpl(effects);
        }
    }
}
