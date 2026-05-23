package com.sergioaguiar.mirageessentials.event.antiafk;

import com.sergioaguiar.mirageessentials.manager.AntiAFKManager;
import com.sergioaguiar.mirageessentials.util.ModLogger;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class AntiAFKPlayerDisconnectEventHandler
{
    public static void register()
    {
        ServerPlayConnectionEvents.DISCONNECT.register(
            (handler, server) ->
            {
                ServerPlayerEntity player = handler.getPlayer();

                if (player == null) return;

                AntiAFKManager.handlePlayerLeave(player);
            }
        );

        ModLogger.info("Anti-AFK Player Disconnect Data Cleaner started.");
    }    
}
