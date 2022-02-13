package dev.janheist.newsaddon.events;

import dev.janheist.newsaddon.features.PlayerUtilities;
import dev.janheist.newsaddon.main.NewsAddon;
import dev.janheist.newsaddon.modules.WerbeCounter120;
import dev.janheist.newsaddon.modules.WerbeCounter90;
import net.labymod.api.events.MessageModifyChatEvent;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.UUID;

public class modifyMessage implements MessageModifyChatEvent {
    private String lastMessage = UUID.randomUUID().toString();
    private final PlayerUtilities pUtils = new PlayerUtilities();
    NewsAddon newsAddon;

    public modifyMessage(NewsAddon newsAddon) {
        this.newsAddon = newsAddon;
    }

    @Override
    public Object onModifyChatMessage(Object o) {
        try {
            ITextComponent cct = (ITextComponent) o;
            System.out.println("Format: " + cct.getFormattedText());
            System.out.println("sFormat: " + cct.getUnformattedComponentText());



            String unformattedText = cct.getUnformattedText();
            String   formattedText = cct.getFormattedText();

            if(cct.getUnformattedText().equals(lastMessage) || cct.getUnformattedText().trim().isEmpty())
                return o;

            if (unformattedText.contains("------------- ***** WERBUNG ***** -------------")) {
                newsAddon.resetSeconds();
                WerbeCounter90.allowed = 0;
                WerbeCounter120.allowed = 0;
            } else if (formattedText.contains("Werbetext: ") &&
                    !(formattedText.contains("Ⓡ") || formattedText.contains("Flüstern") || formattedText.contains("FUNK] (") || formattedText.contains("Ⓢ"))) {
                String werbetext = unformattedText.replaceAll(".*Werbetext: ", "");
                werbetext = werbetext
                        .replace("&\uF8FF", "&")
                        .replace("§a", "")
                        .replace("§r", "")
                        .replace("§7", "")
                        .replaceAll("reperatur", "Reparatur")
                        .replaceAll("Reperatur", "Reparatur")
                        .replaceAll("Reperaturbücher", "Reparaturbücher")
                        .replaceAll("Reperaturbücher", "Reparaturbücher");
                StringSelection selection = new StringSelection(werbetext);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, selection);
                pUtils.displayPrefix("§aDer Werbetext wurde in die Zwischenablage kopiert.");
            }



            if(newsAddon.scanner && unformattedText.toLowerCase().contains(newsAddon.scan_name)) {
                ITextComponent base = new TextComponentString("§7[§aC§7] §r");
                String[] segments = unformattedText.split(" » ");
                base.setStyle(new Style().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, segments[segments.length-1])));
                base.appendSibling(cct);

                lastMessage = base.getUnformattedText();
                return base.createCopy();
            } else return o;

        } catch (Exception e) {
            e.printStackTrace();
            return o;
        }
    }

    private boolean validate(String msg) {
        if(msg.contains("Werbetext: ") && (
            msg.contains("Polizist") ||
            msg.contains("Zivilist") ||
            msg.contains("Journalist") ||
            msg.contains("Notarzt") ||
            msg.contains("Bandito") ||
            msg.contains("Sureno") ||
            msg.contains("Supporter") ||
            msg.contains("Moderator") ||
            msg.contains("Admin") ||
            msg.contains("Serverleit") ||
            msg.contains("Entwickler") ||
            msg.contains("Bauteam")))
        {
            return false;
        } else {
            return true;
        }
    }

}
