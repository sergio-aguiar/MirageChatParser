package com.sergioaguiar.miragechatparser.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.sergioaguiar.miragechatparser.config.antiafk.AntiAFKSettings;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;

public class AntiAFKManager
{
    private static final int TICKS_PER_SECOND = 20;

    private static Map<UUID, Integer> playerTimesOfLastPositionMovement;
    private static Map<UUID, Integer> playerTimesOfLastCameraMovement;
    private static Map<UUID, Integer> playerTimesOfAFKMark;

    private static Map<UUID, Vec3d> playerLastPositions;
    private static Map<UUID, Float> playerLastCameraPitches;
    private static Map<UUID, Float> playerLastCameraYaws;

    public static void start()
    {
        playerTimesOfLastPositionMovement = new HashMap<>();
        playerTimesOfLastCameraMovement = new HashMap<>();
        playerTimesOfAFKMark = new HashMap<>();

        playerLastPositions = new HashMap<>();
        playerLastCameraPitches = new HashMap<>();
        playerLastCameraYaws = new HashMap<>();
    }

    public static void registerPlayerPositionMovement(ServerPlayerEntity player)
    {
        playerTimesOfLastPositionMovement.put(player.getUuid(), player.getServer().getTicks());
    }

    public static void registerPlayerCameraMovement(ServerPlayerEntity player)
    {
        playerTimesOfLastCameraMovement.put(player.getUuid(), player.getServer().getTicks());
    }

    public static void registerPlayerAsAFK(ServerPlayerEntity player)
    {
        if (isPlayerAFK(player)) return;

        ModLogger.info("Player %s is now AFK.".formatted(player.getDisplayName().getString()));
        playerTimesOfAFKMark.put(player.getUuid(), player.getServer().getTicks());
    }

    public static void registerPlayerNoLongerAFK(ServerPlayerEntity player)
    {
        if (!isPlayerAFK(player)) return;

        ModLogger.info("Player %s is no longer AFK.".formatted(player.getDisplayName().getString()));
        playerTimesOfAFKMark.remove(player.getUuid());
    }

    public static void registerPlayerLastPosition(ServerPlayerEntity player, Vec3d position)
    {
        playerLastPositions.put(player.getUuid(), position);
    }

    public static void registerPlayerLastCameraPitch(ServerPlayerEntity player, float pitch)
    {
        playerLastCameraPitches.put(player.getUuid(), pitch);
    }

    public static void registerPlayerLastCameraYaw(ServerPlayerEntity player, float yaw)
    {
        playerLastCameraYaws.put(player.getUuid(), yaw);
    }

    public static Vec3d getPlayerLastPosition(ServerPlayerEntity player)
    {
        return playerLastPositions.getOrDefault(player.getUuid(), player.getPos());
    }

    public static float getPlayerLastCameraPitch(ServerPlayerEntity player)
    {
        return playerLastCameraPitches.getOrDefault(player.getUuid(), player.getPitch());
    }

    public static float getPlayerLastCameraYaw(ServerPlayerEntity player)
    {
        return playerLastCameraYaws.getOrDefault(player.getUuid(), player.getYaw());
    }

    public static boolean shouldPlayerBeMarkedAsAFK(ServerPlayerEntity player)
    {
        if (player.getServer().getTicks() - playerTimesOfLastPositionMovement.getOrDefault(player.getUuid(), player.getServer().getTicks()) >= secondsToTicks(AntiAFKSettings.getSecondsToAFK())) 
        {
            ModLogger.info("Marking player %s as AFK due to insufficient position movement.".formatted(player.getDisplayName().getString()));
            return true;
        }
        if (player.getServer().getTicks() - playerTimesOfLastCameraMovement.getOrDefault(player.getUuid(), player.getServer().getTicks()) >= secondsToTicks(AntiAFKSettings.getSecondsToAFK()))
        {
            ModLogger.info("Marking player %s as AFK due to insufficient camera movement.".formatted(player.getDisplayName().getString()));
            return true;
        }
        return false;
    }

    public static boolean shouldPlayerBeAFKKicked(ServerPlayerEntity player)
    {
        if (player.getServer().getTicks() - playerTimesOfAFKMark.get(player.getUuid()) >= secondsToTicks(AntiAFKSettings.getSecondsToAFKKick())) return true;
        return false;
    }

    public static boolean isPlayerAFK(ServerPlayerEntity player)
    {
        return playerTimesOfAFKMark.containsKey(player.getUuid());
    }

    public static void handlePlayerInit(ServerPlayerEntity player)
    {
        registerPlayerLastPosition(player, player.getPos());
        registerPlayerLastCameraPitch(player, player.getPitch());
        registerPlayerLastCameraYaw(player, player.getYaw());
    }

    public static void handlePlayerLeave(ServerPlayerEntity player)
    {
        playerTimesOfLastPositionMovement.remove(player.getUuid());
        playerTimesOfLastCameraMovement.remove(player.getUuid());
        playerTimesOfAFKMark.remove(player.getUuid());

        playerLastPositions.remove(player.getUuid());
        playerLastCameraPitches.remove(player.getUuid());
        playerLastCameraYaws.remove(player.getUuid());
    }

    public static void handlePlayerPositionChangeLogic(ServerPlayerEntity player)
    {
        Vec3d currentPos = player.getPos();
        Vec3d lastPos = getPlayerLastPosition(player);

        double distanceMoved = currentPos.distanceTo(lastPos);

        if (distanceMoved >= AntiAFKSettings.getMinimumPositionChangeForMovementRegister())
        {
            if (isPlayerAFK(player))
            {
                registerPlayerNoLongerAFK(player);
            }

            registerPlayerPositionMovement(player);
        }

        registerPlayerLastPosition(player, currentPos);
    }

    public static void handlePlayerCameraChangeLogic(ServerPlayerEntity player)
    {
        float currentPitch = player.getPitch();
        float lastPitch = getPlayerLastCameraPitch(player);

        float currentYaw = player.getYaw();
        float lastYaw = getPlayerLastCameraYaw(player);

        float pitchChange = Math.abs(currentPitch - lastPitch);
        float yawChange = Math.abs(currentYaw - lastYaw);

        if (pitchChange >= AntiAFKSettings.getMinimumPitchChangeForCameraMovementRegister() || yawChange >= AntiAFKSettings.getMinimumYawChangeForCameraMovementRegister())
        {
            if (isPlayerAFK(player))
            {
                registerPlayerNoLongerAFK(player);
            }

            registerPlayerCameraMovement(player);
        }

        registerPlayerLastCameraPitch(player, currentPitch);
        registerPlayerLastCameraYaw(player, currentYaw);
    }

    private static int secondsToTicks(int seconds)
    {
        return seconds * TICKS_PER_SECOND;
    }
}
