package dev.janheist.newsaddon.events;

import dev.janheist.newsaddon.main.NewsAddon;
import net.labymod.api.events.UserMenuActionEvent;
import net.labymod.user.User;
import net.labymod.user.util.UserActionEntry;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class userMenuAction implements UserMenuActionEvent {
    NewsAddon newsAddon;
    public userMenuAction(NewsAddon newsAddon) {
        this.newsAddon = newsAddon;
    }

    @Override
    public void createActions(User user, EntityPlayer entityPlayer, NetworkPlayerInfo networkPlayerInfo, List<UserActionEntry> list) {
        if(newsAddon.playermenu && (newsAddon.DEBUGMODE || newsAddon.getServer().contains("germanminer"))) {
            list.add(new UserActionEntry("§7[§aN§7]§f Grüßen", UserActionEntry.EnumActionType.SUGGEST_COMMAND, "/newsaddon show greet {name}".replaceAll("(§.)", ""), null));
            list.add(new UserActionEntry("§7[§aN§7]§f Werbearten", UserActionEntry.EnumActionType.SUGGEST_COMMAND, "/newsaddon show types", null));
            list.add(new UserActionEntry("§7[§aN§7]§f Scan", UserActionEntry.EnumActionType.SUGGEST_COMMAND, "/newsaddon scan {name}".replaceAll("(§.)", ""), null));
        }
    }

}
