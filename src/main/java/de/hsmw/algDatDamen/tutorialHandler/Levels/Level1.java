package de.hsmw.algDatDamen.tutorialHandler.Levels;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.ChessBoard.Queen;
import de.hsmw.algDatDamen.tutorialHandler.ControlItem;
import de.hsmw.algDatDamen.tutorialHandler.Level;
import de.hsmw.algDatDamen.tutorialHandler.NPCTrack;
import de.hsmw.algDatDamen.tutorialHandler.Step;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;

public class Level1 extends Level {

    private final static String LEVEL_NAME = "Level 1 - Einführung";
    private final static String LEVEL_DESCRIPTION = "Erklärung des Aufbaus eines Schachbretts, sowie der Damenfigur und deren Bewegungsmuster";

    public Level1(boolean console, Player player, Tutorial parent) {
        this(console, player, new Location(player.getWorld(), -17, -44, 144, 150, 0),
                new Location(player.getWorld(), -38, -43, 140), parent);
    }

    public Level1(boolean console, Player player, Location startLocation, Location teleporterLocation,
            Tutorial parent) {
        this(console, player, startLocation, teleporterLocation, false, parent);
    }

    public Level1(boolean console, Player player, Location startLocation, Location teleporterLocation,
            boolean completed, Tutorial parent) {
        // ruft den Konstruktor der Elternklasse Level auf
        super(console, LEVEL_NAME, LEVEL_DESCRIPTION, player, startLocation, teleporterLocation, completed, parent);
    }

    @Override
    /*
     * erstellt alle für das Level benötigten Schachbretter und speichert diese in
     * chessBoards
     */
    protected void configureChessBoards() {
        // 8x8 Schachbrett für Level 1 erstellen
        chessBoards = new MChessBoard[1];
        chessBoards[0] = new MChessBoard(new Location(player.getWorld(), -28, -45, 130), 8, player, false);
    }

    @Override
    public void initializeSteps() {
        // Step 1 - Erklärung des Schachbretts durch NPC
        currentStep = new Step(
                () -> {
                    chessBoards[0].spawnChessBoard();
                    npc.playTrack(NPCTrack.NPC_101_EXPLAIN_CHESSBOARD);
                    npc.moveVillagerWithPathfinding(new Location(player.getWorld(), -19, -44, 136), 0.5);
                },
                () -> {
                    chessBoards[0].despawnChessBoard();
                });

        // setupStep wird bis zum Ende durchgegeben und jeweils mit dem vorherigen
        // verknüpft
        Step setupStep = currentStep;

        // Setzen einer Dame auf Schachbrett durch Computer
        // Erklärung der Dame durch NPC
        setupStep.setNext(new Step(
                () -> {
                    // spawne Queen auf Feld (3,2)
                    chessBoards[0].addPiece(new Queen(3, 2));
                    chessBoards[0].updatePieces();
                    npc.playTrack(NPCTrack.NPC_102_EXPLAIN_QUEEN);
                },
                () -> {
                    // entferne alle Figuren
                    chessBoards[0].clearBoard();
                    chessBoards[0].updatePieces();
                }));
        setupStep = setupStep.getNext();

        // Anzeigen der Bewegungsmuster der Dame
        // Erklärung der Bewegungsmuster durch NPC
        setupStep.setNext(new Step(
                () -> {
                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].updateCollisionCarpets();
                    npc.playTrack(NPCTrack.NPC_103_EXPLAIN_MOVEMENT);
                    npc.moveVillagerWithPathfinding(new Location(player.getWorld(), -27, -44, 128), 0.5);
                },
                () -> {
                    chessBoards[0].setCollisionCarpets(false);
                    chessBoards[0].updateCollisionCarpets();
                }));
        setupStep = setupStep.getNext();

        // Setzen einer weiteren Dame durch Computer
        // Anzeigen der Bedrohungen der Damen
        // Erklärung der Bedrohungen durch NPC
        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_104_EXPLAIN_THREATS);
                    // spawne Queen auf Feld (6,5)
                    chessBoards[0].addPiece(new Queen(6, 5));
                    chessBoards[0].updateBoard();
                    npc.moveVillagerWithPathfinding(new Location(player.getWorld(), -24, -44, 139), 0.5);
                },
                () -> {
                    // entferne zuletzt gesetzte Dame von Feld (6,5)
                    chessBoards[0].removeLastQueen();
                    chessBoards[0].updateBoard();
                }));
        setupStep = setupStep.getNext();

        // markieren des Bewegungsmusters der Dame
        setupStep.setNext(new Step(
                () -> {
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setCollisionCarpets(false);
                    chessBoards[0].addPiece(new Queen(2, 0));
                    chessBoards[0].updateBoard();
                    setInventory();
                    player.getInventory().setItem(1, ControlItem.SPAWN_CARPET.getItemStack());
                    npc.playTrack(NPCTrack.NPC_105_MOVEMENT_MARKING);
                },
                () -> {
                    setInventory();
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setCollisionCarpets(true);
                    // spawne Queen auf Feld (6,5) und Feld(3,2)
                    chessBoards[0].addPiece(new Queen(3, 2));
                    chessBoards[0].addPiece(new Queen(6, 5));
                    chessBoards[0].updateBoard();
                },
                unused -> {
                    if (chessBoards[0].checkUserCarpets()) {
                        npc.playTrackPositive();
                        return true;
                    } else return false;
                }));
        setupStep = setupStep.getNext();

        // Löschen aller Damen von Schachbrett
        setupStep.setNext(new Step(
                // alle Figuren entfernen
                () -> {
                    chessBoards[0].despawnAllPieces();
                    chessBoards[0].despawnChessBoard();
                    teleporter.setEnabled(true, true);
                    setInventory();
                    player.getInventory().setItem(4, ControlItem.NEXT_LEVEL.getItemStack());
                    npc.moveVillagerWithPathfinding(new Location(player.getWorld(), -33, -42, 137), 0.5);
                },
                // alle Figuren spawnen
                () -> {
                    setInventory();
                    teleporter.setEnabled(false, true);
                    chessBoards[0].spawnChessBoard();
                    chessBoards[0].spawnAllPieces();
                }));

        // alle Steps in beide Richtungen miteinander verknüpfen
        currentStep.backLink();
    }

}
