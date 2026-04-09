package com.sergioaguiar.miragechatparser.event;

import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class AntiAFKPlayerJoinEventHandler
{
    public static void register()
    {
        ServerPlayConnectionEvents.JOIN.register(
            (handler, sender, server) ->
            {
                AntiAFKManager.handlePlayerInit(handler.getPlayer());
            }
        );

        ModLogger.info("Anti-AFK Player On-Join Setup started.");
    }    
}
