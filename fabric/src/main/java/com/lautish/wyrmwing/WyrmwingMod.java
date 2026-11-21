package com.lautish.wyrmwing;

import com.jahirtrap.configlib.TXFConfig;
import com.lautish.wyrmwing.init.ModConfig;
import net.fabricmc.api.ModInitializer;

public class WyrmwingMod implements ModInitializer {

    public static final String MODID = "wyrmwing";

    @Override
    public void onInitialize() {
        TXFConfig.init(MODID, ModConfig.class);
    }
}
