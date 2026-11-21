package com.lautish.wyrmwing.init;

import com.lautish.wyrmwing.item.BaseSmithingTemplateItem;
import com.lautish.wyrmwing.item.WyrmwingArmorItem;
import com.lautish.wyrmwing.util.WyrmwingSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Function;

import static com.lautish.wyrmwing.WyrmwingMod.MODID;

public class ModContent {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> WYRM_SCALE = registerItem("wyrm_scale", Item::new, new Item.Properties().fireResistant());
    public static final DeferredItem<Item> WYRMWING_UPGRADE_SMITHING_TEMPLATE = registerItem("wyrmwing_upgrade_smithing_template", (p) -> BaseSmithingTemplateItem.createUpgradeTemplate("wyrmwing", p), new Item.Properties().fireResistant());
    public static final List<DeferredItem<Item>> WYRMWING_ARMOR = registerArmor(ModMaterials.Armor.WYRMWING, new Item.Properties().fireResistant());

    private static DeferredItem<Item> registerItem(String name, Function<Item.Properties, Item> function, Item.Properties itemProp) {
        return ITEMS.register(name, () -> function.apply(itemProp.setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MODID, name)))));
    }

    private static List<DeferredItem<Item>> registerArmor(ArmorMaterial material, Item.Properties itemProp) {
        String name = material.assetId().identifier().getPath();
        return List.of(
                registerArmorPiece(name, material, ArmorType.HELMET, itemProp),
                registerArmorPiece(name, material, ArmorType.CHESTPLATE, itemProp),
                registerArmorPiece(name, material, ArmorType.LEGGINGS, itemProp),
                registerArmorPiece(name, material, ArmorType.BOOTS, itemProp)
        );
    }

    private static DeferredItem<Item> registerArmorPiece(String name, ArmorMaterial material, ArmorType type, Item.Properties itemProp) {
        return registerItem(name + "_" + type.getName(), (p) -> new WyrmwingArmorItem(type, p.humanoidArmor(material, type).attributes(WyrmwingSet.armorAttributes(material, type))), itemProp);
    }

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }
}
