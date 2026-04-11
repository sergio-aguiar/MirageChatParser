package com.sergioaguiar.miragechatparser.manager;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.sergioaguiar.miragechatparser.config.antiafk.colors.AntiAFKColors;
import com.sergioaguiar.miragechatparser.config.antiafk.settings.AntiAFKSettings;
import com.sergioaguiar.miragechatparser.util.LuckPermsUtils;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class AntiAFKManager
{
    private static class PlayerCapcha
    {
        private final boolean isClick;
        private final String answer;

        public PlayerCapcha(boolean isClick)
        {
            this.isClick = isClick;

            if (isClick)
            {
                this.answer = "";
                return;
            }

            int length = AntiAFKSettings.getCapchaLength();
            StringBuilder stringBuilder = new StringBuilder(length);
            Random random = new Random();

            for (int i = 0; i < length; i++)
            {
                stringBuilder.append(random.nextInt(10));
            }

            this.answer = stringBuilder.toString();
        }

        public boolean isClickCapcha()
        {
            return isClick;
        }

        public boolean isTheRightAnswer(String answer)
        {
            return this.answer.equalsIgnoreCase(answer);
        }

        public Text getCapchaText()
        {
            MutableText capchaText = Text.literal("").setStyle(Style.EMPTY);

            if (isClick)
            {
                capchaText = capchaText
                    .append(Text.literal("Please prove you are active: ")
                        .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCapchaTextColor())));

                capchaText = capchaText
                    .append(Text.literal("[Click here]")
                        .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCapchaQuestionColor())
                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mirageantiafk fakecapchaclick"))));
            }
            else
            {
                capchaText = capchaText
                    .append(Text.literal("Please type this into chat: ")
                        .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCapchaTextColor())));

                capchaText = capchaText
                    .append(Text.literal(answer)
                        .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCapchaQuestionColor())));
            }

            return capchaText;
        }
    }

    private static final int TICKS_PER_SECOND = 20;

    private static Map<UUID, Integer> playerTimesOfLastPositionMovement;
    private static Map<UUID, Integer> playerTimesOfLastCameraMovement;
    private static Map<UUID, Integer> playerTimesOfLastMessageSent;
    private static Map<UUID, Integer> playerTimesOfLastCapchaAnswer;
    private static Map<UUID, Integer> playerTimesOfAFKMark;

    private static Map<UUID, Vec3d> playerLastPositions;
    private static Map<UUID, Float> playerLastCameraPitches;
    private static Map<UUID, Float> playerLastCameraYaws;
    private static Map<UUID, String> playerLastMessages;

    private static Map<UUID, Integer> playersWhoWouldHaveBeenKicked;
    private static Map<UUID, PlayerCapcha> playerActiveCapchas;
    private static Map<UUID, Integer> playerIgnoredCapchaCounts;

    public static void start()
    {
        playerTimesOfLastPositionMovement = new HashMap<>();
        playerTimesOfLastCameraMovement = new HashMap<>();
        playerTimesOfLastMessageSent = new HashMap<>();
        playerTimesOfLastCapchaAnswer = new HashMap<>();
        playerTimesOfAFKMark = new HashMap<>();

        playerLastPositions = new HashMap<>();
        playerLastCameraPitches = new HashMap<>();
        playerLastCameraYaws = new HashMap<>();
        playerLastMessages = new HashMap<>();

        playersWhoWouldHaveBeenKicked = new HashMap<>();
        playerActiveCapchas = new HashMap<>();
        playerIgnoredCapchaCounts = new HashMap<>();
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

    public static void registerPlayerCapchaAnswer(ServerPlayerEntity player)
    {
        UUID playerUUID = player.getUuid();

        playerTimesOfLastCapchaAnswer.put(playerUUID, player.getServer().getTicks());
        playerIgnoredCapchaCounts.put(playerUUID, 0);
    }

    public static void registerPlayerAsAFK(ServerPlayerEntity player)
    {
        if (isPlayerAFK(player)) return;

        playerTimesOfAFKMark.put(player.getUuid(), player.getServer().getTicks());

        MutableText afkText = Text.literal("").setStyle(Style.EMPTY);

        if (!AntiAFKSettings.shouldHideAFKCheckerMessagePrefix())
        {
            afkText = afkText
                .append(Text.literal("AFKChecker » ")
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerPrefixColor())));
        }

        afkText = afkText
            .append(Text.literal("Player ")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerTextColor())));

        afkText = afkText
            .append(Text.literal(player.getDisplayName().getString())
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerPlayerColor())));

        afkText = afkText
            .append(Text.literal(" is now AFK.")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerTextColor())));

        player.getServer().getPlayerManager().broadcast(afkText, false);
    }

    public static void registerPlayerNoLongerAFK(ServerPlayerEntity player)
    {
        if (!isPlayerAFK(player)) return;

        UUID playerUUID = player.getUuid();
        int timeAFK = playerTimesOfAFKMark.getOrDefault(playerUUID, 0);

        playerTimesOfAFKMark.remove(playerUUID);
        playersWhoWouldHaveBeenKicked.remove(playerUUID);

        MutableText afkText = Text.literal("").setStyle(Style.EMPTY);

        if (!AntiAFKSettings.shouldHideAFKCheckerMessagePrefix())
        {
            afkText = afkText
                .append(Text.literal("AFKChecker » ")
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerPrefixColor())));
        }

        afkText = afkText
            .append(Text.literal("Player ")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerTextColor())));

        afkText = afkText
            .append(Text.literal(player.getDisplayName().getString())
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerPlayerColor())));

        afkText = afkText
            .append(Text.literal(" is no longer AFK.")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerTextColor())));

        afkText = afkText
            .append(Text.literal(" (Gone for ")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerGoneColor())));

        afkText = afkText
            .append(Text.literal("%s".formatted(secondsToReadableTimeString((int) ticksToSeconds(player.getServer().getTicks() - timeAFK))))
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerTextColor())));

        afkText = afkText
            .append(Text.literal(")")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerGoneColor())));

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
        UUID playerUUID = player.getUuid();

        int[] lastActionTimes =
        {
            playerTimesOfLastPositionMovement.getOrDefault(playerUUID, player.getServer().getTicks()),
            playerTimesOfLastCameraMovement.getOrDefault(playerUUID, player.getServer().getTicks()),
            playerTimesOfLastMessageSent.getOrDefault(playerUUID, player.getServer().getTicks()),
            playerTimesOfLastCapchaAnswer.getOrDefault(playerUUID, player.getServer().getTicks())
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
        registerPlayerCapchaAnswer(player);

        registerPlayerLastPosition(player, player.getPos());
        registerPlayerLastCameraPitch(player, player.getPitch());
        registerPlayerLastCameraYaw(player, player.getYaw());
        registerPlayerLastMessage(player, "");

        playerIgnoredCapchaCounts.put(player.getUuid(), 0);
    }

    public static void handlePlayerLeave(ServerPlayerEntity player)
    {
        UUID playerUUID = player.getUuid();

        playerTimesOfLastPositionMovement.remove(playerUUID);
        playerTimesOfLastCameraMovement.remove(playerUUID);
        playerTimesOfLastMessageSent.remove(playerUUID);
        playerTimesOfLastCapchaAnswer.remove(playerUUID);
        playerTimesOfAFKMark.remove(playerUUID);

        playerLastPositions.remove(playerUUID);
        playerLastCameraPitches.remove(playerUUID);
        playerLastCameraYaws.remove(playerUUID);
        playerLastMessages.remove(playerUUID);

        playersWhoWouldHaveBeenKicked.remove(playerUUID);
        playerActiveCapchas.remove(playerUUID);
        playerIgnoredCapchaCounts.remove(playerUUID);
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

    public static void startCapcha(ServerPlayerEntity player)
    {
        UUID playerUUID = player.getUuid();

        if (playerActiveCapchas.containsKey(playerUUID))
        {
            playerActiveCapchas.remove(playerUUID);
            
            int ignoredCapchas = playerIgnoredCapchaCounts.get(playerUUID) + 1;

            if (ignoredCapchas >= AntiAFKSettings.getFailedCapchaBeforeKick())
            {
                if (AntiAFKSettings.isNoKickModeEnabled())
                {
                    if (!playersWhoWouldHaveBeenKicked.containsKey(playerUUID))
                    {
                        ModLogger.info("Player %s should be AFK kicked right now with (ignoredCapchas=%d)".formatted(player.getDisplayName().getString(), ignoredCapchas));
                        playersWhoWouldHaveBeenKicked.put(playerUUID, player.getServer().getTicks());
                    }
                }
                else
                {
                    handlePlayerKick(player, "You ignored too many chat CAPCHA.");
                }
            }

            playerIgnoredCapchaCounts.put(playerUUID, ignoredCapchas);
        }

        playerActiveCapchas.put(playerUUID, new PlayerCapcha(ThreadLocalRandom.current().nextDouble() < 0.3));

        MutableText capchaMessage = Text.literal("").setStyle(Style.EMPTY);

        if (!AntiAFKSettings.shouldHideAFKCapchaMessagePrefix())
        {
            capchaMessage = capchaMessage
                .append(Text.literal("AFKapcha » ")
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCapchaPrefixColor())));
        }

        capchaMessage = capchaMessage
            .append(playerActiveCapchas.get(playerUUID).getCapchaText());

        player.sendMessage(capchaMessage);
    }

    public static boolean hasActiveClickCapcha(ServerPlayerEntity player)
    {
        UUID playerUUID = player.getUuid();

        return playerActiveCapchas.containsKey(playerUUID) && playerActiveCapchas.get(playerUUID).isClickCapcha();
    }

    public static boolean hasActiveMessageCapcha(ServerPlayerEntity player)
    {
        UUID playerUUID = player.getUuid();

        return playerActiveCapchas.containsKey(playerUUID) && !playerActiveCapchas.get(playerUUID).isClickCapcha();
    }

    public static boolean isCorrectCapchaAnswer(ServerPlayerEntity player, String answer)
    {
        UUID playerUUID = player.getUuid();

        if (!playerActiveCapchas.containsKey(playerUUID)) return false;

        return playerActiveCapchas.get(playerUUID).isTheRightAnswer(answer);
    }

    public static void handleCapchaClick(ServerPlayerEntity player)
    {
        UUID playerUUID = player.getUuid();

        if (!playerActiveCapchas.containsKey(playerUUID)) return;

        if (playerActiveCapchas.get(playerUUID).isClickCapcha())
        {
            handleCapchaSuccess(player);
        }
    }

    public static void handleCorrectCapchaAnswer(ServerPlayerEntity player)
    {
        handleCapchaSuccess(player);
    }

    private static void handleCapchaSuccess(ServerPlayerEntity player)
    {
        playerActiveCapchas.remove(player.getUuid());

        registerPlayerNoLongerAFK(player);
        registerPlayerCapchaAnswer(player);

        MutableText capchaMessage = Text.literal("").setStyle(Style.EMPTY);

        if (!AntiAFKSettings.shouldHideAFKCapchaMessagePrefix())
        {
            capchaMessage = capchaMessage
                .append(Text.literal("AFKapcha » ")
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCapchaPrefixColor())));
        }

        capchaMessage = capchaMessage
            .append(Text.literal("Thank you for proving you are active!")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCapchaTextColor())));

        player.sendMessage(capchaMessage);
    }

    public static void handlePlayerAFKKick(ServerPlayerEntity player)
    {
        UUID playerUUID = player.getUuid();

        if (!isPlayerAFK(player) || playersWhoWouldHaveBeenKicked.containsKey(playerUUID)) return;

        if (AntiAFKSettings.isNoKickModeEnabled())
        {
            ModLogger.info("Player %s should be AFK kicked right now with (lastPosMove=%.2f seconds - lastCamMove=%.2f seconds - lastChatMsg=%.2f seconds - lastCapcha=%.2f seconds)".formatted
                (
                    player.getDisplayName().getString(),
                    AntiAFKManager.getSecondsSinceLastPositionMovement(player),
                    AntiAFKManager.getSecondsSinceLastCameraMovement(player),
                    AntiAFKManager.getSecondsSinceLastMessageSent(player),
                    AntiAFKManager.getSecondsSinceLastCapchaAnsweredSent(player)
                )
            );

            playersWhoWouldHaveBeenKicked.put(playerUUID, player.getServer().getTicks());
        }
        else
        {
            handlePlayerKick(player, "You were inactive for too long.");
        }
    }

    private static void handlePlayerKick(ServerPlayerEntity player, String kickMessage)
    {
        if (LuckPermsUtils.hasPermission(player, "mirageantiafk.bypass.kick")) return;

        MutableText kickText = Text.literal("AFKChecker\n")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getKickTitleColor()));

        kickText = kickText
            .append(Text.literal(kickMessage)
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getKickDescriptionColor())));

        player.networkHandler.disconnect(kickText);
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

    public static double getSecondsSinceLastCapchaAnsweredSent(ServerPlayerEntity player)
    {
        return ticksToSeconds(player.getServer().getTicks() - playerTimesOfLastCapchaAnswer.getOrDefault(player.getUuid(), player.getServer().getTicks()));
    }

    public static boolean isCapchaTime(MinecraftServer server)
    {
        return server.getTicks() % secondsToTicks(AntiAFKSettings.getSecondsBetweenCapcha()) == 0;
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
