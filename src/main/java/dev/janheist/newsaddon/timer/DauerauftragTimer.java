package dev.janheist.newsaddon.timer;

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

    public DauerauftragTimer(NewsAddon newsAddon) {
        this.newsAddon = newsAddon;
    }

    @Override
    public void run() {
        if (LabyMod.getInstance().getCurrentServerData().getIp().toLowerCase().contains("germanminer")) {
            PlayerUtilities pUtils = new PlayerUtilities();
            pUtils.displayPrefix("§a§lEventuell muss ein Dauerauftrag geschaltet werden!");

            if (!newsAddon.getConfig().has("soundDA") || newsAddon.getConfig().get("soundDA").getAsBoolean()) {
                EntityPlayerSP p = Minecraft.getMinecraft().player;
                // if(newsAddon.soundDAausw.equalsIgnoreCase("none")){
                p.playSound(new SoundEvent(new ResourceLocation("sirene_2")), 0.25F, 1.0F);
            }
            //    else{
            //    LabyModCore.getMinecraft().playSound(new ResourceLocation(newsAddon.soundDAausw), 1.0F);
            //  }
            //}
        }
    }
}
