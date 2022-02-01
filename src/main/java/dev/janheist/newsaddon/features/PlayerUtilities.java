package dev.janheist.newsaddon.features;

import dev.janheist.newsaddon.modules.WerbeCounter120;
import dev.janheist.newsaddon.modules.WerbeCounter90;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;

public class PlayerUtilities {

    public String PREFIX = "§7[§a§lNEWS§r§7] §r";

    public void displayNormal(String message) {
        LabyMod.getInstance().displayMessageInChat(message);
    }
    public void displayPrefix(String message) {
        LabyMod.getInstance().displayMessageInChat(PREFIX.concat(message));
    }

    public void sendAsPlayer(String message) {
        Minecraft.getMinecraft().player.sendChatMessage(message);
    }

    public void resetCounter() {
        WerbeCounter120.allowed = 0;
        WerbeCounter90.allowed = 0;
    }

}
