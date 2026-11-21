package com.lautish.wyrmwing.init;

import com.jahirtrap.configlib.TXFConfig;

public class ModConfig extends TXFConfig {
    @Entry
    public static boolean flightEnabled = true;
    @Entry
    public static boolean showAura = true;
    @Entry
    public static boolean showOwnAuraFirstPerson = false;
    @Entry(min = 0, max = 64)
    public static int dragonScaleDrops = 8;
}
