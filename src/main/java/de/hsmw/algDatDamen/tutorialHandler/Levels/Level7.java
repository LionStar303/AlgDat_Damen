package de.hsmw.algDatDamen.tutorialHandler.Levels;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.AlgDatDamen;
import de.hsmw.algDatDamen.ChessBoard.Knight;
import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.ChessBoard.MChessBoardMode;
import de.hsmw.algDatDamen.ChessBoard.Piece;
import de.hsmw.algDatDamen.ChessBoard.Queen;
import de.hsmw.algDatDamen.ChessBoard.Superqueen;
import de.hsmw.algDatDamen.tutorialHandler.ControlItem;
import de.hsmw.algDatDamen.tutorialHandler.Level;
import de.hsmw.algDatDamen.tutorialHandler.NPCTrack;
import de.hsmw.algDatDamen.tutorialHandler.Step;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;

/* 
### Level 4 - _erster versuch_:
- Startpunkt `-127 -8 -68`
- Schachbrett `10x10 -128 -8 -98` - wofür ist die Insel da?
- Teleporter `-`
*/
// TODO muss getestet werden
public class Level7 extends Level {

    // TODO LEVEL_NAME & LEVEL_DESCRIPTION
    private final static String LEVEL_NAME = "Level 7 - TODO:";
    private final static String LEVEL_DESCRIPTION = "Zeigen der Schwierigkeit auf einem großen Schachbrett, sowie Zeigen von verschiedenen Algorithmen zu Lösungserleichterung und Zeigen der Schrittfolge des Backtracking-Algorithmus";

    public Level7(boolean console, Player player, Tutorial parent) {
        this(console, player, new Location(player.getWorld(), -127, -7, -68),
                new Location(player.getWorld(), -114, -7, -89), parent);
    }

    public Level7(boolean console, Player player, Location startLocation, Location teleporterLocation,
            Tutorial parent) {
        this(console, player, startLocation, teleporterLocation, false, parent);
    }

    public Level7(boolean console, Player player, Location startLocation, Location teleporterLocation,
            boolean completed, Tutorial parent) {
        // ruft den Konstruktor der Elternklasse Level auf
        super(console, LEVEL_NAME, LEVEL_DESCRIPTION, player, startLocation, teleporterLocation, completed, parent);
    }

    @Override
    protected void configureChessBoards() {
        chessBoards = new MChessBoard[1];
        chessBoards[0] = new MChessBoard(new Location(player.getWorld(), -128, -8, -98), 10, player);
    }

    @Override
    protected void initializeSteps() {

        // Erzeugung eines 8x8 Schachbretts
        currentStep = new Step(
                () -> {
                    // TODO playTrack anpassen nach anpassung
                    npc.playTrack(NPCTrack.NPC_701_OLD); // npc.playTrack(NPCTrack.NPC_702_KNIGHT_1);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].updateBoard();
                    chessBoards[0].setCollisionCarpets(false);

                    chessBoards[0].spawnPiece(new Knight(5, 5));
                    setInventory();
                },
                () -> {
                    setInventory();
                });
        // setupStep wird bis zum Ende durchgegeben und jeweils mit dem vorherigen
        // verknüpft
        Step setupStep = currentStep;

        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_703_KNIGHT_2); // npc.playTrack(NPCTrack.NPC_702_KNIGHT_1);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].updateBoard();
                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].spawnPiece(new Knight(5, 5));
                    setInventory();
                },
                () -> {
                    setInventory();
                }
        // Step ist complete wenn das Schachbrett gelöst ist, oder 3 min abgelaufen
        // sind.

        ));

        setupStep = setupStep.getNext();

        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_704_KNIGHT_AND_QUEEN_1);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].addPiece(new Knight(5, 5));
                    chessBoards[0].addPiece(new Queen(4, 3));
                    chessBoards[0].updateBoard();
                    setInventory();
                },
                () -> {
                    setInventory();
                }));
        setupStep = setupStep.getNext();

        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_705_KNIGHT_AND_QUEEN_2);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].updateBoard();

                    chessBoards[0].setMode(MChessBoardMode.NORMAL);
                    chessBoards[0].setActive(true); // TODO an mode anpassen

                    setInventory();
                    player.getInventory().setItem(0, ControlItem.PLACE_QUEEN.getItemStack());
                    player.getInventory().setItem(1, ControlItem.PLACE_KNIGHT.getItemStack());
                    player.getInventory().setItem(2, ControlItem.SHOW_CARPET.getItemStack());

                },
                () -> {
                    setInventory();

                    chessBoards[0].setMode(MChessBoardMode.INACTIVE);
                    chessBoards[0].setActive(false); // TODO an mode anpassen
                },
                // Step ist complete wenn das Schachbrett gelöst ist

                unused -> {
                    int Kcount = 0;
                    int Qcount = 0;
                    if (console)
                        System.out.println("Condition: chessBoards[0].getPieces().size() = "
                                + chessBoards[0].getPieces().size() + " < 2");
                    if (chessBoards[0].getPieces().size() < 2)
                        return false;
                    for (Piece p : chessBoards[0].getPieces()) {
                        if (chessBoards[0].checkCollision(p)) {
                            if (console) {
                                System.out.println("Collision detected with piece: " + p);
                            }

                            return false;
                        }
                        if (p.getLetter() == 'K')
                            Kcount++;
                        if (p.getLetter() == 'Q')
                            Qcount++;
                        if (console) {
                            System.out.println("K piece found. Updated Kcount: " + Kcount);
                            System.out.println("Q piece found. Updated Qcount: " + Qcount);
                        }
                    }
                    if (Kcount >= 1 && Qcount >= 1) {
                        npc.playTrackPositive();
                        return true;
                    }
                    return false;
                }));

        setupStep = setupStep.getNext();

        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_706_SUPERQUEEN_INTRO); // npc.playTrack(NPCTrack.NPC_702_KNIGHT_1);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setCollisionCarpets(false);
                    chessBoards[0].updateBoard();
                    chessBoards[0].spawnPiece(new Queen(5, 5));

                    setInventory();
                },
                () -> {
                    setInventory();
                    chessBoards[0].removeAllPieces();
                }));

        // Lernenden
        setupStep = setupStep.getNext();

        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_707_SUPERQUEEN_EXPLAIN); // npc.playTrack(NPCTrack.NPC_702_KNIGHT_1);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setCollisionCarpets(false);
                    chessBoards[0].updateBoard();
                    chessBoards[0].spawnPiece(new Queen(5, 5));

                    setInventory();
                },
                () -> {
                    setInventory();
                    chessBoards[0].removeAllPieces();
                }));
        setupStep = setupStep.getNext();

        setupStep.setNext(new Step(
                () -> {
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setCollisionCarpets(false);
                    chessBoards[0].updateBoard();
                    chessBoards[0].spawnPiece(new Superqueen(5, 5));

                    // player.getWorld().strikeLightning(new Location(player.getWorld(), -123, -8,
                    // -93));
                    player.getWorld().strikeLightning(chessBoards[0].getOriginCorner().clone().add(5.5, 0, 5.5));
                    setInventory();
                },
                () -> {
                    setInventory();
                    chessBoards[0].removeAllPieces();
                }));
        setupStep = setupStep.getNext();

        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_708_SUPERQUEEN_MOVE_1);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].addPiece(new Superqueen(5, 5));
                    chessBoards[0].updateBoard();
                    setInventory();
                },
                () -> {
                    setInventory();
                    chessBoards[0].removeAllPieces();
                }));
        setupStep = setupStep.getNext();

        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_709_SUPERQUEEN_MOVE_2);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setCollisionCarpets(false);
                    chessBoards[0].updateBoard();

                    chessBoards[0].setMode(MChessBoardMode.NORMAL);
                    chessBoards[0].setActive(true); // TODO an mode anpassen

                    setInventory();
                    player.getInventory().setItem(0, ControlItem.PLACE_SUPERQUEEN.getItemStack());
                    player.getInventory().setItem(1, ControlItem.SHOW_CARPET.getItemStack());

                    setInventory();
                },
                () -> {
                    setInventory();
                    chessBoards[0].setMode(MChessBoardMode.INACTIVE);
                    chessBoards[0].setActive(false); // TODO an mode anpassen
                },
                // Step ist complete wenn das Schachbrett gelöst ist
                unused -> {
                    MChessBoard cb = chessBoards[0].clone();
                    cb.verfyPieces(new Superqueen());
                    if (cb.getPieces().size() > 3) {
                        chessBoards[0].animationPiece2Piece(AlgDatDamen.getInstance(), 1, new Superqueen());
                        chessBoards[0].setMode(MChessBoardMode.INACTIVE);
                        chessBoards[0].setActive(false); // TODO an mode anpassen
                    }

                    if (chessBoards[0].isSolved()) {
                        return true;
                    }
                    return false;
                }));
        setupStep = setupStep.getNext();

        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_710_END);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].updateBoard();
                    teleporter.setEnabled(true, true);
                    setInventory();
                    player.getInventory().setItem(4, ControlItem.NEXT_LEVEL.getItemStack());
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
