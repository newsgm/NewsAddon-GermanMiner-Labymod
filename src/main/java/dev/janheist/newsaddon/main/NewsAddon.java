package dev.janheist.newsaddon.main;

import dev.janheist.newsaddon.events.messageModifyChatEvent;
import dev.janheist.newsaddon.events.onReceiver;
import dev.janheist.newsaddon.events.onSend;
import dev.janheist.newsaddon.events.userMenuAction;
import dev.janheist.newsaddon.features.Dauerauftrag;
import dev.janheist.newsaddon.features.PlayerUtilities;
import dev.janheist.newsaddon.modules.UpdateChecker;
import dev.janheist.newsaddon.modules.WerbeCounter120;
import dev.janheist.newsaddon.modules.WerbeCounter90;
import dev.janheist.newsaddon.timer.WerbeTimer;
import net.labymod.api.EventManager;
import net.labymod.api.LabyModAddon;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.*;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.labymod.utils.ServerData;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class NewsAddon extends LabyModAddon {

    public final int VERSION = 2;

    public EventManager eventManager;
    public int seconds120 = 0;
    public int seconds90 = 0;
    public List<String> das = new ArrayList<>();

    public String scan_name;
    public boolean scanner;

    public Dauerauftrag dauerauftrag = new Dauerauftrag(this);
    private PlayerUtilities pUtils = new PlayerUtilities();

    public boolean sound90;
    public String sound90ausw;
    public boolean sound120;
    public String sound120ausw;
    public boolean soundDA;
    public String soundDAausw;
    public boolean playermenu;
    public String daurl;
    private boolean gotDA = false;

    public PlayerUtilities getpUtils() {
        return pUtils;
    }

    @Override
    public void onEnable() {
        System.out.println("|                                        |");
        System.out.println("| NEWS-ADDON by JAN HEIST aka. Mexykaner |");
        System.out.println("|                                        |");



        eventManager = this.getApi().getEventManager();
        eventManager.register(new onSend(this));
        eventManager.register(new onReceiver(this));
        eventManager.register(new messageModifyChatEvent(this));
        eventManager.register(new userMenuAction(this));
        eventManager.registerOnJoin(new Consumer<ServerData>() {
            @Override
            public void accept(ServerData serverData) {
                if (serverData.getIp().toLowerCase().contains("germanminer") || true) {
                    pUtils.resetCounter();
                    try {
                        UpdateChecker.initialize(VERSION);
                        if (!gotDA) {
                            gotDA = true;
                            dauerauftrag.init();
                        }
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        getApi().registerModule(new WerbeCounter90());
        getApi().registerModule(new WerbeCounter120());


        WerbeCounter90.allowed = 1;
        WerbeCounter120.allowed = 1;
        this.scanner = false;
        this.scan_name = "";


        Timer timer = new Timer();
        timer.schedule(new WerbeTimer(this), 0, 1000);

    }

    @Override
    public void loadConfig() {
        this.sound90 = !getConfig().has("sound90") || getConfig().get("sound90").getAsBoolean();
        this.sound90ausw = getConfig().has("sound90ausw") ? getConfig().get("sound90ausw").getAsString() : "block.note.harp";
        this.sound120 = !getConfig().has("sound120") || getConfig().get("sound120").getAsBoolean();
        this.sound120ausw = getConfig().has("sound120ausw") ? getConfig().get("sound120ausw").getAsString() : "block.note.pling";
        this.soundDA = !getConfig().has("soundDA") || getConfig().get("soundDA").getAsBoolean();
        this.soundDAausw = getConfig().has("soundDAausw") ? getConfig().get("soundDAausw").getAsString() : "none";
        this.playermenu = !getConfig().has("playermenu") || getConfig().get("playermenu").getAsBoolean();

        this.daurl = getConfig().has("daurl") ? getConfig().get("daurl").getAsString() : "none";
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        getSubSettings().add(new HeaderElement("§7§l[§a§lNEWS§7§l] §a§oAddon by"));
        getSubSettings().add(new HeaderElement("§b§l§oJan Heist aka. Mexykaner"));
        getSubSettings().add(new HeaderElement(""));
        getSubSettings().add(new HeaderElement("§a§lDaueraufträge"));
        getSubSettings().add(new StringElement("Daten-URL", this, new ControlElement.IconData(Material.MOB_SPAWNER), "daurl", this.daurl));
        getSubSettings().add(new HeaderElement(""));
        getSubSettings().add(new HeaderElement("§a§lSounds"));
        getSubSettings().add(new BooleanElement("Nach 90 Werbetimer", this, new ControlElement.IconData(Material.WATCH), "sound90", this.sound90));
        getSubSettings().add(new StringElement("Sound bei 90 Sekunden", this, new ControlElement.IconData(Material.MOB_SPAWNER), "sound90ausw", this.sound90ausw));
        getSubSettings().add(new BooleanElement("Nach 120 Werbetimer", this, new ControlElement.IconData(Material.WATCH), "sound120", this.sound120));
        getSubSettings().add(new StringElement("Sound bei 120 Sekunden", this, new ControlElement.IconData(Material.MOB_SPAWNER), "sound120ausw", this.sound120ausw));
        getSubSettings().add(new BooleanElement("Bei Dauerauftrag", this, new ControlElement.IconData(Material.PAPER), "soundDA", this.soundDA));
        getSubSettings().add( new StringElement( "Sound wenn ein DA ansteht", this, new ControlElement.IconData( Material.MOB_SPAWNER ), "soundDAausw", this.soundDAausw ) );
        getSubSettings().add(new HeaderElement(""));
        getSubSettings().add(new HeaderElement("§a§lSpielermenü"));
        getSubSettings().add(new BooleanElement("An = Aktiv", this, new ControlElement.IconData(Material.SKULL_ITEM), "playermenu", this.playermenu));
    }

    public void resetSeconds() {
        seconds120 = 0;
        seconds90 = 0;
    }

    public String getServer() {
        return LabyMod.getInstance().getCurrentServerData().getIp().toLowerCase();
    }

}
