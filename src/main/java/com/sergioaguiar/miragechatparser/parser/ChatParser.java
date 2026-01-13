package com.sergioaguiar.miragechatparser.parser;

import java.util.regex.Matcher;

import com.sergioaguiar.miragechatparser.util.ModLogger;
import com.sergioaguiar.miragechatparser.util.TextUtils;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ChatParser 
{
    public static Text parseMessage(ServerPlayerEntity player, Text message) 
    {
        try
        {
            String raw = message.getString();
            Matcher matcher = TextUtils.PLACEHOLDER_PATTERN.matcher(raw);
            Text result = Text.empty();
            int lastEnd = 0;
            boolean replaced = false;

            while (matcher.find()) 
            {
                if (matcher.start() > lastEnd) 
                {
                    result = result.copy().append(Text.literal(raw.substring(lastEnd, matcher.start())));
                }

                String content = matcher.group(1).trim();
                Text replacement = PlaceholderResolver.resolveText(player, content);

                if (replacement == null)
                {
                    result = result.copy().append(raw.substring(matcher.start(), matcher.end()));
                } 
                else
                {
                    replaced = true;
                    result = result.copy().append(replacement);
                }

                lastEnd = matcher.end();
            }

            if (lastEnd < raw.length()) 
            {
                result = result.copy().append(Text.literal(raw.substring(lastEnd)));
            }
            return replaced ? result : message;
        }
        catch (Exception e)
        {
            ModLogger.error("Unknown error while parsing message: %s".formatted(e.getMessage()));
            e.printStackTrace();
            return message;
        }
    }
}
