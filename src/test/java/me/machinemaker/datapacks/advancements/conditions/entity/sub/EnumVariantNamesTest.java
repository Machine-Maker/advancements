package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.animal.horse.Variant;
import org.bukkit.Art;
import org.bukkit.Keyed;
import org.bukkit.entity.Axolotl;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Frog;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Llama;
import org.bukkit.entity.MushroomCow;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Rabbit;
import org.bukkit.entity.TropicalFish;
import org.bukkit.entity.Villager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

class EnumVariantNamesTest {

    private static final Map<Class<? extends Enum<?>>, Class<? extends StringRepresentable>> ENUMS = Map.of(
        Axolotl.Variant.class, net.minecraft.world.entity.animal.axolotl.Axolotl.Variant.class,
        Boat.Type.class, net.minecraft.world.entity.vehicle.Boat.Type.class,
        Fox.Type.class, net.minecraft.world.entity.animal.Fox.Type.class,
        MushroomCow.Variant.class, net.minecraft.world.entity.animal.MushroomCow.MushroomType.class,
        Rabbit.Type.class, net.minecraft.world.entity.animal.Rabbit.Variant.class,
        Horse.Color.class, Variant.class,
        Llama.Color.class, net.minecraft.world.entity.animal.horse.Llama.Variant.class,
        Parrot.Variant.class, net.minecraft.world.entity.animal.Parrot.Variant.class,
        TropicalFish.Pattern.class, net.minecraft.world.entity.animal.TropicalFish.Pattern.class
    );

    private static final Map<Class<? extends Enum<?>>, Registry<?>> REGISTRY_ENUMS = Map.of(
        Cat.Type.class, BuiltInRegistries.CAT_VARIANT,
        Frog.Variant.class, BuiltInRegistries.FROG_VARIANT,
        Art.class, BuiltInRegistries.PAINTING_VARIANT,
        Villager.Profession.class, BuiltInRegistries.VILLAGER_PROFESSION
    );

    private static Stream<Arguments> plainEnums() {
        return ENUMS.entrySet().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    private static Stream<Arguments> registryEnums() {
        return REGISTRY_ENUMS.entrySet().stream().map(entry -> Arguments.of(entry.getKey(), entry.getValue()));
    }

    @ParameterizedTest
    @MethodSource("plainEnums")
    <E extends Enum<E> & StringRepresentable> void testPlainEnums(final Class<? extends Enum<?>> apiEnum, final Class<E> nmsEnum) {
        assertTrue(apiEnum.isEnum(), apiEnum + " is not an enum");
        assertTrue(nmsEnum.isEnum(), nmsEnum + " is not an enum");
        final Set<String> nmsStrings = Arrays.stream(nmsEnum.getEnumConstants()).map(StringRepresentable::getSerializedName).collect(Collectors.toCollection(HashSet::new));
        for (final Enum<?> e : apiEnum.getEnumConstants()) {
            assertTrue(nmsStrings.remove(EntityVariants.getSerializedName(e)), e + " doesn't match a serialized nms name");
        }
    }

    @ParameterizedTest
    @MethodSource("registryEnums")
    <E extends Enum<E> & Keyed> void testRegistryEnums(final Class<E> apiEnum, final Registry<?> nmsRegistry) {
        assertTrue(apiEnum.isEnum(), apiEnum + " is not an enum");
        assertTrue(Keyed.class.isAssignableFrom(apiEnum), apiEnum + " does not implement Keyed");
        final Set<String> nmsStrings = collectStrings(nmsRegistry);
        for (final E e : apiEnum.getEnumConstants()) {
            assertTrue(nmsStrings.remove(EntityVariants.getSerializedName(e)), apiEnum + " doesn't match a serialized nms name");
        }
    }

    private static <T> Set<String> collectStrings(final Registry<T> nmsRegistry) {
        final Codec<T> codec = nmsRegistry.byNameCodec();
        return nmsRegistry.stream().map(t -> codec
                .encodeStart(JsonOps.INSTANCE, t)
                .getOrThrow(false, Assertions::fail).getAsString()
            ).collect(Collectors.toCollection(HashSet::new));
    }
}
