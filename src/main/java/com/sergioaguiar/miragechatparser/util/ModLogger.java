package com.sergioaguiar.miragechatparser.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModLogger
{
    private static final String PREFIX = "[MirageChatParser] ";
    private static final Logger LOGGER = LoggerFactory.getLogger("miragechatparser");

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
