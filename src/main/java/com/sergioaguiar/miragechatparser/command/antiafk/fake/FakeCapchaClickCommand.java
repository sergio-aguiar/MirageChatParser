package com.sergioaguiar.miragechatparser.command.antiafk.fake;

import com.mojang.brigadier.context.CommandContext;
import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class FakeCapchaClickCommand
{
    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register
            (
                CommandManager.literal("mirageantiafk")
                    .then(CommandManager.literal("fakecapchaclick")
                        .executes(FakeCapchaClickCommand::executeFakeCapchaClick))
            );
        });
    }
    
    private static int executeFakeCapchaClick(CommandContext<ServerCommandSource> context)
    {
        try
        {
            if (context.getSource().isExecutedByPlayer())
            {
                AntiAFKManager.handleCapchaClick(context.getSource().getPlayer());
            }            
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to execute fakecapchaclick: %s".formatted(e.getMessage()));
            return 1;   
        }

        return 0;
    }
}
