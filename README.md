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
  - [Übersicht Funktionen:](#übersicht-funktionen)
  - [Übersicht Level:](#übersicht-level)
    - [Level Koordinaten](#level-koordinaten)
      - [Level 1 - Einführung:](#level-1---einführung)
      - [Level 2 - Bootcamp:](#level-2---bootcamp)
      - [Level 3 - Scandi Zwilling:](#level-3---scandi-zwilling)
      - [Level 4 - _erster versuch_:](#level-4---erster-versuch)
      - [Level 5 - Amazonas:](#level-5---amazonas)
      - [Level 6- Boreal:](#level-6--boreal)
      - [Level 7- Eiskönigin:](#level-7--eiskönigin)
  - [Konzept: Damenproblem Tutorial in Minecraft](#konzept-damenproblem-tutorial-in-minecraft)
    - [Schachfelder und Level](#schachfelder-und-level)
    - [Simulation des Algorithmus](#simulation-des-algorithmus)
    - [Interaktive Button-Steuerung](#interaktive-button-steuerung)
    - [Manuelles Lösen des Problems](#manuelles-lösen-des-problems)

---

## Links

- [Google Drive Ordner](https://drive.google.com/drive/u/2/folders/1iFkhfj-n0NXlC7PCiuOdhN4oxlZpLrAY)
- [Aufgaben](Aufgaben%20Übersicht.pdf)
- [Konzept Dokument](https://docs.google.com/document/d/13CYumO2Ctvhs1VfjUhYJ1NVMbv2mjmXSdiNyZm6ToJY/edit)
- [Abgabe Nextcloud](https://micloud.hs-mittweida.de/index.php/s/aJtmY5M6AbCNoCS)

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

Um das Damenproblem spielerisch zu verstehen, haben wir einen **Minecraft-Server** eingerichtet.
Dieser wird lokal gehostet, was durch entsprechende Skripte vereinfacht wird.

---

## Übersicht Funktionen:

Hier sind alle aktuell vorhandenen (Entwickler-) Funktionen aufgelistet

| Name                                  | Funktion                       | Item                   | in Menü |
| :------------------------------------ | :----------------------------- | :--------------------- | :-----: |
| `handleBoardCreation()`               | Spawne Schachbrett             | Material.GREY_CONCRETE |   ja    |
| `removeChessBoardFromGame()`          | Entferne Schachbrett           |                        |   ja    |
| `handleCollisionCarpets()`            | Zeige Teppiche                 |                        |   ja    |
| `placeQueen()`                        | Spawne/Entferne Königin        |                        |   ja    |
| `removeAllQueens()`                   | Entferne alle Königinnen       |                        |   ja    |
| `placeTestedQueen()`                  | Spawne getestete Königin       |                        |   ja    |
| `rotateQueens()`                      | Rotiere Königinnen             |                        |   ja    |
| `handleBacktrack()`                   | Löse Schachbrett               |                        |   ja    |
| `handleBacktrackStep()`               | Backtracking nächster Schritt  |                        |   ja    |
| `handleBacktrackAnimation()`          | Backtracking Animation         |                        |   ja    |
| `handleBacktrackAnimationQueenStep()` | Backtracking Animation schnell |                        |   ja    |
| `checkUserCarpets()`                  | Damen Movement Carpets checken |                        |   ja    |
| `placeUserCarpet()`                   | Damen Movement Carpet setzen   |                        |   ja    |

---
## Übersicht Level:
### Level Koordinaten
- Spawn `0 -45 170`
#### Level 1 - Einführung:
- Startpunkt `-30 -43 143`
- Schachbrett `8x8 -40 -44 128`
#### Level 2 - Bootcamp:
- Startpunkt `-56 -37 139`
- Schachbrett `4x4 -70 -36 128`, `3x3 -78 -36 130`
#### Level 3 - Scandi Zwilling:
- Startpunkt `-75 -33 103`
- Schachbrett `4x4 -76 -32 81`, `4x4 -76 -32 89`
#### Level 4 - _erster versuch_:
- Startpunkt `-106 -25 83`
- Schachbrett `8x8 -132 -25 75` - wofür ist die Insel da?
#### Level 5 - Amazonas:
- Startpunkt `-153 -19 45`
- Schachbrett `4x4 -171 -19 25`, `8x8 -187 -19 14`
#### Level 6- Boreal:
- Startpunkt `-182 -12 -21`
- Schachbrett `8x8 -185 -14 -46`
#### Level 7- Eiskönigin:
- Startpunkt `-128 -7 -70`
- Schachbrett `10x10 -192 -8 -98`
---
## Konzept: Damenproblem Tutorial in Minecraft

### Schachfelder und Level

In Minecraft werden verschieden große **Schachfelder** als verschiedene Level gebaut. Diese Schachfelder dienen als visuelle und interaktive Darstellung des Damenproblems. Spieler können anhand dieser Felder und eines **Buches** das Problem durchspielen und die Funktionsweise des Algorithmus nachvollziehen.

![Schachfelder](/Bilder/Konzept/Schachfelder.png)

### Simulation des Algorithmus

Es gibt die Möglichkeit, eine **automatische Simulation** des Backtracking-Algorithmus über die Schachfelder laufen zu lassen. In dieser Simulation können die Spieler anhand der markierten Felder und platzierten Figuren beobachten, wie der Algorithmus vorgeht und Lösungen findet.

![Simulation](/Bilder/Konzept/Simulation.png)

### Interaktive Button-Steuerung

Alternativ kann der Spieler den Algorithmus auch **schrittweise** durch das Klicken auf **Buttons** durchlaufen lassen. So kann jeder Schritt des Algorithmus nachvollzogen werden, indem der Spieler den Prozess manuell steuert und die Positionen der Damen auf dem Brett verfolgt.

![Buttons](/Bilder/Konzept/Buttons.png)

### Manuelles Lösen des Problems

Schließlich gibt es die Möglichkeit, dass der Spieler die Damen **manuell** auf das Schachbrett setzt. Hierbei können die Spieler die Positionen in **Kisten** oder **Blättern** speichern, um am Ende die maximale Anzahl von Damen zu bestimmen, die sicher auf dem Schachbrett platziert werden können.
