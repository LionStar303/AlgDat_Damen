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
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

// TODO testen
public class Level3 extends Level {

    private final static String LEVEL_NAME = "Level 3 - Scandi Zwilling";
    private final static String LEVEL_DESCRIPTION = "Lösung mit und ohne Hilfestellung, sowie Vorgabe und Vergleich mehrerer Lösungsmöglichkeiten";

    public Level3(boolean console, Player player, Tutorial parent) {
        this(console, player, new Location(player.getWorld(), -76, -32, 103, 180, 0),
                new Location(player.getWorld(), -88, -32, 83), parent);
    }

    public Level3(boolean console, Player player, Location startLocation, Location teleporterLocation,
            Tutorial parent) {
        this(console, player, startLocation, teleporterLocation, false, parent);
    }

    public Level3(boolean console, Player player, Location startLocation, Location teleporterLocation,
            boolean completed, Tutorial parent) {
        // ruft den Konstruktor der Elternklasse Level auf
        super(console, LEVEL_NAME, LEVEL_DESCRIPTION, player, startLocation, teleporterLocation, completed, parent);
    }

    @Override
    protected void configureChessBoards() {
        chessBoards = new MChessBoard[2];
        chessBoards[0] = new MChessBoard(new Location(player.getWorld(), -76, -32, 89), 4, player);
        chessBoards[1] = new MChessBoard(new Location(player.getWorld(), -76, -32, 81), 4, player);
    }

    @Override
    protected void initializeSteps() {
        // TODO alle Steps: NPC laufen lassen
        
        // Erzeugung eines 4x4 Schachbretts
        // Erklärung des Levelabschnitts durch NPC
        currentStep = new Step(
            () -> {
                npc.playTrack(NPCTrack.NPC_301_INTRO);
                chessBoards[0].spawnChessBoard();
            },
            () -> {
                // Chessboard despawnen
                chessBoards[0].despawnChessBoard();
            }
        );

        // setupStep wird bis zum Ende durchgegeben und jeweils mit dem vorherigen
        // verknüpft
        Step setupStep = currentStep;

        // Setzen von 4 richtigen Damen mit bedrohten Feldern durch Lernenden
        setupStep.setNext(new Step(
                () -> {
                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].setMode(MChessBoardMode.NORMAL); 
                    chessBoards[0].setActive(true);
                    // Inventar leeren und neu füllen, falls Spieler Items vertauscht hat
                    setInventory();
                    player.getInventory().setItem(0, ControlItem.PLACE_QUEEN.getItemStack());
                },
                () -> {
                    // Schachbrett leeren
                    chessBoards[0].setCollisionCarpets(false);
                    chessBoards[0].setMode(MChessBoardMode.INACTIVE); 
                    chessBoards[0].setActive(false);
                    chessBoards[0].removeAllPieces();
                    // Inventar auf Urspungszustand zurücksetzen
                    setInventory();
                },
                // Step ist complete wenn das Schachbrett gelöst ist
                unused -> {
                    if(chessBoards[0].isSolved()) {
                        npc.playTrackPositive();
                        return true;
                    } else return false;
                }
                ));
        setupStep = setupStep.getNext();

        // Löschen aller Damen von Schachbrett
        // Erzeugung zweier 4x4 Schachbretter
        // Erklärung des Levelabschnitts durch NPC
        // Anzeigen ähnlicher Lösungen auf beiden Schachbrettern
        // Entscheidung des Lernenden ob Lösungen gleich/gespiegelt/rotiert/verschieden sind
        setupStep.setNext(new Step(
            () -> {
                // PLACE_QUEEN item wegnehmen
                setInventory();
                // Schachbrett zurücksetzen und zweites spawnen
                chessBoards[0].setMode(MChessBoardMode.INACTIVE); 
    	        chessBoards[0].setActive(false);
                chessBoards[0].setCollisionCarpets(false);
                chessBoards[0].removeAllPieces();
                chessBoards[1].spawnChessBoard();
                npc.playTrack(NPCTrack.NPC_302_DIFFERENT_SOLUTIONS);
                // gespiegelte Lösungen spawnen
                chessBoards[0].addPiece(new Queen(0, 1));
                chessBoards[0].addPiece(new Queen(1, 3));
                chessBoards[0].addPiece(new Queen(2, 0));
                chessBoards[0].addPiece(new Queen(3, 2));
                chessBoards[1].addPiece(new Queen(0, 2));
                chessBoards[1].addPiece(new Queen(1, 0));
                chessBoards[1].addPiece(new Queen(2, 3));
                chessBoards[1].addPiece(new Queen(3, 1));
                chessBoards[0].updatePieces();
                chessBoards[1].updatePieces();
            },
            () -> {
                // beide Bretter leeren und chessboard 1 despawnen
                chessBoards[0].removeAllPieces();
                chessBoards[1].removeAllPieces();
                chessBoards[1].despawnChessBoard();
            },
            // Step ist complete wenn Eingabe "spiegel" enthält
            unused -> {
                if(latestPlayerInput.toLowerCase().contains("gespiegel")) {
                    player.sendMessage(Component.text("richtig, die beiden Lösungen unterscheiden sich durch ihre Spiegelung", NamedTextColor.GREEN));
                    return true;
                } else {
                    player.sendMessage(Component.text("das ist leider die falsche Antwort", NamedTextColor.RED));
                    return false;
                }
            }
            ));
        setupStep = setupStep.getNext();

        // Löschen aller Damen und Löschen beider Schachbretter
        // Erzeugung eines 4x4 Schachbretts
        // Erklärung des Levelabschnitts durch NPC
        // Setzen von 4 richtigen Damen ohne bedrohte Felder durch Lernenden
        setupStep.setNext(new Step(
            () -> {
                // erstes chessboard despawnen und beide leeren
                chessBoards[0].removeAllPieces();
                chessBoards[0].despawnChessBoard();
                chessBoards[1].removeAllPieces();
                chessBoards[1].updateBoard();

                // zweites chessboard für spieler interaktion vorbereiten
                chessBoards[1].setMode(MChessBoardMode.NORMAL); 
                chessBoards[1].setCollisionCarpets(false);
                npc.playTrack(NPCTrack.NPC_303_SECOND_TASK);
                // Inventar leeren und neu füllen, falls Spieler Items vertauscht hat
                setInventory();
                player.getInventory().setItem(0, ControlItem.PLACE_QUEEN.getItemStack());
            },
            () -> {
                // Schachbrett leeren
                chessBoards[1].setMode(MChessBoardMode.INACTIVE); 
                
                chessBoards[0].removeAllPieces();
                chessBoards[0].spawnChessBoard();
                // Inventar auf Urspungszustand zurücksetzen
                setInventory();
            },
            // Step ist complete wenn das Schachbrett gelöst ist
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
                // Inventar leeren
                setInventory();
                chessBoards[1].despawnChessBoard();
                teleporter.setEnabled(true);
                // teleport item geben
                player.getInventory().setItem(4, ControlItem.NEXT_LEVEL.getItemStack());
            },
            () -> {
                // Schachbrett wieder spawnen
                setInventory();
                teleporter.setEnabled(false);
                chessBoards[1].spawnChessBoard();
            }
            ));

        // alle Steps in beide Richtungen miteinander verknüpfen
        currentStep.backLink();
    }

}
