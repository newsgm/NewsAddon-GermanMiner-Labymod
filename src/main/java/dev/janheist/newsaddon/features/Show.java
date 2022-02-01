package dev.janheist.newsaddon.features;

import net.minecraft.client.Minecraft;

public class Show {

    public void init(String message) {
        String[] args = message.split(" ");
        if(message.startsWith("greet")) {
            sendAsPlayer("Hallo " + args[args.length-1] + ". Willkommen in der News-Zentrale. Wie kann ich weiterhelfen?");
        } else if(message.startsWith("types")) {
            sendAsPlayer("Welche Art von Werbung möchtest du schalten? Chatwerbung (350€), Actionbarwerbung (350€), Appwerbung (1120€) oder Zeitungswerbung?");
        } else if(message.startsWith("chat")) {
            sendAsPlayer("Dann bekomme ich bitte den Text und 350€");
        }
    }

    private void sendAsPlayer(String message) {
        Minecraft.getMinecraft().player.sendChatMessage(message);
    }

}
