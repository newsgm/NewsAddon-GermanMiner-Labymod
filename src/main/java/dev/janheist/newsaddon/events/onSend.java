package dev.janheist.newsaddon.events;

import dev.janheist.newsaddon.features.Auktionen;
import dev.janheist.newsaddon.features.NewsAddonCommand;
import dev.janheist.newsaddon.features.Show;
import dev.janheist.newsaddon.main.NewsAddon;
import net.labymod.api.events.MessageSendEvent;

import java.util.Arrays;

public class onSend implements MessageSendEvent {

    NewsAddon newsAddon;
    Auktionen auctions = new Auktionen();
    Show sendInChat = new Show();
    NewsAddonCommand addonCommand = new NewsAddonCommand();

    public onSend(NewsAddon newsAddon) {
        this.newsAddon = newsAddon;
    }

    @Override
    public boolean onSend(String s) {
        s = s.toLowerCase();
        String[] args = s.split(" ");
        if(args.length > 1)
            args[0] = args[0].toLowerCase();

        if(s.startsWith("/auction get-ticket") || s.startsWith("/auktion get-ticket") ||
            s.startsWith("/auction help") || s.startsWith("/auktion help")) {
            return false;
        } else if(args.length == 2 && (args[0].startsWith("/auction") || args[0].startsWith("/auktion"))) {
            auctions.startAuction(Arrays.copyOfRange(args, 1, args.length));
            return true;
        } else if(s.startsWith("/newsaddon show ")) {
            sendInChat.init(s.substring(16));
            return true;
        } else if(args.length == 3 && s.startsWith("/newsaddon auktion ") || s.startsWith("/newsaddon auction ")) {
            auctions.startAuction(Arrays.copyOfRange(args, 2, args.length));
        } else if(s.startsWith("/newsaddon")) {
            if(s.equals("/newsaddon"))
                s = s.concat(" help");
            addonCommand.init(s);
            return true;
        }

        return false;
    }
}
