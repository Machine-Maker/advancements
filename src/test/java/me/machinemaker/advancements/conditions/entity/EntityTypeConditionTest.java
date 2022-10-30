package me.machinemaker.advancements.conditions.entity;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import me.machinemaker.advancements.GsonTestBase;
import org.bukkit.Tag;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityTypeConditionTest extends GsonTestBase {

    @Test
    void testEntityTypeConditionTag() {
        EntityTypeCondition condition = EntityTypeCondition.of(Tag.ENTITY_TYPES_RAIDERS);
        JsonElement element = new JsonPrimitive("#" + Tag.ENTITY_TYPES_RAIDERS.getKey());
        assertEquals(element, tree(condition));
        assertEquals(condition, fromJson(element, EntityTypeCondition.class));
    }

    @Test
    void testEntityTypeConditionType() {
        EntityTypeCondition condition = EntityTypeCondition.of(EntityType.BAT);
        JsonElement element = new JsonPrimitive(EntityType.BAT.getKey().toString());
        assertEquals(element, tree(condition));
        assertEquals(condition, fromJson(element, EntityTypeCondition.class));
    }

    @Test
    void testAnyEntityTypeCondition() {
        anyTest(JsonNull.INSTANCE, EntityTypeCondition.class);
        anyTest("null", EntityTypeCondition.class);
    }
}
