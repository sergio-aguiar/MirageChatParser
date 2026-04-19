package com.sergioaguiar.miragechatparser.config.chatparser.textures;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.sergioaguiar.miragechatparser.util.ModLogger;

public class GUITexturesConfig
{
    private static final Path CONFIG_PATH = Paths.get("config", "miragechatparser", "chatparser", "gui_textures.toml");

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
                ModLogger.info("Generated default gui_textures.toml.");
            }
            catch (IOException e)
            {
                ModLogger.error("Failed to create default gui_textures.toml: %s".formatted(e.getMessage()));
                return;
            }
        }

        try (CommentedFileConfig config = CommentedFileConfig.builder(file)
                .preserveInsertionOrder()
                .sync()
                .build())
        {
            config.load();

            if (config.contains("ShoutType.General.Item"))
            {
                String item = config.getOrElse("ShoutType.General.Item", GUITextures.DEFAULT_SHOUT_TYPE_GENERAL_ITEM);
                GUITextures.setShoutTypeGeneralItem(item);
            }

            if (config.contains("ShoutType.General.CustomModelData"))
            {
                int data = config.getOrElse("ShoutType.General.CustomModelData", GUITextures.DEFAULT_SHOUT_TYPE_GENERAL_CUSTOM_MODEL_DATA);
                GUITextures.setShoutTypeGeneralCustomModelData(data);
            }

            if (config.contains("ShoutType.Ride.Item"))
            {
                String item = config.getOrElse("ShoutType.Ride.Item", GUITextures.DEFAULT_SHOUT_TYPE_RIDE_ITEM);
                GUITextures.setShoutTypeRideItem(item);
            }

            if (config.contains("ShoutType.Ride.CustomModelData"))
            {
                int data = config.getOrElse("ShoutType.Ride.CustomModelData", GUITextures.DEFAULT_SHOUT_TYPE_RIDE_CUSTOM_MODEL_DATA);
                GUITextures.setShoutTypeRideCustomModelData(data);
            }

            if (config.contains("ShoutType.Ribbon.Item"))
            {
                String item = config.getOrElse("ShoutType.Ribbon.Item", GUITextures.DEFAULT_SHOUT_TYPE_RIBBON_ITEM);
                GUITextures.setShoutTypeRibbonItem(item);
            }

            if (config.contains("ShoutType.Ribbon.CustomModelData"))
            {
                int data = config.getOrElse("ShoutType.Ribbon.CustomModelData", GUITextures.DEFAULT_SHOUT_TYPE_RIBBON_CUSTOM_MODEL_DATA);
                GUITextures.setShoutTypeRibbonCustomModelData(data);
            }

            if (config.contains("ShoutVisibility.Open.Item"))
            {
                String item = config.getOrElse("ShoutVisibility.Open.Item", GUITextures.DEFAULT_SHOUT_VISIBILITY_OPEN_ITEM);
                GUITextures.setShoutVisibilityOpenItem(item);
            }

            if (config.contains("ShoutVisibility.Open.CustomModelData"))
            {
                int data = config.getOrElse("ShoutVisibility.Open.CustomModelData", GUITextures.DEFAULT_SHOUT_VISIBILITY_OPEN_CUSTOM_MODEL_DATA);
                GUITextures.setShoutVisibilityOpenCustomModelData(data);
            }

            if (config.contains("ShoutVisibility.Closed.Item"))
            {
                String item = config.getOrElse("ShoutVisibility.Closed.Item", GUITextures.DEFAULT_SHOUT_VISIBILITY_CLOSED_ITEM);
                GUITextures.setShoutVisibilityClosedItem(item);
            }

            if (config.contains("ShoutVisibility.Closed.CustomModelData"))
            {
                int data = config.getOrElse("ShoutVisibility.Closed.CustomModelData", GUITextures.DEFAULT_SHOUT_VISIBILITY_CLOSED_CUSTOM_MODEL_DATA);
                GUITextures.setShoutVisibilityClosedCustomModelData(data);
            }

            if (config.contains("ShoutVisibility.Self.Item"))
            {
                String item = config.getOrElse("ShoutVisibility.Self.Item", GUITextures.DEFAULT_SHOUT_VISIBILITY_SELF_ITEM);
                GUITextures.setShoutVisibilitySelfItem(item);
            }

            if (config.contains("ShoutVisibility.Self.CustomModelData"))
            {
                int data = config.getOrElse("ShoutVisibility.Self.CustomModelData", GUITextures.DEFAULT_SHOUT_VISIBILITY_SELF_CUSTOM_MODEL_DATA);
                GUITextures.setShoutVisibilitySelfCustomModelData(data);
            }

            if (config.contains("PartyShoutAll.Item"))
            {
                String item = config.getOrElse("PartyShoutAll.Item", GUITextures.DEFAULT_PARTY_SHOUT_ALL_ITEM);
                GUITextures.setPartyShoutAllItem(item);
            }

            if (config.contains("PartyShoutAll.CustomModelData"))
            {
                int data = config.getOrElse("PartyShoutAll.CustomModelData", GUITextures.DEFAULT_PARTY_SHOUT_ALL_CUSTOM_MODEL_DATA);
                GUITextures.setPartyShoutAllCustomModelData(data);
            }

            ModLogger.info("GUI textures successfully loaded from gui_textures.toml.");
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to load gui_textures.toml: %s".formatted(e.getMessage()));
        }
    }

    private static void createDefaultConfig(File file) throws IOException
    {
        String defaultContent = """
            # MirageChatParser - GUI Textures Configuration
            # Item format: minecraft:item_name
            # CustomModelData = 0 means vanilla texture

            [ShoutType.General]
            Item = "minecraft:white_concrete"
            CustomModelData = 0

            [ShoutType.Ride]
            Item = "minecraft:orange_concrete"
            CustomModelData = 0

            [ShoutType.Ribbon]
            Item = "minecraft:pink_concrete"
            CustomModelData = 0

            [ShoutVisibility.Open]
            Item = "minecraft:green_concrete"
            CustomModelData = 0

            [ShoutVisibility.Closed]
            Item = "minecraft:red_concrete"
            CustomModelData = 0

            [ShoutVisibility.Self]
            Item = "minecraft:blue_concrete"
            CustomModelData = 0

            [PartyShoutAll]
            Item = "minecraft:yellow_concrete"
            CustomModelData = 0
            """;

        Files.writeString(file.toPath(), defaultContent);
    }
}