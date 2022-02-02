package dev.janheist.newsaddon.modules;

import dev.janheist.newsaddon.main.NewsAddon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

public class UpdateChecker {
    NewsAddon newsAddon;

    public UpdateChecker(NewsAddon newsAddon) {
        this.newsAddon = newsAddon;
    }

    public void init() throws IOException, ParseException {
        TimeZone.getTimeZone("Europe/Berlin");
        Date now = new Date();

        URL url = new URL("http://http.mexykaner.de/app/news/addonversion.json");

        URLConnection con = url.openConnection();
        InputStream is =con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        String line = null;
        String firstline = br.readLine();
        if(!firstline.equals(newsAddon.VERSION)) {
            System.out.println("[NEWS-DEBUG] old addon version: " + firstline + " / " + newsAddon.VERSION);
            newsAddon.getpUtils().displayPrefix("");
            while ((line = br.readLine()) != null) {
                newsAddon.getpUtils().displayPrefix(line);
            }
            newsAddon.getpUtils().displayPrefix("");
        }



    }

}


