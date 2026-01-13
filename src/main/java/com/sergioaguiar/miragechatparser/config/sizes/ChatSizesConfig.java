package com.sergioaguiar.miragechatparser.config.sizes;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.sergioaguiar.miragechatparser.util.CobblemonSizeVariationUtils;
import com.sergioaguiar.miragechatparser.util.ModLogger;

public class ChatSizesConfig
{
    private static final Path CONFIG_PATH = Paths.get("config", "cobblemonsizevariation", "config.json");
    private static final Path SIZES_PATH = Path.of("config", "cobblemonsizevariation", "sizes");

    public static void load()
    {
        if (CobblemonSizeVariationUtils.isModLoaded())
        {
            getSizingAlgorithm();
            getSizeDefinitions();
            ModLogger.info("CobblemonSizeVariation configuration loading concluded.");
        }
        else
        {
            ModLogger.info("CobblemonSizeVariation mod not loaded. Configuration loading skipped. Using defaults.");
        }
    }

    public static void getSizingAlgorithm()
    {
        try (FileConfig config = FileConfig.of(CONFIG_PATH))
        {
            config.load();
            ChatSizes.setAlgorithmName(config.getOrElse("sizingAlgorithm", ""));
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to obtain the CobblemonSizeVariation sizing algorithm: %s".formatted(e.getMessage()));
        }
    }

    public static void getSizeDefinitions()
    {
        String sizeFileName = ChatSizes.getAlgorithmName() + ".json";
        Path sizeFile = SIZES_PATH.resolve(sizeFileName);

        try (FileConfig config = FileConfig.of(sizeFile))
        {
            config.load();

            List<Config> sizes = config.get("sizes");
            for (Config size : sizes)
            {
                try
                {
                    ChatSizes.addSizeDefinition
                    (
                        size.get("name"),
                        Double.parseDouble(size.get("min").toString()),
                        Double.parseDouble(size.get("max").toString()),
                        size.get("color")
                    );
                }
                catch (Exception e)
                {
                    ModLogger.error("Failed to load a size definition from file %s: %s".formatted(sizeFileName, e.getMessage()));
                    continue;
                }
            }
        }
    }
}
