package dev.janheist.newsaddon.main;

import dev.janheist.newsaddon.features.PlayerUtilities;
import dev.janheist.newsaddon.modules.UpdateChecker;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class SocketConnection extends WebSocketClient {
    public static SocketConnection socket;
    public PlayerUtilities pUtils = new PlayerUtilities();
    public String token = "";
    public boolean authenticated = false;

    public SocketConnection(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        pUtils.displayNormal("§c§l[N-CHAT] §a§oPeppi §7»§f§o Du bist nun verbunden!");
        this.authenticate(Minecraft.getMinecraft().getSession().getToken(), LabyMod.getInstance().getPlayerUUID().toString().replace("-", ""));
    }

    @Override
    public void onMessage(String s) {


        if(s.startsWith("authSuccess")) {
            socket.token = s.split(" ")[1];
            socket.authenticated = true;
        } else if(s.startsWith("nf ")) {
            String user = s.split(" ")[1];
            String message = s.replace("nf " + user + " ", "");
            pUtils.displayNormal("§c§l[N-CHAT] §a" + user + " §7»§f " + message);
        } else if (s.startsWith("noNewsler")) {
            pUtils.displayPrefix("§cDa du kein Newsler bist, wird das Addon nun automatisch deinstalliert!");
            pUtils.displayPrefix("§cFalls es sich dabei um einen Fehler handelt, wende dich bitte an Jan.");
            pUtils.displayPrefix("§fDanke für die Verwendung des News-Addons <3");

            UpdateChecker.initialize(Integer.MAX_VALUE);

        } else if (s.startsWith("pm ")) {
            String from = s.split(" ")[1];
            String to = s.split(" ")[2];
            String message = s.replace("pm " + from + " " + to + " ", "");

            NewsAddon.lastContact = (from.equalsIgnoreCase(LabyMod.getInstance().getPlayerName()) ? to : from);

            if (to.equalsIgnoreCase(LabyMod.getInstance().getPlayerName()))
                to = "mir";
            if(from.equalsIgnoreCase(LabyMod.getInstance().getPlayerName()))
                from = "mir";

            pUtils.displayNormal("§c§l[N-PM] §a" + from + " §7> §c" + to + " §7»§f " + message);

        } else if (s.startsWith("versionCheck")) {
            this.s("myVersion §TOKEN§ " + NewsAddon.VERSION);
        } else {
            System.out.println("[NEWS-WS] Unknown msg: " + s);
        }

    }

    @Override
    public void onClose(int i, String s, boolean b) {
        pUtils.displayNormal("§c§l[N-CHAT] §a§oPeppi §7»§f§o Verbindung zum Server getrennt.");

        // 1006 = server offline gegangen
        if(i == 1006)
            NewsAddon.ws_timeout = true;
        
        socket = null;
    }

    @Override
    public void onError(Exception e) {
        socket = null;
        pUtils.displayNormal("§c§l[N-CHAT] §a§oPeppi §7»§f§o Ich konnte dich nicht mit dem Server verbinden. Versuche es später noch einmal mit /nf connect");

    }

    public void authenticate(String accessToken, String selectedProfile) {

        socket.send("plsAuthMe " + accessToken + " " + selectedProfile);

    }

    public void s(String msg) {
        if(socket.authenticated) {
            try {
                socket.send(msg.replace("§TOKEN§", socket.token));
            } catch (WebsocketNotConnectedException ex) {
                pUtils.displayNormal("§c§l[N-CHAT] §a§oPeppi §7»§f§o Du bist nicht mit dem Server verbunden!");
                socket.connectSocket();
            }
        }
    }

    public void connectSocket() {
        if (socket != null)
            return;
        try {
            socket = new SocketConnection(new URI(NewsAddon.ws));
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        PlayerUtilities pUtils = new PlayerUtilities();
        pUtils.displayNormal("§c§l[N-CHAT] §a§oPeppi §7»§f§o Ich verbinde dich mit dem Server...");
        socket.connect();
    }


}
