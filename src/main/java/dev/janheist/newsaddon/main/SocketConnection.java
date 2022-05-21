package dev.janheist.newsaddon.main;

import dev.janheist.newsaddon.features.PlayerUtilities;
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
        pUtils.displayNormal("§c§l[N-FUNK] §a§oPeppi §7»§f§o Du bist nun verbunden!");
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
            pUtils.displayNormal("§c§l[N-FUNK] §a" + user + " §7»§f " + message);
        } else {
            System.out.println("[NEWS-WS] Unknown msg: " + s);
        }

    }

    @Override
    public void onClose(int i, String s, boolean b) {
        pUtils.displayNormal("§c§l[N-FUNK] §a§oPeppi §7»§f§o Verbindung zum Server getrennt.");

        socket = null;
    }

    @Override
    public void onError(Exception e) {
        socket = null;
        pUtils.displayNormal("§c§l[N-FUNK] §a§oPeppi §7»§f§o Ich konnte dich nicht mit dem Server verbinden. Versuche es später noch einmal mit /newsaddon connect");

    }

    public void authenticate(String accessToken, String selectedProfile) {

        socket.send("plsAuthMe " + accessToken + " " + selectedProfile);

    }

    public void s(String msg) {
        if(socket.authenticated) {
            try {
                socket.send(msg.replace("§TOKEN§", socket.token));
            } catch (WebsocketNotConnectedException ex) {
                pUtils.displayNormal("§c§l[N-FUNK] §a§oPeppi §7»§f§o Du bist nicht mit dem Server verbunden!");
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
        pUtils.displayNormal("§c§l[N-FUNK] §a§oPeppi §7»§f§o Ich verbinde dich mit dem Server...");
        socket.connect();
    }


}
