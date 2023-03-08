package me.machinemaker.advancements.adapters.builders;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GsonBuilderApplicableTest {

    @Test
    void testCollectionType() {
        final GsonBuilderApplicable type1 = new Builders.Type<>(TypeToken.get(Class1.class), new Object());
        final GsonBuilderApplicable type2 = new Builders.TypeHierarchy<>(Class2.class, new Object());
        final GsonBuilderApplicable type3 = new Builders.AdapterFactory(new TypeAdapterFactory() {
            @Override
            public <T> @Nullable TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
                return null;
            }
        });

        final Builders.Collection collection1 = new Builders.Collection(List.of(type1, type2));
        assertTrue(collection1.applicables().contains(type1));
        assertTrue(collection1.applicables().contains(type2));

        final Builders.Collection collection2 = new Builders.Collection(List.of(collection1, type1));
        assertEquals(2, collection2.applicables().size());
        assertTrue(collection2.applicables().contains(type1));
        assertTrue(collection2.applicables().contains(type2));

        final Builders.Collection collection3 = new Builders.Collection(List.of(collection2, type2, type3));
        assertEquals(3, collection3.applicables().size());
        assertTrue(collection3.applicables().contains(type1));
        assertTrue(collection3.applicables().contains(type2));
        assertTrue(collection3.applicables().contains(type3));
    }


    static class Class1 {
    }

    static class Class2 {
    }
}
