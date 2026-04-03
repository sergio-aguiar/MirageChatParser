package com.sergioaguiar.miragechatparser.event;

import com.sergioaguiar.miragechatparser.config.modules.Modules;
import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class AntiAFKPlayerDisconnectEventHandler
{
    public static void register()
    {
        if (!Modules.shouldEnableAntiAFKModule()) 
        {
            ModLogger.info("Anti-AFK Player Disconnect Data Cleaner skipped.");
            return;
        }

        ServerPlayConnectionEvents.DISCONNECT.register(
            (handler, server) ->
            {
                AntiAFKManager.handlePlayerLeave(handler.getPlayer());
            }
        );

        ModLogger.info("Anti-AFK Player Disconnect Data Cleaner started.");
    }    
}
