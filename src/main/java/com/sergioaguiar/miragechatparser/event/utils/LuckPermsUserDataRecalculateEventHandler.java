package com.sergioaguiar.miragechatparser.event.utils;

import com.sergioaguiar.miragechatparser.util.LuckPermsUtils;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.event.user.UserDataRecalculateEvent;

public class LuckPermsUserDataRecalculateEventHandler
{
    public static void register()
    {
        if (!LuckPermsUtils.isModLoaded())
        {
            return;
        }

        LuckPerms luckPerms = LuckPermsProvider.get();

        luckPerms.getEventBus().subscribe
        (
            UserDataRecalculateEvent.class,
            event -> LuckPermsUtils.clearPermsForPlayer(event.getUser().getUniqueId())
        );

        ModLogger.info("LuckPerms User Data Recalculation Handler started.");
    }
}
