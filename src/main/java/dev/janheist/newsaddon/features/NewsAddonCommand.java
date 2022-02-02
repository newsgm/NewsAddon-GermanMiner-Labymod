package dev.janheist.newsaddon.features;

import dev.janheist.newsaddon.main.NewsAddon;

public class NewsAddonCommand {

    PlayerUtilities pUtils = new PlayerUtilities();
    NewsAddon newsAddon;
    public NewsAddonCommand(NewsAddon newsAddon) {
        this.newsAddon = newsAddon;
    }

    public void init(String message) throws NullPointerException {
        String[] args = message.split(" ");
        if(args[1].startsWith("hv") && !args[1].equals("hv2")) {
            pUtils.displayPrefix("§8§l---*--- §cGERMAN§6MINER§8-§aNEWS§8-§l§oAddon§r§8§l ---*---");
            pUtils.displayPrefix(" ");
            pUtils.displayPrefix("§e§oPvP im Gebäude §7»§r §c3 Tage");
            pUtils.displayPrefix("§e§oRaubüberfall im Gebäude §7»§r §c3 Tage");
            pUtils.displayPrefix("§e§oMord am News-Gelände §7»§r §c10 Tage");
            pUtils.displayPrefix("§e§oMord an Newsler im Dienst (egal wo) §7»§r §c5 Tage");
            pUtils.displayPrefix("§e§oSKV auf dem Gelände §7»§r §c3 Tage");
            pUtils.displayPrefix("§e§oKlingelmissbrauch §7»§r §c2 Tage");
            pUtils.displayPrefix("§e§oEinbrechen ins Gebäude §7»§r §c4 Tage");
            pUtils.displayPrefix("§e§oNicht nachkommen einer Aufforderung §7»§r §c2 Tage");
            pUtils.displayPrefix("§e§oBetreten des Geländes trotz HV §7»§r §c4 Tage");
            pUtils.displayPrefix(" ");
            pUtils.displayPrefix("§e/newsaddon hv2 §7»§r §cHausverbote Teil 2");
            pUtils.displayPrefix(" ");
            pUtils.displayPrefix("§8§l---*--- §cGERMAN§6MINER§8-§aNEWS§8-§l§oAddon§r§8§l ---*---");
        } else if(args[1].equals("hv2")) {
            pUtils.displayPrefix("§8§l---*--- §cGERMAN§6MINER§8-§aNEWS§8-§l§oAddon§r§8§l ---*---");
            pUtils.displayPrefix("§e§oTiermord auf dem Gelände §7»§r §c5 Tage");
            pUtils.displayPrefix("§e§oMissbrauch der Neuigkeiten-App §7»§r §c2 Tage");
            pUtils.displayPrefix("§e§oEinbrechen in den TB-Raum §7»§r §c2 Tage, während TB 3 Tage");
            pUtils.displayPrefix("§e§oProvokantes Verhalten §7»§r §c2 Tage");
            pUtils.displayPrefix("§e§oBeleidigen eines Newslers §7»§r §c1 Tag");
            pUtils.displayPrefix("§e§oDauerparken (nur von Frak.leitung) §7»§r §c2 Tage");
            pUtils.displayPrefix("§e§oMit dem Auto auf dem Gelände fahren §7»§r §c2 Tage");
            pUtils.displayPrefix("§e§oAndere Sachen §7»§r §c1 Tag");
            pUtils.displayPrefix(" ");
            pUtils.displayPrefix("§8§l---*--- §cGERMAN§6MINER§8-§aNEWS§8-§l§oAddon§r§8§l ---*---");
        } else if(args[1].equals("illegal")) {
            pUtils.displayPrefix("§e§oIllegal §7»§r §cMöglich Illegale Items");
            pUtils.displayPrefix("§e§oIllegal §7»§r §cSchutzwesten: SK3, SK2");
            pUtils.displayPrefix("§e§oIllegal §7»§r §cMunition: 9mm, 12mm, 18mm, Schrot, Flugabwehr");
            pUtils.displayPrefix("§e§oIllegal §7»§r §cWaffen: Raketenwerfer, M16, M4A1, M4A1-S");
            pUtils.displayPrefix("§e§oIllegal §7»§r §cWaffen: Schrot, Jagdmesser, M1911-Colt");
            pUtils.displayPrefix("§e§oIllegal §7»§r §cWaffen: G22, AK-47 (Kalaschnikow), Pfefferspray");
            pUtils.displayPrefix("§e§oIllegal §7»§r §cDrogen: Heroin, Kokain (Koks), Cannabis");
            pUtils.displayPrefix("§e§oIllegal §7»§r §cDrogen: Hanfsamen, Ecstasy, Tilidin");
        } else if(args[1].equals("warn")) {
            if(!(args.length > 2)) {
                pUtils.displayPrefix("§e/newsaddon warn [pvp1,pvp2,pvp3,hv1,hv2,hv3,1,2,3]");
            } else {
                switch (args[2]) {
                    case "pvp1":
                        pUtils.sendAsPlayer("✸ Achtung ✸ Bitte unterlasse das PVP auf unserem News-Gelände! (Verwarnung 1/2)");
                        break;
                    case "pvp2":
                        pUtils.sendAsPlayer("✸ Achtung ✸ Bitte unterlasse das PVP auf unserem News-Gelände! (Verwarnung 2/2)");
                        break;
                    case "pvp3":
                    case "3":
                        pUtils.sendAsPlayer("✸ Achtung ✸ Du hast nun Hausverbot auf unserem Gelände! Bitte verlasse es umgehend oder wir werden die Polizei rufen!");
                        break;
                    case "hv1":
                        pUtils.sendAsPlayer("✸ Achtung ✸ Du hast momentan Hausverbot auf unserem Gelände, bitte verlasse es umgehend! (Verwarnung 1/2)");
                        break;
                    case "hv2":
                        pUtils.sendAsPlayer("✸ Achtung ✸ Du hast momentan Hausverbot auf unserem Gelände, bitte verlasse es umgehend! (Verwarnung 2/2)");
                        break;
                    case "hv3":
                        pUtils.sendAsPlayer("✸ Achtung ✸ Du hast momentan Hausverbot auf unserem Gelände. Da du dieses nicht verlassen willst werde ich die Polizei rufen!");
                        break;
                    case "1":
                        pUtils.sendAsPlayer("✸ Achtung ✸ Bitte unterlasse das, sonst bekommst du Hausverbot! (Verwarnung 1/2)");
                        break;
                    case "2":
                        pUtils.sendAsPlayer("✸ Achtung ✸ Bitte unterlasse das, sonst bekommst du Hausverbot! (Verwarnung 2/2)");
                        break;
                }

            }
        } else if(args[1].equals("scan") || args[1].equals("scanner")) {
            if(!(args.length > 2)) {
                pUtils.displayPrefix("§e/newsaddon scan [Name,stop]");
            } else {
                if(args[2].equals("stop")) {
                    newsAddon.scan_name = null;
                    newsAddon.scanner = false;
                    pUtils.displayPrefix("§cScanner aus");
                } else {
                    newsAddon.scan_name = args[2];
                    newsAddon.scanner = true;
                    pUtils.displayPrefix("§aScanne nun " + args[2]);
                }
            }
        } else if(args[1].equals("da") || args[1].startsWith("dauerauftr")) {
            pUtils.displayPrefix("§aFolgende DAs sind für heute abgespeichert:");
            for(String item : newsAddon.das) {
                pUtils.displayPrefix("§a" + item);
            }
            pUtils.displayPrefix("");
        } else {
            pUtils.displayPrefix("");
            pUtils.displayPrefix("§e/newsaddon §e[help, hv1, hv2, illegal, warn, scan]");
            pUtils.displayPrefix("§e/auktion [item, code, gs, mie, auto]");
            pUtils.displayPrefix("");
        }
    }

}
