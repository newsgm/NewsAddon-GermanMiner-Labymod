package dev.janheist.newsaddon.modules;

import dev.janheist.newsaddon.features.PlayerUtilities;
import dev.janheist.newsaddon.main.NewsAddon;
import net.labymod.main.LabyMod;
import net.minecraft.client.Minecraft;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateChecker {
    static NewsAddon newsAddon;
    private static File updatedAddon;

    public UpdateChecker(NewsAddon newsAddon) {
        UpdateChecker.newsAddon = newsAddon;
    }

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
                    } catch (SecurityException ignored) {}
                }
            }
        }

        try {
            Files.copy(currentFile.toPath(), (new File(addonFolder, currentFile.getName())).toPath(), StandardCopyOption.REPLACE_EXISTING);
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
        },  3000L);
    }

    private static void check(int VERSION) throws IOException {

        TimeZone.getTimeZone("Europe/Berlin");

        URL url = new URL("https://intern.news-redaktion.de/version.json");

        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setSSLSocketFactory(PlayerUtilities.getSocketFactory());

        InputStream is =con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line;
        String emptyLink = br.readLine();
        String newDownloadLink = br.readLine();
        int newVersion = Integer.parseInt(br.readLine());
        System.out.println("[NEWS-DEBUG] newest version: " + newVersion + ", current version: " + VERSION);

        if(newVersion > VERSION || VERSION == Integer.MAX_VALUE) {
            displayPrefix("");
            while ((line = br.readLine()) != null) {
                displayPrefix(line);
            }
            displayPrefix("");

            if(VERSION == Integer.MAX_VALUE)
                update(-1, emptyLink);
            else update(newVersion, newDownloadLink);

        }
    }

    private static void update(int newVersion, String url) {
        String minecraftDirectory = Minecraft.getMinecraft().mcDataDir.getAbsolutePath().replace("\\", File.separator) + File.separator + "LabyMod" + File.separator + "addons-1.12";
        try {
            Path tempDir;
            try {
                tempDir = Files.createTempDirectory("NewsAddon-");
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            HttpsURLConnection urlConnection = (HttpsURLConnection) (new URL(url)).openConnection();
            urlConnection.setSSLSocketFactory(PlayerUtilities.getSocketFactory());

            updatedAddon = new File(tempDir.toFile(), "NewsAddon-" + newVersion + ".jar");
            Files.copy(urlConnection.getInputStream(), updatedAddon.toPath(), StandardCopyOption.REPLACE_EXISTING);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    if(System.getProperty("os.name").toLowerCase().startsWith("windows")) {
                        String yourShellInput = "\"" + minecraftDirectory + "\"";  // or whatever ...
                        String[] commandAndArgs = new String[]{"java", "-cp", "\"" + updatedAddon.getAbsolutePath() + "\"", "dev.janheist.newsaddon.modules.UpdateChecker", yourShellInput};
                        Runtime.getRuntime().exec(commandAndArgs);
                        System.out.println("[NEWS-DEBUG] Windows-User: " + Arrays.toString(commandAndArgs));
                    } else {
                        Runtime.getRuntime().exec("sh -c 'java -cp \"" + updatedAddon.getAbsolutePath() + "\" dev.janheist.newsaddon.modules.UpdateChecker \"" + minecraftDirectory + "\"'");
                        ProcessBuilder processBuilder = new ProcessBuilder();
                        processBuilder.command("sh", "-c", "java -cp \"" + updatedAddon.getAbsolutePath() + "\" dev.janheist.newsaddon.modules.UpdateChecker \"" + minecraftDirectory + "\"");
                        Process process = processBuilder.start();

                        StringBuilder output = new StringBuilder();
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(process.getInputStream()));

                        String line;
                        while ((line = reader.readLine()) != null) {
                            output.append(line).append("\n");
                        }

                        System.out.println("[NEWS-DEBUG] ProcessBuilder: " + output);


                        System.out.println("[NEWS-DEBUG] No Windows-User: " + "sh -c 'java -cp \"" + updatedAddon.getAbsolutePath() + "\" dev.janheist.newsaddon.modules.UpdateChecker \"" + minecraftDirectory + "\"'");

                    }

                } catch (IOException e) {
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
