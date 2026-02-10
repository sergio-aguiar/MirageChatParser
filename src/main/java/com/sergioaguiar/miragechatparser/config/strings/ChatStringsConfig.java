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

            if (config.contains("Value.ClosedSheet"))
            {
                String string = config.get("Value.ClosedSheet");
                if (string != null && !string.isEmpty())
                    ChatStrings.setClosedSheetString(string);
            }

            if (config.contains("Value.UnknownPlayer"))
            {
                String string = config.get("Value.UnknownPlayer");
                if (string != null && !string.isEmpty())
                    ChatStrings.setUnknownPlayerString(string);
            }

            if (config.contains("GUI.PartyCheckShoutTypeTitle"))
            {
                String string = config.get("GUI.PartyCheckShoutTypeTitle");
                if (string != null && !string.isEmpty())
                    ChatStrings.setPartyCheckShoutTypeTitleString(string);
            }

            if (config.contains("GUI.PartyCheckShoutVisibilityTitle"))
            {
                String string = config.get("GUI.PartyCheckShoutVisibilityTitle");
                if (string != null && !string.isEmpty())
                    ChatStrings.setPartyCheckShoutVisibilityTitleString(string);
            }

            if (config.contains("GUI.PartyCheckPokeShoutAllTitle"))
            {
                String string = config.get("GUI.PartyCheckPokeShoutAllTitle");
                if (string != null && !string.isEmpty())
                    ChatStrings.setPartyCheckPokeShoutAllTitleString(string);
            }

            if (config.contains("GUI.PartyCheckTitle"))
            {
                String string = config.get("GUI.PartyCheckTitle");
                if (string != null && !string.isEmpty())
                    ChatStrings.setPartyCheckTitleString(string);
            }

            if (config.contains("GUI.GeneralShoutTypeName"))
            {
                String string = config.get("GUI.GeneralShoutTypeName");
                if (string != null && !string.isEmpty())
                    ChatStrings.setGeneralShoutTypeNameString(string);
            }

            if (config.contains("GUI.GeneralShoutTypeDescription"))
            {
                String string = config.get("GUI.GeneralShoutTypeDescription");
                if (string != null && !string.isEmpty())
                    ChatStrings.setGeneralShoutTypeDescriptionString(string);
            }

            if (config.contains("GUI.RideShoutTypeName"))
            {
                String string = config.get("GUI.RideShoutTypeName");
                if (string != null && !string.isEmpty())
                    ChatStrings.setRideShoutTypeNameString(string);
            }

            if (config.contains("GUI.RideShoutTypeDescription"))
            {
                String string = config.get("GUI.RideShoutTypeDescription");
                if (string != null && !string.isEmpty())
                    ChatStrings.setRideShoutTypeDescriptionString(string);
            }

            if (config.contains("GUI.RibbonsShoutTypeName"))
            {
                String string = config.get("GUI.RibbonsShoutTypeName");
                if (string != null && !string.isEmpty())
                    ChatStrings.setRibbonShoutTypeNameString(string);
            }

            if (config.contains("GUI.RibbonsShoutTypeDescription"))
            {
                String string = config.get("GUI.RibbonsShoutTypeDescription");
                if (string != null && !string.isEmpty())
                    ChatStrings.setRibbonShoutTypeDescriptionString(string);
            }

            if (config.contains("GUI.OpenShoutVisibilityName"))
            {
                String string = config.get("GUI.OpenShoutVisibilityName");
                if (string != null && !string.isEmpty())
                    ChatStrings.setOpenShoutVisibilityNameString(string);
            }

            if (config.contains("GUI.OpenShoutVisibilityDescription"))
            {
                String string = config.get("GUI.OpenShoutVisibilityDescription");
                if (string != null && !string.isEmpty())
                    ChatStrings.setOpenShoutVisibilityDescriptionString(string);
            }

            if (config.contains("GUI.ClosedShoutVisibilityName"))
            {
                String string = config.get("GUI.ClosedShoutVisibilityName");
                if (string != null && !string.isEmpty())
                    ChatStrings.setClosedShoutVisibilityNameString(string);
            }

            if (config.contains("GUI.ClosedShoutVisibilityDescription"))
            {
                String string = config.get("GUI.ClosedShoutVisibilityDescription");
                if (string != null && !string.isEmpty())
                    ChatStrings.setClosedShoutVisibilityDescriptionString(string);
            }

            if (config.contains("GUI.SelfShoutVisibilityName"))
            {
                String string = config.get("GUI.SelfShoutVisibilityName");
                if (string != null && !string.isEmpty())
                    ChatStrings.setSelfShoutVisibilityNameString(string);
            }

            if (config.contains("GUI.SelfShoutVisibilityDescription"))
            {
                String string = config.get("GUI.SelfShoutVisibilityDescription");
                if (string != null && !string.isEmpty())
                    ChatStrings.setSelfShoutVisibilityDescriptionString(string);
            }

            if (config.contains("GUI.PartyCheckFooter"))
            {
                String string = config.get("GUI.PartyCheckFooter");
                if (string != null && !string.isEmpty())
                    ChatStrings.setPartyCheckFooterString(string);
            }

            if (config.contains("GUI.PartyCheckSplitter"))
            {
                String string = config.get("GUI.PartyCheckSplitter");
                if (string != null && !string.isEmpty())
                    ChatStrings.setPartyCheckSplitterString(string);
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
            ClosedSheet = "Hidden"
            UnknownPlayer = "Unknown Player"

            [GUI]
            PartyCheckTitle = "Party Check"
            PartyCheckShoutTypeTitle = "Shout Type"
            PartyCheckShoutVisibilityTitle = "Shout Visibility"
            PartyCheckPokeShoutAllTitle = "PokeShoutAll"
            GeneralShoutTypeName = "General Stats"
            GeneralShoutTypeDescription = "General species/battle-specific information"
            RideShoutTypeName = "Ride Stats"
            RideShoutTypeDescription = "Mount-specific riding information"
            RibbonsShoutTypeName = "Ribbons"
            RibbonsShoutTypeDescription = "Pokémon ribbon-related information"
            OpenShoutVisibilityName = "Open"
            OpenShoutVisibilityDescription = "All information is displayed publicly"
            ClosedShoutVisibilityName = "Closed"
            ClosedShoutVisibilityDescription = "All battle-relevant information is hidden"
            SelfShoutVisibilityName = "Self"
            SelfShoutVisibilityDescription = "All information is only viewable by you"
            PartyCheckFooter = "Click to cycle through the available options."
            PartyCheckPartyShoutAllFooter = "Click to display all Pokémon in chat."
            PartyCheckSplitter = " - "
            """;
        
        Files.writeString(file.toPath(), defaultContent);
    }
}
