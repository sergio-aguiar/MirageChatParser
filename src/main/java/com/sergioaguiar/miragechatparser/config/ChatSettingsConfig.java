package com.sergioaguiar.miragechatparser.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.sergioaguiar.miragechatparser.util.ModLogger;

public class ChatSettingsConfig
{
    private static final Path CONFIG_PATH = Paths.get("config", "miragechatparser", "chat_settings.toml");

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
                ModLogger.info("Generated default chat_colors.toml.");
            }
            catch (IOException e)
            {
                ModLogger.error("Failed to create default chat_colors.toml: %s".formatted(e.getMessage()));
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

            if (config.contains("Tooltip.ShowNickname"))
            {
                boolean showNickname = config.getOrElse("Tooltip.ShowNickname", true);
                ChatSettings.setShowNickname(showNickname);
            }

            ModLogger.info("Settings configurations successfully loaded from chat_settings.toml.");
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to load chat_settings.toml: %s".formatted(e.getMessage()));
        }
    }

    private static void createDefaultConfig(File file) throws IOException
    {
        String defaultContent = """
            # MirageChatParser - Chat Settings Configuration

            [Tooltip]
            # Whether to show a Pokémon's nickname in hover text (true/false)
            ShowNickname = true
            """;
        
        Files.writeString(file.toPath(), defaultContent);
    }
}
