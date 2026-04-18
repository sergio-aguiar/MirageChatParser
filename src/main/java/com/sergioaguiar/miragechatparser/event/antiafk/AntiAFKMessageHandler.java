package com.sergioaguiar.miragechatparser.event.antiafk;

import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;
import com.sergioaguiar.miragechatparser.util.LuckPermsUtils;
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

                if (AntiAFKManager.hasActiveMessageCaptcha(sender))
                {
                    if (AntiAFKManager.isCorrectCaptchaAnswer(sender, message.getSignedContent().trim()))
                    {
                        AntiAFKManager.handleCorrectCaptchaAnswer(sender);
                        shouldSendMessage = false;
                    }
                }

                return shouldSendMessage;
            }
        );

        ServerMessageEvents.CHAT_MESSAGE.register(
            (message, sender, params) ->
            {
                if (LuckPermsUtils.hasPermission(sender, "mirageantiafk.bypass.check")) return;

                AntiAFKManager.handlePlayerMessageSentLogic(sender, message.getSignedContent());
            }
        );

        ModLogger.info("Anti-AFK Message Checker (Main Chat) started.");
    }
}
