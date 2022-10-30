package me.machinemaker.advancements.conditions.entity;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.JsonAdapter;
import java.lang.reflect.Type;
import me.machinemaker.advancements.GsonHelper;
import me.machinemaker.advancements.adapters.Adapters;
import me.machinemaker.advancements.adapters.GsonBuilderApplicable;
import me.machinemaker.advancements.conditions.Condition;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;

@JsonAdapter(EntitySubCondition.Adapter.class)
public interface EntitySubCondition extends Condition<EntitySubCondition> {

    GsonBuilderApplicable BUILDER_APPLICABLE = Adapters.of(PlayerCondition.BUILDER_APPLICABLE, LightningBoltCondition.BUILDER_APPLICABLE);
    EntitySubCondition ANY = new EntitySubCondition() {
        @Override
        public String toString() {
            return "EntitySubCondition{ANY}";
        }
    };

    @Override
    default EntitySubCondition any() {
        return ANY;
    }

    BiMap<Class<? extends EntitySubCondition>, String> TYPES = ImmutableBiMap.<Class<? extends EntitySubCondition>, String>builder()
            .put(ANY.getClass(), "any")
            .put(PlayerCondition.class, "player")
            .put(LightningBoltCondition.class, "lightning")
            .put(FishingHookCondition.class, "fishing_hook")
            // slime
            // cat
            // frog
            .buildOrThrow();


    @ApiStatus.Internal
    class Adapter implements JsonSerializer<EntitySubCondition>, JsonDeserializer<EntitySubCondition> {

        @Override
        public EntitySubCondition deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
            if (json instanceof JsonObject object) {
                final @Nullable String typeString = GsonHelper.getString(object, "type", null);
                if (typeString == null) {
                    return ANY;
                }
                final @Nullable Class<? extends EntitySubCondition> clazzType = TYPES.inverse().get(typeString);
                if (clazzType == null) {
                    throw new JsonParseException("Unexpected subtype: " + typeString);
                }
                return context.deserialize(object, clazzType);
            } else {
                throw new JsonParseException("Expected object, got " + json.getClass());
            }
        }

        @Override
        public JsonElement serialize(final EntitySubCondition src, final Type typeOfSrc, final JsonSerializationContext context) {
            if (src == ANY) {
                return JsonNull.INSTANCE;
            } else {
                if (!TYPES.containsKey(src.getClass())) {
                    throw new JsonParseException("Expected one of " + TYPES.keySet() + " but got " + src.getClass());
                }
                final JsonObject jsonObject = context.serialize(src, src.getClass()).getAsJsonObject();
                jsonObject.addProperty("type", TYPES.get(src.getClass()));
                return jsonObject;
            }
        }
    }
}
