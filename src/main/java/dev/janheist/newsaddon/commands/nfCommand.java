package dev.janheist.newsaddon.commands;

import dev.janheist.newsaddon.features.PlayerUtilities;
import dev.janheist.newsaddon.main.NewsAddon;

public class nfCommand {

    PlayerUtilities pUtils = new PlayerUtilities();
    NewsAddon newsAddon;
    public nfCommand(NewsAddon newsAddon) {
        this.newsAddon = newsAddon;
    }

    public void init(String message) {
        try {
            String[] args = message.split(" ");
            if (args[1].equalsIgnoreCase("connect")) {
                newsAddon.getSocketConnection().connectSocket();
            } else if (args[1].equalsIgnoreCase("close")) {
                newsAddon.getSocketConnection().s("plsKillMe");
            } else if (args[1].equals("veryUnusualStringThatAPlayerWouldSendToTheServer")) {
                pUtils.displayPrefix("");
                pUtils.displayPrefix("§aAktuelle Version: " + newsAddon.VERSION);
                pUtils.displayPrefix("§e/nf §e[connect, connected, close, <msg>]");
                pUtils.displayPrefix("");
            } else if (args[1].equals("connected")) {
                newsAddon.getSocketConnection().s("plsGetConnected §TOKEN§");
            } else {
                message = message.substring(4);
                newsAddon.getSocketConnection().s("nf §TOKEN§ " + message);
            }
        } catch (NullPointerException e) {
            pUtils.displayNormal("§c§l[N-CHAT] §a§oPeppi §7»§f§o Du bist nicht mit dem Server verbunden!");
            newsAddon.getSocketConnection().connectSocket();
        }
    }

}
