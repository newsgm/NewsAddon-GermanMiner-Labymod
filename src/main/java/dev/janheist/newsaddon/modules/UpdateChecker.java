package dev.janheist.newsaddon.modules;

import dev.janheist.newsaddon.main.NewsAddon;
import net.labymod.main.LabyMod;

import java.io.*;
import java.net.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class UpdateChecker {
    static NewsAddon newsAddon;
    private static File updatedAddon;

    public UpdateChecker(NewsAddon newsAddon) {
        UpdateChecker.newsAddon = newsAddon;
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        File currentFile = new File(UpdateChecker.class.getProtectionDomain().getCodeSource().getLocation().toURI());

        File addonFolder = new File(System.getenv("APPDATA") + File.separator + ".minecraft" + File.separator + "LabyMod" + File.separator + "addons-1.12");
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

    public static void initialize(int VERSION) {
        (new Timer()).schedule(new TimerTask() {
            public void run() {
                try {
                    UpdateChecker.check(VERSION);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        },  10000L);
    }

    private static void check(int VERSION) throws IOException {

        TimeZone.getTimeZone("Europe/Berlin");
        Date now = new Date();

        URL url = new URL("http://http.mexykaner.de/app/news/addonversion.json");

        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;
        String firstline = br.readLine();
        String newDownloadLink = br.readLine();
        int newVersion = Integer.parseInt(br.readLine());
        System.out.println("[NEWS-DEBUG] old addon version: " + newVersion + " / " + VERSION);

        if(newVersion > VERSION) {
            System.out.println("[NEWS-DEBUG] old addon version: " + newVersion + " / " + VERSION);
            displayPrefix("");
            while ((line = br.readLine()) != null) {
                displayPrefix(line);
            }
            displayPrefix("");

            update(newVersion, newDownloadLink);

        }
    }

    private static void update(int newVersion, String url) {
        try {
            Path tempDir;
            try {
                tempDir = Files.createTempDirectory("NewsAddon-", (FileAttribute<?>[])new FileAttribute[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            HttpURLConnection urlConnection = (HttpURLConnection)(new URL(url)).openConnection();
            updatedAddon = new File(tempDir.toFile(), "NewsAddon-" + newVersion + ".jar");
            Files.copy(urlConnection.getInputStream(), updatedAddon.toPath(), new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    Thread.sleep(3000);
                    Runtime.getRuntime().exec("\"" + System.getProperty("java.home") + File.separator + "bin" + File.separator + "javaw.exe\" -cp \"" + updatedAddon.getAbsolutePath() + "\" dev.janheist.newsaddon.modules.UpdateChecker");
                    } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayPrefix(String message) {
        LabyMod.getInstance().displayMessageInChat("§7[§a§lNEWS§r§7] §r".concat(message));
    }
}

