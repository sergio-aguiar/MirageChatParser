package com.sergioaguiar.mirageessentials;

import com.sergioaguiar.mirageessentials.command.InfoCommand;
import com.sergioaguiar.mirageessentials.command.ReloadCommand;
import com.sergioaguiar.mirageessentials.command.antiafk.AFKCommand;
import com.sergioaguiar.mirageessentials.command.antiafk.AFKListCommand;
import com.sergioaguiar.mirageessentials.command.antiafk.ForceCaptchaCommand;
import com.sergioaguiar.mirageessentials.command.antiafk.IsAFKCommand;
import com.sergioaguiar.mirageessentials.command.antiafk.fake.FakeCaptchaClickCommand;
import com.sergioaguiar.mirageessentials.command.chatparser.DebugShoutCommand;
import com.sergioaguiar.mirageessentials.command.chatparser.ItemShoutCommand;
import com.sergioaguiar.mirageessentials.command.chatparser.PCShoutCommand;
import com.sergioaguiar.mirageessentials.command.chatparser.PartyCheckCommand;
import com.sergioaguiar.mirageessentials.command.chatparser.PartyShoutAllCommand;
import com.sergioaguiar.mirageessentials.command.chatparser.PartyShoutCommand;
import com.sergioaguiar.mirageessentials.config.antiafk.colors.AntiAFKColors;
import com.sergioaguiar.mirageessentials.config.antiafk.colors.AntiAFKColorsConfig;
import com.sergioaguiar.mirageessentials.config.antiafk.settings.AntiAFKSettings;
import com.sergioaguiar.mirageessentials.config.antiafk.settings.AntiAFKSettingsConfig;
import com.sergioaguiar.mirageessentials.config.antiafk.strings.AntiAFKStrings;
import com.sergioaguiar.mirageessentials.config.antiafk.strings.AntiAFKStringsConfig;
import com.sergioaguiar.mirageessentials.config.chatparser.aspects.ChatAspects;
import com.sergioaguiar.mirageessentials.config.chatparser.aspects.ChatAspectsConfig;
import com.sergioaguiar.mirageessentials.config.chatparser.colors.ChatColors;
import com.sergioaguiar.mirageessentials.config.chatparser.colors.ChatColorsConfig;
import com.sergioaguiar.mirageessentials.config.chatparser.settings.ChatSettings;
import com.sergioaguiar.mirageessentials.config.chatparser.settings.ChatSettingsConfig;
import com.sergioaguiar.mirageessentials.config.chatparser.sizes.ChatSizes;
import com.sergioaguiar.mirageessentials.config.chatparser.sizes.ChatSizesConfig;
import com.sergioaguiar.mirageessentials.config.chatparser.strings.ChatStrings;
import com.sergioaguiar.mirageessentials.config.chatparser.strings.ChatStringsConfig;
import com.sergioaguiar.mirageessentials.config.chatparser.textures.GUITextures;
import com.sergioaguiar.mirageessentials.config.chatparser.textures.GUITexturesConfig;
import com.sergioaguiar.mirageessentials.config.modules.Modules;
import com.sergioaguiar.mirageessentials.config.modules.ModulesConfig;
import com.sergioaguiar.mirageessentials.event.antiafk.AntiAFKMessageHandler;
import com.sergioaguiar.mirageessentials.event.antiafk.AntiAFKPlayerDisconnectEventHandler;
import com.sergioaguiar.mirageessentials.event.antiafk.AntiAFKPlayerJoinEventHandler;
import com.sergioaguiar.mirageessentials.event.antiafk.AntiAFKTickEventHandler;
import com.sergioaguiar.mirageessentials.event.chatparser.ChatParserMessageHandler;
import com.sergioaguiar.mirageessentials.event.utils.LuckPermsPlayerJoinEventHandler;
import com.sergioaguiar.mirageessentials.event.utils.LuckPermsUserDataRecalculateEventHandler;
import com.sergioaguiar.mirageessentials.manager.AntiAFKManager;
import com.sergioaguiar.mirageessentials.util.LuckPermsUtils;
import com.sergioaguiar.mirageessentials.util.ModLogger;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class MirageEssentials implements ModInitializer 
{
	public static final String MOD_NAME = "MirageEssentials";
	public static final String MOD_ID = "mirageessentials";

	@Override
	public void onInitialize() 
	{
		ModLogger.info("Initializing %s...".formatted(MOD_NAME));

		// Config handling (defaults)
		try
		{
			Modules.setDefaults();

			ChatSettings.setDefaults();
			ChatStrings.setDefaults();
			ChatColors.setDefaults();
			ChatAspects.setDefaults();
			ChatSizes.setDefaults();
			GUITextures.setDefaults();

			AntiAFKSettings.setDefaults();
			AntiAFKStrings.setDefaults();
			AntiAFKColors.setDefaults();
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

			ChatSettingsConfig.load();
			ChatStringsConfig.load();
			ChatColorsConfig.load();
			ChatAspectsConfig.load();
			ChatSizesConfig.load();
			GUITexturesConfig.load();

			AntiAFKSettingsConfig.load();
			AntiAFKStringsConfig.load();
			AntiAFKColorsConfig.load();
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
			handleLuckPermsEvents();

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

			if (Modules.shouldEnableChatParserModule())
			{
				PartyShoutCommand.register();
				PartyShoutAllCommand.register();
				PCShoutCommand.register();
				DebugShoutCommand.register();
				PartyCheckCommand.register();
				ItemShoutCommand.register();
			}

			if (Modules.shouldEnableAntiAFKModule())
			{
				AFKCommand.register();
				IsAFKCommand.register();
				ForceCaptchaCommand.register();
				AFKListCommand.register();
				FakeCaptchaClickCommand.register();
			}
		}
		catch (Exception e)
		{
			ModLogger.error("Uncaught exception during command registering: %s".formatted(e.getMessage()));
			e.printStackTrace();
		}
	}

	private static void handleLuckPermsEvents()
	{
		if (!LuckPermsUtils.isModLoaded()) return;

		ServerLifecycleEvents.SERVER_STARTED.register(server ->
		{
			LuckPermsPlayerJoinEventHandler.register();
			LuckPermsUserDataRecalculateEventHandler.register();
		});

		ModLogger.info("LuckPerms event handlers started.");
	}
}