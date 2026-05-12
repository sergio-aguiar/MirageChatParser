package com.sergioaguiar.miragechatparser.event.utils;

import com.sergioaguiar.miragechatparser.util.LuckPermsUtils;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.minecraft.server.network.ServerPlayerEntity;

public class LuckPermsPlayerJoinEventHandler
{
    public static void register()
    {
        ServerPlayConnectionEvents.JOIN.register(
            (handler, sender, server) ->
            {
                ServerPlayerEntity player = handler.getPlayer();

                if (player == null) return;

                LuckPermsUtils.clearPermsForPlayer(player);

                LuckPerms api = LuckPermsProvider.get();
                api.getUserManager().loadUser(player.getUuid());
            }
        );

        ModLogger.info("LuckPerms Player On-Join Setup started.");
    }    
}
