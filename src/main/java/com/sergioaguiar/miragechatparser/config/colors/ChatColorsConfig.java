package com.sergioaguiar.miragechatparser.config.colors;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.minecraft.text.TextColor;

public class ChatColorsConfig
{
    private static final Path CONFIG_PATH = Paths.get("config", "miragechatparser", "chat_colors.toml");

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

            if (config.contains("Type.Normal"))
            {
                String color = config.get("Type.Normal");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorNormal(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Fire"))
            {
                String color = config.get("Type.Fire");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorFire(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Water"))
            {
                String color = config.get("Type.Water");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorWater(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Electric"))
            {
                String color = config.get("Type.Electric");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorElectric(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Grass"))
            {
                String color = config.get("Type.Grass");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorGrass(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Ice"))
            {
                String color = config.get("Type.Ice");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorIce(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Fighting"))
            {
                String color = config.get("Type.Fighting");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorFighting(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Poison"))
            {
                String color = config.get("Type.Poison");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorPoison(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Ground"))
            {
                String color = config.get("Type.Ground");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorGround(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Flying"))
            {
                String color = config.get("Type.Flying");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorFlying(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Psychic"))
            {
                String color = config.get("Type.Psychic");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorPsychic(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Bug"))
            {
                String color = config.get("Type.Bug");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorBug(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Rock"))
            {
                String color = config.get("Type.Rock");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorRock(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Ghost"))
            {
                String color = config.get("Type.Ghost");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorGhost(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Dragon"))
            {
                String color = config.get("Type.Dragon");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorDragon(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Dark"))
            {
                String color = config.get("Type.Dark");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorDark(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Steel"))
            {
                String color = config.get("Type.Steel");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorSteel(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Type.Fairy"))
            {
                String color = config.get("Type.Fairy");
                if (color != null && !color.isEmpty())
                    ChatColors.setTypeColorFairy(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Command.PrefixColor"))
            {
                String color = config.get("Command.PrefixColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setCommandPrefixColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Command.ValueColor"))
            {
                String color = config.get("Command.ValueColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setCommandValueColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Command.PlayerColor"))
            {
                String color = config.get("Command.PlayerColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setCommandPlayerColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Hoverable.BracketColor"))
            {
                String color = config.get("Hoverable.BracketColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setHoverableBracketColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Hoverable.BracketShinyColor"))
            {
                String color = config.get("Hoverable.BracketShinyColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setHoverableBracketShinyColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Hoverable.BracketErrorColor"))
            {
                String color = config.get("Hoverable.BracketErrorColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setHoverableBracketErrorColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Hoverable.TextColor"))
            {
                String color = config.get("Hoverable.TextColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setHoverableTextColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Hoverable.TextShinyColor"))
            {
                String color = config.get("Hoverable.TextShinyColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setHoverableTextShinyColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Hoverable.TextErrorColor"))
            {
                String color = config.get("Hoverable.TextErrorColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setHoverableTextErrorColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.LabelColor"))
            {
                String color = config.get("Tooltip.LabelColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipLabelColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.ValueColor"))
            {
                String color = config.get("Tooltip.ValueColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipValueColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.FormColor"))
            {
                String color = config.get("Tooltip.FormColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipFormColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.CurrentExperienceColor"))
            {
                String color = config.get("Tooltip.CurrentExperienceColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipCurrentExperienceColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.TargetExperienceColor"))
            {
                String color = config.get("Tooltip.TargetExperienceColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipTargetExperienceColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.HiddenAbilityColor"))
            {
                String color = config.get("Tooltip.HiddenAbilityColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipHiddenAbilityColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.StatUpColor"))
            {
                String color = config.get("Tooltip.StatUpColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipStatUpColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.StatDownColor"))
            {
                String color = config.get("Tooltip.StatDownColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipStatDownColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.HealthColor"))
            {
                String color = config.get("Tooltip.HealthColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipHealthColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.AttackColor"))
            {
                String color = config.get("Tooltip.AttackColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipAttackColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.DefenseColor"))
            {
                String color = config.get("Tooltip.DefenseColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipDefenseColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.SpAttackColor"))
            {
                String color = config.get("Tooltip.SpAttackColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipSpAttackColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.SpDefenseColor"))
            {
                String color = config.get("Tooltip.SpDefenseColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipSpDefenseColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.SpeedColor"))
            {
                String color = config.get("Tooltip.SpeedColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipSpeedColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.MaleColor"))
            {
                String color = config.get("Tooltip.MaleColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipMaleColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.FemaleColor"))
            {
                String color = config.get("Tooltip.FemaleColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipFemaleColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.GenderlessColor"))
            {
                String color = config.get("Tooltip.GenderlessColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipGenderlessColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.TrueColor"))
            {
                String color = config.get("Tooltip.TrueColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipTrueColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("Tooltip.FalseColor"))
            {
                String color = config.get("Tooltip.FalseColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setTooltipFalseColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("GUI.PartyCheckButtonTitleColor"))
            {
                String color = config.get("GUI.PartyCheckButtonTitleColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setPartyCheckButtonTitleColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("GUI.PartyCheckOptionNameColor"))
            {
                String color = config.get("GUI.PartyCheckOptionNameColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setPartyCheckOptionNameColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("GUI.PartyCheckOptionSplitterColor"))
            {
                String color = config.get("GUI.PartyCheckOptionSplitterColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setPartyCheckOptionSplitterColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("GUI.PartyCheckOptionDescriptionColor"))
            {
                String color = config.get("GUI.PartyCheckOptionDescriptionColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setPartyCheckOptionDescriptionColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("GUI.PartyCheckFooterColor"))
            {
                String color = config.get("GUI.PartyCheckFooterColor");
                if (color != null && !color.isEmpty())
                    ChatColors.setPartyCheckFooterColorColor(TextColor.parse(color).getOrThrow());
            }

            ModLogger.info("Color configurations successfully loaded from chat_colors.toml.");
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to load chat_colors.toml: %s".formatted(e.getMessage()));
        }
    }

    private static void createDefaultConfig(File file) throws IOException
    {
        String defaultContent = """
            # MirageChatParser - Chat Color Configuration
            # You can use standard hex color codes like "#aabbcc"

            [Type]
            Normal = "#a8a77a"
            Fire = "#ee8130"
            Water = "#6390f0"
            Electric = "#f7d02c"
            Grass = "#7ac74c"
            Ice = "#96d9d6"
            Fighting = "#c22e28"
            Poison = "#a33ea1"
            Ground = "#e2bf65"
            Flying = "#a98ff3"
            Psychic = "#f95587"
            Bug = "#a6b91a"
            Rock = "#b6a136"
            Ghost = "#735797"
            Dragon = "#6f35fc"
            Dark = "#705746"
            Steel = "#b7b7ce"
            Fairy = "#d685ad"

            [Command]
            PrefixColor = "#2facdd"
            ValueColor = "#d1d8eb"
            PlayerColor = '#cfe95e'

            [Hoverable]
            BracketColor = "#0a9120"
            BracketShinyColor = "#e7e436"
            BracketErrorColor = "#c00303"
            TextColor = "#21bb3a"
            TextShinyColor = "#b6b30b"
            TextErrorColor = "#e21e1e"

            [Tooltip]
            LabelColor = "#3463e6"
            ValueColor = "#d1d8eb"
            FormColor = "#e6e354"
            CurrentExperienceColor = "#6ec95c"
            TargetExperienceColor = "#37af51"
            HiddenAbilityColor = "#31d6e2"
            StatUpColor = "#60d651"
            StatDownColor = "#d14040"
            HealthColor = "#4f9c45"
            AttackColor = "#b33f3f"
            DefenseColor = "#d1842c"
            SpAttackColor = "#d140ca"
            SpDefenseColor = "#ddda36"
            SpeedColor = "#3bd8dd"
            MaleColor = "#0984f7"
            FemaleColor = "#e16ef0"
            GenderlessColor = "#bdbdbd"
            TrueColor = "#40d440"
            FalseColor = "#d12828"

            [GUI]
            PartyCheckButtonTitleColor = "#1f22b8"
            PartyCheckOptionNameColor = "#3463e6"
            PartyCheckOptionSplitterColor = "#3463e6"
            PartyCheckOptionDescriptionColor = "#d1d8eb"
            PartyCheckFooterColor = "#d1d8eb"
            """;
        
        Files.writeString(file.toPath(), defaultContent);
    }
}
