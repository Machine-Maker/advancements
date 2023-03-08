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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.util.HashMap;
import java.util.Map;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.ConditionType;
import me.machinemaker.advancements.conditions.DefaultValues;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ConditionAdapterFactory<C extends Condition<C>> implements TypeAdapterFactory {

    private final ConditionType<C> type;
    private final Class<C> baseClass;
    private final RecordComponent[] components;
    private final Constructor<? extends C> recordCtor;
    private final C anyInstance;
    private final Map<String, TypeToken<?>> typeMap = new HashMap<>();

    public ConditionAdapterFactory(final ConditionType<C> conditionType, final Class<? extends C> recordClass) {
        Preconditions.checkArgument(recordClass.isRecord(), "%s is not a record", recordClass);
        this.type = conditionType;
        this.baseClass = conditionType.baseType();
        this.anyInstance = conditionType.any();
        this.components = recordClass.getRecordComponents();

        final Class<?>[] argTypes = new Class[this.components.length];
        for (int i = 0; i < this.components.length; i++) {
            final RecordComponent component = this.components[i];
            argTypes[i] = component.getType();
            final TypeToken<?> componentType = TypeToken.get(component.getGenericType());
            this.typeMap.put(component.getName(), componentType);
        }
        try {
            this.recordCtor = recordClass.getDeclaredConstructor(argTypes);
            this.recordCtor.trySetAccessible();
        } catch (final NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
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

            final Object[] args = new Object[ConditionAdapterFactory.this.components.length];
            for (int i = 0; i < ConditionAdapterFactory.this.components.length; i++) {
                final RecordComponent component = ConditionAdapterFactory.this.components[i];
                final @Nullable TypeToken<?> type = ConditionAdapterFactory.this.typeMap.get(component.getName());
                if (type == null) continue;
                final Class<?> rawType = type.getRawType();
                @Nullable Object value = arguments.get(component.getName());
                if (value == null && DefaultValues.isDefaulted(type)) {
                    value = DefaultValues.getDefaultValue(type);
                } else if (value == null && rawType.isPrimitive()) {
                    value = PRIMITIVE_DEFAULTS.get(rawType);
                } else if (value == null) {
                    value = this.gson.fromJson(JsonNull.INSTANCE, type.getType());
                }
                args[i] = value;
            }

            try {
                final C instance = ConditionAdapterFactory.this.recordCtor.newInstance(args);
                if (instance.isAny()) {
                    return ConditionAdapterFactory.this.anyInstance;
                }
                return instance;
            } catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
