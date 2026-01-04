package com.sergioaguiar.miragechatparser.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sergioaguiar.miragechatparser.config.aspects.ChatAspectsConfig;
import com.sergioaguiar.miragechatparser.config.colors.ChatColors;
import com.sergioaguiar.miragechatparser.config.colors.ChatColorsConfig;
import com.sergioaguiar.miragechatparser.config.settings.ChatSettingsConfig;
import com.sergioaguiar.miragechatparser.config.strings.ChatStringsConfig;
import com.sergioaguiar.miragechatparser.util.LuckPermsUtils;
import com.sergioaguiar.miragechatparser.util.ModLogger;

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
                CommandManager.literal("miragechatparser")
                    .requires(source -> LuckPermsUtils.hasPermission(source, "miragechatparser.commands.admin.reload"))
                    .then(CommandManager.literal("reload")
                        .executes(ReloadCommand::executeReload))
            );
        });
    }

    private static int executeReload(CommandContext<ServerCommandSource> context) throws CommandSyntaxException
    {
        try
        {
            ChatSettingsConfig.load();
            context.getSource().sendFeedback(() -> 
                Text.literal("MirageChatParser » ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                .append(Text.literal("Reloaded chat settings configuration.")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor()))),
                true
            );
            ModLogger.info("Chat setting configuration reloaded successfully.");
        }
        catch (Exception e)
        {
            context.getSource().sendError(Text.literal("MirageChatParser » Failed to reload settings configuration: %s".formatted(e.getMessage())));
            ModLogger.error("Error reloading setting config: %s".formatted(e.getMessage()));
            return 1;
        }

        try
        {
            ChatStringsConfig.load();
            context.getSource().sendFeedback(() -> 
                Text.literal("MirageChatParser » ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                .append(Text.literal("Reloaded chat strings configuration.")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor()))),
                true
            );
            ModLogger.info("Chat string configuration reloaded successfully.");
        }
        catch (Exception e)
        {
            context.getSource().sendError(Text.literal("MirageChatParser » Failed to reload strings configuration: %s".formatted(e.getMessage())));
            ModLogger.error("Error reloading string config: %s".formatted(e.getMessage()));
            return 1;
        }

        try
        {
            ChatColorsConfig.load();
            context.getSource().sendFeedback(() -> 
                Text.literal("MirageChatParser » ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                .append(Text.literal("Reloaded chat color configuration.")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor()))),
                true
            );
            ModLogger.info("Chat color configuration reloaded successfully.");
        }
        catch (Exception e)
        {
            context.getSource().sendError(Text.literal("MirageChatParser » Failed to reload color configuration: %s".formatted(e.getMessage())));
            ModLogger.error("Error reloading color config: %s".formatted(e.getMessage()));
            return 1;
        }

        try
        {
            ChatAspectsConfig.load();
            context.getSource().sendFeedback(() -> 
                Text.literal("MirageChatParser » ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                .append(Text.literal("Reloaded chat aspect configuration.")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor()))),
                true
            );
            ModLogger.info("Chat aspect configuration reloaded successfully.");
        }
        catch (Exception e)
        {
            context.getSource().sendError(Text.literal("MirageChatParser » Failed to reload aspect configuration: %s".formatted(e.getMessage())));
            ModLogger.error("Error reloading aspect config: %s".formatted(e.getMessage()));
            return 1;
        }

        return 0;
    }
}
