# Spielanleitung für das Minecraft-Plugin "AlgDat Damen"

## Installation und Start

### Voraussetzungen
- **Java 21** (Falls nicht installiert, wird ein Installer bereitgestellt).
- Ein leistungsfähiger PC wird empfohlen.

### Schritt-für-Schritt-Anleitung

#### Download
- Lade die neueste Version des Plugins von der [Release-Seite](#) herunter.

#### Ausführen
1. **Wenn Java 21 installiert ist**, starte die Datei `AlgDat_Start.bat`.
2. **Falls dein PC langsam ist**, benutze die Datei `AlgDat_Start_Low.bat`.
3. **Wenn Java nicht installiert ist**:
   - Öffne den Ordner `Java`.
   - Führe die Datei `javaInstaller.bat` aus.
   - Starte anschließend die `.bat`-Datei wie oben beschrieben.

#### Server starten
1. Zwei Terminal-Fenster öffnen sich automatisch und laden die notwendigen Dateien.
2. Nach kurzer Zeit sollte Minecraft starten, und du wirst automatisch auf den Server verbunden.

**Falls dies nicht funktioniert:**
- Gehe über das Minecraft-Hauptmenü:
  - **Multiplayer > Wähle den ersten Server in der Liste aus und klicke doppelt darauf.**
  - *(Screenshots werden eingefügt.)* <!-- TODO: Screenshot einfügen -->

---

## Minecraft-Steuerung

### Wichtige Befehle
- **Laufen:** `W`, `A`, `S`, `D`-Tasten.
- **Springen:** `Leertaste`.
- **Rechtsklick:** Nutze Items oder aktiviere Objekte.
- **Inventar öffnen:** `E`-Taste.

---

## Tutorialübersicht

### Einstieg ins Tutorial
1. Nach dem Serverbeitritt kannst du am Spawnpunkt das Tutorial starten.
2. Nutze die Buttons oder Schilder:
   - **Tag/Nacht wechseln**.
   - **Tutorial starten**.
   - *(Ein Screenshot wird hier eingefügt.)* <!-- TODO: Screenshot einfügen -->

### Navigation im Tutorial
- Das Tutorial besteht aus verschiedenen Leveln, die Schritt für Schritt durchlaufen werden.
- Du wirst immer zum Eingangstor eines Levels teleportiert. Von dort kannst du zu den Schachbrettern gehen.
- **NPCs erklären die Aufgaben in jedem Level.**

### Steuerung der Tutorial-Items
- **Weiter (blauer Farbstoff):** Führt den Ablauf des Tutorials fort.
- **Wiederholen (grüner Farbstoff):** Wiederholt die aktuelle Aufgabe.
- **Zurück (roter Farbstoff):** Setzt Schritte innerhalb eines Levels zurück.

### Abschluss eines Levels
- **Bei erfolgreichem Abschluss:**
  - Ein Blitz schlägt am Ausgangstor ein.
  - Ein blaues Feuer erscheint.

**Um ins nächste Level zu gelangen:**
- **Flohpulver in der Luft verwenden** oder
- **Rechtsklick auf das blaue Feuer mit dem Flohpulver.**

---

## Zusätzliche Tutorial-Funktionen

### Figuren setzen
- **Dame setzen (gelber Farbstoff).**
- **Springer setzen (orangener Farbstoff).**
- **Superdame setzen (hellblauer Farbstoff).**

### Bewegungen und Bedrohungen
- **Bewegung markieren (magentafarbener Farbstoff).**
- **Bedrohungen anzeigen (brauner Farbstoff).**

---

## Dev-Menü Steuerung

### Funktionen des Dev-Menüs

#### Schachbrett-Optionen
- **Spawne Schachbrett (Diamant):** Erstellt ein neues Schachbrett in der aktuellen Größe.
- **Entferne Schachbrett (Barrier):** Löscht das aktuelle Schachbrett.
- **Größe ändern (Redstone-Fackel):** Erhöht oder verringert die Größe des nächsten zu spawnenden Schachbretts.
- **Teppiche anzeigen (Roter Teppich):** Schaltet die Anzeige der Bedrohungszonen auf dem Brett ein oder aus.
- **Lösungen anzeigen (Grüner Teppich):** Zeigt eine Lösung der Figurenbewegungen auf dem Brett.

#### Figuren-Optionen
- **Spawne Dame (Eisenhelm):** Setzt oder entfernt eine Dame auf dem Brett.
- **Rotiere Figuren (Kompass):** Rotiert die Figuren auf dem Brett, um ihre Bewegungsmöglichkeiten zu analysieren.
- **Entferne alle Figuren (TNT):** Löscht alle Figuren vom Schachbrett.

#### Backtracking-Optionen
- **Backtracking starten (Diamantschwert):** Startet das Backtracking, um das Brett zu lösen.
- **Nächster Schritt (Eisenpickel):** Führt den nächsten Schritt des Backtracking-Algorithmus aus.
- **Schritt zurück (Goldene Hacke):** Geht einen Schritt im Algorithmus zurück.
- **Backtracking Animation (Diamantaxt):** Löst das Brett schrittweise, wobei die Animation alle Bewegungen zeigt.

#### Ästhetische Anpassungen
- **Ändere weiße Blöcke (Weißglasierte Terrakotta):** Ändert das Aussehen der weißen Felder.
- **Ändere schwarze Blöcke (Schwarze Blöcke):** Passt das Aussehen der schwarzen Felder an.

---

## Häufige Probleme und Lösungen

### Minecraft öffnet sich nicht
- Stelle sicher, dass Java 21 korrekt installiert ist.
- Prüfe, ob die `.bat`-Datei mit Administratorrechten ausgeführt wurde.

### Keine Verbindung zum Server
- Stelle sicher, dass du den richtigen Server auswählst.
  - *(Screenshot einfügen.)* <!-- TODO: Screenshot einfügen -->

### Spiel hängt oder läuft langsam
- Nutze `AlgDat_Start_Low.bat` für eine ressourcenschonende Version.

---

## Kontakt und Support
- Bei weiteren Fragen oder technischen Problemen wende dich bitte an das Entwicklungsteam über das [GitHub-Repository](#) oder die angegebene Support-Adresse.

---

**Viel Spaß beim Lösen des n-Damen-Problems!**

