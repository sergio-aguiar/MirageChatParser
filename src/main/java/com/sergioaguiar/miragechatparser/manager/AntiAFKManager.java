package com.sergioaguiar.miragechatparser.manager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.sergioaguiar.miragechatparser.config.antiafk.colors.AntiAFKColors;
import com.sergioaguiar.miragechatparser.config.antiafk.settings.AntiAFKSettings;
import com.sergioaguiar.miragechatparser.util.LuckPermsUtils;
import com.sergioaguiar.miragechatparser.util.ModLogger;
import com.sergioaguiar.miragechatparser.util.TextUtils;

import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class AntiAFKManager
{
    private static class PlayerCaptcha
    {
        private final boolean isClick;
        private final String answer;
        private final String forcedBy;

        public PlayerCaptcha(boolean isClick, String forcedBy)
        {
            this.isClick = isClick;

            int length = AntiAFKSettings.getCaptchaLength();
            StringBuilder stringBuilder = new StringBuilder(length);
            Random random = new Random();

            for (int i = 0; i < length; i++)
            {
                stringBuilder.append(random.nextInt(10));
            }

            this.answer = stringBuilder.toString();
            this.forcedBy = forcedBy;
        }

        public boolean isClickCaptcha()
        {
            return isClick;
        }

        public boolean isTheRightAnswer(String answer)
        {
            return this.answer.equalsIgnoreCase(answer);
        }

        public String getForcedBy()
        {
            return forcedBy;
        }

        public Text getCaptchaText()
        {
            MutableText captchaText = Text.literal("").setStyle(Style.EMPTY);

            if (isClick)
            {
                captchaText = captchaText
                    .append(Text.literal("Please prove you are active: ")
                        .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaTextColor())));

                captchaText = captchaText
                    .append(Text.literal("[Click here]")
                        .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaQuestionColor())
                            .withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/mirageantiafk fakecaptchaclick %s".formatted(answer)))));
            }
            else
            {
                captchaText = captchaText
                    .append(Text.literal("Please type this into chat: ")
                        .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaTextColor())));

                captchaText = captchaText
                    .append(Text.literal(answer)
                        .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaQuestionColor())));
            }

            return captchaText;
        }

        public String getWarningSubtitle()
        {
            return (isClick) ? "Please click the chat CAPTCHA." : "Please type %s into chat.".formatted(answer);
        }
    }

    public enum KickReason
    {
        INACTIVE_FOR_TOO_LONG
        (
            "You were inactive for too long.",
            "Inactive for too long."
        ),
        IGNORING_CAPTCHA
        (
            "You ignored too many chat CAPTCHA.",
            "Ignored too many chat CAPTCHA."
        );

        final String playerKickMessage;
        final String chatKickMessage;

        KickReason(String playerKickMessage, String chatKickMessage)
        {
            this.playerKickMessage = playerKickMessage;
            this.chatKickMessage = chatKickMessage;
        }

        public String getPlayerKickMessage() { return playerKickMessage; }
        public String getChatKickMessage() { return chatKickMessage; }
    }

    public enum SuspicionType
    {
        FAKE_CAPTCHA_CLICK("Fake Click"),
        TOO_FAST_ANSWER("Fast Answer");

        final String susAction;

        SuspicionType(String susAction)
        {
            this.susAction = susAction;
        }

        public String getSusAction() { return susAction; }
    }

    private static final int TICKS_PER_SECOND = 20;

    private static Map<UUID, Integer> playerTimesOfLastPositionMovement;
    private static Map<UUID, Integer> playerTimesOfLastCameraMovement;
    private static Map<UUID, Integer> playerTimesOfLastMessageSent;
    private static Map<UUID, Integer> playerTimesOfLastCaptchaAnswer;
    private static Map<UUID, Integer> playerTimesOfLastCaptchaPrompt;
    private static Map<UUID, Integer> playerTimesOfAFKMark;

    private static Map<UUID, Vec3d> playerLastPositions;
    private static Map<UUID, Float> playerLastCameraPitches;
    private static Map<UUID, Float> playerLastCameraYaws;
    private static Map<UUID, String> playerLastMessages;

    private static Map<UUID, Integer> playersWhoWouldHaveBeenKicked;
    private static Map<UUID, PlayerCaptcha> playerActiveCaptchas;
    private static Map<UUID, Integer> playerIgnoredCaptchaCounts;
    private static Map<UUID, Integer> playerSuspiciousActionCount;

    public static void start()
    {
        playerTimesOfLastPositionMovement = new HashMap<>();
        playerTimesOfLastCameraMovement = new HashMap<>();
        playerTimesOfLastMessageSent = new HashMap<>();
        playerTimesOfLastCaptchaAnswer = new HashMap<>();
        playerTimesOfLastCaptchaPrompt = new HashMap<>();
        playerTimesOfAFKMark = new HashMap<>();

        playerLastPositions = new HashMap<>();
        playerLastCameraPitches = new HashMap<>();
        playerLastCameraYaws = new HashMap<>();
        playerLastMessages = new HashMap<>();

        playersWhoWouldHaveBeenKicked = new HashMap<>();
        playerActiveCaptchas = new HashMap<>();
        playerIgnoredCaptchaCounts = new HashMap<>();
        playerSuspiciousActionCount = new HashMap<>();
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

    public static void registerPlayerCaptchaAnswer(ServerPlayerEntity player, boolean isInit)
    {
        UUID playerUUID = player.getUuid();
        int currentTicks = player.getServer().getTicks();

        if (!isInit && currentTicks - playerTimesOfLastCaptchaPrompt.getOrDefault(playerUUID, currentTicks) < AntiAFKSettings.getMinimumTicksToNotCountAsSuspiciousCaptcha())
        {
            registerPlayerSuspiciousAction(player);
            broadcastSusActionMessageToPermissionUsers(player, SuspicionType.TOO_FAST_ANSWER.getSusAction());
        }

        playerTimesOfLastCaptchaAnswer.put(playerUUID, currentTicks);
        playerIgnoredCaptchaCounts.put(playerUUID, 0);
    }

    public static void registerPlayerSuspiciousAction(ServerPlayerEntity player)
    {
        UUID playerUUID = player.getUuid();

        playerSuspiciousActionCount.put(playerUUID, playerSuspiciousActionCount.getOrDefault(playerUUID, 0) + 1);
    }

    public static void registerPlayerCaptchaPrompt(ServerPlayerEntity player)
    {
        playerTimesOfLastCaptchaPrompt.put(player.getUuid(), player.getServer().getTicks());
    }

    public static void registerPlayerAsAFK(ServerPlayerEntity player)
    {
        if (isPlayerAFK(player)) return;

        playerTimesOfAFKMark.put(player.getUuid(), player.getServer().getTicks());

        player.getServer().getPlayerManager().broadcast(TextUtils.playerAFKMessage(player.getDisplayName().getString()), false);
    }

    public static void registerPlayerNoLongerAFK(ServerPlayerEntity player)
    {
        if (!isPlayerAFK(player)) return;

        UUID playerUUID = player.getUuid();
        int timeAFK = playerTimesOfAFKMark.getOrDefault(playerUUID, 0);

        playerTimesOfAFKMark.remove(playerUUID);
        playersWhoWouldHaveBeenKicked.remove(playerUUID);

        player.getServer().getPlayerManager().broadcast
        (
            TextUtils.playerNotAFKMessage
            (
                player,
                TextUtils.secondsToReadableTimeString((int) ticksToSeconds(player.getServer().getTicks() - timeAFK))
            ),
            false
        );
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
            playerTimesOfLastCaptchaAnswer.getOrDefault(playerUUID, player.getServer().getTicks())
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

    public static boolean shouldPlayerActionsBeIgnored(ServerPlayerEntity player)
    {
        return player.hasVehicle() || player.isTouchingWater() || player.isInFluid();
    }

    public static void handlePlayerInit(ServerPlayerEntity player)
    {
        UUID playerUUID = player.getUuid();

        registerPlayerPositionMovement(player);
        registerPlayerCameraMovement(player);
        registerPlayerMessageSent(player);
        registerPlayerCaptchaAnswer(player, true);
        registerPlayerCaptchaPrompt(player);

        registerPlayerLastPosition(player, player.getPos());
        registerPlayerLastCameraPitch(player, player.getPitch());
        registerPlayerLastCameraYaw(player, player.getYaw());
        registerPlayerLastMessage(player, "");

        playerIgnoredCaptchaCounts.put(playerUUID, 0);
    }

    public static void handlePlayerLeave(ServerPlayerEntity player)
    {
        UUID playerUUID = player.getUuid();

        playerTimesOfLastPositionMovement.remove(playerUUID);
        playerTimesOfLastCameraMovement.remove(playerUUID);
        playerTimesOfLastMessageSent.remove(playerUUID);
        playerTimesOfLastCaptchaAnswer.remove(playerUUID);
        playerTimesOfLastCaptchaPrompt.remove(playerUUID);
        playerTimesOfAFKMark.remove(playerUUID);

        playerLastPositions.remove(playerUUID);
        playerLastCameraPitches.remove(playerUUID);
        playerLastCameraYaws.remove(playerUUID);
        playerLastMessages.remove(playerUUID);

        playersWhoWouldHaveBeenKicked.remove(playerUUID);
        playerActiveCaptchas.remove(playerUUID);
        playerIgnoredCaptchaCounts.remove(playerUUID);
    }

    public static void handlePlayerPositionChangeLogic(ServerPlayerEntity player)
    {
        Vec3d currentPos = player.getPos();
        Vec3d lastPos = getPlayerLastPosition(player);

        double distanceMoved = currentPos.distanceTo(lastPos);

        if (!shouldPlayerActionsBeIgnored(player) && distanceMoved >= AntiAFKSettings.getMinimumPositionChangeForMovementRegister())
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

        if (!shouldPlayerActionsBeIgnored(player) 
            && (pitchChange >= AntiAFKSettings.getMinimumPitchChangeForCameraMovementRegister() 
                || yawChange >= AntiAFKSettings.getMinimumYawChangeForCameraMovementRegister()))
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

    public static void startCaptcha(ServerPlayerEntity player, String forcedBy)
    {
        UUID playerUUID = player.getUuid();
        int ignoredCaptchas = playerIgnoredCaptchaCounts.get(playerUUID) + 1;

        if (playerActiveCaptchas.containsKey(playerUUID))
        {
            String captchaForcedBy = playerActiveCaptchas.get(playerUUID).getForcedBy();
            if (!captchaForcedBy.equals("Server"))
            {
                for (ServerPlayerEntity serverPlayer : player.getServer().getPlayerManager().getPlayerList())
                {
                    if (serverPlayer.getUuidAsString().equals(captchaForcedBy))
                    {
                        serverPlayer.sendMessage(TextUtils.playerIgnoredForcedCaptcha(player.getDisplayName().getString()));
                        break;
                    }
                }
            }

            playerActiveCaptchas.remove(playerUUID);
            playerIgnoredCaptchaCounts.put(playerUUID, ignoredCaptchas);

            if (ignoredCaptchas >= AntiAFKSettings.getFailedCaptchaBeforeKick())
            {
                if (AntiAFKSettings.isNoKickModeEnabled())
                {
                    if (!playersWhoWouldHaveBeenKicked.containsKey(playerUUID))
                    {
                        ModLogger.info("Player %s should be AFK kicked right now with (ignoredCaptchas=%d)".formatted(player.getGameProfile().getName(), ignoredCaptchas));
                        playersWhoWouldHaveBeenKicked.put(playerUUID, player.getServer().getTicks());
                    }
                }
                else
                {
                    handlePlayerKick(player, KickReason.IGNORING_CAPTCHA);
                }
            }
        }

        playerActiveCaptchas.put(playerUUID, new PlayerCaptcha(ThreadLocalRandom.current().nextDouble() < AntiAFKSettings.getClickCaptchaProportion(), forcedBy));
        registerPlayerCaptchaPrompt(player);

        if (ignoredCaptchas == AntiAFKSettings.getFailedCaptchaBeforeKick() - 1)
        {
            player.networkHandler.sendPacket(new TitleS2CPacket(
                Text.literal(AntiAFKSettings.shouldUseCaptchaInWarning() ? "CAPTCHA" : "CAPTCHA WARNING")
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getCaptchaWarningTitleColor()))
            ));

            player.networkHandler.sendPacket(new SubtitleS2CPacket(
                Text.literal(AntiAFKSettings.shouldUseCaptchaInWarning() ? playerActiveCaptchas.get(playerUUID).getWarningSubtitle() : "Ignoring too many CAPTCHA may get you kicked!")
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getCaptchaWarningSubtitleColor()))
            ));
        }

        MutableText captchaMessage = Text.literal("").setStyle(Style.EMPTY);

        if (!AntiAFKSettings.shouldHideAFKCaptchaMessagePrefix())
        {
            captchaMessage = captchaMessage
                .append(Text.literal("AFKaptcha » ")
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaPrefixColor())));
        }

        captchaMessage = captchaMessage
            .append(playerActiveCaptchas.get(playerUUID).getCaptchaText());

        player.sendMessage(captchaMessage);
    }

    public static boolean hasActiveClickCaptcha(ServerPlayerEntity player)
    {
        UUID playerUUID = player.getUuid();

        return playerActiveCaptchas.containsKey(playerUUID) && playerActiveCaptchas.get(playerUUID).isClickCaptcha();
    }

    public static boolean hasActiveMessageCaptcha(ServerPlayerEntity player)
    {
        UUID playerUUID = player.getUuid();

        return playerActiveCaptchas.containsKey(playerUUID) && !playerActiveCaptchas.get(playerUUID).isClickCaptcha();
    }

    public static boolean isCorrectCaptchaAnswer(ServerPlayerEntity player, String answer)
    {
        UUID playerUUID = player.getUuid();

        if (!playerActiveCaptchas.containsKey(playerUUID)) return false;

        return playerActiveCaptchas.get(playerUUID).isTheRightAnswer(answer);
    }

    public static void handleCaptchaClick(ServerPlayerEntity player, String code)
    {
        UUID playerUUID = player.getUuid();

        if (!playerActiveCaptchas.containsKey(playerUUID)) 
        {
            registerPlayerSuspiciousAction(player);
            broadcastSusActionMessageToPermissionUsers(player, SuspicionType.FAKE_CAPTCHA_CLICK.getSusAction());
            return;
        }

        PlayerCaptcha captcha = playerActiveCaptchas.get(playerUUID);

        if (!captcha.isClickCaptcha()) 
        {
            registerPlayerSuspiciousAction(player);
            broadcastSusActionMessageToPermissionUsers(player, SuspicionType.FAKE_CAPTCHA_CLICK.getSusAction());
            return;
        }
        
        if (captcha.isTheRightAnswer(code))
        {
            handleCaptchaSuccess(player);
        }
        else
        {
            registerPlayerSuspiciousAction(player);
            broadcastSusActionMessageToPermissionUsers(player, SuspicionType.FAKE_CAPTCHA_CLICK.getSusAction());
        }
    }

    private static void broadcastSusActionMessageToPermissionUsers(ServerPlayerEntity player, String susAction)
    {
        for (ServerPlayerEntity serverPlayer : player.getServer().getPlayerManager().getPlayerList())
        {
            if (!LuckPermsUtils.hasPermission(serverPlayer, "mirageantiafk.info.sus")) continue;

            serverPlayer.sendMessage(TextUtils.playerPerformedSuspiciousCaptchaAction(player.getDisplayName().getString(), susAction));
        }
    }

    public static void handleCorrectCaptchaAnswer(ServerPlayerEntity player)
    {
        handleCaptchaSuccess(player);
    }

    private static void handleCaptchaSuccess(ServerPlayerEntity player)
    {
        UUID playerUUID = player.getUuid();

        String captchaForcedBy = playerActiveCaptchas.get(playerUUID).getForcedBy();
        if (!captchaForcedBy.equals("Server"))
        {
            for (ServerPlayerEntity serverPlayer : player.getServer().getPlayerManager().getPlayerList())
            {
                if (serverPlayer.getUuidAsString().equals(captchaForcedBy))
                {
                    serverPlayer.sendMessage(TextUtils.playerAnsweredForcedCaptcha(player.getDisplayName().getString()));
                    break;
                }
            }
        }

        playerActiveCaptchas.remove(playerUUID);

        registerPlayerNoLongerAFK(player);
        registerPlayerCaptchaAnswer(player, false);
        
        player.sendMessage(TextUtils.provedActivityMessage());
    }

    public static void handlePlayerAFKKick(ServerPlayerEntity player)
    {
        UUID playerUUID = player.getUuid();

        if (!isPlayerAFK(player) || playersWhoWouldHaveBeenKicked.containsKey(playerUUID)) return;

        if (AntiAFKSettings.isNoKickModeEnabled())
        {
            ModLogger.info("Player %s should be AFK kicked right now with (lastPosMove=%.2f seconds - lastCamMove=%.2f seconds - lastChatMsg=%.2f seconds - lastCaptcha=%.2f seconds)".formatted
                (
                    player.getGameProfile().getName(),
                    getSecondsSinceLastPositionMovement(player),
                    getSecondsSinceLastCameraMovement(player),
                    getSecondsSinceLastMessageSent(player),
                    getSecondsSinceLastCaptchaAnswerSent(player)
                )
            );

            playersWhoWouldHaveBeenKicked.put(playerUUID, player.getServer().getTicks());
        }
        else
        {
            handlePlayerKick(player, KickReason.INACTIVE_FOR_TOO_LONG);
        }
    }

    private static void handlePlayerKick(ServerPlayerEntity player, KickReason kickReason)
    {
        if (LuckPermsUtils.hasPermission(player, "mirageantiafk.bypass.kick")) return;

        switch (kickReason) {
            case KickReason.IGNORING_CAPTCHA:
                ModLogger.info("Player %s is about to be AFK kicked right now with (ignoredCaptchas=%d)".formatted(player.getGameProfile().getName(), getIgnoredCaptchas(player.getUuid())));
                break;
            case KickReason.INACTIVE_FOR_TOO_LONG:
                ModLogger.info("Player %s is about to be AFK kicked right now with (lastPosMove=%.2f seconds - lastCamMove=%.2f seconds - lastChatMsg=%.2f seconds - lastCaptcha=%.2f seconds)".formatted
                    (
                        player.getGameProfile().getName(),
                        getSecondsSinceLastPositionMovement(player),
                        getSecondsSinceLastCameraMovement(player),
                        getSecondsSinceLastMessageSent(player),
                        getSecondsSinceLastCaptchaAnswerSent(player)
                    )
                );
                break;
            default:
                break;
        }

        handlePlayerKickMessage(player, kickReason.getChatKickMessage());
        player.networkHandler.disconnect(TextUtils.playerKickMessage(kickReason));
    }

    private static void handlePlayerKickMessage(ServerPlayerEntity player, String kickMessage)
    {
        MinecraftServer server = player.getServer();
        int currentTicks = server.getTicks();

        server.getPlayerManager().broadcast(TextUtils.playerChatKickMessage(player.getDisplayName().getString(), kickMessage), false);

        for (ServerPlayerEntity serverPlayer : server.getPlayerManager().getPlayerList())
        {
            if (!LuckPermsUtils.hasPermission(serverPlayer, "mirageantiafk.info.kick")) continue;

            serverPlayer.sendMessage(TextUtils.playerPermKickMessage(player, currentTicks));
        }
    }

    public static double getSecondsSinceLastPositionMovement(ServerPlayerEntity player)
    {
        return getSecondsSinceLastPositionMovement(player.getUuid(), player.getServer().getTicks());
    }

    public static double getSecondsSinceLastPositionMovement(UUID playerUUID, int currentTicks)
    {
        return ticksToSeconds(currentTicks - playerTimesOfLastPositionMovement.getOrDefault(playerUUID, currentTicks));
    }

    public static double getSecondsSinceLastCameraMovement(ServerPlayerEntity player)
    {
        return getSecondsSinceLastCameraMovement(player.getUuid(), player.getServer().getTicks());
    }

    public static double getSecondsSinceLastCameraMovement(UUID playerUUID, int currentTicks)
    {
        return ticksToSeconds(currentTicks - playerTimesOfLastCameraMovement.getOrDefault(playerUUID, currentTicks));
    }

    public static double getSecondsSinceLastMessageSent(ServerPlayerEntity player)
    {
        return getSecondsSinceLastMessageSent(player.getUuid(), player.getServer().getTicks());
    }

    public static double getSecondsSinceLastMessageSent(UUID playerUUID, int currentTicks)
    {
        return ticksToSeconds(currentTicks - playerTimesOfLastMessageSent.getOrDefault(playerUUID, currentTicks));
    }

    public static double getSecondsSinceLastCaptchaAnswerSent(ServerPlayerEntity player)
    {
        return getSecondsSinceLastCaptchaAnswerSent(player.getUuid(), player.getServer().getTicks());
    }

    public static double getSecondsSinceLastCaptchaAnswerSent(UUID playerUUID, int currentTicks)
    {
        return ticksToSeconds(currentTicks - playerTimesOfLastCaptchaAnswer.getOrDefault(playerUUID, currentTicks));
    }

    public static double getSecondsSinceLastCaptchaPrompt(UUID playerUUID, int currentTicks)
    {
        return ticksToSeconds(currentTicks - playerTimesOfLastCaptchaPrompt.getOrDefault(playerUUID, currentTicks));
    }

    public static int getIgnoredCaptchas(UUID playerUuid)
    {
        return playerIgnoredCaptchaCounts.getOrDefault(playerUuid, 0);
    }

    public static int getPlayerSuspiciousActionCount(UUID playerUuid)
    {
        return playerSuspiciousActionCount.getOrDefault(playerUuid, 0);
    }

    public static boolean isCaptchaTime(int currentTicks)
    {
        return currentTicks % secondsToTicks(AntiAFKSettings.getSecondsBetweenCaptcha()) == 0;
    }

    public static boolean isIndividualPlayerCaptchaTime(int currentTicks, UUID playerUUID)
    {
        return currentTicks - playerTimesOfLastCaptchaPrompt.getOrDefault(playerUUID, currentTicks) >= secondsToTicks(AntiAFKSettings.getSecondsBetweenCaptcha());
    }

    private static int secondsToTicks(int seconds)
    {
        return seconds * TICKS_PER_SECOND;
    }

    private static double ticksToSeconds(int ticks)
    {
        return ticks / (double) TICKS_PER_SECOND;
    }

    
}
