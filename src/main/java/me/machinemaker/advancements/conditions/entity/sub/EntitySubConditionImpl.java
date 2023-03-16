package me.machinemaker.advancements.conditions.entity.sub;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import me.machinemaker.advancements.adapters.builders.Builders;
import me.machinemaker.advancements.adapters.builders.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.ConditionType;
import me.machinemaker.advancements.util.GsonUtils;
import org.checkerframework.checker.nullness.qual.Nullable;

interface EntitySubConditionImpl extends EntitySubCondition {

    EntitySubCondition ANY = new EntitySubConditionImpl() {
        @Override
        public String toString() {
            return "EntitySubCondition{ANY}";
        }
    };
    ConditionType<EntitySubCondition> TYPE = ConditionType.create(EntitySubCondition.class, ANY, EntitySubCondition::requiredGson);
    GsonBuilderApplicable REQUIRED_GSON = Builders.collection(
        // TODO any
        LightningBoltConditionImpl.REQUIRED_GSON,
        FishingHookConditionImpl.REQUIRED_GSON,
        PlayerConditionImpl.REQUIRED_GSON,
        Builders.factory(new AdapterFactory())
        // this adapter
    );

    BiMap<Class<? extends EntitySubCondition>, String> TYPES = ImmutableBiMap.<Class<? extends EntitySubCondition>, String>builder()
        .put(ANY.getClass(), "any")
        .put(LightningBoltConditionImpl.class, "lightning")
        .put(FishingHookConditionImpl.class, "fishing_hook")
        // .put(PlayerCondition.class, "player")
        // slime
        // cat
        // frog
        .buildOrThrow();

    class AdapterFactory implements TypeAdapterFactory {

        @Override
        public <T> @Nullable TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
            if (!EntitySubCondition.class.isAssignableFrom(type.getRawType())) {
                return null;
            }

            return new TypeAdapter<>() {
                @SuppressWarnings("unchecked")
                @Override
                public void write(final JsonWriter out, final T value) throws IOException {
                    gson.getDelegateAdapter(AdapterFactory.this, (TypeToken<T>) TypeToken.get(value.getClass())).write(out, value);
                }

                @SuppressWarnings("unchecked")
                @Override
                public T read(final JsonReader in) {
                    final JsonElement element = Streams.parse(in);
                    if (element.isJsonNull()) {
                        return (T) ANY;
                    } else if (element instanceof final JsonObject obj) {
                        final @Nullable String typeString = GsonUtils.getString(obj, "type", null);
                        if (typeString == null || typeString.equals("any")) {
                            return (T) ANY;
                        }
                        final @Nullable Class<? extends EntitySubCondition> clazzType = TYPES.inverse().get(typeString);
                        if (clazzType == null) {
                            throw new JsonParseException("Unexpected subtype: " + typeString);
                        }
                        obj.remove("type");
                        return gson.getDelegateAdapter(AdapterFactory.this, (TypeToken<T>) TypeToken.get(clazzType)).fromJsonTree(obj);
                    }
                    throw new JsonParseException("Unexpected json " + element);
                }
            };
        }
    }
}
