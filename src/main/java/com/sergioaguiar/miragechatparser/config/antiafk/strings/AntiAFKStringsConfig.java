package com.sergioaguiar.miragechatparser.config.antiafk.strings;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.sergioaguiar.miragechatparser.util.ModLogger;

public class AntiAFKStringsConfig
{
    private static final Path CONFIG_PATH = Paths.get("config", "miragechatparser", "anti_afk_module", "anti_afk_strings.toml");

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
                ModLogger.info("Generated default anti_afk_strings.toml.");
            }
            catch (IOException e)
            {
                ModLogger.error("Failed to create default anti_afk_strings.toml: %s".formatted(e.getMessage()));
                return;
            }
        }

        try (CommentedFileConfig config = CommentedFileConfig.builder(file)
                .preserveInsertionOrder()
                .sync()
                .build())
        {
            config.load();

            if (config.contains("TextObjectPlaceholder.PlaceholderAFKText"))
            {
                String string = config.get("TextObjectPlaceholder.PlaceholderAFKText");
                if (string != null && !string.isEmpty())
                    AntiAFKStrings.setTextObjectPlaceholderAFKText(string);
            }

            if (config.contains("MCFormattingPlaceholder.PlaceholderAFKText"))
            {
                String string = config.get("MCFormattingPlaceholder.PlaceholderAFKText");
                if (string != null && !string.isEmpty())
                    AntiAFKStrings.setMcFormattingPlaceholderAFKText(string);
            }
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to load anti_afk_strings.toml: %s".formatted(e.getMessage()));
        }
    }

    private static void createDefaultConfig(File file) throws IOException
    {
        String defaultContent = """
            # MirageChatParser - Anti-AFK Strings Configuration    

            # This is for the %mirageessentials:afk-textobject% placeholder.
            # It returns a Minecraft Text object with the configured styles (such as colors from anti_afk_colors.toml).
            # The returned text looks like "[<TextObjectPlaceholder.PlaceholderAFKText>]".
            [TextObjectPlaceholder]
            PlaceholderAFKText = "AFK"

            # This is for the %mirageessentials:afk-mcformatting% placeholder.
            # It returns the configured text, meaning it will display correctly if you need to use Minecraft formatting.
            # The return will be exactly what you specify in MCFormattingPlaceholder.PlaceholderAFKText .
            # This is what you would use with other mods like TAB.
            [MCFormattingPlaceholder]
            PlaceholderAFKText = "&7[&bAFK&7]"
        """;
        
        Files.writeString(file.toPath(), defaultContent);
    }
}
