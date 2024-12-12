package de.hsmw.algDatDamen.tutorialHandler.Levels.level1;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.ChessBoard.Queen;
import de.hsmw.algDatDamen.tutorialHandler.Level;
import de.hsmw.algDatDamen.tutorialHandler.Step;
import net.kyori.adventure.text.Component;

public class Level1 extends Level{

    private final static String DESCRIPTION = "Erklärung des Aufbaus eines Schachbretts, sowie der Damenfigur und deren Bewegungsmuster";
    private final static String NPC_EXPLAIN_CHESSBOARD = "Aber was soll das heißen fragst du? Nun gut zuerst, hier ist ein Schachbrett. Wir betrachten das Problem in unterschiedlichen Variablen. Das hier ist ein [Größe des Bretts] Schachbrett, es ist unterteilt in bikolorierten Quadraten auf denen sich pro Quadrat nur eine Figur befinden kann.";
    private final static String NPC_EXPLAIN_QUEEN = "Im Schach gibt es sechs verschiedene Arten von Figuren. Aber heute sollen uns zwei reichen. Diese Figur nennt man Dame.";
    private final static String NPC_EXPLAIN_MOVEMENT = "Die Dame kann sich beliebig weit in alle Diagonale und Graden bewegen.";
    private final static String NPC_EXPLAIN_THREATS = "Wenn sich eine andere Figur im Bewegungsbereich befindet, ist sie bedroht und kann von der sich bewegenden Figur geschlagen werden.";

    public Level1(Player player) {
        // Schachbrett für Level 1 erstellen
        // TODO location festlegen
        this(new MChessBoard(new Location(player.getWorld(), -40, -44, 128), 8, player), player);
    }

    public Level1(MChessBoard chessBoard, Player player) {
        this(chessBoard, player, new Location(player.getWorld(), -30, -43, 143));
    }

    public Level1(MChessBoard chessBoard, Player player, Location startLocation) {
        this(chessBoard, player, startLocation, false);
    }

    public Level1(MChessBoard chessBoard, Player player, Location startLocation, boolean completed) {
        // ruft den Konstruktor der Elternklasse Level auf
        super("Level 1 - Einführung", DESCRIPTION, chessBoard, player, startLocation, completed);
    }

    protected void setInventory() {
        player.getInventory().clear();
        setControlItems();
    }

    @Override
    public void initializeSteps() {
        // Step 1 - Erklärung des Schachbretts durch NPC
        currentStep = new Step(
            () -> {
                // TODO Audio vom NPC abspielen lassen
                player.sendMessage(Component.text(NPC_EXPLAIN_CHESSBOARD));
            },
            null // TODO NPC zum Schweigen bringen
        );

        // setupStep wird bis zum Ende durchgegeben und jeweils mit dem vorherigen verknüpft
        Step setupStep = currentStep;

        // Setzen einer Dame auf Schachbrett durch Computer
        setupStep.setNext(new Step(
            () -> {
                // spawne Queen auf Feld (3,2)
                chessBoard.addPiece(new Queen(3,2));
                chessBoard.updatePieces();
            },
            () -> {
                // entferne alle Figuren
                chessBoard.clearBoard();
                chessBoard.updatePieces();
            }
        ));
        setupStep = setupStep.getNext();

        // Erklärung der Dame durch NPC
        setupStep.setNext(new Step(
            () -> {
                // TODO Audio vom NPC abspielen lassen
                player.sendMessage(Component.text(NPC_EXPLAIN_QUEEN));
            },
            null // TODO NPC zum Schweigen bringen
        ));
        setupStep = setupStep.getNext();
    
        // Anzeigen der Bewegungsmuster der Dame
        setupStep.setNext(new Step(
            // Teppiche für Bewegungsmuster spawnen
            () -> chessBoard.spawnCollisionCarpets(),
            // Teppiche für Bewegungsmuster spawnen
            () -> chessBoard.despawnCollisionCarpets()
        ));
        setupStep = setupStep.getNext();

        // Erklärung der Bewegungsmuster durch NPC
        setupStep.setNext(new Step(
            () -> {
                // TODO Audio vom NPC abspielen lassen
                player.sendMessage(Component.text(NPC_EXPLAIN_MOVEMENT));
            },
            null // TODO NPC zum Schweigen bringen
        ));
        setupStep = setupStep.getNext();

        // Setzen einer weiteren Dame durch Computer
        setupStep.setNext(new Step(
            () -> {
                // Bewegungsmuster entfernen (wird im nächsten Schritt wieder erzeugt)
                chessBoard.despawnCollisionCarpets();
                // spawne Queen auf Feld (5,3)
                chessBoard.addPiece(new Queen(5,3));
                chessBoard.updatePieces();
            },
            () -> {
                // entferne zuletzt gesetzte Dame von Feld (5,3)
                chessBoard.removeLastQueen();
                chessBoard.updatePieces();
                chessBoard.spawnCollisionCarpets();
            }
        ));
        setupStep = setupStep.getNext();

        // Anzeigen der Bedrohungen der Damen
        setupStep.setNext(new Step(
            // Teppiche für Bewegungsmuster spawnen
            () -> chessBoard.spawnCollisionCarpets(),
            // Teppiche für Bewegungsmuster spawnen
            () -> chessBoard.despawnCollisionCarpets()
        ));
        setupStep = setupStep.getNext();

        // Erklärung der Bedrohungen durch NPC
        setupStep.setNext(new Step(
            () -> {
                // TODO Audio vom NPC abspielen lassen
                player.sendMessage(Component.text(NPC_EXPLAIN_THREATS));
            },
            null // TODO NPC zum Schweigen bringen
        ));
        setupStep = setupStep.getNext();

        // Löschen aller Damen von Schachbrett
        setupStep.setNext(new Step(
            // alle Figuren entfernen
            () -> chessBoard.despawnAllPieces(),
            // alle Figuren spawnen
            () -> chessBoard.spawnAllPieces()
        ));

        // TODO steps richtig verknüpfen
    }

    @Override
    public void start() {
        super.start();
        // alle Schritte erzeugen
        initializeSteps();
        // ersten Schritt starten
        currentStep.start();
    }

}
