package com.lautish.wyrmwing.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.lautish.wyrmwing.WyrmwingMod.MODID;

public class ModTags {
    public interface Items {
        TagKey<Item> WYRM_SCALES = create("wyrm_scales");
        TagKey<Item> WYRMWING_ARMOR = create("wyrmwing_armor");

        private static TagKey<Item> create(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MODID, name));
        }
    }
}
