package com.sergioaguiar.miragechatparser.event;

import com.sergioaguiar.miragechatparser.config.modules.Modules;
import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class TickEventHandler
{
    public static void register()
    {
        if (!Modules.shouldEnableAntiAFKModule()) 
        {
            ModLogger.info("Anti-AFK Checker skipped.");
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
                        // MAKE METHOD TO RETURN SECONDS SINCE LAST MOVE
                        ModLogger.info("Player should be AFK kicked right now with (lastPosMove=%.2f - lastCamMove=%.2f)".formatted(0, 0));
                    }
                }
            }
        );

        ModLogger.info("Anti-AFK Checker started.");
    }
}
