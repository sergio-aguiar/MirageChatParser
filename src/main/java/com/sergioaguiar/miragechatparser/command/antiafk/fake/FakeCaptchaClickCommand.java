package com.sergioaguiar.miragechatparser.command.antiafk.fake;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

public class FakeCaptchaClickCommand
{
    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register
            (
                CommandManager.literal("mirageantiafk")
                    .then(CommandManager.literal("fakecaptchaclick")
                        .then(CommandManager.argument("code", StringArgumentType.word())
                            .executes(FakeCaptchaClickCommand::executeFakeCaptchaClick)
                        )
                    )
            );
        });
    }
    
    private static int executeFakeCaptchaClick(CommandContext<ServerCommandSource> context)
    {
        try
        {
            if (context.getSource().isExecutedByPlayer())
            {
                String code = StringArgumentType.getString(context, "code");

                AntiAFKManager.handleCaptchaClick(context.getSource().getPlayer(), code);
            }            
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to execute fakecaptchaclick: %s".formatted(e.getMessage()));
            return 1;   
        }

        return 0;
    }
}
