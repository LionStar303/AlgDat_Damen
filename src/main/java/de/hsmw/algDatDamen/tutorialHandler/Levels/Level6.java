package de.hsmw.algDatDamen.tutorialHandler.Levels;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.ChessBoard.MChessBoardMode;
import de.hsmw.algDatDamen.ChessBoard.Queen;
import de.hsmw.algDatDamen.tutorialHandler.ControlItem;
import de.hsmw.algDatDamen.tutorialHandler.Level;
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
    protected void configureChessBoards() {
        chessBoards = new MChessBoard[1];
        chessBoards[0] = new MChessBoard(new Location(player.getWorld(), -185, -14, -46), 8, player);
    }

    @Override
    protected void initializeSteps() {
        // TODO alle Steps: NPC laufen lassen
        
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
                    chessBoards[0].solveBacktrackToRow(new Queen(), 5);
                    chessBoards[0].updateBoard();
                    chessBoards[0].setMode(MChessBoardMode.TUTORIAL);
                    // Inventar leeren und neu füllen, falls Spieler Items vertauscht hat
                    setInventory();
                    player.getInventory().setItem(0, ControlItem.PLACE_QUEEN.getItemStack());
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
                chessBoards[0].solveBacktrackToRow(new Queen(), 5);
                chessBoards[0].updateBoard();
                chessBoards[0].setMode(MChessBoardMode.LEVEL6_2);
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
                if(chessBoards[0].isSolved()) {
                    npc.playTrackPositive();
                    return true;
                } else return false;
            }
            ));
        setupStep = setupStep.getNext();

        // Lösen des restlichen Problems durch Computer
        setupStep.setNext(new Step(
            () -> {
                // Inventar leeren
                setInventory();
                chessBoards[0].setMode(MChessBoardMode.INACTIVE);
                chessBoards[0].playBacktrack(new Queen());
            },
            () -> {
                chessBoards[0].removeAllPieces();
                chessBoards[0].solveBacktrackToRow(new Queen(), 5);
            }
            ));
        setupStep = setupStep.getNext();

        // Löschen aller Damen und Löschen des Schachbretts
        setupStep.setNext(new Step(
            () -> {
                // Inventar leeren
                setInventory();
                chessBoards[0].setMode(MChessBoardMode.INACTIVE);
                chessBoards[0].removeAllPieces();
                chessBoards[0].despawnChessBoard();
            },
            () -> {}
            ));

            // alle Steps in beide Richtungen miteinander verknüpfen
            currentStep.backLink();
    }

}
