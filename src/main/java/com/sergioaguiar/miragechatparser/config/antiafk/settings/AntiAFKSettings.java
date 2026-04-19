package com.sergioaguiar.miragechatparser.config.antiafk.settings;

public class AntiAFKSettings
{
    protected static final boolean DEFAULT_NO_KICK_MODE_ENABLED = false;
    protected static final boolean DEFAULT_USE_CAPTCHA_IN_WARNING = false;
    protected static final boolean DEFAULT_USE_INDIVIDUAL_PLAYER_CAPTCHA_TIMES = false;
    protected static final boolean DEFAULT_HIDE_AFK_TIMES_WHEN_BYPASSING_KICKS = true;

    protected static final int DEFAULT_SECONDS_TO_AFK = 600;
    protected static final int DEFAULT_SECONDS_TO_AFK_KICK = 3000;
    protected static final int DEFAULT_SECONDS_BETWEEN_CAPTCHA = 900;
    protected static final int DEFAULT_FAILED_CAPTCHA_BEFORE_KICK = 3;
    protected static final int DEFAULT_CAPTCHA_LENGTH = 4;
    protected static final double DEFAULT_CLICK_CAPTCHA_PROPORTION = 0.5;
    
    protected static final double DEFAULT_MINIMUM_POSITION_CHANGE_FOR_MOVEMENT_REGISTER = 0.05;
    protected static final float DEFAULT_MINIMUM_PITCH_CHANGE_FOR_CAMERA_MOVEMENT_REGISTER = 2;
    protected static final float DEFAULT_MINIMUM_YAW_CHANGE_FOR_CAMERA_MOVEMENT_REGISTER = 2;
    protected static final int DEFAULT_MINIMUM_TICKS_TO_NOT_COUNT_AS_SUSPICIOUS_CAPTCHA = 20;

    protected static final boolean DEFAULT_HIDE_AFK_CHECKER_MESSAGE_PREFIX = false;
    protected static final boolean DEFAULT_HIDE_AFK_CAPTCHA_MESSAGE_PREFIX = false;

    private static boolean noKickModeEnabled;
    private static boolean useCaptchaInWarning;
    private static boolean useIndividualPlayerCaptchaTimes;
    private static boolean hideAFKTimesWhenBypassingKicks;

    private static int secondsToAFK;
    private static int secondsToAFKKick;
    private static int secondsBetweenCaptcha;
    private static int failedCaptchaBeforeKick;
    private static int captchaLength;
    private static double clickCaptchaProportion;

    private static double minimumPositionChangeForMovementRegister;
    private static float minimumPitchChangeForCameraMovementRegister;
    private static float minimumYawChangeForCameraMovementRegister;
    private static int minimumTicksToNotCountAsSuspiciousCaptcha;

    private static boolean hideAFKCheckerMessagePrefix;
    private static boolean hideAFKCaptchaMessagePrefix;

    public static void setDefaults()
    {
        noKickModeEnabled = DEFAULT_NO_KICK_MODE_ENABLED;
        useCaptchaInWarning = DEFAULT_USE_CAPTCHA_IN_WARNING;
        useIndividualPlayerCaptchaTimes = DEFAULT_USE_INDIVIDUAL_PLAYER_CAPTCHA_TIMES;
        hideAFKTimesWhenBypassingKicks = DEFAULT_HIDE_AFK_TIMES_WHEN_BYPASSING_KICKS;

        secondsToAFK = DEFAULT_SECONDS_TO_AFK;
        secondsToAFKKick = DEFAULT_SECONDS_TO_AFK_KICK;
        secondsBetweenCaptcha = DEFAULT_SECONDS_BETWEEN_CAPTCHA;
        failedCaptchaBeforeKick = DEFAULT_FAILED_CAPTCHA_BEFORE_KICK;
        captchaLength = DEFAULT_CAPTCHA_LENGTH;
        clickCaptchaProportion = DEFAULT_CLICK_CAPTCHA_PROPORTION;

        minimumPositionChangeForMovementRegister = DEFAULT_MINIMUM_POSITION_CHANGE_FOR_MOVEMENT_REGISTER;
        minimumPitchChangeForCameraMovementRegister = DEFAULT_MINIMUM_PITCH_CHANGE_FOR_CAMERA_MOVEMENT_REGISTER;
        minimumYawChangeForCameraMovementRegister = DEFAULT_MINIMUM_YAW_CHANGE_FOR_CAMERA_MOVEMENT_REGISTER;
        minimumTicksToNotCountAsSuspiciousCaptcha = DEFAULT_MINIMUM_TICKS_TO_NOT_COUNT_AS_SUSPICIOUS_CAPTCHA;

        hideAFKCheckerMessagePrefix = DEFAULT_HIDE_AFK_CHECKER_MESSAGE_PREFIX;
        hideAFKCaptchaMessagePrefix = DEFAULT_HIDE_AFK_CAPTCHA_MESSAGE_PREFIX;
    }

    public static boolean isNoKickModeEnabled() { return noKickModeEnabled; }
    public static boolean shouldUseCaptchaInWarning() { return useCaptchaInWarning; }
    public static boolean shouldUseIndividualPlayerCaptchaTimes() { return useIndividualPlayerCaptchaTimes; }
    public static boolean shouldHideAFKTimesWhenBypassingKicks() { return hideAFKTimesWhenBypassingKicks; }
    public static int getSecondsToAFK() { return secondsToAFK; }
    public static int getSecondsToAFKKick() { return secondsToAFKKick; }
    public static int getSecondsBetweenCaptcha() { return secondsBetweenCaptcha; }
    public static int getFailedCaptchaBeforeKick() { return failedCaptchaBeforeKick; }
    public static int getCaptchaLength() { return captchaLength; }
    public static double getClickCaptchaProportion() { return clickCaptchaProportion; }
    public static double getMinimumPositionChangeForMovementRegister() { return minimumPositionChangeForMovementRegister; } 
    public static float getMinimumPitchChangeForCameraMovementRegister() { return minimumPitchChangeForCameraMovementRegister; }
    public static float getMinimumYawChangeForCameraMovementRegister() { return minimumYawChangeForCameraMovementRegister; }
    public static int getMinimumTicksToNotCountAsSuspiciousCaptcha() { return minimumTicksToNotCountAsSuspiciousCaptcha; }
    public static boolean shouldHideAFKCheckerMessagePrefix() { return hideAFKCheckerMessagePrefix; }
    public static boolean shouldHideAFKCaptchaMessagePrefix() { return hideAFKCaptchaMessagePrefix; }

    protected static void setNoKickMode(boolean enable) { noKickModeEnabled = enable; }
    protected static void setUseCaptchaInWarning(boolean enable) { useCaptchaInWarning = enable; }
    protected static void setUseIndividualPlayerCaptchaTimes(boolean enable) { useIndividualPlayerCaptchaTimes = enable; }
    protected static void setHideAFKTimesWhenBypassingKicks(boolean enable) { hideAFKTimesWhenBypassingKicks = enable; }
    protected static void setSecondsToAFK(int seconds) { secondsToAFK = seconds; }
    protected static void setSecondsToAFKKick(int seconds) { secondsToAFKKick = seconds; }
    protected static void setSecondsBetweenCaptcha(int seconds) { secondsBetweenCaptcha = seconds; }
    protected static void setFailedCaptchaBeforeKick(int captchaAmount) { failedCaptchaBeforeKick = captchaAmount; }
    protected static void setCaptchaLength(int length) { captchaLength = length; }
    protected static void setClickCaptchaProportion(double proportion) { clickCaptchaProportion = proportion; }
    protected static void setMinimumPositionChangeForMovementRegister(double minimum) { minimumPositionChangeForMovementRegister = minimum; }
    protected static void setMinimumPitchChangeForCameraMovementRegister(float minimum) { minimumPitchChangeForCameraMovementRegister = minimum; }
    protected static void setMinimumYawChangeForCameraMovementRegister(float minimum) { minimumYawChangeForCameraMovementRegister = minimum; }
    protected static void setMinimumTicksToNotCountAsSuspiciousCaptcha(int minimum) { minimumTicksToNotCountAsSuspiciousCaptcha = minimum; }
    protected static void setHideAFKCheckerMessagePrefix(boolean enable) { hideAFKCheckerMessagePrefix = enable; }
    protected static void setHideAFKCaptchaMessagePrefix(boolean enable) { hideAFKCaptchaMessagePrefix = enable; }
}
