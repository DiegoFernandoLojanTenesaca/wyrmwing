package com.lautish.wyrmwing.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.function.Consumer;

import static com.lautish.wyrmwing.WyrmwingMod.MODID;

public class WyrmwingArmorItem extends Item {
    private final ArmorType type;

    public WyrmwingArmorItem(ArmorType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        builder.accept(Component.translatable(MODID + ".tooltip." + type.getName()).withStyle(ChatFormatting.GRAY));
        builder.accept(Component.translatable(MODID + ".tooltip.full_set").withStyle(ChatFormatting.DARK_PURPLE));
    }
}
