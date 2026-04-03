package com.sergioaguiar.miragechatparser.event;

import com.sergioaguiar.miragechatparser.config.modules.Modules;
import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.message.v1.ServerMessageDecoratorEvent;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class AntiAFKMessageHandler
{
    public static void register() 
    {
        if (!Modules.shouldEnableChatParserModule()) 
        {
            ModLogger.info("Anti-AFK Message Checker (Main Chat) skipped.");
            return;
        }

        ServerMessageDecoratorEvent.EVENT.register(ServerMessageDecoratorEvent.CONTENT_PHASE,
            (ServerPlayerEntity sender, Text message) -> 
            {
                AntiAFKManager.handlePlayerMessageSentLogic(sender, message.getString());
                return message;
            }
        );

        ModLogger.info("Anti-AFK Message Checker (Main Chat) started.");
    }
}
