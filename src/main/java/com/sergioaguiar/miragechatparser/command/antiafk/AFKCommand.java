package com.sergioaguiar.miragechatparser.command.antiafk;

import com.mojang.brigadier.context.CommandContext;
import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;
import com.sergioaguiar.miragechatparser.util.LuckPermsUtils;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class AFKCommand
{
    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register
            (
                CommandManager.literal("afk")
                    .requires(source -> LuckPermsUtils.hasPermission(source, "mirageantiafk.commands.afk"))
                    .executes(AFKCommand::executeAFK)
            );
        });
    }

    private static int executeAFK(CommandContext<ServerCommandSource> context)
    {
        ServerCommandSource source = context.getSource();
        if (!source.isExecutedByPlayer())
        {
            ModLogger.info("Only players can run this command.");
            return 1;
        }

        ServerPlayerEntity player;
        try
        {
            player = source.getPlayerOrThrow();
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to execute AFK: %s".formatted(e.getMessage()));
            return 1;
        }

        if (AntiAFKManager.isPlayerAFK(player)) AntiAFKManager.registerPlayerNoLongerAFK(player);
        else AntiAFKManager.registerPlayerAsAFK(player);

        return 0;
    }
}
