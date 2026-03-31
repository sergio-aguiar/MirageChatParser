package com.sergioaguiar.miragechatparser.config.antiafk;

public class AntiAFKSettings
{
    protected static final int DEFAULT_SECONDS_TO_AFK = 600;
    protected static final int DEFAULT_SECONDS_TO_AFK_KICK = 1200;
    protected static final int DEFAULT_SECONDS_BETWEEN_QUESTIONS = 600;
    protected static final int DEFAULT_FAILED_QUESTIONS_BEFORE_KICK = 3;
    
    protected static final double DEFAULT_MINIMUM_POSITION_CHANGE_FOR_MOVEMENT_REGISTER = 0.05;
    protected static final float DEFAULT_MINIMUM_PITCH_CHANGE_FOR_CAMERA_MOVEMENT_REGISTER = 2;
    protected static final float DEFAULT_MINIMUM_YAW_CHANGE_FOR_CAMERA_MOVEMENT_REGISTER = 2;

    private static int secondsToAFK;
    private static int secondsToAFKKick;
    private static int secondsBetweenQuestions;
    private static int failedQuestionsBeforeKick;

    private static double minimumPositionChangeForMovementRegister;
    private static float minimumPitchChangeForCameraMovementRegister;
    private static float minimumYawChangeForCameraMovementRegister;

    public static void setDefaults()
    {
        secondsToAFK = DEFAULT_SECONDS_TO_AFK;
        secondsToAFKKick = DEFAULT_SECONDS_TO_AFK_KICK;
        secondsBetweenQuestions = DEFAULT_SECONDS_BETWEEN_QUESTIONS;
        failedQuestionsBeforeKick = DEFAULT_FAILED_QUESTIONS_BEFORE_KICK;

        minimumPositionChangeForMovementRegister = DEFAULT_MINIMUM_POSITION_CHANGE_FOR_MOVEMENT_REGISTER;
        minimumPitchChangeForCameraMovementRegister = DEFAULT_MINIMUM_PITCH_CHANGE_FOR_CAMERA_MOVEMENT_REGISTER;
        minimumYawChangeForCameraMovementRegister = DEFAULT_MINIMUM_YAW_CHANGE_FOR_CAMERA_MOVEMENT_REGISTER;
    }

    public static int getSecondsToAFK() { return secondsToAFK; }
    public static int getSecondsToAFKKick() { return secondsToAFKKick; }
    public static int getSecondsBetweenQuestions() { return secondsBetweenQuestions; }
    public static int getFailedQuestionsBeforeKick() { return failedQuestionsBeforeKick; }
    public static double getMinimumPositionChangeForMovementRegister() { return minimumPositionChangeForMovementRegister; } 
    public static float getMinimumPitchChangeForCameraMovementRegister() { return minimumPitchChangeForCameraMovementRegister; }
    public static float getMinimumYawChangeForCameraMovementRegister() { return minimumYawChangeForCameraMovementRegister; }

    protected static void setSecondsToAFK(int seconds) { secondsToAFK = seconds; }
    protected static void setSecondsToAFKKick(int seconds) { secondsToAFKKick = seconds; }
    protected static void setSecondsBetweenQuestions(int seconds) { secondsBetweenQuestions = seconds; }
    protected static void setFailedQuestionsBeforeKick(int questionAmount) { failedQuestionsBeforeKick = questionAmount; }
    protected static void setMinimumPositionChangeForMovementRegister(double minimum) { minimumPositionChangeForMovementRegister = minimum; }
    protected static void setMinimumPitchChangeForCameraMovementRegister(float minimum) { minimumPitchChangeForCameraMovementRegister = minimum; }
    protected static void setMinimumYawChangeForCameraMovementRegister(float minimum) { minimumYawChangeForCameraMovementRegister = minimum; }
}
