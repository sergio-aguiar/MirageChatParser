package com.sergioaguiar.miragechatparser.config.strings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.sergioaguiar.miragechatparser.util.ModLogger;

public class ChatStringsConfig
{
    private static final Path CONFIG_PATH = Paths.get("config", "miragechatparser", "chat_strings.toml");

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
                ModLogger.info("Generated default chat_strings.toml.");
            }
            catch (IOException e)
            {
                ModLogger.error("Failed to create default chat_strings.toml: %s".formatted(e.getMessage()));
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

            if (config.contains("General.True"))
            {
                String string = config.get("General.True");
                if (string != null && !string.isEmpty())
                    ChatStrings.setTrueString(string);
            }

            if (config.contains("General.False"))
            {
                String string = config.get("General.False");
                if (string != null && !string.isEmpty())
                    ChatStrings.setFalseString(string);
            }

            if (config.contains("Hoverable.ShinyIcon"))
            {
                String string = config.get("Hoverable.ShinyIcon");
                if (string != null && !string.isEmpty())
                    ChatStrings.setShinyIconString(string);
            }

            if (config.contains("Label.Species"))
            {
                String string = config.get("Label.Species");
                if (string != null && !string.isEmpty())
                    ChatStrings.setSpeciesString(string);
            }

            if (config.contains("Label.Level"))
            {
                String string = config.get("Label.Level");
                if (string != null && !string.isEmpty())
                    ChatStrings.setLevelString(string);
            }

            if (config.contains("Label.Type"))
            {
                String string = config.get("Label.Type");
                if (string != null && !string.isEmpty())
                    ChatStrings.setTypeString(string);
            }

            if (config.contains("Label.Types"))
            {
                String string = config.get("Label.Types");
                if (string != null && !string.isEmpty())
                    ChatStrings.setTypesString(string);
            }

            if (config.contains("Label.Ability"))
            {
                String string = config.get("Label.Ability");
                if (string != null && !string.isEmpty())
                    ChatStrings.setAbilityString(string);
            }

            if (config.contains("Label.Nature"))
            {
                String string = config.get("Label.Nature");
                if (string != null && !string.isEmpty())
                    ChatStrings.setNatureString(string);
            }

            if (config.contains("Label.NatureMinted"))
            {
                String string = config.get("Label.NatureMinted");
                if (string != null && !string.isEmpty())
                    ChatStrings.setNatureMintedString(string);
            }

            if (config.contains("Label.IVs"))
            {
                String string = config.get("Label.IVs");
                if (string != null && !string.isEmpty())
                    ChatStrings.setIVsString(string);
            }

            if (config.contains("Label.IVsHyperTrained"))
            {
                String string = config.get("Label.IVsHyperTrained");
                if (string != null && !string.isEmpty())
                    ChatStrings.setIVsHyperTrainedString(string);
            }

            if (config.contains("Label.EVs"))
            {
                String string = config.get("Label.EVs");
                if (string != null && !string.isEmpty())
                    ChatStrings.setEVsString(string);
            }

            if (config.contains("Label.Moves"))
            {
                String string = config.get("Label.Moves");
                if (string != null && !string.isEmpty())
                    ChatStrings.setMovesString(string);
            }

            if (config.contains("Label.Gender"))
            {
                String string = config.get("Label.Gender");
                if (string != null && !string.isEmpty())
                    ChatStrings.setGenderString(string);
            }

            if (config.contains("Label.Friendship"))
            {
                String string = config.get("Label.Friendship");
                if (string != null && !string.isEmpty())
                    ChatStrings.setFriendshipString(string);
            }

            if (config.contains("Label.HeldItem"))
            {
                String string = config.get("Label.HeldItem");
                if (string != null && !string.isEmpty())
                    ChatStrings.setHeldItemString(string);
            }

            if (config.contains("Label.CaughtBall"))
            {
                String string = config.get("Label.CaughtBall");
                if (string != null && !string.isEmpty())
                    ChatStrings.setCaughtBallString(string);
            }

            if (config.contains("Label.Size"))
            {
                String string = config.get("Label.Size");
                if (string != null && !string.isEmpty())
                    ChatStrings.setSizeString(string);
            }

            if (config.contains("Label.EggGroups"))
            {
                String string = config.get("Label.EggGroups");
                if (string != null && !string.isEmpty())
                    ChatStrings.setEggGroupsString(string);
            }

            if (config.contains("Label.Neutered"))
            {
                String string = config.get("Label.Neutered");
                if (string != null && !string.isEmpty())
                    ChatStrings.setNeuteredString(string);
            }

            if (config.contains("Label.OriginalTrainer"))
            {
                String string = config.get("Label.OriginalTrainer");
                if (string != null && !string.isEmpty())
                    ChatStrings.setOriginalTrainerString(string);
            }

            if (config.contains("Value.HiddenAbility"))
            {
                String string = config.get("Value.HiddenAbility");
                if (string != null && !string.isEmpty())
                    ChatStrings.setHiddenAbilityString(string);
            }

            if (config.contains("Stat.Health"))
            {
                String string = config.get("Stat.Health");
                if (string != null && !string.isEmpty())
                    ChatStrings.setHealthString(string);
            }

            if (config.contains("Stat.Attack"))
            {
                String string = config.get("Stat.Attack");
                if (string != null && !string.isEmpty())
                    ChatStrings.setAttackString(string);
            }

            if (config.contains("Stat.Defense"))
            {
                String string = config.get("Stat.Defense");
                if (string != null && !string.isEmpty())
                    ChatStrings.setDefenseString(string);
            }

            if (config.contains("Stat.SpecialAttack"))
            {
                String string = config.get("Stat.SpecialAttack");
                if (string != null && !string.isEmpty())
                    ChatStrings.setSpecialAttackString(string);
            }

            if (config.contains("Stat.SpecialDefense"))
            {
                String string = config.get("Stat.SpecialDefense");
                if (string != null && !string.isEmpty())
                    ChatStrings.setSpecialDefenseString(string);
            }

            if (config.contains("Stat.Speed"))
            {
                String string = config.get("Stat.Speed");
                if (string != null && !string.isEmpty())
                    ChatStrings.setSpeedString(string);
            }

            if (config.contains("Value.TypeSeparator"))
            {
                String string = config.get("Value.TypeSeparator");
                if (string != null && !string.isEmpty())
                    ChatStrings.setTypeSeparatorString(string);
            }

            if (config.contains("Value.StatIncrease"))
            {
                String string = config.get("Value.StatIncrease");
                if (string != null && !string.isEmpty())
                    ChatStrings.setStatIncreaseString(string);
            }

            if (config.contains("Value.StatDecrease"))
            {
                String string = config.get("Value.StatDecrease");
                if (string != null && !string.isEmpty())
                    ChatStrings.setStatDecreaseString(string);
            }

            if (config.contains("Value.MoveSeparator"))
            {
                String string = config.get("Value.MoveSeparator");
                if (string != null && !string.isEmpty())
                    ChatStrings.setMoveSeparatorString(string);
            }

            if (config.contains("Value.MaleIcon"))
            {
                String string = config.get("Value.MaleIcon");
                if (string != null && !string.isEmpty())
                    ChatStrings.setMaleIconString(string);
            }

            if (config.contains("Value.FemaleIcon"))
            {
                String string = config.get("Value.FemaleIcon");
                if (string != null && !string.isEmpty())
                    ChatStrings.setFemaleIconString(string);
            }

            if (config.contains("Value.GenderlessIcon"))
            {
                String string = config.get("Value.GenderlessIcon");
                if (string != null && !string.isEmpty())
                    ChatStrings.setGenderlessIconString(string);
            }

            if (config.contains("Value.EmptyHeldItem"))
            {
                String string = config.get("Value.EmptyHeldItem");
                if (string != null && !string.isEmpty())
                    ChatStrings.setEmptyHeldItemString(string);
            }

            if (config.contains("Value.EggGroupsSeparator"))
            {
                String string = config.get("Value.EggGroupsSeparator");
                if (string != null && !string.isEmpty())
                    ChatStrings.setEggGroupsSeparatorString(string);
            }

            ModLogger.info("String configurations successfully loaded from chat_strings.toml.");
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to load chat_strings.toml: %s".formatted(e.getMessage()));
        }
    }

    private static void createDefaultConfig(File file) throws IOException
    {
        String defaultContent = """
            # MirageChatParser - Chat Strings Configuration

            [General]
            True = "Yes"
            False = "No"

            [Hoverable]
            # Another suggestion "☆ "
            ShinyIcon = "★ "

            [Label]
            # Another suggestion, "Species (Form): "
            Species = "Species: "
            # Another suggestion, "Level (EXP/EXP Needed): "
            Level = "Level: "
            Type = "Type: "
            Types = "Types: "
            Ability = "Ability: "
            Nature = "Nature: "
            NatureMinted = "Nature (Mint): "
            IVs = "IVs: "
            IVsHyperTrained = "IVs (Hyper Trained): "
            EVs = "EVs: "
            Moves = "Moves: "
            Gender = "Gender: "
            # Another suggestion, "Happiness: "
            Friendship = "Friendship: "
            HeldItem = "Held Item: "
            CaughtBall = "Caught Ball: "
            Size = "Size: "
            EggGroups = "Egg Groups: "
            Neutered = "Neutered: "
            # Other suggestions, "OT: " and "Original Trainer (OT): "
            OriginalTrainer = "Original Trainer: "

            [Stat]
            Health = "HP"
            Attack = "Atk"
            Defense = "Def"
            SpecialAttack = "SpA"
            SpecialDefense = "SpD"
            Speed = "Spe"

            [Value]
            # Another suggestion, "Hidden Ability"
            HiddenAbility = "HA"
            TypeSeparator = "/"
            # Other suggestions, "↑" and "⇧"
            StatIncrease = "+"
            # Other suggestions, "↓" and "⇩"
            StatDecrease = "-"
            # Another suggestion, " - "
            MoveSeparator = ", "
            MaleIcon = "♂"
            FemaleIcon = "♀"
            GenderlessIcon = "⚲"
            EmptyHeldItem = "None"
            EggGroupsSeparator = ", "
            """;
        
        Files.writeString(file.toPath(), defaultContent);
    }
}
