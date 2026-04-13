package com.sergioaguiar.miragechatparser.command.antiafk;

import com.mojang.brigadier.context.CommandContext;
import com.sergioaguiar.miragechatparser.util.LuckPermsUtils;
import com.sergioaguiar.miragechatparser.util.ModLogger;
import com.sergioaguiar.miragechatparser.util.TextUtils;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class IsAFKCommand
{
    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register
            (
                CommandManager.literal("isafk")
                    .requires(source -> LuckPermsUtils.hasPermission(source, "mirageantiafk.commands.admin.isafk"))
                    .then(
                        CommandManager.argument("player", EntityArgumentType.player())
                            .executes(IsAFKCommand::executeIsAFK)
                    )
            );
        });
    }

    private static int executeIsAFK(CommandContext<ServerCommandSource> context)
    {
        ServerCommandSource source = context.getSource();
        if (!source.isExecutedByPlayer())
        {
            ModLogger.info("Only players can run this command.");
            return 1;
        }

        ServerPlayerEntity callingPlayer;
        try
        {
            callingPlayer = source.getPlayerOrThrow();
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to execute AFK: %s".formatted(e.getMessage()));
            return 1;
        }

        ServerPlayerEntity targetPlayer;
        try
        {
            targetPlayer = EntityArgumentType.getPlayer(context, "player");
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to get target player: %s".formatted(e.getMessage()));
            return 1;
        }

        callingPlayer.sendMessage(TextUtils.playerPermKickMessage(targetPlayer.getUuid(), targetPlayer.getServer().getTicks()));

        return 0;
    }
}
