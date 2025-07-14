# AlgDat_Damen

## Inhaltsverzeichnis
- [AlgDat\_Damen](#algdat_damen)
  - [Inhaltsverzeichnis](#inhaltsverzeichnis)
  - [Links](#links)
  - [Das Damenproblem](#das-damenproblem)
    - [Einführung](#einführung)
    - [Wie man es löst](#wie-man-es-löst)
  - [Git-Richtlinien](#git-richtlinien)
  - [Minecraft Server Setup](#minecraft-server-setup)
  - [Übersicht Level:](#übersicht-level)
  - [Level Koordinaten](#level-koordinaten)
    - [Level 1 - Einführung:](#level-1---einführung)
    - [Level 2 - Bootcamp:](#level-2---bootcamp)
    - [Level 3 - Scandi Zwilling:](#level-3---scandi-zwilling)
    - [Level 4 - _erster versuch_:](#level-4---erster-versuch)
    - [Level 5 - Amazonas:](#level-5---amazonas)
    - [Level 6- Boreal:](#level-6--boreal)
    - [Level 7- Eiskönigin:](#level-7--eiskönigin)

---

## Links
- [Spielanleitung](./Spielanleitung/README.md)

## Das Damenproblem

### Einführung

Das Damenproblem (auch **Schachbrettproblem** genannt) ist ein bekanntes Rätsel aus der Informatik und Mathematik. Es geht darum, **8 Damen** auf einem **Schachbrett** so zu platzieren, dass keine Dame eine andere schlagen kann. Eine Dame kann sich auf dem Schachbrett in folgenden Richtungen bewegen und angreifen:
- **Horizontal** (in ihrer Reihe),
- **Vertikal** (in ihrer Spalte),
- **Diagonal** (in beide Richtungen).

Die Herausforderung besteht darin, diese 8 Damen so zu platzieren, dass keine von ihnen in einer der oben genannten Richtungen auf eine andere Dame trifft.

### Wie man es löst

Um das Problem zu lösen, stellt man sich vor, die Damen eine nach der anderen auf das Schachbrett zu setzen. Dabei geht man wie folgt vor:

1. **Platziere die erste Dame** in einer beliebigen Reihe und Spalte.
2. **Setze die nächste Dame** auf ein Feld, auf dem sie weder in derselben Reihe, Spalte noch Diagonale wie die vorherige Dame steht.
3. Wenn du feststellst, dass keine sichere Position für eine Dame möglich ist, gehst du einen Schritt zurück und verschiebst die vorherige Dame. Diesen Prozess nennt man **Backtracking**.
4. Fahre fort, bis alle 8 Damen sicher platziert sind.

Das Ziel des **Backtracking-Algorithmus** ist es, Lösungen zu finden, indem man zurückspringt, wenn man auf ein Problem stößt, und alternative Wege ausprobiert, bis das Problem gelöst ist.

Weitere Informationen findest du auf der [Wikipedia-Seite zum Damenproblem](https://de.wikipedia.org/wiki/Damenproblem).

---

## Git-Richtlinien

Beim Arbeiten am Projekt sollten folgende Git-Richtlinien beachtet werden:

- **Regelmäßiges Pushen**: Nach jedem kleinen Meilenstein sollte der Fortschritt mit einem Git-Commit gepusht werden.
- **Aussagekräftige Commit-Nachrichten**: Die Kommentare sollten kurz, aber präzise sein, sodass sie den Fortschritt klar beschreiben.
- **Code aussagekräftig kommentieren**: Um den Programmier Prozess zu vereinfachen, sollte Code verständlich dokumentiert werden (JavaDoc).

---

## Minecraft Server Setup

Um das Damenproblem spielerisch zu verstehen, haben wir einen **Minecraft-Server** eingerichtet. Der Server wird entweder über die Datei **`AlgDat_Start.bat`** oder für leistungsschwächere PCs über **`AlgDat_Start_Low.bat`** gestartet. Beim Starten wird automatisch der Minecraft-Launcher geöffnet, und du wirst mit dem Server verbunden, auf dem du das Tutorial spielen kannst.

Nachdem du auf die entsprechende Batch-Datei doppelklickst, kann es einen Moment dauern, bis alles heruntergeladen und gestartet ist. Bitte habe etwas Geduld, falls es nicht sofort losgeht.

---

## Übersicht Level:
## Level Koordinaten
- Spawn `0 -45 170`
- Teleporter `0 -46 170`
### Level 1 - Einführung:
- Startpunkt `-17 -44 144, 150 0`
- Schachbrett `8x8 -28 -45 130`
- Teleporter `-38 -43 140`
### Level 2 - Bootcamp:
- Startpunkt `-56 -37 139, 90 -15`
- Schachbrett `4x4 -69 -36 140`, `8x8 -74 -36 128`
- Teleporter `-76 -36 119`
### Level 3 - Scandi Zwilling:
- Startpunkt `-75 -33 103, 180 0`
- Schachbrett `4x4 -76 -32 81`, `4x4 -76 -32 89`
- Teleporter `-88 -32 83`
### Level 4 - _erster versuch_:
- Startpunkt `-106 -25 83, 100 -5`
- Schachbrett `8x8 -132 -25 75` - wofür ist die Insel da?
- Teleporter `-143 -24 62`
### Level 5 - Amazonas:
- Startpunkt `-153 -19 45, 140 -10`
- Schachbrett `4x4 -171 -19 25`, `8x8 -187 -19 14`
- Teleporter `-188 -20 -7`
### Level 6- Boreal:
- Startpunkt `-182 -12 -20, 180 -5`
- Schachbrett `8x8 -185 -14 -46`
- Teleporter `-164 -12 -59`
### Level 7- Eiskönigin:
- Startpunkt `-127 -7 -66, 180 -15`
- Schachbrett `10x10 -192 -8 -98`
- Teleporter -- `-127 -8 -67`
---
