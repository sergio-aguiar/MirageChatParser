package com.sergioaguiar.miragechatparser.command.antiafk;

import com.mojang.brigadier.context.CommandContext;
import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;
import com.sergioaguiar.miragechatparser.util.LuckPermsUtils;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class ForceCaptchaCommand
{
    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register
            (
                CommandManager.literal("forcecaptcha")
                    .requires(source -> LuckPermsUtils.hasPermission(source, "mirageantiafk.commands.admin.forcecaptcha"))
                    .then(CommandManager.argument("player", EntityArgumentType.player())
                        .executes(ForceCaptchaCommand::executeForceCaptcha)
                    )
            );
        });
    }

    private static int executeForceCaptcha(CommandContext<ServerCommandSource> context)
    {
        String forcedBy = (context.getSource().isExecutedByPlayer() && context.getSource().getPlayer() != null) 
            ? context.getSource().getPlayer().getUuidAsString() 
            : "Server";

        try
        {
            ServerPlayerEntity target = EntityArgumentType.getPlayer(context, "player");
            AntiAFKManager.startCaptcha(target, forcedBy);
            return 0;
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to execute forcecaptcha: %s".formatted(e.getMessage()));
            return 1;   
        }
    }
}
