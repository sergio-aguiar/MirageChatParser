package com.sergioaguiar.mirageessentials.event.utils;

import com.sergioaguiar.mirageessentials.util.LuckPermsUtils;
import com.sergioaguiar.mirageessentials.util.ModLogger;

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

                if (!LuckPermsUtils.isModLoaded())
                {
                    return;
                }

                LuckPerms api = LuckPermsProvider.get();
                api.getUserManager().loadUser(player.getUuid());
            }
        );

        ModLogger.info("LuckPerms Player On-Join Setup started.");
    }    
}
