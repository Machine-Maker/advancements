package me.machinemaker.advancements.tests.providers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import org.bukkit.Material;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

public final class MaterialProviders {


    static final List<org.bukkit.Fluid> FLUIDS = Arrays.stream(org.bukkit.Fluid.values()).toList();

    static final Predicate<Material> NOT_LEGACY = Predicate.not(Material::isLegacy);
    static final List<Material> BLOCKS_ITEMS = Arrays.stream(Material.values()).filter(NOT_LEGACY.and(Material::isBlock)).toList();
    static final List<Material> ITEMS_ITEMS = Arrays.stream(Material.values()).filter(NOT_LEGACY.and(Material::isItem)).toList();

    private MaterialProviders() {
    }

    // @Retention(RetentionPolicy.RUNTIME)
    // @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    // @ParameterizedTest
    // @ArgumentsSource(Block.Provider.class)
    // public @interface Block {
    //
    //     class Provider extends RandomItemProvider<Material> {
    //         public Provider() {
    //             super(BLOCKS);
    //         }
    //     }
    // }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @ParameterizedTest
    @ArgumentsSource(Blocks.Provider.class)
    public @interface Blocks {

        class Provider extends RandomItemsProvider<Material> {

            public Provider() {
                super(BLOCKS_ITEMS);
            }
        }
    }

    // @Retention(RetentionPolicy.RUNTIME)
    // @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    // @ParameterizedTest
    // @ArgumentsSource(Item.Provider.class)
    // public @interface Item {
    //
    //     class Provider extends RandomItemProvider<Material> {
    //         public Provider() {
    //             super(ITEMS);
    //         }
    //     }
    // }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @ParameterizedTest
    @ArgumentsSource(Items.Provider.class)
    public @interface Items {

        class Provider extends RandomItemsProvider<Material> {

            public Provider() {
                super(ITEMS_ITEMS);
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
    @ParameterizedTest
    @ArgumentsSource(Fluid.Provider.class)
    public @interface Fluid {

        class Provider extends RandomItemProvider<org.bukkit.Fluid> {

            public Provider() {
                super(FLUIDS);
            }
        }
    }

}
