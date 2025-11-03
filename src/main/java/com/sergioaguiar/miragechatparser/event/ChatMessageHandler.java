package com.sergioaguiar.miragechatparser.event;

import com.sergioaguiar.miragechatparser.parser.ChatParser;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.message.v1.ServerMessageDecoratorEvent;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ChatMessageHandler 
{
    public static void register() 
    {
        ServerMessageDecoratorEvent.EVENT.register(ServerMessageDecoratorEvent.CONTENT_PHASE,
            (ServerPlayerEntity sender, Text message) -> ChatParser.parseMessage(sender, message.getString())
        );

        ModLogger.info("Chat Parser started.");
    }
}
