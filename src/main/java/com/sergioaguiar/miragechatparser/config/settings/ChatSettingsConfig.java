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

            if (config.contains("Tooltip.ShowSpecies"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowSpecies", ChatSettings.DEFAULT_SHOW_SPECIES);
                ChatSettings.setShowSpecies(enabled);
            }

            if (config.contains("Tooltip.ShowLevel"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowLevel", ChatSettings.DEFAULT_SHOW_LEVEL);
                ChatSettings.setShowLevel(enabled);
            }

            if (config.contains("Tooltip.ShowTypes"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowTypes", ChatSettings.DEFAULT_SHOW_TYPES);
                ChatSettings.setShowTypes(enabled);
            }

            if (config.contains("Tooltip.ShowAbilities"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowAbilities", ChatSettings.DEFAULT_SHOW_ABILITIES);
                ChatSettings.setShowAbilities(enabled);
            }

            if (config.contains("Tooltip.ShowNature"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowNature", ChatSettings.DEFAULT_SHOW_NATURE);
                ChatSettings.setShowNature(enabled);
            }

            if (config.contains("Tooltip.ShowIVs"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowIVs", ChatSettings.DEFAULT_SHOW_IVS);
                ChatSettings.setShowIVs(enabled);
            }

            if (config.contains("Tooltip.ShowEVs"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowEVs", ChatSettings.DEFAULT_SHOW_EVS);
                ChatSettings.setShowEVs(enabled);
            }

            if (config.contains("Tooltip.ShowMoves"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowMoves", ChatSettings.DEFAULT_SHOW_MOVES);
                ChatSettings.setShowMoves(enabled);
            }

            if (config.contains("Tooltip.ShowGender"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowGender", ChatSettings.DEFAULT_SHOW_GENDER);
                ChatSettings.setShowGender(enabled);
            }

            if (config.contains("Tooltip.ShowFriendship"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowFriendship", ChatSettings.DEFAULT_SHOW_FRIENDSHIP);
                ChatSettings.setShowFriendship(enabled);
            }

            if (config.contains("Tooltip.ShowHeldItem"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowHeldItem", ChatSettings.DEFAULT_SHOW_HELD_ITEM);
                ChatSettings.setShowHeldItem(enabled);
            }

            if (config.contains("Tooltip.ShowBall"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowBall", ChatSettings.DEFAULT_SHOW_BALL);
                ChatSettings.setShowBall(enabled);
            }

            if (config.contains("Tooltip.ShowSize"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowSize", ChatSettings.DEFAULT_SHOW_SIZE);
                ChatSettings.setShowSize(enabled);
            }

            if (config.contains("Tooltip.ShowEggGroups"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowEggGroups", ChatSettings.DEFAULT_SHOW_EGG_GROUPS);
                ChatSettings.setShowEggGroups(enabled);
            }

            if (config.contains("Tooltip.ShowNeutered"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowNeutered", ChatSettings.DEFAULT_SHOW_NEUTERED);
                ChatSettings.setShowNeutered(enabled);
            }

            if (config.contains("Tooltip.ShowOT"))
            {
                boolean enabled = config.getOrElse("Tooltip.ShowOT", ChatSettings.DEFAULT_SHOW_OT);
                ChatSettings.setShowOT(enabled);
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

            if (config.contains("Tooltip.BoldHyperTrainingValues"))
            {
                boolean enabled = config.getOrElse("Tooltip.BoldHyperTrainingValues", ChatSettings.DEFAULT_BOLD_HYPER_TRAINING_VALUES);
                ChatSettings.setBoldHyperTrainingValues(enabled);
            }

            if (config.contains("Tooltip.ItalicHyperTrainingValues"))
            {
                boolean enabled = config.getOrElse("Tooltip.ItalicHyperTrainingValues", ChatSettings.DEFAULT_ITALIC_HYPER_TRAINING_VALUES);
                ChatSettings.setItalicHyperTrainingValues(enabled);
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
            # Whether to show a Pokémon's species in hover text (true/false)
            ShowSpecies = true
            # Whether to show a Pokémon's level in hover text (true/false)
            ShowLevel = true
            # Whether to show a Pokémon's types in hover text (true/false)
            ShowTypes = true
            # Whether to show a Pokémon's abilities in hover text (true/false)
            ShowAbilities = true
            # Whether to show a Pokémon's nature in hover text (true/false)
            ShowNature = true
            # Whether to show a Pokémon's IVs in hover text (true/false)
            ShowIVs = true
            # Whether to show a Pokémon's EVs in hover text (true/false)
            ShowEVs = true
            # Whether to show a Pokémon's moves in hover text (true/false)
            ShowMoves = true
            # Whether to show a Pokémon's gender in hover text (true/false)
            ShowGender = true
            # Whether to show a Pokémon's friendship in hover text (true/false)
            ShowFriendship = true
            # Whether to show a Pokémon's held item in hover text (true/false)
            ShowHeldItem = true
            # Whether to show a Pokémon's caught ball in hover text (true/false)
            ShowBall = true
            # Whether to show a Pokémon's size in hover text (true/false)
            ShowSize = true
            # Whether to show a Pokémon's egg groups in hover text (true/false)
            ShowEggGroups = true
            # Whether to show a Pokémon's neutered state in hover text (true/false)
            ShowNeutered = true
            # Whether to show a Pokémon's original trainer in hover text (true/false)
            ShowOT = true
            # Whether to show a Pokémon's form in hover text when the value is Normal (true/false)
            ShowFormIfNormal = true
            # Whether to show a Pokémon's neutered state in hover text when the value is False (true/false)
            ShowNeuteredIfFalse = true
            # Whether hyper trained IV stat values should appear in bold (true/false)
            BoldHyperTrainingValues = true
            # Whether hyper trained IV stat values should appear in italic (true/false)
            ItalicHyperTrainingValues = false
            """;
        
        Files.writeString(file.toPath(), defaultContent);
    }
}
