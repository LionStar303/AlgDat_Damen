package de.hsmw.algDatDamen.tutorialHandler.Levels;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.tutorialHandler.ControlItem;
import de.hsmw.algDatDamen.tutorialHandler.Level;
import de.hsmw.algDatDamen.tutorialHandler.NPCTrack;
import de.hsmw.algDatDamen.tutorialHandler.Step;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;

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
        chessBoards = new MChessBoard[0];
        chessBoards[0] = new MChessBoard(new Location(player.getWorld(), -171, -19, 25), 4, player);
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
                    npc.playTrack(NPCTrack.NPC_502_EXPLAIN_1);


                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].setActive(true);
                    // Inventar leeren und neu füllen, falls Spieler Items vertauscht hat
                    setInventory();
                    player.getInventory().setItem(0, ControlItem.PLACE_QUEEN.getItemStack());
                },
                () -> {
                    // Chessboard despawnen
                    chessBoards[0].despawnChessBoard();
                }
                ));
        setupStep = setupStep.getNext();
    }
    
}
