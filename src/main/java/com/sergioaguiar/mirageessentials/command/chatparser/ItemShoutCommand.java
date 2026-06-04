package com.sergioaguiar.mirageessentials.command.chatparser;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sergioaguiar.mirageessentials.util.LuckPermsUtils;
import com.sergioaguiar.mirageessentials.util.ModLogger;
import com.sergioaguiar.mirageessentials.util.ShoutUtils;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class ItemShoutCommand
{
    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register
            (
                CommandManager.literal("itemshout")
                    .requires(source -> LuckPermsUtils.hasPermission(source, "miragechatparser.commands.itemshout"))
                    .then(CommandManager.argument("slot", IntegerArgumentType.integer(1, 9))
                        .executes(context -> ItemShoutCommand.executeItemShout(context))
                    )
            );
        });
    }

    private static int executeItemShout(CommandContext<ServerCommandSource> context) throws CommandSyntaxException
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
            ModLogger.error("Failed to execute ItemShout: %s".formatted(e.getMessage()));
            return 1;
        }

        int itemIndex = IntegerArgumentType.getInteger(context, "slot");

        ShoutUtils.doItemShout(player, itemIndex);
        return 0;
    }
}
