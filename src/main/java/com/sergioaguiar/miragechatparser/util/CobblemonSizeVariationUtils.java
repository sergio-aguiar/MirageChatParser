package com.sergioaguiar.miragechatparser.util;

import net.fabricmc.loader.api.FabricLoader;

public class CobblemonSizeVariationUtils
{
    public static class SizeDefinition
    {
        private final String name;
        private final double min;
        private final double max;
        private final String color;

        public SizeDefinition(String name, double min, double max, String color)
        {
            this.name = name;
            this.min = min;
            this.max = max;
            this.color = color;
        }

        public String getName() { return name; }
        public double getMin() { return min; }
        public double getMax() { return max; }
        public String getColor() { return color; }
    }

    private static final String MOD_ID_STRING = "cobblemonsizevariation";

    public static boolean isModLoaded()
    {
        return FabricLoader.getInstance().isModLoaded(MOD_ID_STRING);
    }
}
