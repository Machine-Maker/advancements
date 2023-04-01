package me.machinemaker.datapacks.advancements.conditions.entity.sub;

import java.util.List;
import org.bukkit.Art;
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
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.VisibleForTesting;

import static me.machinemaker.datapacks.advancements.conditions.entity.sub.EntityVariantConditionImpl.createType;

@ApiStatus.NonExtendable
public interface EntityVariantCondition<T> extends EntitySubCondition {

    Type<Cat.Type> CAT = createType(Cat.Type.class, "cat");
    Type<Frog.Variant> FROG = createType(Frog.Variant.class, "frog");
    Type<Axolotl.Variant> AXOLOTL = createType(Axolotl.Variant.class, "axolotl");
    Type<Boat.Type> BOAT = createType(Boat.Type.class, "boat");
    Type<Fox.Type> FOX = createType(Fox.Type.class, "fox");
    Type<MushroomCow.Variant> MUSHROOM_COW = createType(MushroomCow.Variant.class, "mooshroom");
    Type<Art> ART = createType(Art.class, "painting");
    Type<Rabbit.Type> RABBIT = createType(Rabbit.Type.class, "rabbit");
    Type<Horse.Color> HORSE = createType(Horse.Color.class, "horse");
    Type<Llama.Color> LLAMA = createType(Llama.Color.class, "llama");
    Type<Villager.Profession> VILLAGER = createType(Villager.Profession.class, "villager");
    Type<Parrot.Variant> PARROT = createType(Parrot.Variant.class, "parrot");
    Type<TropicalFish.Pattern> TROPICAL_FISH = createType(TropicalFish.Pattern.class, "tropical_fish");

    @VisibleForTesting
    static @Unmodifiable List<Type<?>> variantTypes() {
        return List.copyOf(EntityVariantConditionImpl.TYPES.values());
    }

    @Contract(pure = true)
    T variant();

    @Contract(pure = true)
    Type<T> type();

    @ApiStatus.NonExtendable
    interface Type<T> {

        @Contract(pure = true)
        Class<T> type();

        @Contract(value = "_ -> new", pure = true)
        EntityVariantCondition<T> create(T value);
    }

}
