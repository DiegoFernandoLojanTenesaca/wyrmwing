package com.lautish.wyrmwing;

import com.jahirtrap.configlib.TXFConfig;
import com.lautish.wyrmwing.init.ModConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WyrmwingMod.MODID)
public class WyrmwingMod {

    public static final String MODID = "wyrmwing";

    public WyrmwingMod(FMLJavaModLoadingContext context) {
        TXFConfig.init(MODID, ModConfig.class);
    }
}
