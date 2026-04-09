package com.sergioaguiar.miragechatparser.event;

import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;

public class AntiAFKMessageHandler
{
    public static void register() 
    {
        ServerMessageEvents.ALLOW_CHAT_MESSAGE.register(
            (message, sender, params) ->
            {
                boolean shouldSendMessage = true;

                if (AntiAFKManager.hasActiveMessageCapcha(sender))
                {
                    if (AntiAFKManager.isCorrectCapchaAnswer(sender, message.getSignedContent().trim()))
                    {
                        AntiAFKManager.handleCorrectCapchaAnswer(sender);
                        shouldSendMessage = false;
                    }
                }

                return shouldSendMessage;
            }
        );

        ServerMessageEvents.CHAT_MESSAGE.register(
            (message, sender, params) ->
            {
                AntiAFKManager.handlePlayerMessageSentLogic(sender, message.getSignedContent());
            }
        );

        ModLogger.info("Anti-AFK Message Checker (Main Chat) started.");
    }
}
