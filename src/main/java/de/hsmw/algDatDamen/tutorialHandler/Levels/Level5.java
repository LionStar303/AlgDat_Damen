package de.hsmw.algDatDamen.tutorialHandler.Levels;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.ChessBoard.MChessBoardMode;
import de.hsmw.algDatDamen.ChessBoard.Queen;
import de.hsmw.algDatDamen.tutorialHandler.ControlItem;
import de.hsmw.algDatDamen.tutorialHandler.Level;
import de.hsmw.algDatDamen.tutorialHandler.NPCTrack;
import de.hsmw.algDatDamen.tutorialHandler.Step;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;

// TODO testen
public class Level5 extends Level{

    private final static String LEVEL_NAME = "Level 5 - Amazonas";
    private final static String LEVEL_DESCRIPTION = "Lösung auf 4x4-Schachbrett mit normalen und invertierten Hilfestellungen";

    public Level5(boolean console, Player player, Tutorial parent) {
        this(console, player, new Location(player.getWorld(), -153, -19, 45, 140, -10), new Location(player.getWorld(), -188, -20, -7), false,  parent);
    }

    public Level5(boolean console, Player player, Location startLocation, Location teleporterLocation, boolean completed, Tutorial parent) {
        // ruft den Konstruktor der Elternklasse Level auf
        super(console, LEVEL_NAME, LEVEL_DESCRIPTION, player, startLocation, teleporterLocation, completed, parent);
    }

    @Override
    protected void configureChessBoards() {
        chessBoards = new MChessBoard[2];
        chessBoards[0] = new MChessBoard(new Location(player.getWorld(), -186, -19, 15), 6, player);
        chessBoards[0].setCollisionCarpets(false);

        chessBoards[1] = new MChessBoard(new Location(player.getWorld(), -186, -19, 15), 6, player);
        chessBoards[1].setWhiteFieldMaterial(Material.BLUE_CONCRETE);
        chessBoards[1].setBlackFieldMaterial(Material.YELLOW_CONCRETE);
        chessBoards[1].setCollisionCarpets(false);
    }

    @Override
    protected void initializeSteps() {
        // TODO alle Steps: NPC laufen lassen
        
        // Erzeugung eines 4x4 Schachbretts
        // Erklärung des Levelabschnitts durch NPC
        currentStep = new Step(
            () -> {
                npc.playTrack(NPCTrack.NPC_501_INTRO);
            },
            () -> {}
        );

        // setupStep wird bis zum Ende durchgegeben und jeweils mit dem vorherigen
        // verknüpft
        Step setupStep = currentStep;

        // Setzen von 4 richtigen Damen mit bedrohten Feldern durch Lernenden
        setupStep.setNext(new Step(
                () -> {
                    chessBoards[0].spawnChessBoard();
                    chessBoards[0].setMode(MChessBoardMode.TUTORIAL);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].addPiece(new Queen(0,0));
                    chessBoards[0].updateBoard();
                    chessBoards[0].verfyPieces(new Queen());
                    // Inventar leeren und neu füllen, falls Spieler Items vertauscht hat
                    setInventory();
                    player.getInventory().setItem(0, ControlItem.PLACE_QUEEN.getItemStack());
                },
                () -> {
                    // Inventar leeren & Chessboard despawnen
                    setInventory();
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


        setupStep.setNext(new Step(
                () -> {
                    chessBoards[0].setMode(MChessBoardMode.INACTIVE);
                    chessBoards[0].despawnChessBoard();
                    chessBoards[1].setMode(MChessBoardMode.TUTORIAL);
                    chessBoards[1].removeAllPieces();
                    chessBoards[1].addPiece(new Queen(0,0));
                    chessBoards[1].updateBoard();
                    chessBoards[1].verfyPieces(new Queen());
                    npc.playTrack(NPCTrack.NPC_502_EXPLAIN_1);

                    // Inventar leeren und neu füllen, falls Spieler Items vertauscht hat
                    setInventory();
                    player.getInventory().setItem(0, ControlItem.PLACE_QUEEN.getItemStack());
                },
                () -> {
                    // Chessboard despawnen
                    chessBoards[1].despawnChessBoard();
                    chessBoards[1].setMode(MChessBoardMode.INACTIVE);
                },
                unused -> {
                    if(chessBoards[1].isSolved()) {
                        npc.playTrackPositive();
                        return true;
                    } else return false;
                }
                ));
        setupStep = setupStep.getNext();

        // Löschen des Schachbretts
        setupStep.setNext(new Step(
            () -> {
                chessBoards[1].despawnChessBoard();
                // teleport item geben
                teleporter.setEnabled(true);
                // Inventar leeren und teleport item geben
                setInventory();
                player.getInventory().setItem(4, ControlItem.NEXT_LEVEL.getItemStack());
            },
            () -> {
                // Schachbrett wieder spawnen
                setInventory();
                teleporter.setEnabled(false);
            }
            ));

        // alle Steps in beide Richtungen miteinander verknüpfen
        currentStep.backLink();
    }
    
}
