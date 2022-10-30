package me.machinemaker.advancements.conditions.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import me.machinemaker.advancements.GsonTestBase;
import me.machinemaker.advancements.conditions.item.ItemCondition;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityEquipmentConditionTest extends GsonTestBase {

    @Test
    void testEntityEquipmentConditionTestSingle() {
        EntityEquipmentCondition condition = EntityEquipmentCondition.forHead(ItemCondition.forTag(Tag.ITEMS_BOATS));
        JsonObject object = new JsonObject();
        JsonObject headObject = new JsonObject();
        headObject.addProperty("tag", Tag.ITEMS_BOATS.getKey().toString());
        object.add("head", headObject);
        assertEquals(object, tree(condition));
        assertEquals(condition, fromJson(object, EntityEquipmentCondition.class));
    }

    @Test
    void testEntityEquipmentConditionMultiple() {
        EntityEquipmentCondition condition = EntityEquipmentCondition.forHands(ItemCondition.forItems(Material.BONE), ItemCondition.forTag(Tag.ITEMS_COALS));
        JsonObject object = new JsonObject();
        JsonObject mainHand = new JsonObject();
        JsonArray mainHandArray = new JsonArray();
        mainHandArray.add(Material.BONE.getKey().toString());
        mainHand.add("items", mainHandArray);
        object.add("mainhand", mainHand);
        JsonObject offHand = new JsonObject();
        offHand.addProperty("tag", Tag.ITEMS_COALS.getKey().toString());
        object.add("offhand", offHand);
        assertEquals(object, tree(condition));
        assertEquals(condition, fromJson(object, EntityEquipmentCondition.class));
    }
}
