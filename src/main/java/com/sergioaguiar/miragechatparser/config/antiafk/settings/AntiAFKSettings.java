package com.sergioaguiar.miragechatparser.config.antiafk.settings;

public class AntiAFKSettings
{
    protected static final int DEFAULT_SECONDS_TO_AFK = 600;
    protected static final int DEFAULT_SECONDS_TO_AFK_KICK = 1200;
    protected static final int DEFAULT_SECONDS_BETWEEN_CAPCHA = 600;
    protected static final int DEFAULT_FAILED_CAPCHA_BEFORE_KICK = 3;
    protected static final int DEFAULT_CAPCHA_LENGTH = 4;
    
    protected static final double DEFAULT_MINIMUM_POSITION_CHANGE_FOR_MOVEMENT_REGISTER = 0.05;
    protected static final float DEFAULT_MINIMUM_PITCH_CHANGE_FOR_CAMERA_MOVEMENT_REGISTER = 2;
    protected static final float DEFAULT_MINIMUM_YAW_CHANGE_FOR_CAMERA_MOVEMENT_REGISTER = 2;

    protected static final boolean DEFAULT_HIDE_AFK_CHECKER_MESSAGE_PREFIX = false;
    protected static final boolean DEFAULT_HIDE_AFK_CAPCHA_MESSAGE_PREFIX = false;

    private static int secondsToAFK;
    private static int secondsToAFKKick;
    private static int secondsBetweenCapcha;
    private static int failedCapchaBeforeKick;
    private static int capchaLength;

    private static double minimumPositionChangeForMovementRegister;
    private static float minimumPitchChangeForCameraMovementRegister;
    private static float minimumYawChangeForCameraMovementRegister;

    private static boolean hideAFKCheckerMessagePrefix;
    private static boolean hideAFKCapchaMessagePrefix;

    public static void setDefaults()
    {
        secondsToAFK = DEFAULT_SECONDS_TO_AFK;
        secondsToAFKKick = DEFAULT_SECONDS_TO_AFK_KICK;
        secondsBetweenCapcha = DEFAULT_SECONDS_BETWEEN_CAPCHA;
        failedCapchaBeforeKick = DEFAULT_FAILED_CAPCHA_BEFORE_KICK;
        capchaLength = DEFAULT_CAPCHA_LENGTH;

        minimumPositionChangeForMovementRegister = DEFAULT_MINIMUM_POSITION_CHANGE_FOR_MOVEMENT_REGISTER;
        minimumPitchChangeForCameraMovementRegister = DEFAULT_MINIMUM_PITCH_CHANGE_FOR_CAMERA_MOVEMENT_REGISTER;
        minimumYawChangeForCameraMovementRegister = DEFAULT_MINIMUM_YAW_CHANGE_FOR_CAMERA_MOVEMENT_REGISTER;

        hideAFKCheckerMessagePrefix = DEFAULT_HIDE_AFK_CHECKER_MESSAGE_PREFIX;
        hideAFKCapchaMessagePrefix = DEFAULT_HIDE_AFK_CAPCHA_MESSAGE_PREFIX;
    }

    public static int getSecondsToAFK() { return secondsToAFK; }
    public static int getSecondsToAFKKick() { return secondsToAFKKick; }
    public static int getSecondsBetweenCapcha() { return secondsBetweenCapcha; }
    public static int getFailedCapchaBeforeKick() { return failedCapchaBeforeKick; }
    public static int getCapchaLength() { return capchaLength; }
    public static double getMinimumPositionChangeForMovementRegister() { return minimumPositionChangeForMovementRegister; } 
    public static float getMinimumPitchChangeForCameraMovementRegister() { return minimumPitchChangeForCameraMovementRegister; }
    public static float getMinimumYawChangeForCameraMovementRegister() { return minimumYawChangeForCameraMovementRegister; }
    public static boolean shouldHideAFKCheckerMessagePrefix() { return hideAFKCheckerMessagePrefix; }
    public static boolean shouldHideAFKCapchaMessagePrefix() { return hideAFKCapchaMessagePrefix; }

    protected static void setSecondsToAFK(int seconds) { secondsToAFK = seconds; }
    protected static void setSecondsToAFKKick(int seconds) { secondsToAFKKick = seconds; }
    protected static void setSecondsBetweenCapcha(int seconds) { secondsBetweenCapcha = seconds; }
    protected static void setFailedCapchaBeforeKick(int capchaAmount) { failedCapchaBeforeKick = capchaAmount; }
    protected static void setCapchaLength(int length) { capchaLength = length; }
    protected static void setMinimumPositionChangeForMovementRegister(double minimum) { minimumPositionChangeForMovementRegister = minimum; }
    protected static void setMinimumPitchChangeForCameraMovementRegister(float minimum) { minimumPitchChangeForCameraMovementRegister = minimum; }
    protected static void setMinimumYawChangeForCameraMovementRegister(float minimum) { minimumYawChangeForCameraMovementRegister = minimum; }
    protected static void setHideAFKCheckerMessagePrefix(boolean enable) { hideAFKCheckerMessagePrefix = enable; }
    protected static void setHideAFKCapchaMessagePrefix(boolean enable) { hideAFKCapchaMessagePrefix = enable; }
}
