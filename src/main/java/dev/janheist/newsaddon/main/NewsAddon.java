package dev.janheist.newsaddon.main;

import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.HeaderElement;
import net.labymod.settings.elements.SettingsElement;

import java.util.List;

public class NewsAddon extends LabyModAddon {
    @Override
    public void onEnable() {
        System.out.println("|                                        |");
        System.out.println("|                                        |");
        System.out.println("| NEWS-ADDON by JAN HEIST aka. Mexykaner |");
        System.out.println("|     VIELEN DANK FÜR DIE VERWENDUNG     |");
        System.out.println("|                                        |");
        System.out.println("| DU KANNST DIESES  ADDON DEINSTALLIEREN |");
        System.out.println("|    DAMIT HAB ICH  MICH NUR VEREWIGT    |");
        System.out.println("|                                        |");
        System.out.println("|      FRÜHER HATTEST DU DIESES ADDON    |");
        System.out.println("|   FÜR DEN NEWS-DIENST AUF GERMANMINER  |");
        System.out.println("| GENUTZT. BEI FRAGEN MELDE DICH EINFACH |");
        System.out.println("|                                        |");
        System.out.println("|  DATEINAME:  NEWSADDON--1.JAR ODER SO  |");
        System.out.println("|                                        |");
        System.out.println("|                                        |");
    }

    @Override
    public void loadConfig() { }
    @Override
    protected void fillSettings(List<SettingsElement> list) {
        getSubSettings().add(new HeaderElement("§c§l|                                        |"));
        getSubSettings().add(new HeaderElement("§c§l|                                        |"));
        getSubSettings().add(new HeaderElement("§c§l| NEWS-ADDON by JAN HEIST aka. Mexykaner |"));
        getSubSettings().add(new HeaderElement("§c§l|     VIELEN DANK FÜR DIE VERWENDUNG     |"));
        getSubSettings().add(new HeaderElement("§c§l|                                        |"));
        getSubSettings().add(new HeaderElement("§c§l| DU KANNST DIESES  ADDON DEINSTALLIEREN |"));
        getSubSettings().add(new HeaderElement("§c§l|    DAMIT HAB ICH  MICH NUR VEREWIGT    |"));
        getSubSettings().add(new HeaderElement("§c§l|                                        |"));
        getSubSettings().add(new HeaderElement("§c§l|      FRÜHER HATTEST DU DIESES ADDON    |"));
        getSubSettings().add(new HeaderElement("§c§l|   FÜR DEN NEWS-DIENST AUF GERMANMINER  |"));
        getSubSettings().add(new HeaderElement("§c§l| GENUTZT. BEI FRAGEN MELDE DICH EINFACH |"));
        getSubSettings().add(new HeaderElement("§c§l|                                        |"));
        getSubSettings().add(new HeaderElement("§c§l|  DATEINAME:  NEWSADDON--1.JAR ODER SO  |"));
        getSubSettings().add(new HeaderElement("§c§l|                                        |"));
        getSubSettings().add(new HeaderElement("§c§l|                                        |"));
    }
}
