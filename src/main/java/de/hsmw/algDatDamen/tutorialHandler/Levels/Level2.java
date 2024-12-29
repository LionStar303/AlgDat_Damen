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

// TODO testen
public class Level2 extends Level {

    private final static String LEVEL_NAME = "Level 2 - Boot Camp";
    private final static String LEVEL_DESCRIPTION = "Erklärung des N-Damen-Problems";

    public Level2(boolean console, Player player, Tutorial parent) {
        this(console, player, new Location(player.getWorld(), -56, -37, 139, 90, -15), new Location(player.getWorld(), -76, -36, 119), parent);
    }

    public Level2(boolean console, Player player, Location startLocation, Location teleporterLocation, Tutorial parent) {
        this(console, player, startLocation, teleporterLocation, false, parent);
    }

    public Level2(boolean console, Player player, Location startLocation, Location teleporterLocation, boolean completed, Tutorial parent) {
        // ruft den Konstruktor der Elternklasse Level auf
        super(console, LEVEL_NAME, LEVEL_DESCRIPTION, player, startLocation, teleporterLocation, completed, parent);
    }

    @Override
    protected void configureChessBoards() {
        // 8x8, 4x4 und 3x3 Schachbretter erstellen
        chessBoards = new MChessBoard[3];
        chessBoards[0] = new MChessBoard(new Location(player.getWorld(), -74, -36, 128), 8, player, false);
        chessBoards[1] = new MChessBoard(new Location(player.getWorld(), -69, -36, 140), 4, player, false);
        chessBoards[2] = new MChessBoard(chessBoards[1].getOriginCorner(), 3, player, false);
    }

    @Override
    protected void initializeSteps() {
        // TODO alle Steps: NPC laufen lassen

        // Erklärung des N-Damen-Problems durch NPC
        currentStep = new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_201_INTRO);
                },
                () -> {}
                );

        // setupStep wird bis zum Ende durchgegeben und jeweils mit dem vorherigen
        // verknüpft
        Step setupStep = currentStep;

        // Erzeugung eines 8x8 Schachbretts
        // Setzen einer Dame auf Schachbrett durch Computer
        // Erklärung der Bedrohungen durch NPC
        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_202_EXPLAIN_THREATS_1);
                    chessBoards[0].spawnChessBoard();
                    chessBoards[0].addPiece(new Queen(4, 2));
                    chessBoards[0].updatePieces();
                },
                () -> {
                    // Dame entfernen und Chessboard despawnen
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].despawnChessBoard();
                }));
        setupStep = setupStep.getNext();

        // Anzeigen der Bedrohungen der Dame
        setupStep.setNext(new Step(
                () -> {
                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].updateCollisionCarpets();
                    npc.playTrack(NPCTrack.NPC_203_EXPLAIN_THREATS_2);
                },
                () -> {
                    chessBoards[0].setCollisionCarpets(false);
                    chessBoards[0].updateCollisionCarpets();}
                ));
        setupStep = setupStep.getNext();

        // Erklärung der Bedrohungen zwischen Damen durch NPC
        // Setzen einer weiteren Dame in bedrohtes Feld durch Computer
        // Erklärung des N-Damen-Problems durch NPC
        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_204_EXPLAIN_PROBLEM);
                    // platziere Dame auf Feld(4,5) welches von Dame(4,2) bedroht ist
                    chessBoards[0].addPiece(new Queen(4, 5));
                    chessBoards[0].updatePieces();
                },
                () -> {
                    chessBoards[0].removeLastPiece();
                    chessBoards[0].updatePieces();
                }));
        setupStep = setupStep.getNext();

        // Löschen der letzten Dame
        // Setzen einer weiteren Dame in nicht bedrohtes Feld durch Computer
        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_205_SOLVE);
                    // im vorherigen Schritt platzierte Dame entfernen
                    chessBoards[0].removeLastPiece();
                    // platziere Dame auf Feld(3,5) welches nicht bedroht ist
                    chessBoards[0].addPiece(new Queen(3, 5));
                    chessBoards[0].updatePieces();
                },
                () -> {
                    // neue Dame entfernen und falsche Dame wieder setzen
                    chessBoards[0].removeLastPiece();
                    chessBoards[0].addPiece(new Queen(4, 5));
                    chessBoards[0].updatePieces();
                }));
        setupStep = setupStep.getNext();

        // Setzen aller Damen und Lösen des Problems durch Computer
        // Erklärung der Lösung durch NPC
        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_206_EXPLAIN_SOLUTION);
                    chessBoards[0].animationSolve(new Queen());
                },
                () -> {
                    // neue Dame entfernen und alte Dame wieder setzen
                    chessBoards[0].removeAllPieces();
                    // vorher platzierte Königinnen wieder setzen
                    chessBoards[0].addPiece(new Queen(4, 2));
                    chessBoards[0].addPiece(new Queen(3, 5));
                    chessBoards[0].updatePieces();
                }));
        setupStep = setupStep.getNext();

        // Löschen aller Damen und Löschen des Schachbretts
        // Erzeugung eines 3x3 Schachbretts
        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_207_EXPLAIN_3X3_1);
                    // 8x8 Schachbrett löschen
                    chessBoards[0].despawnAllPieces();
                    chessBoards[0].despawnChessBoard(); // TODO ggf einfach weglassen
                    // 3x3 Schachbrett spawnen
                    chessBoards[2].spawnChessBoard();
                },
                () -> {
                    // 3x3 Brett löschen und gelöstes 8x8 Feld spawnen
                    chessBoards[2].despawnChessBoard();
                    chessBoards[0].spawnChessBoard();
                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].spawnAllPieces();
                }));
        setupStep = setupStep.getNext();

        // Setzen von 3 falschen Damen auf Schachbrett durch Computer
        // Erklärung von Problem mit 3x3 durch NPC
        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_208_EXPLAIN_3X3_2);
                    /*
                     * 3 Damen platzieren
                     * Q . Q
                     * . . .
                     * . Q .
                     */
                    chessBoards[2].addPiece(new Queen(0, 0));
                    chessBoards[2].addPiece(new Queen(2, 0));
                    chessBoards[2].addPiece(new Queen(1, 2));
                    chessBoards[2].updatePieces();
                },
                () -> {
                    // alle drei Damen löschen
                    chessBoards[2].removeAllPieces();
                }));
        setupStep = setupStep.getNext();

        // Löschen aller Damen und Löschen des Schachbretts
        // Erzeugung eines 4x4 Schachbretts
        // Setzen aller Damen und Lösen des Problems durch Computer
        // Erklärung der Lösung durch NPC
        // Erklärung von Notwendigkeit für mindestens 4x4 Schachbrett
        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_209_EXPLAIN_4x4_SOLUTION);
                    // 3x3 Brett entfernen
                    chessBoards[2].despawnAllPieces();
                    chessBoards[2].despawnChessBoard();
                    // 4x4 Brett spawnen und lösen, wurde evtl schon mal gelöst
                    chessBoards[1].spawnChessBoard();
                    if (!chessBoards[1].isSolved())
                        chessBoards[1].playBacktrack(new Queen());
                    chessBoards[1].updatePieces();
                },
                () -> {
                    // 4x4 Feld entfernen
                    chessBoards[1].despawnAllPieces();
                    chessBoards[1].despawnChessBoard();
                    // 3x3 Feld spawnen und Damen platzieren siehe voriger Schritt
                    chessBoards[2].spawnChessBoard();
                    chessBoards[2].spawnAllPieces();
                }));
        setupStep = setupStep.getNext();

        // Löschen aller Damen und Löschen des Schachbretts
        setupStep.setNext(new Step(
                () -> {
                    // 4x4 Brett samt Figuren entfernen
                    chessBoards[1].despawnAllPieces();
                    chessBoards[1].despawnChessBoard();
                    teleporter.setEnabled(true, true);
                    setInventory();
                    player.getInventory().setItem(4, ControlItem.NEXT_LEVEL.getItemStack());
                },
                () -> {
                    setInventory();
                    teleporter.setEnabled(false, true);
                    chessBoards[1].spawnChessBoard();
                    chessBoards[1].updatePieces();
                }));
        setupStep = setupStep.getNext();

        // alle Steps in beide Richtungen miteinander verknüpfen
        currentStep.backLink();
    }
}
