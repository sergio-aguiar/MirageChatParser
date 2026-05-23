package com.sergioaguiar.mirageessentials.event.antiafk;

import com.sergioaguiar.mirageessentials.manager.AntiAFKManager;
import com.sergioaguiar.mirageessentials.util.ModLogger;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class AntiAFKPlayerJoinEventHandler
{
    public static void register()
    {
        ServerPlayConnectionEvents.JOIN.register(
            (handler, sender, server) ->
            {
                ServerPlayerEntity player = handler.getPlayer();

                if (player == null) return;

                AntiAFKManager.handlePlayerInit(player);
            }
        );

        ModLogger.info("Anti-AFK Player On-Join Setup started.");
    }    
}
