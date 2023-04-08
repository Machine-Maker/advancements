package me.machinemaker.datapacks.common.adapters.factories;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;
import me.machinemaker.datapacks.advancements.conditions.Condition;
import me.machinemaker.datapacks.advancements.conditions.ConditionType;
import me.machinemaker.datapacks.advancements.conditions.Conditions;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;

/**
 * Adapter factory for serializing/deserializing interface + impl types
 * @param <C>
 */
@ApiStatus.Internal
public final class InterfaceImplAdapterFactory<C> implements TypeAdapterFactory {

    private final Class<C> baseClass;
    private final boolean anyIsNull;
    private final Predicate<C> anyCheck;
    private final @Nullable C anyInstance;
    private final Component[] components;
    private final Constructor<? extends C> constructor;
    private final Map<String, TypeToken<?>> typeMap = new HashMap<>();

    private InterfaceImplAdapterFactory(final Class<C> baseType, final boolean anyIsNull, final Predicate<C> anyCheck, final @Nullable C anyInstance, final Class<? extends C> implType) {
        this.baseClass = baseType;
        this.anyIsNull = anyIsNull;
        this.anyCheck = anyCheck;
        this.anyInstance = anyInstance;
        this.components = collectComponents(implType);

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

    public static <C extends Condition> TypeAdapterFactory condition(final ConditionType<C> type, final Class<? extends C> implType) {
        return new Builder<>(type.baseType(), implType).any(type.any(), Condition::isAny, type.anyIsNull()).create();
    }

    public static <T> TypeAdapterFactory simple(final Class<T> baseType, final Class<? extends T> implType) {
        return new Builder<>(baseType, implType).create();
    }

    private static Component[] collectComponents(final Class<?> implType) {
        final List<Component> components = new ArrayList<>();
        for (final Field field : implType.getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                components.add(new Component(getFieldName(field), field.getType(), field.getGenericType()));
            }
        }
        return components.toArray(Component[]::new);
    }

    private static String getFieldName(final Field field) {
        final String name;
        if (field.isAnnotationPresent(SerializedName.class)) {
            name = field.getAnnotation(SerializedName.class).value();
        } else {
            name = field.getName();
        }
        return name;
    }

    private record Component(
        String name,
        Class<?> type,
        Type genericType
    ) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> @Nullable TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        if (this.baseClass.isAssignableFrom(type.getRawType())) {
            return (TypeAdapter<T>) new InterfaceImplAdapter(gson);
        }
        return null;
    }

    private final class InterfaceImplAdapter extends TypeAdapter<C> {

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

        private InterfaceImplAdapter(final Gson gson) {
            this.gson = gson;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void write(final JsonWriter out, final @Nullable C value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            Preconditions.checkArgument(InterfaceImplAdapterFactory.this.baseClass.isInstance(value), value + " is not an instance of required type");
            if (InterfaceImplAdapterFactory.this.anyCheck.test(value)) {
                if (InterfaceImplAdapterFactory.this.anyIsNull) {
                    out.nullValue();
                } else {
                    out.beginObject().endObject();
                }
            } else {
                final TypeToken<C> token = TypeToken.get((Class<C>) value.getClass());
                this.gson.getDelegateAdapter(InterfaceImplAdapterFactory.this, token).write(out, value);
            }
        }

        @Override
        public C read(final JsonReader in) throws IOException {
            if (InterfaceImplAdapterFactory.this.anyInstance != null && in.peek() == JsonToken.NULL) {
                in.nextNull();
                return InterfaceImplAdapterFactory.this.anyInstance;
            } else if (in.peek() != JsonToken.BEGIN_OBJECT) {
                throw new JsonParseException("expected %s, got %s".formatted(JsonToken.BEGIN_OBJECT, in.peek()));
            }
            final Map<String, Object> arguments = new HashMap<>();
            in.beginObject();
            while (in.hasNext()) {
                final String key = in.nextName();
                final @Nullable TypeToken<?> token = InterfaceImplAdapterFactory.this.typeMap.get(key);
                if (token != null) {
                    arguments.put(key, this.gson.fromJson(in, token));
                } else {
                    throw new JsonParseException("Unexpected key %s found while parsing %s".formatted(key, InterfaceImplAdapterFactory.this.baseClass.getSimpleName()));
                }
            }
            in.endObject();
            if (InterfaceImplAdapterFactory.this.anyInstance != null && !InterfaceImplAdapterFactory.this.anyIsNull && arguments.isEmpty()) {
                return InterfaceImplAdapterFactory.this.anyInstance;
            }

            final Object[] args = new Object[InterfaceImplAdapterFactory.this.components.length];
            for (int i = 0; i < InterfaceImplAdapterFactory.this.components.length; i++) {
                final Component component = InterfaceImplAdapterFactory.this.components[i];
                final @Nullable TypeToken<?> type = InterfaceImplAdapterFactory.this.typeMap.get(componentName(component));
                if (type == null) continue;
                final Class<?> rawType = type.getRawType();
                @Nullable Object value = arguments.get(componentName(component));
                if (value == null) {
                    if (Conditions.isDefaulted(type)) {
                        value = Conditions.getDefaultValue(type);
                    } else if (rawType.isPrimitive()) {
                        value = PRIMITIVE_DEFAULTS.get(rawType);
                    } else if (Map.class.isAssignableFrom(rawType)) {
                        value = this.gson.fromJson(new JsonArray(), type.getType());
                    } else {
                        if (Condition.class.isAssignableFrom(rawType)) {
                            throw new IllegalStateException("All conditions should have had defaults set");
                        }
                        value = this.gson.fromJson(JsonNull.INSTANCE, type.getType());
                    }
                }
                args[i] = value;
            }

            try {
                final C instance = InterfaceImplAdapterFactory.this.constructor.newInstance(args);
                if (InterfaceImplAdapterFactory.this.anyInstance != null && InterfaceImplAdapterFactory.this.anyCheck.test(instance)) {
                    return InterfaceImplAdapterFactory.this.anyInstance;
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
    @SuppressWarnings("SameParameterValue")
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

    public static final class Builder<T> {

        private final Class<T> baseType;
        private final Class<? extends T> implType;

        private @Nullable T anyInstance = null;
        private Predicate<T> anyCheck = t -> false;
        private boolean anyIsNull = true;

        public Builder(final Class<T> baseType, final Class<? extends T> implType) {
            Preconditions.checkArgument(baseType.isInterface(), baseType + " is not an interface");
            Preconditions.checkArgument(!baseType.equals(implType), "Must have different base and impl types");
            Preconditions.checkArgument(baseType.isAssignableFrom(implType), implType + " does not extend " + baseType);
            this.baseType = baseType;
            this.implType = implType;
        }

        public Builder<T> any(final T anyInstance, final Predicate<T> anyCheck) {
            return this.any(anyInstance, anyCheck, true);
        }

        public Builder<T> any(final T anyInstance, final Predicate<T> anyCheck, final boolean anyIsNull) {
            this.anyInstance = anyInstance;
            this.anyCheck = anyCheck;
            this.anyIsNull = anyIsNull;
            return this;
        }

        public TypeAdapterFactory create() {
            return new InterfaceImplAdapterFactory<>(this.baseType, this.anyIsNull, this.anyCheck, this.anyInstance, this.implType);
        }
    }
}
