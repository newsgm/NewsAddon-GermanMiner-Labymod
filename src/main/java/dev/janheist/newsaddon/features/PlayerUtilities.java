package dev.janheist.newsaddon.features;

import dev.janheist.newsaddon.modules.WerbeCounter120;
import dev.janheist.newsaddon.modules.WerbeCounter90;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;


public class PlayerUtilities {
    private static SSLContext sslContext;

    public String PREFIX = "§7[§a§lNEWS§r§7] §r";

    public void displayNormal(String message) {
        LabyMod.getInstance().displayMessageInChat(message);
    }
    public void displayPrefix(String message) {
        LabyMod.getInstance().displayMessageInChat(PREFIX.concat(message));
    }

    public void clickableCommand(String message, String command, boolean prefix) {
        TextComponentString next;
        if(prefix)
            next = new TextComponentString(this.PREFIX + message);
        else
            next = new TextComponentString(message);

        Style style = new Style();
        style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        next.setStyle(style);
        Minecraft.getMinecraft().player.sendMessage(next);
    }

    public void sendAsPlayer(String message) {
        Minecraft.getMinecraft().player.sendChatMessage(message);
    }

    public void resetCounter() {
        WerbeCounter120.allowed = 0;
        WerbeCounter90.allowed = 0;
    }

    public static SSLSocketFactory getSocketFactory() {
        if (sslContext == null) {
            TrustManager[] trustAllCerts = { new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            } };
            try {
                sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new SecureRandom());
            } catch (KeyManagementException |java.security.NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return sslContext.getSocketFactory();
    }

}
