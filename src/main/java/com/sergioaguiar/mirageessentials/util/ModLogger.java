package com.sergioaguiar.mirageessentials.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sergioaguiar.mirageessentials.MirageEssentials;

public class ModLogger
{
    private static final String PREFIX = "[%s] ".formatted(MirageEssentials.MOD_NAME);
    private static final Logger LOGGER = LoggerFactory.getLogger(MirageEssentials.MOD_ID);

    public static void info(String msg) {
        LOGGER.info(PREFIX + msg);
    }

    public static void warn(String msg) {
        LOGGER.warn(PREFIX + msg);
    }

    public static void error(String msg) {
        LOGGER.error(PREFIX + msg);
    }

    public static void error(String msg, Throwable t) {
        LOGGER.error(PREFIX + msg, t);
    }

    public static void debug(String msg) {
        LOGGER.debug(PREFIX + msg);
    }
}
