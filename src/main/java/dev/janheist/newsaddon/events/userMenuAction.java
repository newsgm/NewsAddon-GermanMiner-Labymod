package dev.janheist.newsaddon.events;

import net.labymod.api.events.UserMenuActionEvent;
import net.labymod.user.User;
import net.labymod.user.util.UserActionEntry;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class userMenuAction implements UserMenuActionEvent {
    @Override
    public void createActions(User user, EntityPlayer entityPlayer, NetworkPlayerInfo networkPlayerInfo, List<UserActionEntry> list) {
        list.add(new UserActionEntry("§7[§aN§7]§f Grüßen", UserActionEntry.EnumActionType.RUN_COMMAND, "/newsaddon show greet {name}", null));
        list.add(new UserActionEntry("§7[§aN§7]§f Werbearten", UserActionEntry.EnumActionType.RUN_COMMAND, "/newsaddon show types", null));
        list.add(new UserActionEntry("§7[§aN§7]§f Chatwerbung", UserActionEntry.EnumActionType.RUN_COMMAND, "/newsaddon show chat", null));
    }
}
