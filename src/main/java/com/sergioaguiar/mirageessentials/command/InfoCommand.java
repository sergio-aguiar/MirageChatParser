package com.sergioaguiar.mirageessentials.command;

import com.mojang.brigadier.context.CommandContext;
import com.sergioaguiar.mirageessentials.util.LuckPermsUtils;
import com.sergioaguiar.mirageessentials.util.ModLogger;
import com.sergioaguiar.mirageessentials.util.TextUtils;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class InfoCommand
{
    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register
            (
                CommandManager.literal("mirageessentials")
                    .then(CommandManager.literal("info")
                        .requires(source -> LuckPermsUtils.hasPermission(source, "mirageessentials.commands.info"))
                        .executes(InfoCommand::executeInfo)
                    )
            );
        });
    }

    private static int executeInfo(CommandContext<ServerCommandSource> context)
    {
        try
        {
            ServerCommandSource source = context.getSource();
            source.sendMessage(TextUtils.infoCommandMessage());
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to execute info: %s".formatted(e.getMessage()));
            return 1;   
        }

        return 0;
    }
}
