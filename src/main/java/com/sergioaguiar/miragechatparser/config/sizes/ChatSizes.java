package com.sergioaguiar.miragechatparser.config.sizes;

import java.util.HashMap;
import java.util.Map;

import com.sergioaguiar.miragechatparser.util.CobblemonSizeVariationUtils;
import com.sergioaguiar.miragechatparser.util.CobblemonSizeVariationUtils.SizeDefinition;

public class ChatSizes
{
    public enum SizeCategory
    {
        TINY(0.2f, 0.5f, "#1b88cc"),
        SMALL(0.51f, 0.9f, "#1bcc9a"),
        AVERAGE(0.91f, 1.2f, "#ffffff"),
        BIG(1.21f, 1.6f, "#e6ff2b"),
        LARGE(1.61f, 1.9f, "#f07426"),
        HUGE(1.91f, 2.0f, "#f21800");

        private final float min;
        private final float max;
        private final String color;

        SizeCategory(float min, float max, String color)
        {
            this.min = min;
            this.max = max;
            this.color = color;
        }

        public float getMin() { return min; }
        public float getMax() { return max; }
        public String getColor() { return color; }

        public static SizeCategory fromSize(float size)
        {
            for (SizeCategory category : values())
            {
                if (size >= category.min && size <= category.max)
                {
                    return category;
                }
            }
            return AVERAGE;
        }

        @Override
        public String toString()
        {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }

    private static String algorithmName;
    private static Map<String, SizeDefinition> sizes;

    public static void setDefaults()
    {
        algorithmName = "";
        sizes = new HashMap<>();

        if (!CobblemonSizeVariationUtils.isModLoaded())
        {
            for (SizeCategory sizeCategory : SizeCategory.values())
            {
                sizes.put
                (
                    sizeCategory.toString(),
                    new SizeDefinition
                    (
                        sizeCategory.toString(),
                        sizeCategory.getMin(),
                        sizeCategory.getMax(),
                        sizeCategory.getColor()
                    )
                );
            }
        }
    }

    public static void setAlgorithmName(String algName) { algorithmName = algName; }
    public static void addSizeDefinition(String name, double min, double max, String color) { sizes.put(name, new CobblemonSizeVariationUtils.SizeDefinition(name, min, max, color)); }

    public static String getAlgorithmName() { return algorithmName; }
    public static Map<String, SizeDefinition> getSizes() { return sizes; }

    public static String getSizefromScale(double sizeScale)
    {
        for (Map.Entry<String, SizeDefinition> sizeDefinition : sizes.entrySet())
        {
            if (sizeScale >= sizeDefinition.getValue().getMin() && sizeScale <= sizeDefinition.getValue().getMax())
            {
                return sizeDefinition.getKey();
            }
        }
        return SizeCategory.AVERAGE.toString();
    }

    public static String getColorfromScale(double sizeScale)
    {
        for (Map.Entry<String, SizeDefinition> sizeDefinition : sizes.entrySet())
        {
            if (sizeScale >= sizeDefinition.getValue().getMin() && sizeScale <= sizeDefinition.getValue().getMax())
            {
                return sizeDefinition.getValue().getColor();
            }
        }
        return SizeCategory.AVERAGE.getColor();
    }
}
