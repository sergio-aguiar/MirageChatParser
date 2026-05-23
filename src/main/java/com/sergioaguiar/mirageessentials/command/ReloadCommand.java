package com.sergioaguiar.mirageessentials.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sergioaguiar.mirageessentials.MirageEssentials;
import com.sergioaguiar.mirageessentials.config.antiafk.colors.AntiAFKColorsConfig;
import com.sergioaguiar.mirageessentials.config.antiafk.settings.AntiAFKSettingsConfig;
import com.sergioaguiar.mirageessentials.config.antiafk.strings.AntiAFKStringsConfig;
import com.sergioaguiar.mirageessentials.config.chatparser.aspects.ChatAspectsConfig;
import com.sergioaguiar.mirageessentials.config.chatparser.colors.ChatColors;
import com.sergioaguiar.mirageessentials.config.chatparser.colors.ChatColorsConfig;
import com.sergioaguiar.mirageessentials.config.chatparser.settings.ChatSettingsConfig;
import com.sergioaguiar.mirageessentials.config.chatparser.sizes.ChatSizesConfig;
import com.sergioaguiar.mirageessentials.config.chatparser.strings.ChatStringsConfig;
import com.sergioaguiar.mirageessentials.config.chatparser.textures.GUITexturesConfig;
import com.sergioaguiar.mirageessentials.config.modules.Modules;
import com.sergioaguiar.mirageessentials.util.LuckPermsUtils;
import com.sergioaguiar.mirageessentials.util.ModLogger;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class ReloadCommand 
{
    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register
            (
                CommandManager.literal("mirageessentials")
                    .then(CommandManager.literal("reload")
                        .requires(source -> LuckPermsUtils.hasPermission(source, "mirageessentials.commands.admin.reload"))
                        .executes(ReloadCommand::executeReload)
                    )
            );
        });
    }

    private static int executeReload(CommandContext<ServerCommandSource> context) throws CommandSyntaxException
    {
        if (Modules.shouldEnableChatParserModule())
        {
            try
            {
                ChatSettingsConfig.load();
                context.getSource().sendFeedback(() -> 
                    {
                        var text = Text.literal("%s » ".formatted(MirageEssentials.MOD_NAME))
                            .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()));
                        
                        text = text
                            .append(Text.literal("Reloaded chat settings configuration.")
                                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));
                        
                        return text;
                    }, 
                    true
                );
                ModLogger.info("Chat setting configuration reloaded successfully.");
            }
            catch (Exception e)
            {
                context.getSource().sendError(Text.literal("%s » Failed to reload settings configuration: %s".formatted(MirageEssentials.MOD_NAME, e.getMessage())));
                ModLogger.error("Error reloading setting config: %s".formatted(e.getMessage()));
                return 1;
            }

            try
            {
                ChatStringsConfig.load();
                context.getSource().sendFeedback(() -> 
                    {
                        var text = Text.literal("%s » ".formatted(MirageEssentials.MOD_NAME))
                            .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()));
                        
                        text = text
                            .append(Text.literal("Reloaded chat strings configuration.")
                                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));
                        
                        return text;
                    }, 
                    true
                );
                ModLogger.info("Chat string configuration reloaded successfully.");
            }
            catch (Exception e)
            {
                context.getSource().sendError(Text.literal("%s » Failed to reload strings configuration: %s".formatted(MirageEssentials.MOD_NAME, e.getMessage())));
                ModLogger.error("Error reloading string config: %s".formatted(e.getMessage()));
                return 1;
            }

            try
            {
                ChatColorsConfig.load();
                context.getSource().sendFeedback(() -> 
                    {
                        var text = Text.literal("%s » ".formatted(MirageEssentials.MOD_NAME))
                            .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()));
                        
                        text = text
                            .append(Text.literal("Reloaded chat color configuration.")
                                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));
                        
                        return text;
                    }, 
                    true
                );
                ModLogger.info("Chat color configuration reloaded successfully.");
            }
            catch (Exception e)
            {
                context.getSource().sendError(Text.literal("%s » Failed to reload color configuration: %s".formatted(MirageEssentials.MOD_NAME, e.getMessage())));
                ModLogger.error("Error reloading color config: %s".formatted(e.getMessage()));
                return 1;
            }

            try
            {
                ChatAspectsConfig.load();
                context.getSource().sendFeedback(() -> 
                    {
                        var text = Text.literal("%s » ".formatted(MirageEssentials.MOD_NAME))
                            .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()));
                        
                        text = text
                            .append(Text.literal("Reloaded chat aspect configuration.")
                                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));
                        
                        return text;
                    }, 
                    true
                );
                ModLogger.info("Chat aspect configuration reloaded successfully.");
            }
            catch (Exception e)
            {
                context.getSource().sendError(Text.literal("%s » Failed to reload aspect configuration: %s".formatted(MirageEssentials.MOD_NAME, e.getMessage())));
                ModLogger.error("Error reloading aspect config: %s".formatted(e.getMessage()));
                return 1;
            }

            try
            {
                ChatSizesConfig.load();
                context.getSource().sendFeedback(() -> 
                    {
                        var text = Text.literal("%s » ".formatted(MirageEssentials.MOD_NAME))
                            .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()));
                        
                        text = text
                            .append(Text.literal("Reloaded chat size configuration.")
                                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));
                        
                        return text;
                    }, 
                    true
                );
                ModLogger.info("Chat size configuration reloaded successfully.");
            }
            catch (Exception e)
            {
                context.getSource().sendError(Text.literal("%s » Failed to reload size configuration: %s".formatted(MirageEssentials.MOD_NAME, e.getMessage())));
                ModLogger.error("Error reloading size config: %s".formatted(e.getMessage()));
                return 1;
            }

            try
            {
                GUITexturesConfig.load();
                context.getSource().sendFeedback(() -> 
                    {
                        var text = Text.literal("%s » ".formatted(MirageEssentials.MOD_NAME))
                            .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()));
                        
                        text = text
                            .append(Text.literal("Reloaded GUI texture configuration.")
                                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));
                        
                        return text;
                    }, 
                    true
                );
                ModLogger.info("GUI texture configuration reloaded successfully.");
            }
            catch (Exception e)
            {
                context.getSource().sendError(Text.literal("%s » Failed to reload GUI texture configuration: %s".formatted(MirageEssentials.MOD_NAME, e.getMessage())));
                ModLogger.error("Error reloading GUI texture config: %s".formatted(e.getMessage()));
                return 1;
            }
        }

        if (Modules.shouldEnableAntiAFKModule())
        {
            try
            {
                AntiAFKSettingsConfig.load();
                context.getSource().sendFeedback(() -> 
                    {
                        var text = Text.literal("%s » ".formatted(MirageEssentials.MOD_NAME))
                            .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()));
                        
                        text = text
                            .append(Text.literal("Reloaded anti-AFK settings configuration.")
                                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));
                        
                        return text;
                    }, 
                    true
                );
                ModLogger.info("Anti-AFK setting configuration reloaded successfully.");
            }
            catch (Exception e)
            {
                context.getSource().sendError(Text.literal("%s » Failed to reload anti-AFK settings configuration: %s".formatted(MirageEssentials.MOD_NAME, e.getMessage())));
                ModLogger.error("Error reloading anti-AFK setting config: %s".formatted(e.getMessage()));
                return 1;
            }

            try
            {
                AntiAFKStringsConfig.load();
                context.getSource().sendFeedback(() -> 
                    {
                        var text = Text.literal("%s » ".formatted(MirageEssentials.MOD_NAME))
                            .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()));
                        
                        text = text
                            .append(Text.literal("Reloaded anti-AFK string configuration.")
                                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));
                        
                        return text;
                    }, 
                    true
                );
                ModLogger.info("Anti-AFK string configuration reloaded successfully.");
            }
            catch (Exception e)
            {
                context.getSource().sendError(Text.literal("%s » Failed to reload anti-AFK string configuration: %s".formatted(MirageEssentials.MOD_NAME, e.getMessage())));
                ModLogger.error("Error reloading anti-AFK string config: %s".formatted(e.getMessage()));
                return 1;
            }

            try
            {
                AntiAFKColorsConfig.load();
                context.getSource().sendFeedback(() -> 
                    {
                        var text = Text.literal("%s » ".formatted(MirageEssentials.MOD_NAME))
                            .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()));
                        
                        text = text
                            .append(Text.literal("Reloaded anti-AFK color configuration.")
                                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));
                        
                        return text;
                    }, 
                    true
                );
                ModLogger.info("Anti-AFK color configuration reloaded successfully.");
            }
            catch (Exception e)
            {
                context.getSource().sendError(Text.literal("%s » Failed to reload anti-AFK color configuration: %s".formatted(MirageEssentials.MOD_NAME, e.getMessage())));
                ModLogger.error("Error reloading anti-AFK color config: %s".formatted(e.getMessage()));
                return 1;
            }
        }

        return 0;
    }
}
