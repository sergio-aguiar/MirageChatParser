package com.sergioaguiar.miragechatparser.parser;

import java.util.regex.Matcher;

import com.sergioaguiar.miragechatparser.util.TextUtils;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ChatParser 
{
    public static Text parseMessage(ServerPlayerEntity player, String message) 
    {
        Matcher matcher = TextUtils.PLACEHOLDER_PATTERN.matcher(message);
        Text result = Text.empty();
        int lastEnd = 0;
        boolean replaced = false;

        while (matcher.find()) 
        {
            if (matcher.start() > lastEnd) 
            {
                result = result.copy().append(Text.literal(message.substring(lastEnd, matcher.start())));
            }

            String content = matcher.group(1).trim();
            Text replacement = PlaceholderResolver.resolveText(player, content);

            if (!replacement.getString().equals("[" + content + "]")) 
            {
                replaced = true;
            }

            result = result.copy().append(replacement);
            lastEnd = matcher.end();
        }

        if (lastEnd < message.length()) 
        {
            result = result.copy().append(Text.literal(message.substring(lastEnd)));
        }
        return replaced ? result : Text.literal(message);
    }
}
