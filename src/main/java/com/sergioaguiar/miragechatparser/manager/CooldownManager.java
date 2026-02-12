package com.sergioaguiar.miragechatparser.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.server.network.ServerPlayerEntity;

public class CooldownManager
{
    private static final int COOLDOWN_TICKS = 20;

    private static final Map<UUID, Long> LAST_COMMAND_TICKS = new HashMap<>();

    public static boolean isOnCooldown(ServerPlayerEntity player)
    {
        long currentTick = player.getServer().getTicks();
        UUID uuid = player.getUuid();

        if (!LAST_COMMAND_TICKS.containsKey(uuid)) return false;

        return currentTick - LAST_COMMAND_TICKS.get(uuid) < COOLDOWN_TICKS;
    }

    public static long getRemainingTicks(ServerPlayerEntity player)
    {
        return Math.max(0, COOLDOWN_TICKS - (player.getServer().getTicks() - LAST_COMMAND_TICKS.getOrDefault(player.getUuid(), 0L)));
    }

    public static void setUsed(ServerPlayerEntity player)
    {
        LAST_COMMAND_TICKS.put(player.getUuid(), (long) player.getServer().getTicks());
    }
}
