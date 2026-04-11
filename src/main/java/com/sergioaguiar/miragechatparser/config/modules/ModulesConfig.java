package com.sergioaguiar.miragechatparser.config.modules;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.sergioaguiar.miragechatparser.util.ModLogger;

public class ModulesConfig
{
    private static final Path CONFIG_PATH = Paths.get("config", "miragechatparser", "module_settings.toml");

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
                ModLogger.info("Generated default module_settings.toml.");
            }
            catch (IOException e)
            {
                ModLogger.error("Failed to create default module_settings.toml: %s".formatted(e.getMessage()));
                return;
            }
        }

        try (CommentedFileConfig config = CommentedFileConfig.builder(file)
                .preserveInsertionOrder()
                .sync()
                .build())
        {
            config.load();

            if (config.contains("Modules.EnableChatParserModule"))
            {
                boolean enabled = config.getOrElse("Modules.EnableChatParserModule", Modules.DEFAULT_ENABLE_CHAT_PARSER_MODULE);
                Modules.setEnableChatParserModule(enabled);
            }

            if (config.contains("Modules.EnableAntiAFKModule"))
            {
                boolean enabled = config.getOrElse("Modules.EnableAntiAFKModule", Modules.DEFAULT_ENABLE_ANTI_AFK_MODULE);
                Modules.setEnableAntiAFKModule(enabled);
            }

            ModLogger.info("Setting configurations successfully loaded from module_settings.toml.");
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to load module_settings.toml: %s".formatted(e.getMessage()));
        }
    }

    private static void createDefaultConfig(File file) throws IOException
    {
        String defaultContent = """
            # MirageChatParser - Module Settings Configuration

            [Modules]
            # Whether to start the chat parser module or not (true/false)
            EnableChatParserModule = true
            # Whether to start the anti-AFK module or not (true/false)
            EnableAntiAFKModule = false
            """;
        
        Files.writeString(file.toPath(), defaultContent);
    }
}
