package com.sergioaguiar.miragechatparser;

import net.fabricmc.api.ModInitializer;

import com.sergioaguiar.miragechatparser.command.DebugShoutCommand;
import com.sergioaguiar.miragechatparser.command.InfoCommand;
import com.sergioaguiar.miragechatparser.command.PCShoutCommand;
import com.sergioaguiar.miragechatparser.command.PartyCheckCommand;
import com.sergioaguiar.miragechatparser.command.PartyShoutAllCommand;
import com.sergioaguiar.miragechatparser.command.PartyShoutCommand;
import com.sergioaguiar.miragechatparser.command.ReloadCommand;
import com.sergioaguiar.miragechatparser.config.antiafk.settings.AntiAFKSettings;
import com.sergioaguiar.miragechatparser.config.antiafk.settings.AntiAFKSettingsConfig;
import com.sergioaguiar.miragechatparser.config.chatparser.aspects.ChatAspects;
import com.sergioaguiar.miragechatparser.config.chatparser.aspects.ChatAspectsConfig;
import com.sergioaguiar.miragechatparser.config.chatparser.colors.ChatColors;
import com.sergioaguiar.miragechatparser.config.chatparser.colors.ChatColorsConfig;
import com.sergioaguiar.miragechatparser.config.chatparser.settings.ChatSettings;
import com.sergioaguiar.miragechatparser.config.chatparser.settings.ChatSettingsConfig;
import com.sergioaguiar.miragechatparser.config.chatparser.sizes.ChatSizes;
import com.sergioaguiar.miragechatparser.config.chatparser.sizes.ChatSizesConfig;
import com.sergioaguiar.miragechatparser.config.chatparser.strings.ChatStrings;
import com.sergioaguiar.miragechatparser.config.chatparser.strings.ChatStringsConfig;
import com.sergioaguiar.miragechatparser.config.modules.Modules;
import com.sergioaguiar.miragechatparser.config.modules.ModulesConfig;
import com.sergioaguiar.miragechatparser.event.ChatParserMessageHandler;
import com.sergioaguiar.miragechatparser.event.AntiAFKPlayerJoinEventHandler;
import com.sergioaguiar.miragechatparser.event.AntiAFKMessageHandler;
import com.sergioaguiar.miragechatparser.event.AntiAFKPlayerDisconnectEventHandler;
import com.sergioaguiar.miragechatparser.event.AntiAFKTickEventHandler;
import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;
import com.sergioaguiar.miragechatparser.util.ModLogger;

public class MirageChatParser implements ModInitializer 
{
	public static final String MOD_ID = "miragechatparser";

	@Override
	public void onInitialize() 
	{
		ModLogger.info("Initializing MirageChatParser...");

		// Config handling (defaults)
		try
		{
			Modules.setDefaults();

			ChatSettings.setDefaults();
			ChatStrings.setDefaults();
			ChatColors.setDefaults();
			ChatAspects.setDefaults();
			ChatSizes.setDefaults();

			AntiAFKSettings.setDefaults();
		}
		catch (Exception e)
		{
			ModLogger.error("Uncaught exception during default config setting: %s".formatted(e.getMessage()));
			e.printStackTrace();
		}

		// Config handling (config file overrides)
		try
		{
			ModulesConfig.load();

			if (Modules.shouldEnableChatParserModule())
			{
				ChatSettingsConfig.load();
				ChatStringsConfig.load();
				ChatColorsConfig.load();
				ChatAspectsConfig.load();
				ChatSizesConfig.load();
			}

			if (Modules.shouldEnableAntiAFKModule())
			{
				AntiAFKSettingsConfig.load();
			}
		}
		catch (Exception e)
		{
			ModLogger.error("Uncaught exception during config loading: %s".formatted(e.getMessage()));
			e.printStackTrace();
		}

		// Module starting
		try
		{
			if (Modules.shouldEnableAntiAFKModule())
			{
				AntiAFKManager.start();
			}
		}
		catch (Exception e)
		{
			ModLogger.error("Uncaught exception during module starting: %s".formatted(e.getMessage()));
			e.printStackTrace();
		}

		// Event registering
		try
		{
			if (Modules.shouldEnableChatParserModule())
			{
				ChatParserMessageHandler.register();
			}

			if (Modules.shouldEnableAntiAFKModule())
			{
				AntiAFKPlayerJoinEventHandler.register();
				AntiAFKPlayerDisconnectEventHandler.register();
				AntiAFKTickEventHandler.register();
				AntiAFKMessageHandler.register();
			}
		}
		catch (Exception e)
		{
			ModLogger.error("Uncaught exception during event registering: %s".formatted(e.getMessage()));
			e.printStackTrace();
		}

		// Command registering
		try
		{
			ReloadCommand.register();
			InfoCommand.register();

			if (Modules.shouldEnableAntiAFKModule())
			{
				PartyShoutCommand.register();
				PartyShoutAllCommand.register();
				PCShoutCommand.register();
				DebugShoutCommand.register();
				PartyCheckCommand.register();
			}
		}
		catch (Exception e)
		{
			ModLogger.error("Uncaught exception during command registering: %s".formatted(e.getMessage()));
			e.printStackTrace();
		}
	}
}