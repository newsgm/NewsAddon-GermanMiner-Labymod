package dev.janheist.newsaddon.modules;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class AutoDeploy {

    public static void main(String[] args) throws URISyntaxException {

        System.out.println("Projekt builden ...");
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "gradlew.bat", "build");
            File jarLocation = new File(AutoDeploy.class.getProtectionDomain().getCodeSource().getLocation().toURI())
                    .getParentFile().getParentFile().getParentFile();
            pb.directory(jarLocation);
            pb.inheritIO();

            Process p = pb.start();
            p.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Buildvorgang abgeschlossen!");


        // Altes News-Addon löschen
        File addonFolder;
        addonFolder = new File(System.getenv("APPDATA") + File.separator + ".minecraft" + File.separator + "LabyMod" + File.separator + "addons-1.12");
        File[] files = addonFolder.listFiles();

        if (files == null) {
            System.out.println("Keine Dateien im Addon-Ordner gefunden, abbruch :(");
            return;
        }
        System.out.println("Addon-Ordner gefunden: " + addonFolder.getAbsolutePath());

        System.out.println("Lösche alte Addons...");
        for (File file : files) {
            if (file.getName().startsWith("NewsAddon")) {
                boolean success = false;
                while (!success) {
                    try {
                        success = file.delete();
                    } catch (SecurityException ignored) {}
                }
                System.out.println("Datei gelöscht: " + file.getName());
            }
        }
        System.out.println("Alte News-Addons gelöscht.");

        System.out.println("Kopiere aktuelle Addon-Datei...");
        File jarLocation = new File(AutoDeploy.class.getProtectionDomain().getCodeSource().getLocation().toURI())
                .getParentFile().getParentFile().getParentFile();
        File buildFolder = new File(jarLocation, "build/libs");
        File jarFile = new File(buildFolder, "NewsAddon-1.jar");
        System.out.println("Jar-Datei gefunden: " + jarFile.getAbsolutePath());

        // Kopieren
        try {
            Files.copy(jarFile.toPath(), (new File(addonFolder, jarFile.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Addon-Datei kopiert. Speicherort: " + addonFolder.getAbsolutePath());
        System.out.println("AutoDeploy beendet.");
    }

}
