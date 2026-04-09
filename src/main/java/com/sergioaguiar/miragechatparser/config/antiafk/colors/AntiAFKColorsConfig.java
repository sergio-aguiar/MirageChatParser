package com.sergioaguiar.miragechatparser.config.antiafk.colors;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.minecraft.text.TextColor;

public class AntiAFKColorsConfig
{
    private static final Path CONFIG_PATH = Paths.get("config", "miragechatparser", "anti_afk_module", "anti_afk_colors.toml");

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
                ModLogger.info("Generated default anti_afk_colors.toml.");
            }
            catch (IOException e)
            {
                ModLogger.error("Failed to create default anti_afk_colors.toml: %s".formatted(e.getMessage()));
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

            if (config.contains("AFKChecker.PrefixColor"))
            {
                String color = config.get("AFKChecker.PrefixColor");
                if (color != null && !color.isEmpty())
                    AntiAFKColors.setAFKCheckerPrefixColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("AFKChecker.TextColor"))
            {
                String color = config.get("AFKChecker.TextColor");
                if (color != null && !color.isEmpty())
                    AntiAFKColors.setAFKCheckerTextColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("AFKChecker.PlayerColor"))
            {
                String color = config.get("AFKChecker.PlayerColor");
                if (color != null && !color.isEmpty())
                    AntiAFKColors.setAFKCheckerPlayerColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("AFKChecker.GoneColor"))
            {
                String color = config.get("AFKChecker.GoneColor");
                if (color != null && !color.isEmpty())
                    AntiAFKColors.setAFKCheckerGoneColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("AFKChecker.TimeColor"))
            {
                String color = config.get("AFKChecker.TimeColor");
                if (color != null && !color.isEmpty())
                    AntiAFKColors.setAFKCheckerTimeColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("AFKapcha.PrefixColor"))
            {
                String color = config.get("AFKapcha.PrefixColor");
                if (color != null && !color.isEmpty())
                    AntiAFKColors.setAFKCapchaPrefixColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("AFKapcha.TextColor"))
            {
                String color = config.get("AFKapcha.TextColor");
                if (color != null && !color.isEmpty())
                    AntiAFKColors.setAFKCapchaTextColor(TextColor.parse(color).getOrThrow());
            }

            if (config.contains("AFKapcha.QuestionColor"))
            {
                String color = config.get("AFKapcha.QuestionColor");
                if (color != null && !color.isEmpty())
                    AntiAFKColors.setAFKCapchaQuestionColor(TextColor.parse(color).getOrThrow());
            }

            ModLogger.info("Setting configurations successfully loaded from anti_afk_colors.toml.");
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to load anti_afk_colors.toml: %s".formatted(e.getMessage()));
        }
    }

    private static void createDefaultConfig(File file) throws IOException
    {
        String defaultContent = """
            # MirageChatParser - Anti-AFK Color Configuration
            # You can use standard hex color codes like "#aabbcc"

            [AFKChecker]
            PrefixColor = "#2facdd"
            TextColor = "#d1d8eb"
            PlayerColor = "#cfe95e"
            GoneColor = "#2facdd"
            TimeColor = "#cfe95e"

            [AFKapcha]
            PrefixColor = "#b81106"
            TextColor = "#d1d8eb"
            QuestionColor = "#cfe95e"
            """;
        
        Files.writeString(file.toPath(), defaultContent);
    }
}
