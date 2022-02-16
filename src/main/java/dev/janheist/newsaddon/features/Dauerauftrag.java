package dev.janheist.newsaddon.features;

import dev.janheist.newsaddon.main.NewsAddon;
import dev.janheist.newsaddon.timer.DauerauftragTimer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;

public class Dauerauftrag {
    NewsAddon newsAddon;

    public Dauerauftrag(NewsAddon newsAddon) {
        this.newsAddon = newsAddon;
    }

    public void init() throws IOException, ParseException {
        if(newsAddon.daurl.toLowerCase().startsWith("http") && newsAddon.daurl.length() > 8) {
            TimeZone.getTimeZone("Europe/Berlin");
            Date now = new Date();

            URL url = new URL("" + newsAddon.daurl);

            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            String line = null;
            String prefix = getPrefix(now.getDay());

            while ((line = br.readLine()) != null) {
                if ((line.startsWith(prefix) || line.startsWith("all-")) && !(line.startsWith("//"))) {
                    line = line.replace(prefix, "").replace("all-", "");
                    if (Integer.parseInt(line.split(":")[0]) >= now.getHours()) {

                        if(Integer.parseInt(line.split(":")[0]) == now.getHours() &&
                                (Integer.parseInt(line.split(":")[1]) < now.getMinutes())) {
                            System.out.println("[NEWS-DEBUG] ignored DA at " + line);
                        } else {
                            if (line.split(" ").length > 1)
                                line = line.split(" ")[0];
                            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String dateString = createDate() + " " + line + ":00";
                            Date date = dateFormatter.parse(dateString);
                            Timer daTimer = new Timer();
                            daTimer.schedule(new DauerauftragTimer(newsAddon), date);
                            newsAddon.das.add(dateString);

                            System.out.println("[NEWS-DEBUG] registered DA at " + dateString);
                        }
                    } else {
                        System.out.println("[NEWS-DEBUG] ignored DA at " + line);
                    }
                }
            }
        }

    }

    private String getPrefix(int date) {
        if(date == 0)
            return "so-";
        else if(date == 1)
            return "mo-";
        else if(date == 2)
            return "di-";
        else if(date == 3)
            return "mi-";
        else if(date == 4)
            return "do-";
        else if(date == 5)
            return "fr-";
        else return "sa-";
    }
    private String createDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }



}


