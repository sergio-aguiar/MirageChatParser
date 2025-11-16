package com.sergioaguiar.miragechatparser.config.aspects;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.sergioaguiar.miragechatparser.util.ModLogger;

public class ChatAspectsConfig
{
    private static final Path CONFIG_PATH = Paths.get("config", "miragechatparser", "chat_aspects.toml");

    public static void load()
    {
        File file = CONFIG_PATH.toFile();
        file.getParentFile().mkdirs();

        if (!file.exists()) 
        {
            try
            {
                Files.createDirectories(file.getParentFile().toPath());
                createDefaultConfig(file);
                ModLogger.info("Generated default chat_aspects.toml.");
            }
            catch (IOException e)
            {
                ModLogger.error("Failed to create default chat_aspects.toml: %s".formatted(e.getMessage()));
                return;
            }
        }

        try (CommentedFileConfig config = CommentedFileConfig.builder(file)
                .preserveInsertionOrder()
                .autoreload()
                .sync()
                .build())
        {
            config.load();

            ChatAspects.resetDisplayedAspects();

            if (config.contains("Aspect"))
            {
                CommentedConfig aspects = config.get("Aspect");
                Map<String, Object> valueMap = aspects.valueMap();

                for (String aspect : valueMap.keySet())
                {
                    ChatAspects.addDisplayedAspect(aspect, valueMap.get(aspect).toString());
                }
            }

            ModLogger.info("Aspect configurations successfully loaded from chat_aspects.toml.");
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to load chat_aspects.toml: %s".formatted(e.getMessage()));
        }
    }

    private static void createDefaultConfig(File file) throws IOException
    {
        String defaultContent = """
            # MirageChatParser - Aspect Color Configuration

            [Aspect]
            # Add the aspect value as the key and the string to appear in the hoverable UI as the value in front of it.
            # If you set a pokémon aspect with aspect=halloween2025 and want it to appear as (Halloween 2025), use the following:
            # halloween2025 = "Halloween 2025"

            # Custom Aspects to show
            halloween2025 = "Halloween 2025"
            """;
        
        Files.writeString(file.toPath(), defaultContent);
    }
}
