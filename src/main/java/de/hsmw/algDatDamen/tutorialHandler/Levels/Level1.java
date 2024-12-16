package de.hsmw.algDatDamen.tutorialHandler.Levels;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.ChessBoard.Queen;
import de.hsmw.algDatDamen.tutorialHandler.Level;
import de.hsmw.algDatDamen.tutorialHandler.Step;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;
import net.kyori.adventure.text.Component;

public class Level1 extends Level {

    private final static String LEVEL_NAME = "Level 1 - Einführung";
    private final static String LEVEL_DESCRIPTION = "Erklärung des Aufbaus eines Schachbretts, sowie der Damenfigur und deren Bewegungsmuster";
    private final static Component NPC_EXPLAIN_CHESSBOARD = Component.text(
            "Aber was soll das heißen fragst du? Nun gut zuerst, hier ist ein Schachbrett. Wir betrachten das Problem in unterschiedlichen Variablen. Das hier ist ein 8*8 Schachbrett, es ist unterteilt in bikolorierten Quadraten auf denen sich pro Quadrat nur eine Figur befinden kann.");
    private final static Component NPC_EXPLAIN_QUEEN = Component.text(
            "Im Schach gibt es sechs verschiedene Arten von Figuren. Aber heute sollen uns zwei reichen. Diese Figur nennt man Dame.");
    private final static Component NPC_EXPLAIN_MOVEMENT = Component
            .text("Die Dame kann sich beliebig weit in alle Diagonale und Graden bewegen.");
    private final static Component NPC_EXPLAIN_THREATS = Component.text(
            "Wenn sich eine andere Figur im Bewegungsbereich befindet, ist sie bedroht und kann von der sich bewegenden Figur geschlagen werden.");

    public Level1(boolean console, Player player, Tutorial parent) {
        this(console, player, new Location(player.getWorld(), -30, -43, 143), parent);
    }

    public Level1(boolean console, Player player, Location startLocation, Tutorial parent) {
        this(console, player, startLocation, false, parent);
    }

    public Level1(boolean console, Player player, Location startLocation, boolean completed, Tutorial parent) {
        // ruft den Konstruktor der Elternklasse Level auf
        super(console, LEVEL_NAME, LEVEL_DESCRIPTION, player, startLocation, completed, parent);
    }

    @Override
    /*
     * erstellt alle für das Level benötigten Schachbretter und speichert diese in
     * chessBoards
     */
    protected void configureChessBoards() {
        // 8x8 Schachbrett für Level 1 erstellen
        chessBoards = new MChessBoard[1];
        chessBoards[0] = new MChessBoard(new Location(player.getWorld(), -40, -44, 128), 8, player, false);
    }

    @Override
    protected void setInventory() {
        player.getInventory().clear();
        setControlItems();
    }

    @Override
    public void initializeSteps() {
        // Step 1 - Erklärung des Schachbretts durch NPC
        currentStep = new Step(
                () -> {
                    chessBoards[0].spawnChessBoard();
                    // TODO Audio vom NPC abspielen lassen
                    player.sendMessage(Component.textOfChildren(EMPTY_LINE, NPC_EXPLAIN_CHESSBOARD));
                    // TODO evtl Verzögerung einbauen, sodass completed erst true gesetzt wird wenn
                    // der NPC fertig ist
                },
                () -> {
                    chessBoards[0].despawnChessBoard();
                    // TODO NPC zum Schweigen bringen
                });

        // setupStep wird bis zum Ende durchgegeben und jeweils mit dem vorherigen
        // verknüpft
        Step setupStep = currentStep;

        // Setzen einer Dame auf Schachbrett durch Computer
        // Erklärung der Dame durch NPC
        setupStep.setNext(new Step(
                () -> {
                    // spawne Queen auf Feld (3,2)
                    if (console)
                        System.out.println("setze Dame auf 3, 2");
                    // TODO Dame wird nicht richtig gespawnt FFFF
                    chessBoards[0].addPiece(new Queen(3, 2));
                    chessBoards[0].updatePieces();
                    // TODO Audio vom NPC abspielen lassen
                    player.sendMessage(Component.textOfChildren(EMPTY_LINE, NPC_EXPLAIN_QUEEN));
                    // TODO evtl Verzögerung einbauen...
                },
                () -> {
                    // entferne alle Figuren
                    chessBoards[0].clearBoard();
                    chessBoards[0].updatePieces();
                    // TODO NPC zum Schweigen bringen
                }));
        setupStep = setupStep.getNext();

        // Anzeigen der Bewegungsmuster der Dame
        // Erklärung der Bewegungsmuster durch NPC
        setupStep.setNext(new Step(
                () -> {
                    chessBoards[0].spawnCollisionCarpets();
                    // TODO Audio vom NPC abspielen lassen
                    player.sendMessage(Component.textOfChildren(EMPTY_LINE, NPC_EXPLAIN_MOVEMENT));
                    // TODO evtl Verzögerung einbauen...
                },
                () -> {
                    chessBoards[0].despawnCollisionCarpets();
                    // TODO NPC zum Schweigen bringen
                }));
        setupStep = setupStep.getNext();

        // Setzen einer weiteren Dame durch Computer
        setupStep.setNext(new Step(
                () -> {
                    // Bewegungsmuster entfernen (wird im nächsten Schritt wieder erzeugt)
                    chessBoards[0].despawnCollisionCarpets();
                    // spawne Queen auf Feld (5,3)
                    chessBoards[0].addPiece(new Queen(5, 3));
                    chessBoards[0].updatePieces();
                },
                () -> {
                    // entferne zuletzt gesetzte Dame von Feld (5,3)
                    chessBoards[0].removeLastQueen();
                    chessBoards[0].updatePieces();
                    chessBoards[0].spawnCollisionCarpets();
                }));
        setupStep = setupStep.getNext();

        // Anzeigen der Bedrohungen der Damen
        // Erklärung der Bedrohungen durch NPC
        setupStep.setNext(new Step(
                () -> {
                    chessBoards[0].spawnCollisionCarpets();
                    // TODO Audio vom NPC abspielen lassen
                    player.sendMessage(Component.textOfChildren(EMPTY_LINE, NPC_EXPLAIN_THREATS));
                    // TODO evtl Verzögerung einbauen...
                },
                () -> {
                    chessBoards[0].despawnCollisionCarpets();
                    // TODO NPC zum Schweigen bringen
                }));
        setupStep = setupStep.getNext();

        // Löschen aller Damen von Schachbrett
        setupStep.setNext(new Step(
                // alle Figuren entfernen
                () -> {
                    chessBoards[0].despawnAllPieces();
                    chessBoards[0].despawnChessBoard();
                },
                // alle Figuren spawnen
                () -> {
                    chessBoards[0].spawnChessBoard();
                    chessBoards[0].spawnAllPieces();
                }));

        // alle Steps in beide Richtungen miteinander verknüpfen
        currentStep.backLink();
    }
}
