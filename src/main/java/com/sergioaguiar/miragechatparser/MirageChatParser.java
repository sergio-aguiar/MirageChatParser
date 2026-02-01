package com.sergioaguiar.miragechatparser;

import net.fabricmc.api.ModInitializer;

import com.sergioaguiar.miragechatparser.command.DebugShoutCommand;
import com.sergioaguiar.miragechatparser.command.InfoCommand;
import com.sergioaguiar.miragechatparser.command.PCShoutCommand;
import com.sergioaguiar.miragechatparser.command.PartyCheckCommand;
import com.sergioaguiar.miragechatparser.command.PartyShoutAllCommand;
import com.sergioaguiar.miragechatparser.command.PartyShoutCommand;
import com.sergioaguiar.miragechatparser.command.ReloadCommand;
import com.sergioaguiar.miragechatparser.config.aspects.ChatAspects;
import com.sergioaguiar.miragechatparser.config.aspects.ChatAspectsConfig;
import com.sergioaguiar.miragechatparser.config.colors.ChatColors;
import com.sergioaguiar.miragechatparser.config.colors.ChatColorsConfig;
import com.sergioaguiar.miragechatparser.config.settings.ChatSettings;
import com.sergioaguiar.miragechatparser.config.settings.ChatSettingsConfig;
import com.sergioaguiar.miragechatparser.config.sizes.ChatSizes;
import com.sergioaguiar.miragechatparser.config.sizes.ChatSizesConfig;
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

		try
		{
			// Config handling (defaults)
			ChatSettings.setDefaults();
			ChatStrings.setDefaults();
			ChatColors.setDefaults();
			ChatAspects.setDefaults();
			ChatSizes.setDefaults();
		}
		catch (Exception e)
		{
			ModLogger.error("Uncaught exception during default config setting: %s".formatted(e.getMessage()));
			e.printStackTrace();
		}

		try
		{
			// Config handling (config file overrides)
			ChatSettingsConfig.load();
			ChatStringsConfig.load();
			ChatColorsConfig.load();
			ChatAspectsConfig.load();
			ChatSizesConfig.load();
		}
		catch (Exception e)
		{
			ModLogger.error("Uncaught exception during config loading: %s".formatted(e.getMessage()));
			e.printStackTrace();
		}

		try
		{
			// Event registering
			ChatMessageHandler.register();
		}
		catch (Exception e)
		{
			ModLogger.error("Uncaught exception during event registering: %s".formatted(e.getMessage()));
			e.printStackTrace();
		}

		try
		{
			// Command registering
			ReloadCommand.register();
			PartyShoutCommand.register();
			PartyShoutAllCommand.register();
			PCShoutCommand.register();
			DebugShoutCommand.register();
			InfoCommand.register();
			PartyCheckCommand.register();
		}
		catch (Exception e)
		{
			ModLogger.error("Uncaught exception during command registering: %s".formatted(e.getMessage()));
			e.printStackTrace();
		}
	}
}