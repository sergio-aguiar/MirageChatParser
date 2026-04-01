package com.sergioaguiar.miragechatparser.event;

import com.sergioaguiar.miragechatparser.config.modules.Modules;
import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;

public class PlayerJoinEventHandler
{
    public static void register()
    {
        if (!Modules.shouldEnableAntiAFKModule()) 
        {
            ModLogger.info("Anti-AFK Player Setup skipped.");
            return;
        }

        ServerPlayConnectionEvents.JOIN.register(
            (handler, sender, server) ->
            {
                AntiAFKManager.handlePlayerInit(handler.getPlayer());
            }
        );

        ModLogger.info("Anti-AFK Player Setup started.");
    }    
}
