package com.sergioaguiar.miragechatparser.event;

import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class TickEventHandler
{
    public static void register()
    {
        ServerTickEvents.END_SERVER_TICK.register(
            server -> 
            {
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList())
                {
                    if (player.hasVehicle() || player.isTouchingWater()) continue;

                    AntiAFKManager.handlePlayerPositionChangeLogic(player);
                    AntiAFKManager.handlePlayerCameraChangeLogic(player);

                    if (AntiAFKManager.shouldPlayerBeMarkedAsAFK(player))
                    {
                        AntiAFKManager.registerPlayerAsAFK(player);
                    }
                }
            }
        );
    }
}
