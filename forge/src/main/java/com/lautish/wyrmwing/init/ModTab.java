package com.lautish.wyrmwing.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.lautish.wyrmwing.WyrmwingMod.MODID;

public class ModTab {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> TAB_WYRMWING = TABS.register("tab_wyrmwing", () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(ModContent.WYRMWING_ARMOR.get(1).get()))
            .displayItems((features, event) -> {
                for (RegistryObject<Item> item : ModContent.ITEMS.getEntries())
                    event.accept(item.get());
            })
            .title(Component.translatable("itemGroup.wyrmwing.tab_wyrmwing"))
            .build());

    public static void init(BusGroup bus) {
        TABS.register(bus);
    }
}
