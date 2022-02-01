package dev.janheist.newsaddon.timer;

import dev.janheist.newsaddon.features.PlayerUtilities;

import java.util.TimerTask;

public class DauerauftragTimer extends TimerTask {
    @Override
    public void run() {
        PlayerUtilities pUtils = new PlayerUtilities();
        pUtils.displayPrefix("§a§lEventuell muss ein Dauerauftrag geschaltet werden!");
    }
}
