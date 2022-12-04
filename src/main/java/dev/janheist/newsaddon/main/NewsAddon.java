package dev.janheist.newsaddon.main;

import dev.janheist.newsaddon.events.modifyMessage;
import dev.janheist.newsaddon.events.onSend;
import dev.janheist.newsaddon.events.userMenuAction;
import dev.janheist.newsaddon.features.PlayerUtilities;
import dev.janheist.newsaddon.modules.WerbeCounter120;
import dev.janheist.newsaddon.modules.WerbeCounter90;
import dev.janheist.newsaddon.timer.WerbeTimer;
import dev.janheist.newsaddon.utls.ClickableTexts.TickEvent;
import net.labymod.api.EventManager;
import net.labymod.api.LabyModAddon;
import net.labymod.main.LabyMod;
import net.labymod.settings.elements.*;
import net.labymod.utils.Material;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class NewsAddon extends LabyModAddon {

    public static final int VERSION = 15;
    public static String ws = "ws://janheist.dev:8181";

    // Vor Release auf false setzen!
    public final boolean DEBUGMODE = false;

    public EventManager eventManager;
    public int seconds120 = 0;
    public int seconds90 = 0;
    public List<String> das = new ArrayList<>();

    public String scan_name;
    public boolean scanner;
    public static boolean ws_timeout = false;

    public boolean sound90;
    public String sound90ausw;
    public boolean sound120;
    public String sound120ausw;
    public boolean soundDA;
    public String soundDAausw;
    public boolean playermenu;
    public boolean autoconnectgm;
    public boolean isazubi;
    public String last_scanned_name;
    public SocketConnection socketConnection;
    public SocketConnection getSocketConnection() {
        return socketConnection;
    }
    public static String lastContact = "none";
    private static NewsAddon newsAddon;
    private Random rand;
    private PlayerUtilities playerUtilities;

    public static NewsAddon getInstance() {
        return newsAddon;
    }

    public static Random getRandom() {
        return newsAddon.rand;
    }

    public static PlayerUtilities getPlayerUtilities() {
        return newsAddon.playerUtilities;
    }

    @Override
    public void onEnable() {
        newsAddon = this;
        newsAddon.rand = new Random();
        newsAddon.playerUtilities = new PlayerUtilities();

        try {
          this.socketConnection = new SocketConnection(new URI(NewsAddon.ws));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        System.out.println("|                                        |");
        System.out.println("| NEWS-ADDON by JAN HEIST aka. Mexykaner |");
        System.out.println("|            Aktuelle Version: " + VERSION + " |");
        System.out.println("|                                        |");
        System.out.println("[NEWS-DEBUG] " + System.getenv("APPDATA") + File.separator + ".minecraft" + File.separator + "LabyMod" + File.separator + "addons-1.12");
        System.out.println("[NEWS-DEBUG] " + Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + File.separator + "LabyMod" + File.separator + "addons-1.12");

        eventManager = this.getApi().getEventManager();
        eventManager.register(new onSend(this));
        eventManager.register(new modifyMessage(this));
        eventManager.register(new userMenuAction(this));
        eventManager.registerOnJoin(serverData -> {
            if (serverData.getIp().toLowerCase().contains("germanminer") || DEBUGMODE) {
                if(autoconnectgm) {
                    getSocketConnection().connectSocket();
                }
                newsAddon.playerUtilities.resetCounter();
            }
        });

        getApi().registerModule(new WerbeCounter90());
        getApi().registerModule(new WerbeCounter120());


        WerbeCounter90.allowed = 1;
        WerbeCounter120.allowed = 1;
        this.scanner = false;
        this.scan_name = "";

        getApi().registerForgeListener(new TickEvent());

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
        this.soundDAausw = getConfig().has("soundDAausw") ? getConfig().get("soundDAausw").getAsString() : "sirene_2";
        this.playermenu = !getConfig().has("playermenu") || getConfig().get("playermenu").getAsBoolean();
        this.autoconnectgm = !getConfig().has("autoconnectgm") || getConfig().get("autoconnectgm").getAsBoolean();
        this.isazubi = getConfig().has("isazubi") && getConfig().get("isazubi").getAsBoolean();
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        getSubSettings().add(new HeaderElement("§7§l[§a§lNEWS§7§l] §a§oAddon by"));
        getSubSettings().add(new HeaderElement("§b§l§oJan Heist aka. Mexykaner"));
        getSubSettings().add(new HeaderElement(""));
        getSubSettings().add(new HeaderElement("§a§lSounds"));
        getSubSettings().add(new BooleanElement("Nach 90 Werbetimer", this, new ControlElement.IconData(Material.WATCH), "sound90", this.sound90));
        getSubSettings().add(new StringElement("Sound bei 90 Sekunden", this, new ControlElement.IconData(Material.MOB_SPAWNER), "sound90ausw", this.sound90ausw));
        getSubSettings().add(new BooleanElement("Nach 120 Werbetimer", this, new ControlElement.IconData(Material.WATCH), "sound120", this.sound120));
        getSubSettings().add(new StringElement("Sound bei 120 Sekunden", this, new ControlElement.IconData(Material.MOB_SPAWNER), "sound120ausw", this.sound120ausw));
        getSubSettings().add(new BooleanElement("Bei Dauerauftrag", this, new ControlElement.IconData(Material.PAPER), "soundDA", this.soundDA));
        getSubSettings().add(new StringElement( "Sound wenn ein DA ansteht", this, new ControlElement.IconData( Material.MOB_SPAWNER ), "soundDAausw", this.soundDAausw ) );
        getSubSettings().add(new HeaderElement(""));
        getSubSettings().add(new HeaderElement("§a§lSpielermenü"));
        getSubSettings().add(new BooleanElement("An = Aktiv", this, new ControlElement.IconData(Material.SKULL_ITEM), "playermenu", this.playermenu));
        getSubSettings().add(new HeaderElement(""));
        getSubSettings().add(new HeaderElement("§a§lRangauswahl"));
        getSubSettings().add(new BooleanElement("Azubi", this, new ControlElement.IconData(Material.NAME_TAG), "isazubi", this.isazubi));
        getSubSettings().add(new HeaderElement(""));
        getSubSettings().add(new HeaderElement("§a§lWebSocket"));
        getSubSettings().add(new BooleanElement("An = AutoConnect GM", this, new ControlElement.IconData(Material.REDSTONE_LAMP_ON), "autoconnectgm", this.autoconnectgm));
    }

    public void resetSeconds() {
        seconds120 = 0;
        seconds90 = 0;
    }

    public String getServer() {
        return LabyMod.getInstance().getCurrentServerData().getIp().toLowerCase();
    }
}
