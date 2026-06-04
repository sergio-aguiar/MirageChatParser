package com.sergioaguiar.mirageessentials.mixin.antiafk;

import java.util.Collection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.sergioaguiar.mirageessentials.config.antiafk.colors.AntiAFKColors;
import com.sergioaguiar.mirageessentials.config.antiafk.settings.AntiAFKSettings;
import com.sergioaguiar.mirageessentials.manager.AntiAFKManager;
import com.sergioaguiar.mirageessentials.util.TextUtils;

import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.command.MessageCommand;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

@Mixin(MessageCommand.class)
public class AntiAFKMsgCommandMixin
{
    @Inject(method = "execute", at = @At("RETURN"))
    private static void onExecute(ServerCommandSource source, Collection<ServerPlayerEntity> targets, SignedMessage message, CallbackInfo ci)
    {
        for (ServerPlayerEntity player : targets)
        {
            if (!AntiAFKManager.isPlayerAFK(player)) continue;
            
            TextUtils.CustomTextBuilder textBuilder = new TextUtils.CustomTextBuilder();

            if (!AntiAFKSettings.shouldHideAFKCheckerMessagePrefix())
            {
                textBuilder.append
                (
                    "AFKChecker » ",
                    AntiAFKColors.getAFKCheckerPrefixColor()
                );
            }

            textBuilder.append
            (
                "Player ",
                AntiAFKColors.getAFKCheckerTextColor()
            );

            textBuilder.append
            (
                player.getDisplayName().getString(),
                AntiAFKColors.getAFKCheckerPlayerColor()
            );

            textBuilder.append
            (
                " is marked as AFK and may not see your message.",
                AntiAFKColors.getAFKCheckerTextColor()
            );

            source.sendMessage(textBuilder.getText());
        }
    }
}
