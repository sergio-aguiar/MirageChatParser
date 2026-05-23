package com.sergioaguiar.mirageessentials.mixin.antiafk;

import java.util.Collection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sergioaguiar.mirageessentials.config.antiafk.colors.AntiAFKColors;
import com.sergioaguiar.mirageessentials.config.antiafk.settings.AntiAFKSettings;
import com.sergioaguiar.mirageessentials.manager.AntiAFKManager;

import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.command.MessageCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

@Mixin(MessageCommand.class)
public class AntiAFKMsgCommandMixin
{
    @Inject(method = "execute", at = @At("RETURN"))
    private static void onExecute(ServerCommandSource source, Collection<ServerPlayerEntity> targets, SignedMessage message, CallbackInfo ci)
    {
        for (ServerPlayerEntity player : targets)
        {
            if (!AntiAFKManager.isPlayerAFK(player)) continue;
            
            MutableText afkText = Text.literal("").setStyle(Style.EMPTY);

            if (!AntiAFKSettings.shouldHideAFKCheckerMessagePrefix())
            {
                afkText = afkText
                    .append(Text.literal("AFKChecker » ")
                        .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerPrefixColor())));
            }

            afkText = afkText
                .append(Text.literal("Player ")
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerTextColor())));

            afkText = afkText
                .append(Text.literal(player.getDisplayName().getString())
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerPlayerColor())));

            afkText = afkText
                .append(Text.literal(" is marked as AFK and may not see your message.")
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerTextColor())));

            source.sendMessage(afkText);
        }
    }
}
