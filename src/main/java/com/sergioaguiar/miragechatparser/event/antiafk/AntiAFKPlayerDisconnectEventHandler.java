package com.sergioaguiar.miragechatparser.event.antiafk;

import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class AntiAFKPlayerDisconnectEventHandler
{
    public static void register()
    {
        ServerPlayConnectionEvents.DISCONNECT.register(
            (handler, server) ->
            {
                AntiAFKManager.handlePlayerLeave(handler.getPlayer());
            }
        );

        ModLogger.info("Anti-AFK Player Disconnect Data Cleaner started.");
    }    
}
