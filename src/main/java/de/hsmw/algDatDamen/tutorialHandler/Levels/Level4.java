package de.hsmw.algDatDamen.tutorialHandler.Levels;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.AlgDatDamen;
import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.ChessBoard.MChessBoardMode;
import de.hsmw.algDatDamen.ChessBoard.Queen;
import de.hsmw.algDatDamen.tutorialHandler.ControlItem;
import de.hsmw.algDatDamen.tutorialHandler.Level;
import de.hsmw.algDatDamen.tutorialHandler.NPCTrack;
import de.hsmw.algDatDamen.tutorialHandler.Step;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;

/* 
### Level 4 - _erster versuch_:
- Startpunkt `-106 -25 83`
- Schachbrett `8x8 -132 -25 75` - wofür ist die Insel da?
- Teleporter `-143 -24 62`
*/
// TODO muss getestet werden
public class Level4 extends Level {

    private final static String LEVEL_NAME = "Level 4 - Erster versuch:";
    private final static String LEVEL_DESCRIPTION = "Zeigen der Schwierigkeit auf einem großen Schachbrett, sowie Zeigen von verschiedenen Algorithmen zu Lösungserleichterung und Zeigen der Schrittfolge des Backtracking-Algorithmus";

    public Level4(boolean console, Player player, Tutorial parent) {
        this(console, player, new Location(player.getWorld(), -106, -25, 83, 90, 0),
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
        chessBoards[0] = new MChessBoard(new Location(player.getWorld(), -131, -25, 76), 6, player);
    }

    @Override
    protected void initializeSteps() {
        initBossBar();

        // Erzeugung eines 8x8 Schachbretts
        currentStep = new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_401_INTRO);
                    npc.moveVillagerWithPathfinding(new Location(player.getWorld(), -129, -24, 83), 1);
                },
                () -> {

                });
        // setupStep wird bis zum Ende durchgegeben und jeweils mit dem vorherigen
        // verknüpft
        Step setupStep = currentStep;

        // Setzen aller Damen ohne Lösung des Problems durch Computer
        setupStep.setNext(new Step(
                () -> {
                    // Erklärung des Levelabschnitts und des Problems mit großen Schachbrettern
                    // durch NPC
                    /*
                     * TODO neuer Text
                     * 
                     */
                    npc.playTrack(NPCTrack.NPC_402_EXPLAIN_PROBLEM);
                    stopBossBar();
                    chessBoards[0].updateBoard();
                    chessBoards[0].setCollisionCarpets(true); 
                    chessBoards[0].setMode(MChessBoardMode.NORMAL);
                    chessBoards[0].setActive(true); // TODO an mode anpassen

                    // Inventar leeren und neu füllen, falls Spieler Items vertauscht hat
                    setInventory();
                    player.getInventory().setItem(0, ControlItem.PLACE_QUEEN.getItemStack());
                    player.getInventory().setItem(1, ControlItem.SHOW_CARPET.getItemStack());
                    startBossBarTimer(3, "Überspringen verfügbar in:");
                },
                () -> {
                    chessBoards[0].setMode(MChessBoardMode.NORMAL);
                    chessBoards[0].setActive(false); // TODO mode
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].despawnChessBoard();
                    // Inventar auf Urspungszustand zurücksetzen
                    setInventory();
                    stopBossBar();
                },
                // Step ist complete wenn das Schachbrett gelöst ist, oder 3 min abgelaufen
                // sind.
                unused -> {
                    if (chessBoards[0].isSolved()) {
                        // Easteregg
                        npc.playTrackPositive();
                        return true;
                    } else if (!bbisRunning) {
                        return true;
                    } else {
                        return false;
                    }
                }

        ));

        setupStep = setupStep.getNext();

        setupStep.setNext(new Step(
                () -> {
                    // Erklärung des Backtracking-Algorithmus durch NPC
                    npc.playTrack(NPCTrack.NPC_403_EXPLAIN_BACKTRACKING_1);
                    stopBossBar();
                    setInventory();
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].updateBoard();
                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].setMode(MChessBoardMode.NORMAL);
                    chessBoards[0].setActive(false); // TODO mode
                    player.getInventory().setItem(0, ControlItem.SHOW_CARPET.getItemStack());
                    chessBoards[0].animationPiece2Piece(AlgDatDamen.getInstance(), 8, new Queen()); // <-- später 8
                },
                () -> {
                    // Chessboard leeren und Animation stoppen
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].updateBoard();
                    chessBoards[0].stopCurrentAnimation();
                    setInventory();
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
                    chessBoards[0].updateBoard();
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].setStateX(0);
                    chessBoards[0].setStateY(0);
                    chessBoards[0].setMode(MChessBoardMode.NORMAL);
                    chessBoards[0].setActive(true); // TODO mode
                    this.currentCBID = 0;
                    setInventory();
                    // Löschen und Neusetzen von Damen bis zur richtigen Lösung unter Zuhilfenahme
                    // des Backtracking-Algorithmus mit NPC-Interaktion, Hilfestellung und
                    // Möglichkeiten vor- und zurückzuspringen
                    player.getInventory().setItem(0, ControlItem.BACKTRACKING_FORWARD_Q.getItemStack());
                    player.getInventory().setItem(1, ControlItem.BACKTRACKING_FORWARDFAST_Q.getItemStack());
                    player.getInventory().setItem(2, ControlItem.SHOW_CARPET.getItemStack());
                    player.getInventory().setItem(3, ControlItem.BACKTRACKING_BACKWARD_Q.getItemStack());
                    player.getInventory().setItem(4, ControlItem.BACKTRACKING_BACKWARDFAST_Q.getItemStack());
                },
                () -> {
                    chessBoards[0].setMode(MChessBoardMode.NORMAL);
                    chessBoards[0].setActive(false); // TODO mode
                    setInventory();
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
        setupStep = setupStep.getNext();

        setupStep.setNext(new Step(
                () -> {
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].updateBoard();
                    teleporter.setEnabled(true, true);
                    setInventory();
                    player.getInventory().setItem(4, ControlItem.NEXT_LEVEL.getItemStack());
                    npc.moveVillagerWithPathfinding(new Location(player.getWorld(), -144, -24, 64), 1);
                },
                () -> {
                    setInventory();
                    teleporter.setEnabled(false, true);
                }));
        setupStep = setupStep.getNext();

        // alle Steps in beide Richtungen miteinander verknüpfen
        currentStep.backLink();
    }

}
