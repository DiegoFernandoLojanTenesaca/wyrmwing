package com.lautish.wyrmwing.util;

import com.lautish.wyrmwing.init.ModTags;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.List;

import static com.lautish.wyrmwing.WyrmwingMod.MODID;

public class WyrmwingSet {
    private static final List<EquipmentSlot> SLOTS = List.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);

    public static boolean isFullSet(LivingEntity entity) {
        return SLOTS.stream().allMatch(slot -> entity.getItemBySlot(slot).is(ModTags.Items.WYRMWING_ARMOR));
    }

    public static ItemAttributeModifiers armorAttributes(ArmorMaterial material, ArmorType type) {
        EquipmentSlotGroup slot = EquipmentSlotGroup.bySlot(type.getSlot());
        ItemAttributeModifiers modifiers = material.createAttributes(type)
                .withModifierAdded(Attributes.MOVEMENT_SPEED, modifier(type, "speed", 0.025, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), slot)
                .withModifierAdded(Attributes.ATTACK_DAMAGE, modifier(type, "damage", 0.5, AttributeModifier.Operation.ADD_VALUE), slot);
        return switch (type) {
            case HELMET -> modifiers.withModifierAdded(Attributes.OXYGEN_BONUS, modifier(type, "breathing", 1000, AttributeModifier.Operation.ADD_VALUE), slot);
            case BOOTS -> modifiers.withModifierAdded(Attributes.FALL_DAMAGE_MULTIPLIER, modifier(type, "fall", -1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL), slot);
            default -> modifiers;
        };
    }

    private static AttributeModifier modifier(ArmorType type, String effect, double amount, AttributeModifier.Operation operation) {
        return new AttributeModifier(Identifier.fromNamespaceAndPath(MODID, "armor." + type.getName() + "." + effect), amount, operation);
    }
}
