package com.sergioaguiar.miragechatparser.command;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.pc.PCBox;
import com.cobblemon.mod.common.api.storage.pc.PCStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sergioaguiar.miragechatparser.config.ChatColors;
import com.sergioaguiar.miragechatparser.parser.PlaceholderResolver;
import com.sergioaguiar.miragechatparser.util.LuckPermsUtils;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class PCShoutCommand
{
    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register
            (
                CommandManager.literal("pcshout")
                    .requires(source -> LuckPermsUtils.hasPermission(source, "miragechatparser.commands.pcshout"))
                    .then(CommandManager.argument("box", IntegerArgumentType.integer(1, Integer.MAX_VALUE))
                        .then(CommandManager.argument("slot", IntegerArgumentType.integer(1, 30))
                            .executes(PCShoutCommand::executePCShout)
                        )
                    )
            );
        });
    }

    private static int executePCShout(CommandContext<ServerCommandSource> context) throws CommandSyntaxException
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
            ModLogger.error("Failed to execute partyshout/pokeshout: %s".formatted(e.getMessage()));
            return 1;
        }

        int box = IntegerArgumentType.getInteger(context, "box");
        int slot = IntegerArgumentType.getInteger(context, "slot");

        int maxBoxes = Cobblemon.INSTANCE.getStorage().getPC(player).getBoxes().size();
        if (box > maxBoxes)
        {
            player.sendMessage(Text.literal("PCShout » ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                .append(Text.literal("You only have %s boxes!".formatted(maxBoxes))
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor()))), 
                false);

            return 1;
        }

        PCStore pc = Cobblemon.INSTANCE.getStorage().getPC(player);
        PCBox boxStorage = pc.getBoxes().get(box - 1);
        Pokemon pokemon = boxStorage.get(slot - 1);

        if (pokemon == null)
        {
            player.sendMessage(Text.literal("PCShout » ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                .append(Text.literal("You can not shout an empty slot!")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor()))), 
                false);

            return 1;
        }

        Text message = PlaceholderResolver.getPCPokemonName(player, box, slot);

        player.getServer().getPlayerManager().broadcast
        (
            Text.literal("PCShout » ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                .append(Text.literal("Player ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
                .append(Text.literal(player.getDisplayName().getString())
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPlayerColor())))
                .append(Text.literal(" shouted: ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
                .append(message),
            false
        );

        return 0;
    }
}
