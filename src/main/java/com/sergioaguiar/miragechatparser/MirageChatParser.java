package com.sergioaguiar.miragechatparser;

import net.fabricmc.api.ModInitializer;

import com.sergioaguiar.miragechatparser.command.DebugShoutCommand;
import com.sergioaguiar.miragechatparser.command.PCShoutCommand;
import com.sergioaguiar.miragechatparser.command.PartyShoutAllCommand;
import com.sergioaguiar.miragechatparser.command.PartyShoutCommand;
import com.sergioaguiar.miragechatparser.command.ReloadCommand;
import com.sergioaguiar.miragechatparser.config.colors.ChatColors;
import com.sergioaguiar.miragechatparser.config.colors.ChatColorsConfig;
import com.sergioaguiar.miragechatparser.config.settings.ChatSettings;
import com.sergioaguiar.miragechatparser.config.settings.ChatSettingsConfig;
import com.sergioaguiar.miragechatparser.config.strings.ChatStrings;
import com.sergioaguiar.miragechatparser.config.strings.ChatStringsConfig;
import com.sergioaguiar.miragechatparser.event.ChatMessageHandler;
import com.sergioaguiar.miragechatparser.util.ModLogger;

public class MirageChatParser implements ModInitializer 
{
	public static final String MOD_ID = "miragechatparser";

	@Override
	public void onInitialize() 
	{
		ModLogger.info("Initializing MirageChatParser...");

		// Config handling (defaults)
		ChatSettings.setDefaults();
		ChatStrings.setDefaults();
		ChatColors.setDefaults();

		// Config handling (config file overrides)
		ChatSettingsConfig.load();
		ChatStringsConfig.load();
		ChatColorsConfig.load();

		// Event registering
		ChatMessageHandler.register();

		// Command registering
		ReloadCommand.register();
		PartyShoutCommand.register();
		PartyShoutAllCommand.register();
		PCShoutCommand.register();
		DebugShoutCommand.register();
	}
}