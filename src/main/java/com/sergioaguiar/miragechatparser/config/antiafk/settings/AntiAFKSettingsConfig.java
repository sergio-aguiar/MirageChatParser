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
                .sync()
                .build())
        {
            config.load();

            if (config.contains("General.NoKickMode"))
            {
                boolean enabled = config.getOrElse("General.NoKickMode", AntiAFKSettings.DEFAULT_NO_KICK_MODE_ENABLED);
                AntiAFKSettings.setNoKickMode(enabled);
            }

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

            if (config.contains("General.SecondsBetweenCaptcha"))
            {
                int seconds = config.getOrElse("General.SecondsBetweenCaptcha", AntiAFKSettings.DEFAULT_SECONDS_BETWEEN_CAPTCHA);
                AntiAFKSettings.setSecondsBetweenCaptcha(seconds);
            }

            if (config.contains("General.FailedCaptchaBeforeKick"))
            {
                int captchaAmount = config.getOrElse("General.FailedCaptchaBeforeKick", AntiAFKSettings.DEFAULT_FAILED_CAPTCHA_BEFORE_KICK);
                AntiAFKSettings.setFailedCaptchaBeforeKick(captchaAmount);
            }

            if (config.contains("General.CaptchaLength"))
            {
                int length = Math.clamp(config.getOrElse("General.CaptchaLength", AntiAFKSettings.DEFAULT_CAPTCHA_LENGTH), 1, 9) ;
                AntiAFKSettings.setCaptchaLength(length);
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

            if (config.contains("Message.HideAFKCheckerMessagePrefix"))
            {
                boolean enabled = config.getOrElse("Message.HideAFKCheckerMessagePrefix", AntiAFKSettings.DEFAULT_HIDE_AFK_CHECKER_MESSAGE_PREFIX);
                AntiAFKSettings.setHideAFKCheckerMessagePrefix(enabled);
            }

            if (config.contains("Message.HideAFKCaptchaMessagePrefix"))
            {
                boolean enabled = config.getOrElse("Message.HideAFKCaptchaMessagePrefix", AntiAFKSettings.DEFAULT_HIDE_AFK_CAPTCHA_MESSAGE_PREFIX);
                AntiAFKSettings.setHideAFKCaptchaMessagePrefix(enabled);
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
            # With the default values, players will become AFK after 10 minutes of no relevant actions, followed by a kick if they remain inactive for 50 more minutes.
            # Players will also have to answer CAPTCHA every 15 minutes. If someone is unable to answer three times in a row, they are also kicked (roughly 60 minutes).

            [General]
            # Whether No-Kick Mode (only reports when players would have been kicked, rather than kicking - good for testing) should be enabled or not (true/false)
            NoKickMode = false
            # The number of seconds before a player is flagged as AFK if not enough relevant actions were taken
            SecondsToAFK = 600
            # The number of seconds before a player is kicked for being AFK, after being flagged as AFK (while still lacking relevant actions)
            SecondsToAFKKick = 3000
            # The number of seconds between anti-AFK CAPTCHA (do not require the player being flagged as AFK to trigger)
            SecondsBetweenCaptcha = 900
            # The number of ignored anti-AFK CAPTCHA before getting kicked (regardless of AFK state)
            FailedCaptchaBeforeKick = 3
            # The amount of digits in a text-based CAPTCHA [1-9]
            CaptchaLength = 4
            # The minimum movement a player must do per tick to count as enough movement to reset the movement-based AFK timer
            MinimumPositionChangeForMovementRegister = 0.05
            # The minimum camera pitch change to do per tick to count as enough camera movement to reset the camera-based AFK timer
            MinimumPitchChangeForCameraMovementRegister = 2.0
            # The minimum camera yaw change to do per tick to count as enough camera movement to reset the camera-based AFK timer
            MinimumYawChangeForCameraMovementRegister = 2.0

            [Message]
            HideAFKCheckerMessagePrefix = false
            HideAFKCaptchaMessagePrefix = false
            """;
        
        Files.writeString(file.toPath(), defaultContent);
    }
}
