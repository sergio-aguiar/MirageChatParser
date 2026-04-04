package com.sergioaguiar.miragechatparser.manager;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.sergioaguiar.miragechatparser.config.antiafk.settings.AntiAFKSettings;
import com.sergioaguiar.miragechatparser.config.chatparser.colors.ChatColors;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class AntiAFKManager
{
    private static final int TICKS_PER_SECOND = 20;

    private static Map<UUID, Integer> playerTimesOfLastPositionMovement;
    private static Map<UUID, Integer> playerTimesOfLastCameraMovement;
    private static Map<UUID, Integer> playerTimesOfLastMessageSent;
    private static Map<UUID, Integer> playerTimesOfAFKMark;

    private static Map<UUID, Vec3d> playerLastPositions;
    private static Map<UUID, Float> playerLastCameraPitches;
    private static Map<UUID, Float> playerLastCameraYaws;
    private static Map<UUID, String> playerLastMessages;

    private static Map<UUID, Integer> playersWhoWouldHaveBeenKicked;

    public static void start()
    {
        playerTimesOfLastPositionMovement = new HashMap<>();
        playerTimesOfLastCameraMovement = new HashMap<>();
        playerTimesOfLastMessageSent = new HashMap<>();
        playerTimesOfAFKMark = new HashMap<>();

        playerLastPositions = new HashMap<>();
        playerLastCameraPitches = new HashMap<>();
        playerLastCameraYaws = new HashMap<>();
        playerLastMessages = new HashMap<>();

        playersWhoWouldHaveBeenKicked = new HashMap<>();
    }

    public static void registerPlayerPositionMovement(ServerPlayerEntity player)
    {
        playerTimesOfLastPositionMovement.put(player.getUuid(), player.getServer().getTicks());
    }

    public static void registerPlayerCameraMovement(ServerPlayerEntity player)
    {
        playerTimesOfLastCameraMovement.put(player.getUuid(), player.getServer().getTicks());
    }

    public static void registerPlayerMessageSent(ServerPlayerEntity player)
    {
        playerTimesOfLastMessageSent.put(player.getUuid(), player.getServer().getTicks());
    }

    public static void registerPlayerAsAFK(ServerPlayerEntity player)
    {
        if (isPlayerAFK(player)) return;

        playerTimesOfAFKMark.put(player.getUuid(), player.getServer().getTicks());

        ModLogger.info("On no longer AFK: %s".formatted(ChatColors.getCommandPlayerColor().getHexCode()));

        MutableText afkText = Text.literal("AFKChecker » ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()));

        afkText = afkText
            .append(Text.literal("Player ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));

        afkText = afkText
            .append(Text.literal(player.getDisplayName().getString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPlayerColor())));

        afkText = afkText
            .append(Text.literal(" is now AFK.")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));

        player.getServer().getPlayerManager().broadcast(afkText, false);
    }

    public static void registerPlayerNoLongerAFK(ServerPlayerEntity player)
    {
        if (!isPlayerAFK(player)) return;

        int timeAFK = playerTimesOfAFKMark.getOrDefault(player.getUuid(), 0);

        playerTimesOfAFKMark.remove(player.getUuid());
        playersWhoWouldHaveBeenKicked.remove(player.getUuid());

        ModLogger.info("On no longer AFK: %s".formatted(ChatColors.getCommandPlayerColor().getHexCode()));

        MutableText afkText = Text.literal("AFKChecker » ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()));

        afkText = afkText
            .append(Text.literal("Player ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));

        afkText = afkText
            .append(Text.literal(player.getDisplayName().getString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPlayerColor())));

        afkText = afkText
            .append(Text.literal(" is no longer AFK.")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));

        afkText = afkText
            .append(Text.literal(" (Gone for ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor())));

        afkText = afkText
            .append(Text.literal("%s".formatted(secondsToReadableTimeString((int) ticksToSeconds(player.getServer().getTicks() - timeAFK))))
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPlayerColor())));

        afkText = afkText
            .append(Text.literal(")")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor())));

        player.getServer().getPlayerManager().broadcast(afkText, false);
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

    public static void registerPlayerLastMessage(ServerPlayerEntity player, String message)
    {
        playerLastMessages.put(player.getUuid(), message);
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

    public static String getPlayerLastMessage(ServerPlayerEntity player)
    {
        return playerLastMessages.getOrDefault(player.getUuid(), "");
    }

    public static boolean shouldPlayerBeMarkedAsAFK(ServerPlayerEntity player)
    {
        int[] lastActionTimes =
        {
            playerTimesOfLastPositionMovement.getOrDefault(player.getUuid(), player.getServer().getTicks()),
            playerTimesOfLastCameraMovement.getOrDefault(player.getUuid(), player.getServer().getTicks()),
            playerTimesOfLastMessageSent.getOrDefault(player.getUuid(), player.getServer().getTicks())
        };

        int mostRecentActionTime = Arrays.stream(lastActionTimes).max().getAsInt();

        return player.getServer().getTicks() - mostRecentActionTime >= secondsToTicks(AntiAFKSettings.getSecondsToAFK());
    }

    public static boolean shouldPlayerBeAFKKicked(ServerPlayerEntity player)
    {
        if (!isPlayerAFK(player)) return false;
        if (player.getServer().getTicks() - playerTimesOfAFKMark.getOrDefault(player.getUuid(), player.getServer().getTicks()) >= secondsToTicks(AntiAFKSettings.getSecondsToAFKKick())) return true;
        return false;
    }

    public static boolean isPlayerAFK(ServerPlayerEntity player)
    {
        return playerTimesOfAFKMark.containsKey(player.getUuid());
    }

    public static void handlePlayerInit(ServerPlayerEntity player)
    {
        registerPlayerPositionMovement(player);
        registerPlayerCameraMovement(player);
        registerPlayerMessageSent(player);

        registerPlayerLastPosition(player, player.getPos());
        registerPlayerLastCameraPitch(player, player.getPitch());
        registerPlayerLastCameraYaw(player, player.getYaw());
        registerPlayerLastMessage(player, "");
    }

    public static void handlePlayerLeave(ServerPlayerEntity player)
    {
        playerTimesOfLastPositionMovement.remove(player.getUuid());
        playerTimesOfLastCameraMovement.remove(player.getUuid());
        playerTimesOfAFKMark.remove(player.getUuid());
        playerTimesOfLastMessageSent.remove(player.getUuid());

        playerLastPositions.remove(player.getUuid());
        playerLastCameraPitches.remove(player.getUuid());
        playerLastCameraYaws.remove(player.getUuid());
        playerLastMessages.remove(player.getUuid());
    }

    public static void handlePlayerPositionChangeLogic(ServerPlayerEntity player)
    {
        Vec3d currentPos = player.getPos();
        Vec3d lastPos = getPlayerLastPosition(player);

        double distanceMoved = currentPos.distanceTo(lastPos);

        if (distanceMoved >= AntiAFKSettings.getMinimumPositionChangeForMovementRegister())
        {
            registerPlayerNoLongerAFK(player);
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
            registerPlayerNoLongerAFK(player);
            registerPlayerCameraMovement(player);
        }

        registerPlayerLastCameraPitch(player, currentPitch);
        registerPlayerLastCameraYaw(player, currentYaw);
    }

    public static void handlePlayerMessageSentLogic(ServerPlayerEntity player, String newMessage)
    {
        String lastMessage = getPlayerLastMessage(player);

        if (!newMessage.equals(lastMessage))
        {
            registerPlayerNoLongerAFK(player);
            registerPlayerMessageSent(player);
        }

        registerPlayerLastMessage(player, newMessage);
    }

    public static void handlePlayerAFKKick(ServerPlayerEntity player)
    {
        if (!isPlayerAFK(player) || playersWhoWouldHaveBeenKicked.containsKey(player.getUuid())) return;

        ModLogger.info("Player should be AFK kicked right now with (lastPosMove=%.2f seconds - lastCamMove=%.2f seconds - lastChatMsg=%.2f seconds)".formatted
            (
                AntiAFKManager.getSecondsSinceLastPositionMovement(player),
                AntiAFKManager.getSecondsSinceLastCameraMovement(player),
                AntiAFKManager.getSecondsSinceLastMessageSent(player)
            )
        );

        playersWhoWouldHaveBeenKicked.put(player.getUuid(), player.getServer().getTicks());
    }

    public static double getSecondsSinceLastPositionMovement(ServerPlayerEntity player)
    {
        return ticksToSeconds(player.getServer().getTicks() - playerTimesOfLastPositionMovement.getOrDefault(player.getUuid(), player.getServer().getTicks()));
    }

    public static double getSecondsSinceLastCameraMovement(ServerPlayerEntity player)
    {
        return ticksToSeconds(player.getServer().getTicks() - playerTimesOfLastCameraMovement.getOrDefault(player.getUuid(), player.getServer().getTicks()));
    }

    public static double getSecondsSinceLastMessageSent(ServerPlayerEntity player)
    {
        return ticksToSeconds(player.getServer().getTicks() - playerTimesOfLastMessageSent.getOrDefault(player.getUuid(), player.getServer().getTicks()));
    }

    private static int secondsToTicks(int seconds)
    {
        return seconds * TICKS_PER_SECOND;
    }

    private static double ticksToSeconds(int ticks)
    {
        return ticks / (double) TICKS_PER_SECOND;
    }

    private static String secondsToReadableTimeString(int totalSeconds)
    {
        Duration duration = Duration.ofSeconds(totalSeconds);
    
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        List<String> resultPartList = new ArrayList<>();

        if (days > 0) resultPartList.add(days + " Day" + (days > 1 ? "s" : ""));
        if (hours > 0) resultPartList.add(hours + " Hour" + (hours > 1 ? "s" : ""));
        if (minutes > 0) resultPartList.add(minutes + " Minute" + (minutes > 1 ? "s" : ""));
        if (seconds > 0 || resultPartList.isEmpty()) resultPartList.add(seconds + " Second" + (seconds > 1 ? "s" : ""));

        return String.join(", ", resultPartList);
    }
}
