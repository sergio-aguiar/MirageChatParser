package com.sergioaguiar.miragechatparser.util;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import net.fabricmc.loader.api.FabricLoader;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class LuckPermsUtils
{
    private static final String MOD_ID_STRING = "luckperms";

    private static Map<UUID, Map<String, Boolean>> playerPerms;

    static
    {
        playerPerms = new ConcurrentHashMap<>();
    }

    public static void clearPermsForPlayer(ServerPlayerEntity player)
    {
        clearPermsForPlayer(player.getUuid());
    }

    public static void clearPermsForPlayer(UUID playerUUID)
    {
        playerPerms.remove(playerUUID);
    }

    public static boolean isModLoaded()
    {
        return FabricLoader.getInstance().isModLoaded(MOD_ID_STRING);
    }

    public static boolean hasPermission(ServerCommandSource source, String permission) 
    {
        if (!source.isExecutedByPlayer() || source.hasPermissionLevel(2)) return true;
        if (!isModLoaded()) return source.hasPermissionLevel(2);

        ServerPlayerEntity player;
        try
        {
            player = source.getPlayerOrThrow();
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to check player permissions: %s".formatted(e.getMessage()));
            return false;
        }

        return playerHasPermissionInLuckPerms(player, permission);
    }

    public static boolean hasPermission(ServerPlayerEntity player, String permission)
    {
        if (player.hasPermissionLevel(2)) return true;
        if (!isModLoaded()) return player.hasPermissionLevel(2);

        return playerHasPermissionInLuckPerms(player, permission);
    }

    private static boolean playerHasPermissionInLuckPerms(ServerPlayerEntity player, String permission)
    {
        UUID playerUUID = player.getUuid();

        Map<String, Boolean> permissions = playerPerms.computeIfAbsent(playerUUID, uuid -> new ConcurrentHashMap<>());

        return permissions.computeIfAbsent(permission, perm -> 
        {
            LuckPerms api = LuckPermsProvider.get();
            User user = api.getUserManager().getUser(playerUUID);

            if (user == null) return false;

            return user
                .getCachedData()
                .getPermissionData()
                .checkPermission(perm)
                .asBoolean();
        });
    }
}
