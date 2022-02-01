package dev.janheist.newsaddon.main;

import dev.janheist.newsaddon.events.onReceive;
import dev.janheist.newsaddon.events.onSend;
import dev.janheist.newsaddon.events.userMenuAction;
import dev.janheist.newsaddon.features.PlayerUtilities;
import dev.janheist.newsaddon.modules.WerbeCounter120;
import dev.janheist.newsaddon.modules.WerbeCounter90;
import dev.janheist.newsaddon.timer.WerbeTimer;
import net.labymod.api.EventManager;
import net.labymod.api.LabyModAddon;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.ServerData;

import java.util.List;
import java.util.Timer;

public class NewsAddon extends LabyModAddon {

    public EventManager eventManager;
    public int seconds120 = 0;
    public int seconds90 = 0;
    public static boolean playsound = true;
    PlayerUtilities pUtils = new PlayerUtilities();

    @Override
    public void onEnable() {
        System.out.println("|                                        |");
        System.out.println("| NEWS-ADDON by JAN HEIST aka. Mexykaner |");
        System.out.println("|                                        |");

        eventManager = this.getApi().getEventManager();
        eventManager.register(new onSend(this));
        eventManager.register(new onReceive(this));
        eventManager.register(new userMenuAction());
        eventManager.registerOnJoin(new Consumer<ServerData>() {
            @Override
            public void accept(ServerData serverData) {
                pUtils.resetCounter();
            }
        });

        getApi().registerModule(new WerbeCounter90());
        getApi().registerModule(new WerbeCounter120());


        WerbeCounter90.allowed = 1;
        WerbeCounter120.allowed = 1;

        Timer timer = new Timer();
        timer.schedule(new WerbeTimer(this), 0, 1000);

    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

    }

    public void resetSeconds() {
        seconds120 = 0;
        seconds90 = 0;
    }
    public String getServer() {
        return LabyMod.getInstance().getCurrentServerData().getIp().toLowerCase();
    }
}
