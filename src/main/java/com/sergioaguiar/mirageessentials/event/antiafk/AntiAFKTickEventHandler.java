package com.sergioaguiar.mirageessentials.event.antiafk;

import java.util.List;

import com.sergioaguiar.mirageessentials.config.antiafk.settings.AntiAFKSettings;
import com.sergioaguiar.mirageessentials.manager.AntiAFKManager;
import com.sergioaguiar.mirageessentials.util.LuckPermsUtils;
import com.sergioaguiar.mirageessentials.util.ModLogger;

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
                int currentTicks = server.getTicks();

                if (AntiAFKManager.isCaptchaCheckTick(currentTicks))
                {
                    handleCaptchaCheck(playerList, currentTicks);
                }

                if (AntiAFKManager.isPositionAndCameraMovementCheckTick(currentTicks))
                {
                    handlePositionAndCameraMovementCheck(playerList);
                }
            }
        );

        ModLogger.info("Anti-AFK Activity Checker started.");
    }

    private static void handleCaptchaCheck(List<ServerPlayerEntity> playerList, int currentTicks)
    {
        if (AntiAFKSettings.shouldUseIndividualPlayerCaptchaTimes())
        {
            for (ServerPlayerEntity player : playerList)
            {
                if (player == null) continue;

                if (LuckPermsUtils.hasPermission(player, "mirageantiafk.bypass.captcha")) continue;
                if (!AntiAFKManager.isIndividualPlayerCaptchaTime(currentTicks, player.getUuid())) continue;

                AntiAFKManager.startCaptcha(player, "Server");
            }
        }
        else
        {
            if (AntiAFKManager.isGlobalCaptchaTime(currentTicks))
            {
                for (ServerPlayerEntity player : playerList)
                {
                    if (player == null) continue;

                    if (LuckPermsUtils.hasPermission(player, "mirageantiafk.bypass.captcha")) continue;

                    AntiAFKManager.startCaptcha(player, "Server");
                }
            }

            AntiAFKManager.registerGlobalCaptchaHappened(currentTicks);
        }
    }

    private static void handlePositionAndCameraMovementCheck(List<ServerPlayerEntity> playerList)
    {
        for (ServerPlayerEntity player : playerList)
        {
            if (player == null) continue;
            if (LuckPermsUtils.hasPermission(player, "mirageantiafk.bypass.check")) continue;

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
}
