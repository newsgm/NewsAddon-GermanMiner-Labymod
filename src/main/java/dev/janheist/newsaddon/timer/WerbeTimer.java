package dev.janheist.newsaddon.timer;

import dev.janheist.newsaddon.main.NewsAddon;
import dev.janheist.newsaddon.modules.WerbeCounter120;
import dev.janheist.newsaddon.modules.WerbeCounter90;
import net.labymod.core.LabyModCore;
import net.minecraft.util.ResourceLocation;

import java.util.TimerTask;

public class WerbeTimer extends TimerTask {
    NewsAddon newsAddon;
    public WerbeTimer(NewsAddon newsAddon) {
        this.newsAddon = newsAddon;
    }

    @Override
    public void run() {
        if(WerbeCounter90.allowed == 0) {
            newsAddon.seconds90++;
            WerbeCounter90.end = String.valueOf(90 - newsAddon.seconds90);
        }
        if(WerbeCounter120.allowed == 0) {
            newsAddon.seconds120++;
            WerbeCounter120.end = String.valueOf(120 - newsAddon.seconds120);
        }

        if(newsAddon.seconds90 == 90) {
            WerbeCounter90.allowed = 1;
            newsAddon.seconds90 = 0;
            if(newsAddon.sound90 && newsAddon.getServer().contains("germanminer"))
                LabyModCore.getMinecraft().playSound(new ResourceLocation(newsAddon.sound90ausw), 1.0F);
        }
        if(newsAddon.seconds120 == 120) {
            WerbeCounter120.allowed = 1;
            newsAddon.seconds120 = 0;
            if(newsAddon.sound120 && newsAddon.getServer().contains("germanminer"))
                LabyModCore.getMinecraft().playSound(new ResourceLocation(newsAddon.sound120ausw), 1.0F);
        }
    }
}
