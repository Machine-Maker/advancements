package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import me.machinemaker.datapacks.advancements.adapters.factories.HierarchyAdapterFactory;
import me.machinemaker.datapacks.advancements.adapters.maps.StatisticMapAdapter;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.utils.GsonUtils;
import org.checkerframework.checker.nullness.qual.Nullable;

interface EntitySubConditionImpl extends EntitySubCondition {

    String ANY_TYPE = "any";
    EntitySubCondition ANY = new EntitySubConditionImpl() {
        @Override
        public String toString() {
            return "EntitySubCondition{ANY}";
        }

        @Override
        public String serializedType() {
            return ANY_TYPE;
        }
    };
    ConditionType<EntitySubCondition> TYPE = ConditionType.create(EntitySubCondition.class, ANY, builder -> {
        builder.registerTypeAdapterFactory(LightningBoltConditionImpl.FACTORY);
        builder.registerTypeAdapterFactory(FishingHookConditionImpl.FACTORY);
        builder.registerTypeAdapterFactory(AdvancementConditionImpl.FACTORY);
        builder.registerTypeAdapterFactory(StatisticMapAdapter.FACTORY);
        builder.registerTypeAdapterFactory(PlayerConditionImpl.FACTORY);
        builder.registerTypeAdapterFactory(SlimeConditionImpl.FACTORY);
        builder.registerTypeAdapterFactory(EntityVariantConditionImpl.FACTORY);
        builder.registerTypeAdapterFactory(new AdapterFactory());
    });

    Map<String, Type> TYPES = ImmutableMap.<String, Type>builder()
        .put(ANY_TYPE, ANY.getClass())
        .put(LightningBoltConditionImpl.TYPE, LightningBoltConditionImpl.class)
        .put(FishingHookConditionImpl.TYPE, FishingHookConditionImpl.class)
        .put(PlayerConditionImpl.TYPE, PlayerConditionImpl.class)
        .put(SlimeConditionImpl.TYPE, SlimeConditionImpl.class)
        .putAll(EntityVariantConditionImpl.subConditionEntries())
        .buildOrThrow();

    @Override
    default boolean isAny() {
        return this.equals(ANY);
    }

    String serializedType();

    class AdapterFactory extends HierarchyAdapterFactory<EntitySubCondition> {

        @SuppressWarnings("unchecked")
        private static TypeToken<EntitySubCondition> createTypeToken(final EntitySubCondition value) {
            if (value instanceof final EntityVariantCondition<?> variantCondition) {
                final Type paramType = EntityVariantConditionImpl.TYPES.inverse().get(variantCondition.type());
                return (TypeToken<EntitySubCondition>) TypeToken.getParameterized(EntityVariantConditionImpl.class, paramType);
            } else {
                return (TypeToken<EntitySubCondition>) TypeToken.get(value.getClass());
            }
        }

        AdapterFactory() {
            super(EntitySubCondition.class);
        }

        @Override
        public void write(final Gson gson, final JsonWriter out, final EntitySubCondition value) throws IOException {
            final JsonElement element = gson.getDelegateAdapter(AdapterFactory.this, createTypeToken(value)).toJsonTree(value);
            if (element.isJsonNull()) {
                out.nullValue();
            } else if (element instanceof final JsonObject obj) {
                obj.addProperty("type", ((EntitySubConditionImpl) value).serializedType());
                Streams.write(obj, out);
            } else {
                throw new JsonParseException("Unexpected json " + element);
            }
        }

        @SuppressWarnings("unchecked")
        @Override
        public EntitySubCondition read(final Gson gson, final JsonReader in) throws IOException {
            final JsonElement element = Streams.parse(in);
            if (element.isJsonNull()) {
                return ANY;
            } else if (element instanceof final JsonObject obj) {
                final @Nullable String typeString = GsonUtils.getString(obj, "type", null);
                if (typeString == null || typeString.equals("any")) {
                    return ANY;
                }
                final @Nullable Type clazzType = TYPES.get(typeString);
                if (clazzType == null) {
                    throw new JsonParseException("Unexpected subtype: " + typeString);
                }
                final JsonElement type = obj.remove("type");
                final EntitySubCondition condition = gson.getDelegateAdapter(AdapterFactory.this, (TypeToken<EntitySubCondition>) TypeToken.get(clazzType)).fromJsonTree(obj);
                obj.add("type", type);
                return condition;
            }
            throw new JsonParseException("Unexpected json " + element);
        }
    }
}
