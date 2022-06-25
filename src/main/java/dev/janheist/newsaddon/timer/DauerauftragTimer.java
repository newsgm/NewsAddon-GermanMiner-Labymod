package dev.janheist.newsaddon.timer;

import dev.janheist.newsaddon.features.Dauerauftrag;
import dev.janheist.newsaddon.features.PlayerUtilities;
import dev.janheist.newsaddon.main.NewsAddon;
import net.labymod.core.LabyModCore;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.util.TimerTask;

public class DauerauftragTimer extends TimerTask {
    NewsAddon newsAddon;
    Dauerauftrag dauerauftrag;
    private final int version;

    public DauerauftragTimer(NewsAddon newsAddon, Dauerauftrag dauerauftrag, int version) {
        this.newsAddon = newsAddon;
        this.dauerauftrag = dauerauftrag;
        this.version = version;
    }

    @Override
    public void run() {
        try {
            if (version == dauerauftrag.current_version &&
                    (newsAddon.DEBUGMODE || LabyMod.getInstance().getCurrentServerData().getIp().toLowerCase().contains("germanminer"))) {
                PlayerUtilities pUtils = new PlayerUtilities();
                pUtils.displayPrefix("§a§lEventuell muss ein Dauerauftrag geschaltet werden!");

                if (!newsAddon.getConfig().has("soundDA") || newsAddon.getConfig().get("soundDA").getAsBoolean()) {
                    EntityPlayerSP p = Minecraft.getMinecraft().player;
                    if (newsAddon.soundDAausw.equalsIgnoreCase("sirene_2")) {
                        p.playSound(new SoundEvent(new ResourceLocation("sirene_2")), 0.25F, 1.0F);
                    } else {
                        LabyModCore.getMinecraft().playSound(new ResourceLocation(newsAddon.soundDAausw), 1.0F);
                    }

                }
            }
        } catch (NullPointerException ex) {
            System.out.println("[NEWS-DEBUG] NullPointerException at DauerauftragTimer");
        }
    }
}
