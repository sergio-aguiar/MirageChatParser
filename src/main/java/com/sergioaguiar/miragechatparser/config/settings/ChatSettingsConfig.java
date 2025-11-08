package com.sergioaguiar.miragechatparser.config.settings;

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
                ModLogger.info("Generated default chat_settings.toml.");
            }
            catch (IOException e)
            {
                ModLogger.error("Failed to create default chat_settings.toml: %s".formatted(e.getMessage()));
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

            if (config.contains("General.ParseNonPlayerMessages"))
            {
                boolean enabled = config.getOrElse("General.ParseNonPlayerMessages", ChatSettings.DEFAULT_PARSE_NON_PLAYER_MESSAGES);
                ChatSettings.setParseNonPlayerMessages(enabled);
            }

            if (config.contains("Tooltip.ShowNickname"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowNickname", ChatSettings.DEFAULT_SHOW_NICKNAME);
                ChatSettings.setShowNickname(enabled);
            }

            if (config.contains("Tooltip.ShowFormIfNormal"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowFormIfNormal", ChatSettings.DEFAULT_SHOW_FORM_IF_NORMAL);
                ChatSettings.setShowFormIfNormal(enabled);
            }

            if (config.contains("Tooltip.ShowNeuteredIfFalse"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowNeuteredIfFalse", ChatSettings.DEFAULT_SHOW_NEUTERED_IF_FALSE);
                ChatSettings.setShowNeuteredIfFalse(enabled);
            }

            if (config.contains("Tooltip.ShowEggGroups"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowEggGroups", ChatSettings.DEFAULT_SHOW_EGG_GROUPS);
                ChatSettings.setShowEggGroups(enabled);
            }

            ModLogger.info("Setting configurations successfully loaded from chat_settings.toml.");
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

            [General]
            # Whether to parse non-player (e.g. server) messages or not (true/false)
            ParseNonPlayerMessages = false

            [Tooltip]
            # Whether to show a Pokémon's nickname in hover text (true/false)
            ShowNickname = true
            # Whether to show a Pokémon's form in hover text when the value is Normal (true/false)
            ShowFormIfNormal = true
            # Whether to show a Pokémon's neutered state in hover text when the value is False (true/false)
            ShowNeuteredIfFalse = true
            # Whether to show a Pokémon's egg groups in hover text (true/false)
            ShowEggGroups = true
            """;
        
        Files.writeString(file.toPath(), defaultContent);
    }
}
