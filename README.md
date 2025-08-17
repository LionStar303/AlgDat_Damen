# AlgDat_Damen

## Inhaltsverzeichnis
- [AlgDat\_Damen](#algdat_damen)
  - [Inhaltsverzeichnis](#inhaltsverzeichnis)
  - [Links](#links)
  - [Das Damenproblem](#das-damenproblem)
  - [Minecraft Server Setup](#minecraft-server-setup)

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

## Minecraft Server Setup

Um das Damenproblem spielerisch zu verstehen, haben wir einen **Minecraft-Server** eingerichtet. Der Server wird entweder über die Datei **`AlgDat_Start.bat`** oder für leistungsschwächere PCs über **`AlgDat_Start_Low.bat`** gestartet. Beim Starten wird automatisch der Minecraft-Launcher geöffnet, und du wirst mit dem Server verbunden, auf dem du das Tutorial spielen kannst.

Nachdem du auf die entsprechende Batch-Datei doppelklickst, kann es einen Moment dauern, bis alles heruntergeladen und gestartet ist. Bitte habe etwas Geduld, falls es nicht sofort losgeht.
