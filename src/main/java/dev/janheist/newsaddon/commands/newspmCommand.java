package dev.janheist.newsaddon.commands;

import dev.janheist.newsaddon.features.PlayerUtilities;
import dev.janheist.newsaddon.main.NewsAddon;

public class newspmCommand {

    PlayerUtilities pUtils = new PlayerUtilities();
    NewsAddon newsAddon;
    public newspmCommand(NewsAddon newsAddon) {
        this.newsAddon = newsAddon;
    }

    public void init(String message) {
        try {
            String[] args = message.split(" ");

            if(args.length < 3) {
                pUtils.displayNormal("§c§l[N-PM] §cKorrekte Nutzung: /newspm <Spieler> <Nachricht>");
                return;
            }

            StringBuilder sendMessage = new StringBuilder();
            for(int i = 2; i < args.length; i++) {
                sendMessage.append(args[i]).append(" ");
            }

            newsAddon.getSocketConnection().s("pm §TOKEN§ " + args[1] + " " + sendMessage);

        } catch (NullPointerException e) {
            pUtils.displayNormal("§c§l[N-CHAT] §a§oPeppi §7»§f§o Du bist nicht mit dem Server verbunden!");
            newsAddon.getSocketConnection().connectSocket();
        }
    }

}
