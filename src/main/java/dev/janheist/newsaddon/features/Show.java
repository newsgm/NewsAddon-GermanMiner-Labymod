package dev.janheist.newsaddon.features;

public class Show {

    PlayerUtilities pUtils = new PlayerUtilities();

    public void init(String message) {
        String[] args = message.split(" ");
        if(message.toLowerCase().startsWith("greet")) {
            args[args.length-1] = args[args.length-1].replaceAll("(§.)|(&.)", "");
            pUtils.sendAsPlayer("Hallo " + args[args.length-1] + ". Willkommen in der News-Zentrale. Wie kann ich weiterhelfen?");
        } else if(message.toLowerCase().startsWith("types")) {
            pUtils.sendAsPlayer("Welche Art von Werbung möchtest du schalten? Chatwerbung (350€), Actionbarwerbung (350€), Appwerbung (1120€) oder Zeitungswerbung?");
        } else if(message.toLowerCase().startsWith("chat")) {
            pUtils.sendAsPlayer("Dann bekomme ich bitte den Text und 350€");
        } else {
            pUtils.displayPrefix("§e/newsaddon show [greet <Person>, types, chat, scan]");
        }
    }

}
