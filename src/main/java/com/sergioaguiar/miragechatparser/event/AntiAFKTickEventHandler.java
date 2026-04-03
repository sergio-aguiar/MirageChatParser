package com.sergioaguiar.miragechatparser.event;

import com.sergioaguiar.miragechatparser.config.modules.Modules;
import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class AntiAFKTickEventHandler
{
    public static void register()
    {
        if (!Modules.shouldEnableAntiAFKModule()) 
        {
            ModLogger.info("Anti-AFK Activity Checker skipped.");
            return;
        }

        ServerTickEvents.END_SERVER_TICK.register(
            server -> 
            {
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList())
                {
                    if (player.hasVehicle() || player.isTouchingWater()) continue;

                    AntiAFKManager.handlePlayerPositionChangeLogic(player);
                    AntiAFKManager.handlePlayerCameraChangeLogic(player);

                    if (!AntiAFKManager.isPlayerAFK(player) && AntiAFKManager.shouldPlayerBeMarkedAsAFK(player))
                    {
                        AntiAFKManager.registerPlayerAsAFK(player);
                    }

                    if (AntiAFKManager.shouldPlayerBeAFKKicked(player))
                    {
                        AntiAFKManager.handlePlayerAFKKick(player);
                    }
                }
            }
        );

        ModLogger.info("Anti-AFK Activity Checker started.");
    }
}
