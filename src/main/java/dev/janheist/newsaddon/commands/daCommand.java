package dev.janheist.newsaddon.commands;

import dev.janheist.newsaddon.main.NewsAddon;

public class daCommand {

    public void init(String message) {
        try {
            String[] args = message.split(" ");
            if (args[0].equalsIgnoreCase("reload")) {
                NewsAddon.getInstance().getSocketConnection().s("da giveMeAllThousandTimedAdvertisements");
            } else if (args[0].equalsIgnoreCase("add")) {
                // add all 15:00
                if (args.length < 3 || !args[2].contains(":")) {
                    NewsAddon.getPlayerUtilities().displayPrefix("§cVerwendung: da add <all, mo...so> <hh:mm>");
                    return;
                }
                NewsAddon.getInstance().getSocketConnection().s("da add " + args[1] + " " + args[2]);
            } else if (args[0].equals("remove")) {
                // da remove all 15:00
                if (args.length < 3 || !args[2].contains(":")) {
                    NewsAddon.getPlayerUtilities().displayPrefix("§cVerwendung: da remove <all, mo...so> <hh:mm>");
                    return;
                }
                NewsAddon.getInstance().getSocketConnection().s("da remove " + args[1] + " " + args[2]);
            } else if (args[0].equals("listall")) {
                NewsAddon.getInstance().getSocketConnection().s("da giveMeANiceListofAllFuckingThousandTimedAdvertisements");
            }
        } catch (NullPointerException e) {
            NewsAddon.getPlayerUtilities().displayNormal("§c§l[N-CHAT] §a§oPeppi §7»§f§o Du bist nicht mit dem Server verbunden!");
            NewsAddon.getInstance().getSocketConnection().connectSocket();
        }
    }

}
