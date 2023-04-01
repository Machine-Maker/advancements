package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

record EntityVariantConditionImpl<T>(
    T variant,
    EntityVariantCondition.Type<T> type
) implements EntitySubConditionImpl, EntityVariantCondition<T> {

    static final BiMap<java.lang.reflect.Type, EntityVariantCondition.Type<?>> TYPES = HashBiMap.create();
    private static final Map<String, java.lang.reflect.Type> ENTITY_SUB_CONDITION_ENTRIES = new HashMap<>();
    static {
        registerType(EntityVariantCondition.CAT);
        registerType(EntityVariantCondition.FROG);
        registerType(EntityVariantCondition.AXOLOTL);
        registerType(EntityVariantCondition.BOAT);
        registerType(EntityVariantCondition.FOX);
        registerType(EntityVariantCondition.MUSHROOM_COW);
        registerType(EntityVariantCondition.ART);
        registerType(EntityVariantCondition.RABBIT);
        registerType(EntityVariantCondition.HORSE);
        registerType(EntityVariantCondition.LLAMA);
        registerType(EntityVariantCondition.VILLAGER);
        registerType(EntityVariantCondition.PARROT);
        registerType(EntityVariantCondition.TROPICAL_FISH);
    }
    static final TypeAdapterFactory FACTORY = new AdapterFactory();

    private static void registerType(final EntityVariantCondition.Type<?> type) {
        TYPES.put(type.type(), type);
        ENTITY_SUB_CONDITION_ENTRIES.put(((TypeImpl<?>) type).serializedType(), TypeToken.getParameterized(EntityVariantConditionImpl.class, type.type()).getType());
    }

    static <T> EntityVariantCondition.Type<T> createType(final Class<T> type, final String name) {
        final TypeImpl<T> typeImpl = new TypeImpl<>(type, name);
        TYPES.put(type, typeImpl);
        ENTITY_SUB_CONDITION_ENTRIES.put(name, EntityVariantConditionImpl.class);
        return typeImpl;
    }

    static Map<String, java.lang.reflect.Type> subConditionEntries() {
        //noinspection ResultOfMethodCallIgnored
        EntityVariantCondition.CAT.getClass();
        return ENTITY_SUB_CONDITION_ENTRIES;
    }

    @Override
    public String serializedType() {
        return ((TypeImpl<T>) this.type()).serializedType();
    }

    record TypeImpl<T>(Class<T> type, String serializedType) implements EntityVariantCondition.Type<T> {

        public EntityVariantCondition<T> create(final T value) {
            return new EntityVariantConditionImpl<>(value, this);
        }
    }

    private static class AdapterFactory implements TypeAdapterFactory {

        @SuppressWarnings("unchecked")
        @Override
        public <T> @Nullable TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
            if (!EntityVariantCondition.class.isAssignableFrom(type.getRawType())) {
                return null;
            }
            if (!(type.getType() instanceof final ParameterizedType parameterizedType)) {
                throw new IllegalArgumentException(type + " is not a ParameterizedType");
            }
            final EntityVariantCondition.@Nullable Type<?> variantConditionType = TYPES.get(parameterizedType.getActualTypeArguments()[0]);
            if (variantConditionType == null) {
                throw new IllegalArgumentException("Unknown type: " + parameterizedType.getActualTypeArguments()[0]);
            }

            return (TypeAdapter<T>) new TypeAdapter<EntityVariantCondition<?>>() {

                @Override
                public void write(final JsonWriter out, final EntityVariantCondition<?> value) throws IOException {
                    out.beginObject()
                        .name("variant")
                        .value(EntityVariants.getSerializedName(value.variant()))
                        .endObject();
                }

                @Override
                public EntityVariantCondition<?> read(final JsonReader in) throws IOException {
                    in.beginObject();
                    if (!Objects.equals(in.nextName(), "variant")) {
                        throw new JsonParseException("Expected 'variant' key");
                    }
                    final EntityVariantCondition<?> condition = create(variantConditionType, in.nextString());
                    in.endObject();
                    return condition;
                }

                static <T> EntityVariantCondition<T> create(final EntityVariantCondition.Type<T> type, final String variant) {
                    return type.create(EntityVariants.getDeserializedValue(variant, type.type()));
                }
            };
        }
    }
}
