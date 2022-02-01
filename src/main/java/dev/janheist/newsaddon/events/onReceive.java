package dev.janheist.newsaddon.events;

import dev.janheist.newsaddon.features.PlayerUtilities;
import dev.janheist.newsaddon.main.NewsAddon;
import dev.janheist.newsaddon.modules.WerbeCounter120;
import dev.janheist.newsaddon.modules.WerbeCounter90;
import net.labymod.api.events.MessageReceiveEvent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class onReceive implements MessageReceiveEvent {
    PlayerUtilities pUtils = new PlayerUtilities();
    NewsAddon newsAddon;
    public onReceive(NewsAddon newsAddon) {
        this.newsAddon = newsAddon;
    }

    @Override
    public boolean onReceive(String s, String msg) {
        if(msg.contains("------------- ***** WERBUNG ***** -------------")) {
            newsAddon.resetSeconds();
            WerbeCounter90.allowed = 0;
            WerbeCounter120.allowed = 0;
        } else if(msg.contains("Werbetext: ")) {
            String werbetext = msg.replaceAll(".*Werbetext: ", "");
            String werbetextand = werbetext.replace("&\uF8FF", "&");
            werbetextand = werbetextand.replace("§a", "");
            werbetextand = werbetextand.replace("§r", "");
            werbetextand = werbetextand.replace("§7", "");
            werbetextand = werbetextand.replaceAll("reperatur", "Reparatur");
            werbetextand = werbetextand.replaceAll("Reperatur", "Reparatur");
            werbetextand = werbetextand.replaceAll("Reperaturbücher", "Reparaturbücher");
            werbetextand = werbetextand.replaceAll("Reperaturbücher", "Reparaturbücher");
            StringSelection selection = new StringSelection(werbetextand);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
            pUtils.displayNormal("§e§oInformation §7»§r §cDer Werbetext wurde in die Zwischenablage kopiert.");
        }
        return false;
    }
}
