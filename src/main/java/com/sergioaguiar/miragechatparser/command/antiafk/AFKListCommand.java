package com.sergioaguiar.miragechatparser.command.antiafk;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sergioaguiar.miragechatparser.util.LuckPermsUtils;
import com.sergioaguiar.miragechatparser.util.ModLogger;
import com.sergioaguiar.miragechatparser.util.TextUtils;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class AFKListCommand
{
    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register
            (
                CommandManager.literal("afklist")
                    .requires(source -> LuckPermsUtils.hasPermission(source, "mirageantiafk.commands.afklist"))
                    .executes(AFKListCommand::executeAFKList)
            );
        });
    }

    private static int executeAFKList(CommandContext<ServerCommandSource> context) throws CommandSyntaxException
    {
        try
        {
            ServerCommandSource source = context.getSource();
            source.sendMessage(TextUtils.afkListCommand(source.getServer()));
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to execute afkList: %s".formatted(e.getMessage()));
            return 1;   
        }

        return 0;
    }
}
