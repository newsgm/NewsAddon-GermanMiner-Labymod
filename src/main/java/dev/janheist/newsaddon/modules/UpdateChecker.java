package dev.janheist.newsaddon.modules;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class UpdateChecker {
    public static void main(String[] args) throws IOException, URISyntaxException {
        File currentFile = new File(UpdateChecker.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        File addonFolder;
        if(args.length >= 1) {
            addonFolder = new File(args[0]);
        } else {
            addonFolder = new File(System.getenv("APPDATA") + File.separator + ".minecraft" + File.separator + "LabyMod" + File.separator + "addons-1.12");
        }

        File[] files = addonFolder.listFiles();
        if (files == null)
            return;

        for (File file : files) {
            if (file.getName().startsWith("NewsAddon")) {
                boolean success = false;
                while (!success) {
                    try {
                        success = file.delete();
                    } catch (SecurityException securityException) {}
                }
            }
        }

        try {
            Files.copy(currentFile.toPath(), (new File(addonFolder, currentFile.getName())).toPath(), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
