package me.machinemaker.advancements.adapters.builders;

import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonDeserializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class Builders {

    private Builders() {
    }

    public static <T> GsonBuilderApplicable type(final TypeToken<T> type, final TypeAdapter<T> adapter) {
        return new Type<>(type, adapter);
    }

    public static <T> GsonBuilderApplicable type(final Class<T> type, final TypeAdapter<T> adapter) {
        return new Type<>(TypeToken.get(type), adapter);
    }

    public static <T> GsonBuilderApplicable type(final Class<T> type, final JsonDeserializer<T> deserializer) {
        return new Type<>(TypeToken.get(type), deserializer);
    }

    public static <T> GsonBuilderApplicable typeHierarchy(final Class<? super T> type, final TypeAdapter<T> adapter) {
        return new TypeHierarchy<>(type, adapter);
    }

    public static <T> GsonBuilderApplicable typeHierarchy(final Class<T> type, final JsonDeserializer<T> deserializer) {
        return new TypeHierarchy<>(type, deserializer);
    }

    public static GsonBuilderApplicable factory(final TypeAdapterFactory factory) {
        return new AdapterFactory(factory);
    }

    public static <T> GsonBuilderApplicable instantiator(final Class<T> type, final InstanceCreator<T> instanceCreator) {
        return new Type<>(TypeToken.get(type), instanceCreator);
    }

    public static GsonBuilderApplicable collection(final GsonBuilderApplicable...applicables) {
        return new Collection(Set.of(applicables));
    }

    record Type<T>(TypeToken<T> type, Object adapter) implements GsonBuilderApplicable {

        @Override
        public void applyTo(final GsonBuilder builder) {
            builder.registerTypeAdapter(this.type.getType(), this.adapter);
        }
    }

    record TypeHierarchy<T>(Class<? super T> type, Object adapter) implements GsonBuilderApplicable {

        @Override
        public void applyTo(final GsonBuilder builder) {
            builder.registerTypeHierarchyAdapter(this.type, this.adapter);
        }
    }

    record AdapterFactory(TypeAdapterFactory factory) implements GsonBuilderApplicable {

        @Override
        public void applyTo(final GsonBuilder builder) {
            builder.registerTypeAdapterFactory(this.factory);
        }
    }

    record Collection(java.util.Collection<GsonBuilderApplicable> applicables) implements GsonBuilderApplicable {

        Collection {
            applicables = applicables.stream().flatMap(applicable -> {
                if (applicable instanceof Collection collection) {
                    return collection.applicables().stream();
                }
                return Stream.of(applicable);
            }).distinct().toList();
        }

        @Override
        public void applyTo(final GsonBuilder builder) {
            for (final GsonBuilderApplicable gsonBuilderApplicable : this.applicables) {
                gsonBuilderApplicable.applyTo(builder);
            }
        }

    }
}
