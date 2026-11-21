package com.lautish.wyrmwing;

import com.jahirtrap.configlib.TXFConfig;
import com.lautish.wyrmwing.init.ModConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(WyrmwingMod.MODID)
public class WyrmwingMod {

    public static final String MODID = "wyrmwing";

    public WyrmwingMod(IEventBus bus) {
        TXFConfig.init(MODID, ModConfig.class);
    }
}
