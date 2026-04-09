package com.sergioaguiar.miragechatparser.event;

import java.util.List;

import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class AntiAFKTickEventHandler
{
    public static void register()
    {
        ServerTickEvents.END_SERVER_TICK.register(
            server -> 
            {
                List<ServerPlayerEntity> playerList = server.getPlayerManager().getPlayerList();

                if (AntiAFKManager.isCapchaTime(server))
                {
                    for (ServerPlayerEntity player : playerList)
                    {
                        AntiAFKManager.startCapcha(player);
                    }
                }

                for (ServerPlayerEntity player : playerList)
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
