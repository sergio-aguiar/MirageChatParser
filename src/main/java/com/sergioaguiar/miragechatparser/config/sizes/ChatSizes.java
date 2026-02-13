package com.sergioaguiar.miragechatparser.config.sizes;

import java.util.Comparator;
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
    private static double minimumSizeScale;
    private static double maximumSizeScale;
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
    public static void setMinimumSizeScale(double scale) { minimumSizeScale = scale; }
    public static void setMaximumSizeScale(double scale) { maximumSizeScale = scale; }
    public static void addSizeDefinition(String name, double min, double max, String color) { sizes.put(name, new CobblemonSizeVariationUtils.SizeDefinition(name, min, max, color)); }

    public static String getAlgorithmName() { return algorithmName; }
    public static double getMinimumSizeScale() { return minimumSizeScale; }
    public static double getMaximumSizeScale() { return maximumSizeScale; }
    public static Map<String, SizeDefinition> getSizes() { return sizes; }

    public static String getSizefromScale(double sizeScale)
    {
        for (Map.Entry<String, SizeDefinition> sizeDefinition : sizes.entrySet())
        {
            if (sizeScale >= sizeDefinition.getValue().getMin() && sizeScale < sizeDefinition.getValue().getMax())
            {
                return sizeDefinition.getKey();
            }
        }

        try
        {
            SizeDefinition lowestMinSize = getLowestMinSize();
            if (sizeScale <= lowestMinSize.getMin()) return lowestMinSize.getName();

            SizeDefinition highestMaxSize = getHighestMaxSize();
            if (sizeScale >= highestMaxSize.getMax()) return highestMaxSize.getName();

            return getHighestMaxBelow(sizeScale).getName();
        }
        catch (Exception e)
        {
            return "Unknown Size";
        }
    }

    public static String getColorfromScale(double sizeScale)
    {
        for (Map.Entry<String, SizeDefinition> sizeDefinition : sizes.entrySet())
        {
            if (sizeScale >= sizeDefinition.getValue().getMin() && sizeScale < sizeDefinition.getValue().getMax())
            {
                return sizeDefinition.getValue().getColor();
            }
        }
        
        try
        {
            SizeDefinition lowestMinSize = getLowestMinSize();
            if (sizeScale <= lowestMinSize.getMin()) return lowestMinSize.getColor();

            SizeDefinition highestMaxSize = getHighestMaxSize();
            if (sizeScale >= highestMaxSize.getMax()) return highestMaxSize.getColor();

            return getHighestMaxBelow(sizeScale).getColor();
        }
        catch (Exception e)
        {
            return SizeCategory.AVERAGE.getColor();
        }
    }

    public static double getScaleFromGen9Int(int gen9Int)
    {
        return gen9Int * ((maximumSizeScale - minimumSizeScale) / 255.0) + minimumSizeScale;
    }

    private static SizeDefinition getLowestMinSize()
    {
        return sizes.values()
            .stream()
            .min(Comparator.comparingDouble(SizeDefinition::getMin))
            .orElseThrow();
    }

    private static SizeDefinition getHighestMaxSize()
    {
        return sizes.values()
            .stream()
            .max(Comparator.comparingDouble(SizeDefinition::getMax))
            .orElseThrow();
    }

    private static SizeDefinition getHighestMaxBelow(double value)
    {
        return sizes.values()
            .stream()
            .filter(size -> size.getMax() <= value)
            .max(Comparator.comparingDouble(SizeDefinition::getMax))
            .orElseThrow();
    }
}
