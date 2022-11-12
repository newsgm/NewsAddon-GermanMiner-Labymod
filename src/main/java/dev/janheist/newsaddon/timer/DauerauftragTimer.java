package dev.janheist.newsaddon.timer;

import dev.janheist.newsaddon.features.PlayerUtilities;
import dev.janheist.newsaddon.main.NewsAddon;
import net.labymod.core.LabyModCore;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.util.TimerTask;

public class DauerauftragTimer extends TimerTask {

    @Override
    public void run() {
        try {
            if (
                !NewsAddon.getInstance().isazubi &&
                (NewsAddon.getInstance().DEBUGMODE || LabyMod.getInstance().getCurrentServerData().getIp().toLowerCase().contains("germanminer"))
            ) {
                PlayerUtilities pUtils = new PlayerUtilities();
                pUtils.displayPrefix("§a§lEventuell muss ein Dauerauftrag geschaltet werden!");

                if (!NewsAddon.getInstance().getConfig().has("soundDA") || NewsAddon.getInstance().getConfig().get("soundDA").getAsBoolean()) {
                    if (NewsAddon.getInstance().soundDAausw.equalsIgnoreCase("sirene_2")) {
                        Minecraft.getMinecraft().player.playSound(new SoundEvent(new ResourceLocation("sirene_2")), 0.25F, 1.0F);
                    } else {
                        LabyModCore.getMinecraft().playSound(new ResourceLocation(NewsAddon.getInstance().soundDAausw), 1.0F);
                    }
                }
            }
        } catch (NullPointerException ex) {
            System.out.println("[NEWS-DEBUG] NullPointerException at DauerauftragTimer");
        }
    }
}
