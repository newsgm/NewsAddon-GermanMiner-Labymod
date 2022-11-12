package dev.janheist.newsaddon.utls;

import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

public class ClickableTexts {


    public static void send() {

        TextComponentString textComponent = new TextComponentString("§7[§a§lNEWS§r§7] §r§7§oClick me!");

        textComponent.setStyle(
            textComponent.getStyle()
            .setClickEvent(new CustomClickEvent(CustomClickEvent.CustomAction.NEWSFUNK, CustomClickEvent.createMessage("nf §TOKEN§ das ist ein Test")))
        );

        textComponent.getStyle().setColor(TextFormatting.GRAY);
        textComponent.getStyle().setItalic(true);

        if (LabyMod.getInstance().isInGame()) {
            Minecraft.getMinecraft().player.sendMessage(textComponent);
        }

    }

}
