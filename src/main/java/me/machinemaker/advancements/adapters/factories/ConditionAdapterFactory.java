package me.machinemaker.advancements.adapters.factories;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.RecordComponent;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.ConditionType;
import me.machinemaker.advancements.conditions.DefaultValues;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class ConditionAdapterFactory<C extends Condition<? super C>> implements TypeAdapterFactory {

    private final ConditionType<C> type;
    private final Class<C> baseClass;
    private final Component[] components;
    private final Constructor<? extends C> constructor;
    private final C anyInstance;
    private final Map<String, TypeToken<?>> typeMap = new HashMap<>();

    private ConditionAdapterFactory(final ConditionType<C> conditionType, final Class<? extends C> implType, final Component[] components) {
        this.type = conditionType;
        this.baseClass = conditionType.baseType();
        this.anyInstance = conditionType.any();
        this.components = components;

        final Class<?>[] argTypes = new Class[this.components.length];
        for (int i = 0; i < this.components.length; i++) {
            final Component component = this.components[i];
            argTypes[i] = component.type();
            final TypeToken<?> componentType = TypeToken.get(component.genericType());
            this.typeMap.put(componentName(component), componentType);
        }
        try {
            this.constructor = implType.getDeclaredConstructor(argTypes);
            this.constructor.trySetAccessible();
        } catch (final NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static <C extends Condition<? super C>> ConditionAdapterFactory<C> record(final ConditionType<C> type, final Class<? extends C> recordClass) {
        Preconditions.checkArgument(recordClass.isRecord(), "%s is not a record", recordClass);
        final Component[] components = new Component[recordClass.getRecordComponents().length];
        for (int i = 0; i < components.length; i++) {
            final RecordComponent recordComponent = recordClass.getRecordComponents()[i];
            components[i] = new Component(recordComponent.getName(), recordComponent.getType(), recordComponent.getGenericType());
        }
        return new ConditionAdapterFactory<>(type, recordClass, components);
    }

    public static <C extends Condition<C>> ConditionAdapterFactory<C> type(final ConditionType<C> type, final Class<? extends C> implType) {
        final List<Component> components = new ArrayList<>();
        for (final Field field : implType.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                components.add(new Component(field.getName(), field.getType(), field.getGenericType()));
            }
        }
        return new ConditionAdapterFactory<>(type, implType, components.toArray(Component[]::new));
    }

    private record Component(String name, Class<?> type, Type genericType) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> @Nullable TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        if (this.baseClass.isAssignableFrom(type.getRawType())) {
            return (TypeAdapter<T>) new ConditionAdapter(gson);
        }
        return null;
    }

    public final class ConditionAdapter extends TypeAdapter<C> {

        private static final Map<Class<?>, Object> PRIMITIVE_DEFAULTS = new HashMap<>();

        static {
            PRIMITIVE_DEFAULTS.put(byte.class, (byte) 0);
            PRIMITIVE_DEFAULTS.put(int.class, 0);
            PRIMITIVE_DEFAULTS.put(long.class, 0L);
            PRIMITIVE_DEFAULTS.put(short.class, (short) 0);
            PRIMITIVE_DEFAULTS.put(double.class, 0D);
            PRIMITIVE_DEFAULTS.put(float.class, 0F);
            PRIMITIVE_DEFAULTS.put(char.class, '\0');
            PRIMITIVE_DEFAULTS.put(boolean.class, false);
        }

        private final Gson gson;

        private ConditionAdapter(final Gson gson) {
            this.gson = gson;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void write(final JsonWriter out, final @Nullable C value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            Preconditions.checkArgument(ConditionAdapterFactory.this.type.baseType().isInstance(value), value + " is not an instance of required type");
            if (value.isAny()) {
                if (ConditionAdapterFactory.this.type.anyIsNull()) {
                    out.nullValue();
                } else {
                    out.beginObject().endObject();
                }
            } else {
                final TypeToken<C> token = TypeToken.get((Class<C>) value.getClass());
                this.gson.getDelegateAdapter(ConditionAdapterFactory.this, token).write(out, value);
            }
        }

        @Override
        public C read(final JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return ConditionAdapterFactory.this.anyInstance;
            } else if (in.peek() != JsonToken.BEGIN_OBJECT) {
                throw new JsonParseException("expected %s, got %s".formatted(JsonToken.BEGIN_OBJECT, in.peek()));
            }
            final Map<String, Object> arguments = new HashMap<>();
            in.beginObject();
            while (in.hasNext()) {
                final String key = in.nextName();
                final @Nullable TypeToken<?> token = ConditionAdapterFactory.this.typeMap.get(key);
                if (token != null) {
                    arguments.put(key, this.gson.fromJson(in, token));
                } else {
                    throw new JsonParseException("Unexpected key %s found while parsing %s".formatted(key, ConditionAdapterFactory.this.baseClass.getSimpleName()));
                }
            }
            in.endObject();
            if (!ConditionAdapterFactory.this.type.anyIsNull() && arguments.isEmpty()) {
                return ConditionAdapterFactory.this.anyInstance;
            }

            final Object[] args = new Object[ConditionAdapterFactory.this.components.length];
            for (int i = 0; i < ConditionAdapterFactory.this.components.length; i++) {
                final Component component = ConditionAdapterFactory.this.components[i];
                final @Nullable TypeToken<?> type = ConditionAdapterFactory.this.typeMap.get(componentName(component));
                if (type == null) continue;
                final Class<?> rawType = type.getRawType();
                @Nullable Object value = arguments.get(componentName(component));
                if (value == null && DefaultValues.isDefaulted(type)) {
                    value = DefaultValues.getDefaultValue(type);
                } else if (value == null && rawType.isPrimitive()) {
                    value = PRIMITIVE_DEFAULTS.get(rawType);
                } else if (value == null) {
                    if (Condition.class.isAssignableFrom(rawType)) {
                        throw new IllegalStateException("All conditions should have had defaults set");
                    }
                    value = this.gson.fromJson(JsonNull.INSTANCE, type.getType());
                }
                args[i] = value;
            }

            try {
                final C instance = ConditionAdapterFactory.this.constructor.newInstance(args);
                if (instance.isAny()) {
                    return ConditionAdapterFactory.this.anyInstance;
                }
                return instance;
            } catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String componentName(final Component component) {
        return separateCamelCase(component.name(), '_').toLowerCase(Locale.ENGLISH);
    }

    /*
    From gson FieldNamingPolicy.java
     */
    private static String separateCamelCase(final String name, final char separator) {
        final StringBuilder translation = new StringBuilder();
        for (int i = 0, length = name.length(); i < length; i++) {
            final char character = name.charAt(i);
            if (Character.isUpperCase(character) && !translation.isEmpty()) {
                translation.append(separator);
            }
            translation.append(character);
        }
        return translation.toString();
    }
}
