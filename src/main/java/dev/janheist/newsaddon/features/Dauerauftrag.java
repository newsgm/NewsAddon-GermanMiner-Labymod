package dev.janheist.newsaddon.features;

import dev.janheist.newsaddon.main.NewsAddon;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.TimeZone;

public class Dauerauftrag {
    NewsAddon newsAddon;
    public static JSONObject dauerauftrags;

    public Dauerauftrag(NewsAddon newsAddon) {
        this.newsAddon = newsAddon;
    }



    public static void init() throws IOException {
        TimeZone.getTimeZone("Europe/Berlin");
        Date now = new Date();
        TimeZone.getTimeZone("Europe/Berlin");

        dauerauftrags = readJsonFromUrl("http://http.mexykaner.de/app/news/dauerauftrag.json");
        dauerauftrags = (JSONObject) dauerauftrags.get("" + now.getDay());
    }


    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }


}


