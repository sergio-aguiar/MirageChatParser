package com.sergioaguiar.miragechatparser.event;

import com.sergioaguiar.miragechatparser.config.modules.Modules;
import com.sergioaguiar.miragechatparser.config.settings.ChatSettings;
import com.sergioaguiar.miragechatparser.parser.ChatParser;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.message.v1.ServerMessageDecoratorEvent;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ChatParserMessageHandler 
{
    public static void register() 
    {
        if (!Modules.shouldEnableChatParserModule()) 
        {
            ModLogger.info("Chat Parser (Main Chat) skipped.");
            return;
        }

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

        ModLogger.info("Chat Parser (Main Chat) started.");
    }
}
