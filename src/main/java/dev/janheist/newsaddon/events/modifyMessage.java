package dev.janheist.newsaddon.events;

import dev.janheist.newsaddon.main.NewsAddon;
import net.labymod.api.events.MessageModifyChatEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;

import java.util.UUID;

public class modifyMessage implements MessageModifyChatEvent {
    private String lastMessage = UUID.randomUUID().toString();
    NewsAddon newsAddon;
    public modifyMessage(NewsAddon newsAddon) {
        this.newsAddon = newsAddon;
    }

    @Override
    public Object onModifyChatMessage(Object o) {
        try {
            ITextComponent cct = (ITextComponent) o;

            String unformattedText = cct.getUnformattedText();
            if(cct.getUnformattedText().equals(lastMessage) || cct.getUnformattedText().trim().isEmpty())
                return o;

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
}
