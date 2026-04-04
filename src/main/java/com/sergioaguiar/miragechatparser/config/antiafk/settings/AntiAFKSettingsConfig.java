package com.sergioaguiar.miragechatparser.config.antiafk.settings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.sergioaguiar.miragechatparser.util.ModLogger;

public class AntiAFKSettingsConfig
{
    private static final Path CONFIG_PATH = Paths.get("config", "miragechatparser", "anti_afk_module", "anti_afk_settings.toml");

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
                ModLogger.info("Generated default anti_afk_settings.toml.");
            }
            catch (IOException e)
            {
                ModLogger.error("Failed to create default anti_afk_settings.toml: %s".formatted(e.getMessage()));
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

            if (config.contains("General.SecondsToAFK"))
            {
                int seconds = config.getOrElse("General.SecondsToAFK", AntiAFKSettings.DEFAULT_SECONDS_TO_AFK);
                AntiAFKSettings.setSecondsToAFK(seconds);
            }

            if (config.contains("General.SecondsToAFKKick"))
            {
                int seconds = config.getOrElse("General.SecondsToAFKKick", AntiAFKSettings.DEFAULT_SECONDS_TO_AFK_KICK);
                AntiAFKSettings.setSecondsToAFKKick(seconds);
            }

            if (config.contains("General.SecondsBetweenQuestions"))
            {
                int seconds = config.getOrElse("General.SecondsBetweenQuestions", AntiAFKSettings.DEFAULT_SECONDS_BETWEEN_QUESTIONS);
                AntiAFKSettings.setSecondsBetweenQuestions(seconds);
            }

            if (config.contains("General.FailedQuestionsBeforeKick"))
            {
                int questionAmount = config.getOrElse("General.FailedQuestionsBeforeKick", AntiAFKSettings.DEFAULT_FAILED_QUESTIONS_BEFORE_KICK);
                AntiAFKSettings.setFailedQuestionsBeforeKick(questionAmount);
            }

            if (config.contains("General.MinimumPositionChangeForMovementRegister"))
            {
                double minimum = config.getOrElse("General.MinimumPositionChangeForMovementRegister", AntiAFKSettings.DEFAULT_MINIMUM_POSITION_CHANGE_FOR_MOVEMENT_REGISTER);
                AntiAFKSettings.setMinimumPositionChangeForMovementRegister(minimum);
            }

            if (config.contains("General.MinimumPitchChangeForCameraMovementRegister"))
            {
                Number minimum = config.getOrElse("General.MinimumPitchChangeForCameraMovementRegister", AntiAFKSettings.DEFAULT_MINIMUM_PITCH_CHANGE_FOR_CAMERA_MOVEMENT_REGISTER);
                AntiAFKSettings.setMinimumPitchChangeForCameraMovementRegister(minimum.floatValue());
            }

            if (config.contains("General.MinimumYawChangeForCameraMovementRegister"))
            {
                Number minimum = config.getOrElse("General.MinimumYawChangeForCameraMovementRegister", AntiAFKSettings.DEFAULT_MINIMUM_YAW_CHANGE_FOR_CAMERA_MOVEMENT_REGISTER);
                AntiAFKSettings.setMinimumYawChangeForCameraMovementRegister(minimum.floatValue());
            }

            ModLogger.info("Setting configurations successfully loaded from anti_afk_settings.toml.");
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to load anti_afk_settings.toml: %s".formatted(e.getMessage()));
        }
    }

    private static void createDefaultConfig(File file) throws IOException
    {
        String defaultContent = """
            # MirageChatParser - Anti-AFK Settings Configuration
            # With the default values, players will become AFK after 10 minutes of no relevant actions, followed by a kick if they remain inactive for 20 more minutes.
            # Players will also have to answer questions every 10 minutes. If someone is unable to answer three times in a row, they are also kicked (roughly 30 minutes).

            [General]
            # The number of seconds before a player is flagged as AFK if not enough relevant actions were taken
            SecondsToAFK = 600
            # The number of seconds before a player is kicked for being AFK, after being flagged as AFK (while still lacking relevant actions)
            SecondsToAFKKick = 1200
            # The number of seconds between anti-AFK questions (do not require the player being flagged as AFK to trigger)
            SecondsBetweenQuestions = 600
            # The number of ignored anti-AFK questions before getting kicked (regardless of AFK state)
            FailedQuestionsBeforeKick = 3
            # The minimum movement a player must do per tick to count as enough movement to reset the movement-based AFK timer
            MinimumPositionChangeForMovementRegister = 0.05
            # The minimum camera pitch change to do per tick to count as enough camera movement to reset the camera-based AFK timer
            MinimumPitchChangeForCameraMovementRegister = 2.0
            # The minimum camera yaw change to do per tick to count as enough camera movement to reset the camera-based AFK timer
            MinimumYawChangeForCameraMovementRegister = 2.0
            """;
        
        Files.writeString(file.toPath(), defaultContent);
    }
}
