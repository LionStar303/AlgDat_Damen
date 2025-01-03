package de.hsmw.algDatDamen.tutorialHandler.Levels;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import de.hsmw.algDatDamen.AlgDatDamen;
import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.ChessBoard.MChessBoardMode;
import de.hsmw.algDatDamen.ChessBoard.Queen;
import de.hsmw.algDatDamen.tutorialHandler.ControlItem;
import de.hsmw.algDatDamen.tutorialHandler.Level;
import de.hsmw.algDatDamen.tutorialHandler.NPC;
import de.hsmw.algDatDamen.tutorialHandler.NPCTrack;
import de.hsmw.algDatDamen.tutorialHandler.Step;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;

// TODO muss noch getestet werden
public class Level6 extends Level {

    private final static String LEVEL_NAME = "Level 6 - Boreal";
    private final static String LEVEL_DESCRIPTION = "Lösung auf 8x8-Schachbrett ab der 5. Dame und bis zur 3. Dame, sowie Unterscheidung von richtigen und falschen Lösungsvorschlägen";

    public Level6(boolean console, Player player, Tutorial parent) {
        this(console, player, new Location(player.getWorld(), -182, -12, -20, 180, -5), new Location(player.getWorld(), -164, -12, -59), false,  parent);
    }

    public Level6(boolean console, Player player, Location startLocation, Location teleporterLocation, boolean completed, Tutorial parent) {
        // ruft den Konstruktor der Elternklasse Level auf
        super(console, LEVEL_NAME, LEVEL_DESCRIPTION, player, startLocation, teleporterLocation, completed, parent);
    }

    @Override
    protected void spawnVillager() {
        this.npc = new NPC(new Location(player.getWorld(), -184, -11, -24), console);
        this.npc.setType(Villager.Type.TAIGA);
        this.npc.spawn();
    }

    @Override
    protected void configureChessBoards() {
        chessBoards = new MChessBoard[1];
        chessBoards[0] = new MChessBoard(new Location(player.getWorld(), -184, -14, -45), 8, player);
    }

    @Override
    protected void initializeSteps() {
        currentStep = new Step(
            () -> {
                npc.playTrack(NPCTrack.NPC_601_INTRO);
            },
            () -> {}
        );

        // setupStep wird bis zum Ende durchgegeben und jeweils mit dem vorherigen
        // verknüpft
        Step setupStep = currentStep;
        
        // Erzeugung eines 8x8 Schachbretts
        // Setzen der ersten 5 Damen auf Schachbrett durch Computer
        // Erklärung des Levelabschnitts durch NPC
        // Setzen von Damen bis zur Problemlösung mit Hilfestellung durch NPC und Anzeige von bedrohten Feldern
        // sowie explodierenden falsch gesetzten Damen
        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_602_EXPLAIN_2);
                    npc.moveVillagerWithPathfinding(new Location(player.getWorld(), -185, -13, -37), 0.5);
                    chessBoards[0].solveBacktrackToRow(new Queen(), 5);
                    chessBoards[0].updateBoard();
                    chessBoards[0].verfyPieces(new Queen());
                    chessBoards[0].setMode(MChessBoardMode.TUTORIAL);
                    // Inventar leeren und neu füllen, falls Spieler Items vertauscht hat
                    setInventory();
                    player.getInventory().setItem(0, ControlItem.PLACE_QUEEN.getItemStack());
                    player.getInventory().setItem(1, ControlItem.SHOW_CARPET.getItemStack());
                },
                () -> {
                    // Inventar leeren & Chessboard despawnen
                    setInventory();
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].despawnChessBoard();
                    chessBoards[0].setMode(MChessBoardMode.INACTIVE);
                },
                unused -> {
                    if(chessBoards[0].isSolved()) {
                        npc.playTrackPositive();
                        return true;
                    } else return false;
                }
                ));
        setupStep = setupStep.getNext();

        // Löschen aller Damen
        // Erklärung des Levelabschnitts durch NPC
        // Setzen von Damen bis zur dritten Dame mit Hilfestellung durch NPC und Anzeige von bedrohten Feldern,
        // sowie explodierenden falsch gesetzten Damen
        setupStep.setNext(new Step(
            () -> {
                npc.playTrack(NPCTrack.NPC_603_EXPLAIN_3);
                chessBoards[0].removeAllPieces();
                chessBoards[0].removeAllPieces();
                chessBoards[0].updateBoard();
                chessBoards[0].setStateX(0);
                chessBoards[0].setStateY(0);
                chessBoards[0].setMode(MChessBoardMode.TUTORIAL);
                // Inventar leeren und neu füllen, falls Spieler Items vertauscht hat
                setInventory();
                player.getInventory().setItem(0, ControlItem.PLACE_QUEEN.getItemStack());
            },
            () -> {
                // Chessboard leeren
                chessBoards[0].removeAllPieces();
                chessBoards[0].updateBoard();
                chessBoards[0].setMode(MChessBoardMode.INACTIVE);
            },
            unused -> {
                    if (chessBoards[0].isSolved()) {
                        return true;
                    }

                    if (chessBoards[0].getPieces().size() > 2) {
                        setInventory();
                        if(!chessBoards[0].isAnimationRunning()) npc.playTrackPositive();
                        chessBoards[0].setMode(MChessBoardMode.INACTIVE);
                        chessBoards[0].animationPiece2Piece(AlgDatDamen.getInstance(), 1, new Queen());
                    }
                    return false;

                }));
        setupStep = setupStep.getNext();

        setupStep.setNext(new Step(
                () -> {
                    chessBoards[0].setMode(MChessBoardMode.INACTIVE);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].despawnAllPieces();
                    chessBoards[0].despawnChessBoard();
                    teleporter.setEnabled(true, true);
                    setInventory();
                    player.getInventory().setItem(4, ControlItem.NEXT_LEVEL.getItemStack());
                },
                () -> {
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].updateBoard();
                    chessBoards[0].setMode(MChessBoardMode.INACTIVE);
                    setInventory();
                    teleporter.setEnabled(false, true);
                }));
        setupStep = setupStep.getNext();

        // alle Steps in beide Richtungen miteinander verknüpfen
        currentStep.backLink();
    }

}
