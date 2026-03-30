package com.sergioaguiar.miragechatparser.config.antiafk;

public class AntiAFKSettings
{
    protected static final int DEFAULT_SECONDS_TO_AFK = 600;
    protected static final int DEFAULT_SECONDS_TO_AFK_KICK = 1200;
    protected static final int DEFAULT_SECONDS_BETWEEN_QUESTIONS = 600;
    protected static final int DEFAULT_FAILED_QUESTIONS_BEFORE_KICK = 3;

    private static int secondsToAFK;
    private static int secondsToAFKKick;
    private static int secondsBetweenQuestions;
    private static int failedQuestionsBeforeKick;

    public static void setDefaults()
    {
        secondsToAFK = DEFAULT_SECONDS_TO_AFK;
        secondsToAFKKick = DEFAULT_SECONDS_TO_AFK_KICK;
        secondsBetweenQuestions = DEFAULT_SECONDS_BETWEEN_QUESTIONS;
        failedQuestionsBeforeKick = DEFAULT_FAILED_QUESTIONS_BEFORE_KICK;
    }

    public static int getSecondsToAFK() { return secondsToAFK; }
    public static int getSecondsToAFKKick() { return secondsToAFKKick; }
    public static int getSecondsBetweenQuestions() { return secondsBetweenQuestions; }
    public static int getFailedQuestionsBeforeKick() { return failedQuestionsBeforeKick; }

    protected static void setSecondsToAFK(int seconds) { secondsToAFK = seconds; }
    protected static void setSecondsToAFKKick(int seconds) { secondsToAFKKick = seconds; }
    protected static void setSecondsBetweenQuestions(int seconds) { secondsBetweenQuestions = seconds; }
    protected static void setFailedQuestionsBeforeKick(int questionAmount) { failedQuestionsBeforeKick = questionAmount; }
}
