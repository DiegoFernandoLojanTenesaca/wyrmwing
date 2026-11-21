package com.lautish.wyrmwing;

import com.jahirtrap.configlib.TXFConfig;
import com.lautish.wyrmwing.init.ModConfig;
import com.lautish.wyrmwing.init.ModContent;
import com.lautish.wyrmwing.init.ModTab;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WyrmwingMod.MODID)
public class WyrmwingMod {

    public static final String MODID = "wyrmwing";

    public WyrmwingMod(FMLJavaModLoadingContext context) {
        BusGroup bus = context.getModBusGroup();

        TXFConfig.init(MODID, ModConfig.class);
        ModContent.init(bus);
        ModTab.init(bus);
    }
}
