package me.machinemaker.advancements.adapters.factories;

import com.google.gson.Gson;
import com.google.gson.JsonNull;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import me.machinemaker.advancements.adapters.util.IgnoreRecordTypeAdapter;
import me.machinemaker.advancements.conditions.Condition;
import me.machinemaker.advancements.conditions.Conditions;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gson support for Java 16+ record types.
 * Taken from https://github.com/google/gson/issues/1794 and adjusted for performance
 * and proper handling of {@link SerializedName} annotations
 */
public class RecordTypeAdapterFactory implements TypeAdapterFactory {

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

    private final Map<RecordComponent, List<String>> recordComponentNameCache = new ConcurrentHashMap<>();

    /**
     * Get all names of a record component
     * If annotated with {@link SerializedName} the list returned will be the primary name first, then any alternative names
     * Otherwise, the component name will be returned.
     */
    private List<String> getRecordComponentNames(final RecordComponent recordComponent) {
        List<String> inCache = this.recordComponentNameCache.get(recordComponent);
        if (inCache != null) {
            return inCache;
        }
        List<String> names = new ArrayList<>();
        // The @SerializedName is compiled to be part of the componentName() method
        // The use of a loop is also deliberate, getAnnotation seemed to return null if Gson's package was relocated
        SerializedName annotation = null;
        for (Annotation a : recordComponent.getAccessor().getAnnotations()) {
            if (a.annotationType() == SerializedName.class) {
                annotation = (SerializedName) a;
                break;
            }
        }

        if (annotation != null) {
            names.add(annotation.value());
            names.addAll(Arrays.asList(annotation.alternate()));
        } else {
            names.add(separateCamelCase(recordComponent.getName()).toLowerCase(Locale.ENGLISH)); // This has to change if the FieldNamingStrategy ever changes
        }
        var namesList = List.copyOf(names);
        this.recordComponentNameCache.put(recordComponent, namesList);
        return namesList;
    }

    @Override
    public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
        @SuppressWarnings("unchecked")
        final Class<T> clazz = (Class<T>) type.getRawType();
        final TypeAdapter<T> delegate;
        if (!clazz.isRecord() || clazz.isAnnotationPresent(IgnoreRecordTypeAdapter.class)) {
            return null;
        } else {
            delegate = gson.getDelegateAdapter(this, type);
        }
        final Class<T> actualClass = clazz;

        return new TypeAdapter<>() {
            @Override
            public void write(JsonWriter out, T value) throws IOException {
                delegate.write(out, value);
            }

            @SuppressWarnings("unchecked")
            @Override
            public T read(JsonReader reader) throws IOException {
                if (reader.peek() == JsonToken.NULL) {
                    return delegate.read(reader);
                }
                var recordComponents = actualClass.getRecordComponents();
                var typeMap = new HashMap<String, TypeToken<?>>();
                var adapterMap = new HashMap<String, TypeAdapter<?>>();
                for (RecordComponent recordComponent : recordComponents) {
                    for (String name : RecordTypeAdapterFactory.this.getRecordComponentNames(recordComponent)) {
                        final TypeToken<?> type = TypeToken.get(recordComponent.getGenericType());
                        typeMap.put(name, type);
                        try {
                            // roundabout way of getting field annotations applied to a record component, is there a better way?
                            Field recordField = actualClass.getDeclaredField(recordComponent.getName());
                            if (recordField.isAnnotationPresent(JsonAdapter.class)) {
                                final JsonAdapter annotation = recordField.getAnnotation(JsonAdapter.class);
                                final TypeAdapter<?> adapter = createTypeAdapter(gson, annotation.value(), TypeToken.get(recordComponent.getGenericType()));
                                adapterMap.put(name, annotation.nullSafe() ? adapter.nullSafe() : adapter);
                            } else {
                                adapterMap.put(name, gson.getAdapter(type));
                            }
                        } catch (NoSuchFieldException ex) {
                            throw new RuntimeException(ex);
                        }

                    }
                }
                var argsMap = new HashMap<String, Object>();

                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    var type = typeMap.get(name);
                    var adapter = adapterMap.get(name);
                    if (type != null && adapter != null) {
                        argsMap.put(name, adapter.read(reader));
                    } else {
                        gson.getAdapter(Object.class).read(reader);
                    }

                }
                reader.endObject();


                var argTypes = new Class<?>[recordComponents.length];
                var args = new Object[recordComponents.length];
                for (int i = 0; i < recordComponents.length; i++) {
                    argTypes[i] = recordComponents[i].getType();
                    List<String> names = RecordTypeAdapterFactory.this.getRecordComponentNames(recordComponents[i]);
                    Object value = null;
                    TypeToken<?> type = null;
                    TypeAdapter<?> adapter = null;
                    // Find the first matching type and value
                    for (String name : names) {
                        value = argsMap.get(name);
                        type = typeMap.get(name);
                        adapter = adapterMap.get(name);
                        if (value != null && type != null && adapter != null) {
                            break;
                        }
                    }

                    Class<?> conditionClass = type != null ? type.getRawType() : null;
                    if (value == null && conditionClass != null && Condition.class.isAssignableFrom(conditionClass)) {
                        value = Conditions.getDefaultCondition((TypeToken<? extends Condition<?>>) type);
                    } else if (value == null && type != null && type.getRawType() != null && type.getRawType().isPrimitive()) {
                        value = PRIMITIVE_DEFAULTS.get(type.getRawType());
                    } else if (value == null && adapter != null) {
                        value = adapter.read(new JsonTreeReader(JsonNull.INSTANCE));
                    }
                    args[i] = value;
                }
                try {
                    final Constructor<T> ctor;
                    ctor = actualClass.getDeclaredConstructor(argTypes);
                    ctor.trySetAccessible();
                    return ctor.newInstance(args);
                } catch (NoSuchMethodException | InstantiationException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }

    // Copied from gson's FieldNamingPolicy
    private static String separateCamelCase(String name) {
        StringBuilder translation = new StringBuilder();
        for (int i = 0; i < name.length(); i++) {
            char character = name.charAt(i);
            if (Character.isUpperCase(character) && translation.length() != 0) {
                translation.append("_");
            }
            translation.append(character);
        }
        return translation.toString();
    }

    private static TypeAdapter<?> createTypeAdapter(Gson gson, Class<?> typeAdapterClass, TypeToken<?> type) {
        Object instance;
        try {
            final Constructor<?> ctor = typeAdapterClass.getDeclaredConstructor();
            ctor.trySetAccessible();
            instance = ctor.newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        if (instance instanceof TypeAdapter<?> typeAdapter) {
            return typeAdapter;
        } else if (instance instanceof TypeAdapterFactory factory) {
            return factory.create(gson, type);
        } else {
            throw new UnsupportedOperationException(typeAdapterClass + " is not supported");
        }
    }
}