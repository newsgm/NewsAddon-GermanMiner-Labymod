package dev.janheist.newsaddon.features;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Auktionen {
    public PlayerUtilities pUtils = new PlayerUtilities();

    private final List<String> itemTexte = new ArrayList<>();
    private final List<String> gsTexte = new ArrayList<>();
    private final List<String> bizTexte = new ArrayList<>();
    private final List<String> autoTexte = new ArrayList<>();
    private final List<String> codeTexte = new ArrayList<>();
    private int current_counter;

    public Auktionen() {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(7);
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN);
        String latestEnd = formatDate.format(localDateTime);
        current_counter = 0;

        itemTexte.add("Bist du dir bewusst, dass die Auktion anfangs 500€ kostet? Weiterhin fallen 3% vom Gewinn Auktionsgebühr an, sofern das Item versteigert wird.");
        itemTexte.add("An welchem Tag und zu welcher Uhrzeit soll die Auktion enden? (Maximal bis zum " + latestEnd + ")");
        itemTexte.add("Wie soll der Startpreis der Auktion sein?");
        itemTexte.add("Welche Summe soll für den Sofortkauf bestimmt werden?");
        itemTexte.add("In welchen Schritten sollen die Spieler mindestens bieten?");
        itemTexte.add("Als letztes bräuchte ich bitte einmal deine Kontonummer und das Item mit den 500€ Auktionsgebühr über das Handelsmenü.");

        gsTexte.add("Bist du dir bewusst, dass die Auktion anfangs 500€ kostet? Weiterhin fallen 3% vom Gewinn Auktionsgebühr an, sofern die Region versteigert wird.");
        gsTexte.add("In welchem Stadtteil befindet sich die Region und wie ist die ID (mie-/kau-ID)?");
        gsTexte.add("Wie ist der Grundpreis (Staat) und wie hoch sind die Grundsteuern?");
        gsTexte.add("Kennst du die Maße der Region?");
        gsTexte.add("An welchem Tag und zu welcher Uhrzeit soll die Auktion enden? (Maximal bis zum " + latestEnd + ")");
        gsTexte.add("Wie soll der Startpreis in der Auktion sein?");
        gsTexte.add("Welche Summe soll für den Sofortkauf bestimmt werden?");
        gsTexte.add("In welchen Schritten sollen die Spieler mindestens bieten?");
        gsTexte.add("Dann bekomme ich bitte noch deine Kontonummer und die 500€ Auktionsgebühr.");

        bizTexte.add("Bist du dir bewusst, dass die Auktion anfangs 500€ kostet? Weiterhin fallen 3% vom Gewinn Auktionsgebühr an, sofern das BIZ versteigert wird.");
        bizTexte.add("Welche Art von BIZ möchtest du versteigern und in welcher Region befindet es sich?");
        bizTexte.add("Dann brauche ich bitte die Übernahmekosten (Staat) und die aktuellen Miet- bzw. Pachtkosten.");
        bizTexte.add("An welchem Tag und zu welcher Uhrzeit soll die Auktion enden? (Maximal bis zum " + latestEnd + ")");
        bizTexte.add("Wie soll der Startpreis in der Auktion sein?");
        bizTexte.add("Welche Summe soll für den Sofortkauf bestimmt werden?");
        bizTexte.add("In welchen Schritten sollen die Spieler mindestens bieten?");
        bizTexte.add("Dann bekomme ich bitte noch deine Kontonummer und die 500€ Auktionsgebühr.");

        autoTexte.add("Bist du dir bewusst, dass die Auktion anfangs 500€ kostet? Weiterhin fallen 3% vom Gewinn Auktionsgebühr an, sofern das Auto versteigert wird.");
        autoTexte.add("Von welcher Marke ist das Auto und welches Modell ist es?");
        autoTexte.add("Besitzt das Auto irgendwelche Tunings?");
        autoTexte.add("Welche Farbe hat das Auto?");
        autoTexte.add("Welchen Reparaturzustand hat das Auto? Dieser muss min. 80% sein. Gerne kannst du dein Auto auch im Anschluss in der Garage beim Mechaniker reparieren.");
        autoTexte.add("An welchem Tag und zu welcher Uhrzeit soll die Auktion enden? (Maximal bis zum " + latestEnd + ")");
        autoTexte.add("Wie soll der Startpreis in der Auktion sein?");
        autoTexte.add("Welche Summe soll für den Sofortkauf bestimmt werden?");
        autoTexte.add("In welchen Schritten sollen die Spieler mindestens bieten?");
        autoTexte.add("Kennst du den aktuellen Neupreis des Autos im Autohaus?");
        autoTexte.add("Dann bräuchte ich bitte einmal deine Kontonummer und den Autoschlüssel mit den 500€ Auktionsgebühr über das Handelsmenü.");

        codeTexte.add("Bist du dir bewusst, dass die Auktion anfangs 500€ kostet? Weiterhin fallen 3% vom Gewinn Auktionsgebühr an, sofern der Gutschein versteigert wird.");
        codeTexte.add("An welchem Tag und zu welcher Uhrzeit soll die Auktion enden? (Maximal bis zum " + latestEnd + ")");
        codeTexte.add("Wie soll der Startpreis der Auktion sein?");
        codeTexte.add("Welche Summe soll für den Sofortkauf bestimmt werden?");
        codeTexte.add("In welchen Schritten sollen die Spieler mindestens bieten?");
        codeTexte.add("Dann bräuchte ich bitte einmal deine Kontonummer und die 500€ Auktionsgebühr.");

    }

    public void startAuction(String[] message) {
        message[0] = message[0].toLowerCase();
        switch (message[0]) {
            case "item":
                doAuction(itemTexte);
                break;
            case "gutschein":
            case "code":
                doAuction(codeTexte);
                pUtils.displayPrefix("§aDie Person muss den Code per Forenkonversation an dich und die Fraktionsleitung senden." +
                        "Weiterhin muss noch ein Vertrag erstellt werden.");
                break;
            case "gs":
            case "grundstück":
            case "grundstueck":
            case "mietregion":
            case "mietzone":
            case "mie":
            case "werbetafel":
                doAuction(gsTexte);
                pUtils.displayPrefix("§aDie Person muss noch einen Vertrag unterschreiben.");
                break;
            case "vehicle":
            case "auto":
            case "car":
                doAuction(autoTexte);
                pUtils.displayPrefix("§aDie Person muss noch einen Vertrag unterschreiben.");
                break;
            case "biz":
                doAuction(bizTexte);
                pUtils.displayPrefix("§aDie Person muss noch einen Vertrag unterschreiben.");
                break;
            default:
                pUtils.displayPrefix("§cKorrekte Anwendung: §e/auktion [item, code, gs, mie, auto]");
                break;
        }
    }

    private void doAuction(List<String> msg) {
        if(current_counter == 0) {
            pUtils.displayPrefix("");
            pUtils.displayPrefix("§aKlicke nun die Texte für die jeweiligen Auktionstexte an");
        }
        TextComponentString next = new TextComponentString(pUtils.PREFIX + "§a" + (current_counter+1) + ". Auktionstext einfügen");
        Style style = new Style();
        style.setColor(TextFormatting.GREEN);
        style.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, msg.get(current_counter++)));
        next.setStyle(style);
        Minecraft.getMinecraft().player.sendMessage(next);
        if(current_counter < msg.size())
            doAuction(msg);
        else {
            current_counter = 0;
            pUtils.displayPrefix("");
        }
    }


}
