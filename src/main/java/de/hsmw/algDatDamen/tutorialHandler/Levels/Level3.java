package de.hsmw.algDatDamen.tutorialHandler.Levels;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.tutorialHandler.Level;
import de.hsmw.algDatDamen.tutorialHandler.Step;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;
import net.kyori.adventure.text.Component;

public class Level3 extends Level {

    private final static String LEVEL_NAME = "Level 3 - Scandi Zwilling";
    private final static String LEVEL_DESCRIPTION = "Lösung mit und ohne Hilfestellung, sowie Vorgabe und Vergleich mehrerer Lösungsmöglichkeiten";
    private final static Component NPC_INTRO = Component.text("Aber jetzt sollst du mal dich versuchen. Hier ist ein 4x4 Brett. Setzte vier Damen so das keine eine andere bedroht.");

    public Level3(boolean console, Player player, Tutorial parent) {
        this(console, player, new Location(player.getWorld(), -75, -33, 103), parent);
    }

    public Level3(boolean console, Player player, Location startLocation, Tutorial parent) {
        this(console, player, startLocation, false, parent);
    }

    public Level3(boolean console, Player player, Location startLocation, boolean completed, Tutorial parent) {
        // ruft den Konstruktor der Elternklasse Level auf
        super(console, LEVEL_NAME, LEVEL_DESCRIPTION, player, startLocation, completed, parent, new Location(player.getWorld(), -88, -31, 83));
    }

    @Override
    protected void configureChessBoards() {
        chessBoards = new MChessBoard[2];
        chessBoards[0] = new MChessBoard(new Location(player.getWorld(), -76, -32, 81), 4, player);
        chessBoards[1] = new MChessBoard(new Location(player.getWorld(), -76, -32, 89), 4, player);
    }

    @Override
    protected void setInventory() {
        player.getInventory().clear();
        setControlItems();
        // TODO items für Interaktion hinzufügen
    }

    @Override
    protected void initializeSteps() {
        // Erzeugung eines 4x4 Schachbretts
        // Erklärung des Levelabschnitts durch NPC
        currentStep = new Step(
            () -> {
                // TODO Audio vom NPC abspielen lassen
                player.sendMessage(Component.textOfChildren(EMPTY_LINE, NPC_INTRO));
                chessBoards[0].spawnChessBoard();
                teleporter.setEnabled(true);
                // TODO evtl Verzögerung einbauen, sodass completed erst true gesetzt wird wenn der NPC fertig ist
            },
            () -> {
                // Chessboard despawnen
                chessBoards[0].despawnChessBoard();
                // TODO NPC zum Schweigen bringen
            }
        );

        // Setzen von 4 richtigen Damen mit bedrohten Feldern durch Lernenden
        // Löschen aller Damen von Schachbrett
        // Erzeugung zweier 4x4 Schachbretter
        // Erklärung des Levelabschnitts durch NPC
        // Anzeigen ähnlicher Lösungen auf beiden Schachbrettern
        // Entscheidung des Lernenden ob Lösungen gleich/gespiegelt/rotiert/verschieden sind
        // Löschen aller Damen und Löschen beider Schachbretter
        // Erzeugung eines 4x4 Schachbretts
        // Erklärung des Levelabschnitts durch NPC
        // Setzen von 4 richtigen Damen ohne bedrohte Felder durch Lernenden
        // Löschen aller Damen und Löschen des Schachbretts
    }
    
}
