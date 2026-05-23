package com.sergioaguiar.mirageessentials.event.chatparser;

import com.sergioaguiar.mirageessentials.config.chatparser.settings.ChatSettings;
import com.sergioaguiar.mirageessentials.parser.ChatParser;
import com.sergioaguiar.mirageessentials.util.ModLogger;

import net.fabricmc.fabric.api.message.v1.ServerMessageDecoratorEvent;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ChatParserMessageHandler 
{
    public static void register() 
    {
        ServerMessageDecoratorEvent.EVENT.register(ServerMessageDecoratorEvent.CONTENT_PHASE,
            (ServerPlayerEntity sender, Text message) -> 
            {
                if (!ChatSettings.shouldParseNonPlayerMessages() && sender == null)
                {
                    return message;
                }
                return ChatParser.parseMessage(sender, message);
            }
        );

        ModLogger.info("Chat Parser Message Checker (Main Chat) started.");
    }
}
