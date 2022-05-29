package dev.janheist.newsaddon.features;

import dev.janheist.newsaddon.main.NewsAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

public class Show {

    NewsAddon newsAddon;
    public Show(NewsAddon newsAddon) {
        this.newsAddon = newsAddon;
    }
    PlayerUtilities pUtils = new PlayerUtilities();

    public void init(String message) {
        String[] args = message.split(" ");
        if(message.toLowerCase().startsWith("greet")) {
            args[args.length-1] = args[args.length-1].replaceAll("(§.)|(&.)", "");
            pUtils.sendAsPlayer("Hallo " + args[args.length-1] + ". Willkommen in der News-Zentrale. Wie kann ich weiterhelfen?");

            TextComponentString next = new TextComponentString(pUtils.PREFIX + "§aScanne nun " + args[args.length-1] + ". Klicke hier, um den Scanner zu beenden.");
            Style style = new Style();
            style.setColor(TextFormatting.GREEN);
            style.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/newsaddon scan stop"));
            next.setStyle(style);
            Minecraft.getMinecraft().player.sendMessage(next);

            args[args.length-1] = args[args.length-1].toLowerCase();
            newsAddon.scan_name = args[args.length-1];
            newsAddon.scanner = true;
            newsAddon.last_scanned_name = args[args.length-1];

        } else if(message.toLowerCase().startsWith("types")) {
            pUtils.sendAsPlayer("Welche Art von Werbung möchtest du schalten? Chatwerbung (350€), Actionbarwerbung (350€), Appwerbung (1120€) oder Zeitungswerbung?");
        } else if(message.toLowerCase().startsWith("chat")) {
            pUtils.sendAsPlayer("Dann bekomme ich bitte den Text und 350€");
        } else {
            pUtils.displayPrefix("§e/newsaddon show [greet <Person>, types, chat, scan]");
        }
    }

}
