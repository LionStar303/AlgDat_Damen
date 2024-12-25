package de.hsmw.algDatDamen.tutorialHandler.Levels;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.AlgDatDamen;
import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.ChessBoard.Queen;
import de.hsmw.algDatDamen.tutorialHandler.ControlItem;
import de.hsmw.algDatDamen.tutorialHandler.Level;
import de.hsmw.algDatDamen.tutorialHandler.NPCTrack;
import de.hsmw.algDatDamen.tutorialHandler.Step;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;
import net.kyori.adventure.text.Component;

/* 
### Level 4 - _erster versuch_:
- Startpunkt `-106 -25 83`
- Schachbrett `8x8 -132 -25 75` - wofür ist die Insel da?
- Teleporter `-143 -24 62`
*/

public class Level4 extends Level {

    private final static String LEVEL_NAME = "Level 4 - Erster versuch:";
    private final static String LEVEL_DESCRIPTION = "Zeigen der Schwierigkeit auf einem großen Schachbrett, sowie Zeigen von verschiedenen Algorithmen zu Lösungserleichterung und Zeigen der Schrittfolge des Backtracking-Algorithmus";
    private final static Component NPC_INTRO = Component.text(
            "Lass uns das ganze jetzt wieder auf einem acht mal acht Feld betrachten und den Backtracking-Algoritmus verwenden");
    private final static Component NPC_INSTRUCTION = Component
            .text("Setzen aller Damen ohne Lösung des Problems durch Computer");
    private final static Component NPC_HELP = Component.text(
            "Wie du sehen kannst, ist es nicht so einfach, alle Damen auf einem 8x8 Schachbrett zu platzieren. Manchmal führt eine falsche Platzierung dazu, dass wir nicht weiterkommen. Das ist der Punkt, an dem der Backtracking-Algorithmus ins Spiel kommt.");
    private final static Component NPC_EXPLAIN_BACKTRACK_1 = Component.text(
            "Der Backtracking-Algorithmus ist eine Methode, um Lösungen für Probleme zu finden, indem man systematisch alle möglichen Optionen ausprobiert und zurückgeht, wenn man auf eine Sackgasse stößt. Lass mich dir zeigen, wie das funktioniert.");
    private final static Component NPC_SHOW_STEPS = Component
            .text("Lass uns den Algorithmus nun Schritt für Schritt durchlaufen.");
    private final static Component NPC_EXPLAIN_BACKTRACK_2 = Component.text(
            "Jedes mal wenn eine Dame platziert wird, prüfen wir ob diese Position es erlaubt eine weiter zu stellen, ansonsten nehmen wir die Dame wieder vom Brett und versuchen sie an eine andere Stelle zu platzieren, wo wir dann wieder prüfen, ob sie gültig ist.");
    private long cooldownMillis;

    public Level4(boolean console, Player player, Tutorial parent) {
        this(console, player, new Location(player.getWorld(), -106, -25, 83),
                new Location(player.getWorld(), -143, -24, 62), parent);
    }

    public Level4(boolean console, Player player, Location startLocation, Location teleporterLocation,
            Tutorial parent) {
        this(console, player, startLocation, teleporterLocation, false, parent);
    }

    public Level4(boolean console, Player player, Location startLocation, Location teleporterLocation,
            boolean completed, Tutorial parent) {
        // ruft den Konstruktor der Elternklasse Level auf
        super(console, LEVEL_NAME, LEVEL_DESCRIPTION, player, startLocation, teleporterLocation, completed, parent);
    }

    @Override
    protected void configureChessBoards() {
        chessBoards = new MChessBoard[1];
        chessBoards[0] = new MChessBoard(new Location(player.getWorld(), -132, -25, 75), 8, player);
    }

    @Override
    protected void initializeSteps() {
        // Erzeugung eines 8x8 Schachbretts
        currentStep = new Step(
                () -> {
                    // TODO Audio vom NPC abspielen lassen -> Welcher keine Ahnung :(
                    npc.playTrack(NPCTrack.NPC_401_INTRO);
                    // TODO evtl Verzögerung einbauen, sodass completed erst true gesetzt wird wenn
                    // der NPC fertig ist
                },
                () -> {
                    chessBoards[0].spawnChessBoard();
                });
        // setupStep wird bis zum Ende durchgegeben und jeweils mit dem vorherigen
        // verknüpft
        Step setupStep = currentStep;

        // Setzen aller Damen ohne Lösung des Problems durch Computer
        cooldownMillis = System.currentTimeMillis();
        chessBoards[0].setConsoleEnabled(true);
        setupStep.setNext(new Step(
                () -> {
                    chessBoards[0].setCollisionCarpets(false);
                    chessBoards[0].setActive(true);
                    // Inventar leeren und neu füllen, falls Spieler Items vertauscht hat
                    setInventory();
                    player.getInventory().setItem(0, ControlItem.PLACE_QUEEN.getItemStack());
                },
                () -> {

                    if (chessBoards[0].isSolved()) {
                        // TODO super gemacht, so war es mühsamm bla bla, mit Backtracking einfacher
                        // player.sendMessage(Component.textOfChildren(EMPTY_LINE, "Super gemacht
                        // TODO"));
                    } else {
                        // player.sendMessage(Component.textOfChildren(EMPTY_LINE, "Keine Panik
                        // Diesbezüglic dir wird geholfen!"));
                        // TODO nicht so schlimm ich erkläre es dir ??
                    }
                    // Erklärung des falschen Ergebnisses und des Problems mit großen Schachbrettern
                    // durch NPC
                    npc.playTrack(NPCTrack.NPC_402_EXPLAIN_PROBLEM);
                    player.sendMessage(Component.textOfChildren(EMPTY_LINE, NPC_HELP));
                    // TODO NPC Text Abspielen - ENTITY_AXOLOTL_SPLASH

                    // Schachbrett leeren
                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setActive(false);
                    chessBoards[0].updateBoard();
                    // Inventar auf Urspungszustand zurücksetzen
                    setInventory();
                },
                // Step ist complete wenn das Schachbrett gelöst ist, oder 3 min abgelaufen
                // sind.
                unused -> //(chessBoards[0].isSolved() || System.currentTimeMillis() - cooldownMillis > (1000 * 60 * 3))
                {
                    if (chessBoards[0].isSolved() || System.currentTimeMillis() - cooldownMillis > (1000 * 60 * 3)) {
                        return true;
                    } else {
                       
                        player.sendMessage(
                                "Das Schachbrett ist noch nicht gelöst und du muss noch " + ((1000 * 60 * 3) - System.currentTimeMillis() - cooldownMillis) + "millis testen");
                        return false;
                    }
                }

        ));

        setupStep = setupStep.getNext();

        setupStep.setNext(new Step(
                () -> {
                    // Erklärung des Backtracking-Algorithmus durch NPC
                    npc.playTrack(NPCTrack.NPC_403_EXPLAIN_BACKTRACKING_1);
                    // TODO NPC Text Abspielen - ENTITY_AXOLOTL_SWIM
                    // TODO warten bis NPC feritg

                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setActive(false);
                    chessBoards[0].updateBoard();
                    chessBoards[0].animationField2Field(AlgDatDamen.getInstance(), 1, new Queen()); // <-- später 8

                    npc.playTrack(NPCTrack.NPC_404_STEP_BY_STEP);
                    // TODO NPC Text Abspielen - ENTITY_BAT_AMBIENT
                    // TODO warten bis NPC feritg
                    // Clear Inventory
                    setInventory();
                },
                () -> {
                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setActive(false);
                    chessBoards[0].updateBoard();
                },
                // Step ist complete wenn das Schachbrett gelöst ist

                unused -> {
                    if (chessBoards[0].isSolved()) {
                        return true;
                    } else {
                        
                        player.sendMessage(
                                "Die Animation ist noch nicht beendet und das Schachbrett noch nicht gelöst");
                        return false;

                    }
                }));

        setupStep = setupStep.getNext();

        setupStep.setNext(new Step(
                () -> {
                    chessBoards[0].setCollisionCarpets(true);
                    // Erklärung des Backtracking-Algorithmus durch NPC
                    npc.playTrack(NPCTrack.NPC_405_EXPLAIN_BACKTRACKING_2);
                    // TODO NPC Text Abspielen - ENTITY_BAT_DEATH
                    // TODO warten bis NPC feritg
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].setActive(true);
                    setInventory();
                    // Löschen und Neusetzen von Damen bis zur richtigen Lösung unter Zuhilfenahme
                    // des Backtracking-Algorithmus mit NPC-Interaktion, Hilfestellung und
                    // Möglichkeiten vor- und zurückzuspringen
                    player.getInventory().setItem(0, ControlItem.BACKTRACKING_FORWARD_Q.getItemStack());
                    player.getInventory().setItem(1, ControlItem.SHOW_CARPET.getItemStack());
                    player.getInventory().setItem(2, ControlItem.BACKTRACKING_BACKWARD_Q.getItemStack());
                },
                () -> {
                    chessBoards[0].setActive(false);
                },
                // Step ist complete wenn das Schachbrett gelöst ist

                unused -> {
                    if (chessBoards[0].isSolved()) {
                        return true;
                    } else {
                        
                        player.sendMessage(
                                "Die Animation ist noch nicht beendet und das Schachbrett noch nicht gelöst");
                        return false;
                    }
                }));

        // Lernenden
        // Löschen aller Damen und Löschen des Schachbretts
        setupStep = setupStep.getNext();

        // Löschen aller Damen und Löschen des Schachbretts
        setupStep.setNext(new Step(
                () -> {
                    // 4x4 Brett samt Figuren entfernen
                    chessBoards[0].despawnAllPieces();
                    chessBoards[0].despawnChessBoard();
                    teleporter.setEnabled(true);
                    setInventory();
                    player.getInventory().setItem(4, ControlItem.NEXT_LEVEL.getItemStack());
                },
                () -> {
                    setInventory();
                    teleporter.setEnabled(false);
                    chessBoards[0].spawnChessBoard();
                    chessBoards[0].updatePieces();
                }));
        setupStep = setupStep.getNext();

        // alle Steps in beide Richtungen miteinander verknüpfen
        currentStep.backLink();
    }

}
