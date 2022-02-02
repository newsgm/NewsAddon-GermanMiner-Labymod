# NewsAddon-GermanMiner-Labymod
LabyMod-Addon für die Fraktion News auf dem Minecraft-Wirtschaftsserver GermanMiner.de

[Zu LabyMod](https://www.labymod.net/), [Zu GermanMiner](https://www.germanminer.de/)

**Jeglicher Inhalt ist fiktiv und basiert auf virtuellem Geschehen!**

Mein Discord: `Mexykaner#8636`

---
# Wichtige Änderungen: 
Die Dateinamen des Addons dürfen nur noch `NewsAddon-<version>.jar` heißen.

Bspw. `NewsAddon-1.jar` für Version 1, usw.

---
# Installation
Dieses Addon ist nur für LabyMod bzw. Minecraft in der Version 1.12 gedacht. Die Kompatibilität zu anderen Programmen kann nicht garantiert werden.
Unter Windows muss die Addon **.jar** in folgenden Ordner: `%appdata%/.minecraft/LabyMod/addons-1.12`
Die aktuelle Addon-Datei findest du rechts im Github-Repository unter **Releases**

# Features im Überblick
Das Addon hat zwei Befehle zur Steuerung: `/newsaddon` und `/auktion`. Hierüber werden auch Hilfsmenüs angezeigt, die die möglichten Befehle beinhalten.
Des Weiteren werden diese nun etwas genauer erklärt.

## Commands
### /newsaddon help
Zeigt dir mögliche Befehle an.

### /newsaddon hv
Zeigt dir an, wofür es wie lange Hausverbot auf dem Redaktionsgelände gibt. Seite 1 von 2.

### /newsaddon hv2
Zeigt dir an, wofür es wie lange Hausverbot auf dem Redaktionsgelände gibt. Seite 2 von 2.

### /newsaddon illegal
Zeigt dir an, welche Items auf GermanMiner möglich illegal sein könnte.

### /newsaddon warn
Dies sind nur Kurzbefehle für Verwarnungs-Hotkeys:
Verwarnungen für PvP: `/newsaddon warn` `pvp1` `pvp2` `pvp3`
Verwarnungen für Hausverbot: `/newsaddon warn` `hv1` `hv2` `hv3`
Verwarnungen _Allgemein_: `/newsaddon warn` `1` `2` `3`

### /newsaddon show
Auch hier sind kurzbefehle, die als Vorlage zum Spielermenü weiterhelfen. Mögliche Befehle:
- `/newsaddon show greet <String>`
  - Spieler sendet folgende Nachricht: `Hallo <String>. Willkommen in der News-Zentrale. Wie kann ich weiterhelfen?`
- `/newsaddon show types`
  - Spieler sendet folgende Nachricht: `Welche Art von Werbung möchtest du schalten? Chatwerbung (350€), Actionbarwerbung (350€), Appwerbung (1120€) oder Zeitungswerbung?`
- `/newsaddon show chat`
  - Spieler sendet folgende Nachricht: `Dann bekomme ich bitte den Text und 350€`

### /newsaddon scan `<person>||stop`
Hiermit kannst du eine Person markieren, sodass jede Nachricht "gescannt" wird. Solltest du eine Nachricht von dieser Person oder einem Zusammenhang erhalten, erscheint vor der nachricht ein grünes C in eckigen Klammern ("[C]"), welches du anklicken kannst, um die Nachricht der Person in die Chatleiste zu kopieren. Dies ist vor allem bei Kunden an der Rezeption praktisch.
Mit `/newsaddon scan stop` ist niemand mehr ausgewählt.

### /newsaddon da
Mit diesem Befehl kannst du dir die heutigen Daueraufträge anzeigen lassen (Vorsicht Spam :wink:).
Die Zeiten aktualisieren sich bei jedem Start von Minecraft. _Weitere Infos unten_

### /auktion `[item, code, gs, auto, biz]` || /auction `[item, code, gs, auto, biz]`
Hiermit können Auktionstexte abgerufen werden. Für die verschiedenen Auktionen gibt es mehrere Aliasse.
- Auktionen für Items: `/auktion `
  - `item`
- Auktionen für Gutscheincodes: `/auktion `
  - `gutschein`
  - `code`
- Auktionen für Regionen: `/auktion `
  - `gs`
  - `grundstück`
  - `grundstueck`
  - `mietregion`
  - `mietzone`
  - `mie`
  - `werbetafel`
- Auktionen für Autos: `/auktion `
  - `vehicle`
  - `auto`
  - `car`
- Auktionen für BIZ: `/auktion `
  - `biz`

## GUI
Über die LabyMod GUI-Einstellungen im Unterpunkt "Informationen" können zwei Counter angezeigt werden: ```90-Counter``` und ```120-Counter```.
Sollte eine Werbung im Chat geschaltet werden, so stellt sich der Counter auf 90- bzw. 120 Sekunden.
Der Counter wird auch zurückgesetzt, wenn du einen Server betrittst.

In den Einstellungen kannst du festlegen, ob ein Ton abgespielt werden soll, wenn der Counter 0 erreicht hat (=> Zeit abgelaufen).
Diese Töne sind vordefiniert auf einen "Pling" für den 120-Sekunden-Counter und eine "Harfe" für den 90-Sekunden-Counter.

## Weitere Funktionen
### Daueraufträge
Beim Starten von Minecraft werden automatisch Daten für Daueraufträge abgerufen, sodass du eine Benachrichtigung im Chat erhältst, sobald ein Dauerauftrag geschaltet werden muss.
Auch hier erwartet dich ein Sound, welchen du ggf. in den Einstellungen deaktivieren kannst.

Die URL zum Datenabruf ist nicht statisch einprogrammiert und muss in den LabyMod Addon-Einstellungen eingetragen werden.
Für die News haben wir einen Link, den wir alle nutzen können, dieser wird nur intern weitergegeben.
Für alle anderen habe ich unten ein Dateiaufbau mit kurzer Erklärung angefügt.


### Spielermenü
Im Spielermenü (_Mausrad-Menü_) hast du 3 Kurzbefehle, welche du auch in den Addon-Einstellungen deaktivieren kannst. Die möglichen Funktionen sind
- `[N] Scan`
  - Hiermit kannst du eine Person scannen, wie oben schon beschrieben
- `[N] Werbearten`
  - Kurzbefehl, um Werbearten anzeigen zu lassen
- `[N] Grüßen`
  - Grüße die Person, hierbei wird der Name automatisch eingefügt


### Sonstiges
### Beispieldatei Daueraufträge
Für Newsler nicht wirklich relevant, wir haben schon einen vorgefertigten internen Link. Für den Rest:

Wie oben schon beschrieben, benötigt man eine bestimmte Datei, welche per URL aufgerufen werden muss.
Hierbei kann über einen bestimmten Präfix der Wochentag angegeben werden: `mo-`ntag, `di-`enstag, und so weiter...
Für alle Wochentage kann man den Präfix `all-` nutzen.

Zuletzt hier die Datei:
```
// Kommentare werden dem Nutzer im Game-Output angezeigt!
// Kommenteare == Zeilen beginnend mit "//"

// ALLE TAGE
all-12:00

// MONTAG "mo-"
mo-13:00

// DIENSTAG "di-"
// MITTWOCH "mi-"
// DONNERSTAG "do-"
// FREITAG "fr-"
// SAMSTAG "sa-"
// SONNTAG "so-"
```
