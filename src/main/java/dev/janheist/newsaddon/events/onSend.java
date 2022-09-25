package dev.janheist.newsaddon.events;

import dev.janheist.newsaddon.commands.newspmCommand;
import dev.janheist.newsaddon.features.Auktionen;
import dev.janheist.newsaddon.features.NewsAddonCommand;
import dev.janheist.newsaddon.features.Show;
import dev.janheist.newsaddon.commands.nfCommand;
import dev.janheist.newsaddon.main.NewsAddon;
import net.labymod.api.events.MessageSendEvent;

import java.util.Arrays;

public class onSend implements MessageSendEvent {

    NewsAddon newsAddon;
    Auktionen auctions = new Auktionen();
    Show sendInChat;
    NewsAddonCommand addonCommand;
    nfCommand nfcmd;
    newspmCommand newspm;

    public onSend(NewsAddon newsAddon) {
        this.newsAddon = newsAddon;
        this.addonCommand = new NewsAddonCommand(newsAddon);
        this.nfcmd = new nfCommand(newsAddon);
        this.sendInChat = new Show(newsAddon);
        this.newspm = new newspmCommand(newsAddon);
    }

    @Override
    public boolean onSend(String s) {
        String orig = s;
        s = s.toLowerCase();
        String[] args = s.split(" ");
        if (args.length > 1)
            args[0] = args[0].toLowerCase();

        if (newsAddon.isazubi == true) {
            if (s.startsWith("/auction get-ticket") || s.startsWith("/auktion get-ticket") ||
                    s.startsWith("/auction help") || s.startsWith("/auktion help")) {
                return true;
            }
            if (args.length >= 2 && (args[0].startsWith("/auction") || args[0].startsWith("/auktion"))) {
                auctions.startAuction(Arrays.copyOfRange(args, 1, args.length));
                return true;
            }
        } else if (newsAddon.isazubi == false) {
            if (s.startsWith("/newsaddon show ")) {
                sendInChat.init(orig.substring(16));
                return true;
            } else if (args.length >= 3 && s.startsWith("/newsaddon auktion ") || s.startsWith("/newsaddon auction ")) {
                auctions.startAuction(Arrays.copyOfRange(args, 2, args.length));
            } else if (s.startsWith("/newsaddon")) {
                if (s.split(" ").length == 1)
                    s = s.concat(" help");
                addonCommand.init(s);
                return true;
            }
        } else return false;


        if (s.startsWith("/nf") || s.startsWith("--")) {
            try {
                if (orig.startsWith("-- ")) {
                    orig = orig.substring(3);
                    orig = "/nf " + orig;
                } else if (orig.startsWith("--")) {
                    orig = orig.substring(2);
                    orig = "/nf " + orig;
                }
                if (s.equals("/nf"))
                    orig = "/nf veryUnusualStringThatAPlayerWouldSendToTheServer";
                if (orig.split(" ").length == 1)
                    orig = orig.concat("veryUnusualStringThatAPlayerWouldSendToTheServer");
                nfcmd.init(orig);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
            return true;
        } else if (s.startsWith("/newspmr")) {
            newspm.init(orig.replace("/newspmr ", ("/newspm " + NewsAddon.lastContact + " ")));
            return true;
        } else if (s.startsWith("/newspm")) {
            newspm.init(orig);
            return true;
        }

        return false;
    }
}
