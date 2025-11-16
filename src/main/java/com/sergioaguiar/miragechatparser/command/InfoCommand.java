package com.sergioaguiar.miragechatparser.command;

import com.mojang.brigadier.context.CommandContext;
import com.sergioaguiar.miragechatparser.MirageChatParser;
import com.sergioaguiar.miragechatparser.config.colors.ChatColors;
import com.sergioaguiar.miragechatparser.util.LuckPermsUtils;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class InfoCommand
{
    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register
            (
                CommandManager.literal("miragechatparser")
                    .requires(source -> LuckPermsUtils.hasPermission(source, "miragechatparser.commands.info"))
                    .then(CommandManager.literal("info")
                        .executes(InfoCommand::executeInfo))
            );
        });
    }

    private static int executeInfo(CommandContext<ServerCommandSource> context)
    {
        try
        {
            ServerCommandSource source = context.getSource();

            MutableText coloredLine = Text.literal("Info » ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
            .append(Text.literal("MirageShoutParser ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
            .append(Text.literal("v%s".formatted(FabricLoader.getInstance().getModContainer(MirageChatParser.MOD_ID).orElseThrow().getMetadata().getVersion().getFriendlyString()))
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPlayerColor())))
            .append(Text.literal(" is developed by ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
            .append(Text.literal("pioavenger")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPlayerColor())));

            source.sendMessage(coloredLine);
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to execute info: %s".formatted(e.getMessage()));
            return 1;   
        }

        return 0;
    }
}
