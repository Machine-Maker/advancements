package me.machinemaker.advancements.mocks;

import io.papermc.paper.potion.Potion;
import me.machinemaker.advancements.mocks.impls.TestPotion;
import org.bukkit.NamespacedKey;

import java.lang.reflect.Field;
import java.util.Map;

public final class DummyPotions {

    private DummyPotions() {
    }

    private static boolean setup = false;

    public static void setup() {
        if (!setup) {
            setupPotion("leaping");
            setupPotion("empty");
            setup = true;
        }
    }

    @SuppressWarnings("unchecked")
    private static void setupPotion(String name) {
        try {
            Field field = Potion.class.getDeclaredField("BY_KEY");
            field.trySetAccessible();
            Map<NamespacedKey, Potion> map = (Map<NamespacedKey, Potion>) field.get(null);
            Potion potion = new TestPotion(NamespacedKey.minecraft(name));
            map.put(potion.getKey(), potion);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}
