package com.lautish.wyrmwing.init;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;

import static com.lautish.wyrmwing.WyrmwingMod.MODID;

public class ModMaterials {
    public interface Armor {
        ArmorMaterial WYRMWING = new ArmorMaterial(42, createMap(new int[]{3, 8, 6, 3, 19}),
                18, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.5f, 0.12f, ModTags.Items.WYRM_SCALES, createAsset("wyrmwing"));

        private static EnumMap<ArmorType, Integer> createMap(int[] values) {
            EnumMap<ArmorType, Integer> enumMap = new EnumMap<>(ArmorType.class);
            for (int i = 0; i < values.length; i++) enumMap.put(ArmorType.values()[i], values[i]);
            return enumMap;
        }

        private static ResourceKey<EquipmentAsset> createAsset(String name) {
            return ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(MODID, name));
        }
    }
}
