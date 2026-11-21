package com.lautish.wyrmwing;

import com.jahirtrap.configlib.TXFConfig;
import com.lautish.wyrmwing.init.ModConfig;
import com.lautish.wyrmwing.init.ModContent;
import com.lautish.wyrmwing.init.ModTab;
import net.fabricmc.api.ModInitializer;

public class WyrmwingMod implements ModInitializer {

    public static final String MODID = "wyrmwing";

    @Override
    public void onInitialize() {
        TXFConfig.init(MODID, ModConfig.class);
        ModContent.init();
        ModTab.init();
    }
}
