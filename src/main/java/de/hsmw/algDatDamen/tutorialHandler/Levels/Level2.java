package de.hsmw.algDatDamen.tutorialHandler.Levels;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.ChessBoard.Queen;
import de.hsmw.algDatDamen.tutorialHandler.Level;
import de.hsmw.algDatDamen.tutorialHandler.Step;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;
import net.kyori.adventure.text.Component;

public class Level2 extends Level {

    private final static String LEVEL_NAME = "Level 2 - Boot Camp";
    private final static String LEVEL_DESCRIPTION = "Erklärung des N-Damen-Problems";
    private final static Component NPC_INTRO = Component.text("Jetzt will ich dir zeigen wie ich ein 8x8 Feld fülle ohne das sich eine der Damen gegenseitig bedroht.");
    private final static Component NPC_EXPLAIN_THREATS_1 = Component.text("Denn, wie du sehen kannst, wenn ich die Dame so platziere bedroht sie folgende Felder.");
    private final static Component NPC_EXPLAIN_THREATS_2 = Component.text("Und würde ich jetzt eine weitere Dame platzieren. Ungefähr so…");
    private final static Component NPC_EXPLAIN_PROBLEM = Component.text("Dann würde meine erste Dame die zweite schlagen können und das wollen wir hier vermeiden. Also entfernen wir die zweite Dame wieder und setzen sie neu.");
    private final static Component NPC_SOLVE = Component.text("Nun bedrohen sich die beiden Damen nicht mehr. Aber so ist das Brett natürlich noch nicht gefüllt. Lass mich die restlichen Damen auch noch platzieren.");
    private final static Component NPC_EXPLAIN_SOLUTION = Component.text("Wenn du jetzt selbst einen Blick auf das Feld wirfst solltest du feststellen können das wir keine weitere Dame mehr platzieren können, ohne das sie bedroht wird, aber auch kein unbedrohtes Feld übrig bleibt.\r\n" +
                "Hierbei handelt es sich um das sogenannte N-Damen-Problem. Eine Herausforderung, bei der N Damen auf einem N x N Schachbrett platziert werden müssen, ohne dass sich zwei Damen gegenseitig angreifen.\r\n" +
                "Und wie du auch gesehen hast haben wir das Problem soeben gelöst.\r\n" +
                "Also lass mich nun dazu über gehen wie das passiert ist.\r\n");
    private final static Component NPC_EXPLAIN_3X3_1 = Component.text("Hier ist ein weitaus kleineres Feld, aber leider ist dieses Feld zu klein als das man hier ein Lösung finden könnte.");
    private final static Component NPC_EXPLAIN_3X3_2 = Component.text("Egal wie ich diese drei Damen platziere, es würden sich immer mindestens zwei gegenseitig schlagen.\r\n" +
                "Denn das N-Damen Problem kann man erst ab einer Größe von 4x4 lösen.\r\n");
    private final static Component NPC_EXPLAIN_4x4_SOLUTION = Component.text("Ab 4x4 kann ich allerdings wieder allen Damen einen Platz zuweisen ohne das sie sich gegenseitig bedrohen.");

    public Level2(boolean console, Player player, Tutorial parent) {
        this(console, player, new Location(player.getWorld(), -56, -37, 139), parent);
    }

    public Level2(boolean console, Player player, Location startLocation, Tutorial parent) {
        this(console, player, startLocation, false, parent);
    }

    public Level2(boolean console, Player player, Location startLocation, boolean completed, Tutorial parent) {
        // ruft den Konstruktor der Elternklasse Level auf
        super(console, LEVEL_NAME, LEVEL_DESCRIPTION, player, startLocation, completed, parent);
    }
    @Override
    protected void configureChessBoards() {
        // 8x8, 4x4 und 3x3 Schachbretter erstellen
        chessBoards = new MChessBoard[3];
        // TODO richtige Location für 8x8 Schachbrett finden
        chessBoards[0] = new MChessBoard(new Location(player.getWorld(), -70, -36, 128), 8, player);
        chessBoards[1] = new MChessBoard(new Location(player.getWorld(), -70, -36, 128), 4, player);
        chessBoards[2] = new MChessBoard(new Location(player.getWorld(), -78, -36, 130), 3, player);
    }

    @Override
    protected void setInventory() {
        player.getInventory().clear();
        setControlItems();
    }

    @Override
    protected void initializeSteps() {
        // Erklärung des N-Damen-Problems durch NPC
        // Erzeugung eines 8x8 Schachbretts
        // Setzen einer Dame auf Schachbrett durch Computer
        currentStep = new Step(
            () -> {
                // TODO Audio vom NPC abspielen lassen
                player.sendMessage(Component.textOfChildren(EMPTY_LINE, NPC_INTRO));
                chessBoards[0].spawnChessBoard();
                // TODO evtl Koordinaten anpassen um nicht in Konflikt mit Animation Solve zu kommen
                chessBoards[0].addPiece(new Queen(3, 2));
                chessBoards[0].updatePieces();
                // TODO evtl Verzögerung einbauen, sodass completed erst true gesetzt wird wenn der NPC fertig ist
            },
            () -> {
                // Dame entfernen und Chessboard despawnen
                chessBoards[0].removeAllPieces();
                chessBoards[0].updatePieces();
                chessBoards[0].despawnChessBoard();
                // TODO NPC zum Schweigen bringen
            }
        );

        // setupStep wird bis zum Ende durchgegeben und jeweils mit dem vorherigen verknüpft
        Step setupStep = currentStep;

        // Anzeigen der Bedrohungen der Dame
        // Erklärung der Bedrohungen durch NPC
        setupStep.setNext(new Step(
            () -> {
                chessBoards[0].spawnCollisionCarpets();
                // TODO Audio vom NPC abspielen lassen
                player.sendMessage(Component.textOfChildren(EMPTY_LINE, NPC_EXPLAIN_THREATS_1));
                // TODO evtl Verzögerung einbauen, sodass completed erst true gesetzt wird wenn der NPC fertig ist
            },
            () -> {
                chessBoards[0].despawnCollisionCarpets();
                // TODO NPC zum Schweigen bringen
            }
        ));
        setupStep = setupStep.getNext();

        // Erklärung der Bedrohungen zwischen Damen durch NPC
        // Setzen einer weiteren Dame in bedrohtes Feld durch Computer
        setupStep.setNext(new Step(
            () -> {
                // TODO Audio vom NPC abspielen lassen
                player.sendMessage(Component.textOfChildren(EMPTY_LINE, NPC_EXPLAIN_THREATS_2));
                // platziere Dame auf Feld(6,5) welches von Dame(3,2) bedroht ist
                chessBoards[0].addPiece(new Queen(6, 5));
                chessBoards[0].updatePieces();
                // TODO evtl Verzögerung einbauen, sodass completed erst true gesetzt wird wenn der NPC fertig ist
            },
            () -> {
                chessBoards[0].removeLastPiece();
                chessBoards[0].updatePieces();
                // TODO NPC zum Schweigen bringen
            }
        ));
        setupStep = setupStep.getNext();

        // Erklärung des N-Damen-Problems durch NPC
        // Löschen der letzten Dame
        // Setzen einer weiteren Dame in nicht bedrohtes Feld durch Computer
        setupStep.setNext(new Step(
            () -> {
                // TODO Audio vom NPC abspielen lassen
                player.sendMessage(Component.textOfChildren(EMPTY_LINE, NPC_EXPLAIN_PROBLEM));
                // im vorherigen Schritt platzierte Dame entfernen
                chessBoards[0].removeLastPiece();
                // platziere Dame auf Feld(6,4) welches nicht bedroht ist
                // TODO evtl Koordinaten anpassen um nicht in Konflikt mit Animation Solve zu kommen
                chessBoards[0].addPiece(new Queen(6, 4));
                // TODO evtl Verzögerung einbauen, sodass completed erst true gesetzt wird wenn der NPC fertig ist
            },
            () -> {
                // neue Dame entfernen und alte Dame wieder setzen
                chessBoards[0].removeLastPiece();
                chessBoards[0].addPiece(new Queen(6, 5));
                // TODO NPC zum Schweigen bringen
            }
        ));
        setupStep = setupStep.getNext();

        // Setzen aller Damen und Lösen des Problems durch Computer
        setupStep.setNext(new Step(
            () -> {
                // TODO Audio vom NPC abspielen lassen
                player.sendMessage(Component.textOfChildren(EMPTY_LINE, NPC_SOLVE));
                chessBoards[0].animationSolve(new Queen());
                // TODO evtl Verzögerung einbauen, sodass das Schachbrett erst gelöst wird wenn der NPC fertig ist
            },
            () -> {
                // neue Dame entfernen und alte Dame wieder setzen
                // TODO prüfen, ob Animation abgeschlossen ist bzw Animation abbrechen
                chessBoards[0].removeAllPieces();
                // vorher platzierte Königinnen wieder setzen
                // TODO evtl Koordinaten anpassen um nicht in Konflikt mit Animation Solve zu kommen
                chessBoards[0].addPiece(new Queen(3, 2));
                chessBoards[0].addPiece(new Queen(6, 5));
                // TODO NPC zum Schweigen bringen
            }
        ));
        setupStep = setupStep.getNext();

        // Erklärung der Lösung durch NPC
        setupStep.setNext(new Step(
            () -> {
                // TODO Audio vom NPC abspielen lassen
                player.sendMessage(Component.textOfChildren(EMPTY_LINE, NPC_EXPLAIN_SOLUTION));
                // TODO evtl Verzögerung einbauen, sodass completed erst true gesetzt wird wenn der NPC fertig ist
            },
            () -> {} // TODO NPC zum Schweigen bringen
        ));
        setupStep = setupStep.getNext();

        // Löschen aller Damen und Löschen des Schachbretts
        // Erzeugung eines 3x3 Schachbretts
        setupStep.setNext(new Step(
            () -> {
                // TODO Audio vom NPC abspielen lassen
                player.sendMessage(Component.textOfChildren(EMPTY_LINE, NPC_EXPLAIN_3X3_1));
                // 8x8 Schachbrett löschen
                chessBoards[0].despawnAllPieces();
                chessBoards[0].despawnChessBoard(); // TODO ggf einfach weglassen
                // 3x3 Schachbrett spawnen
                chessBoards[2].spawnChessBoard();
                // TODO evtl Verzögerung einbauen, sodass completed erst true gesetzt wird wenn der NPC fertig ist
            },
            () -> {
                // 3x3 Brett löschen und gelöstes 8x8 Feld spawnen
                chessBoards[2].despawnChessBoard();
                chessBoards[0].spawnChessBoard();
                chessBoards[0].spawnAllPieces();
                // TODO NPC zum Schweigen bringen
            }
        ));
        setupStep = setupStep.getNext();

        // Setzen von 3 falschen Damen auf Schachbrett durch Computer
        // Erklärung von Problem mit 3x3 durch NPC
        setupStep.setNext(new Step(
            () -> {
                // TODO Audio vom NPC abspielen lassen
                player.sendMessage(Component.textOfChildren(EMPTY_LINE, NPC_EXPLAIN_3X3_2));
                /* 3 Damen platzieren
                 * Q . Q
                 * . . .
                 * . Q .
                 */
                chessBoards[2].addPiece(new Queen(0,0));
                chessBoards[2].addPiece(new Queen(2,0));
                chessBoards[2].addPiece(new Queen(1,2));
                chessBoards[2].updatePieces();
                // TODO evtl Verzögerung einbauen, sodass completed erst true gesetzt wird wenn der NPC fertig ist
            },
            () -> {
                // alle drei Damen löschen
                chessBoards[2].removeAllPieces();
                // TODO NPC zum Schweigen bringen
            }
        ));
        setupStep = setupStep.getNext();

        // Löschen aller Damen und Löschen des Schachbretts
        // Erzeugung eines 4x4 Schachbretts
        // Setzen aller Damen und Lösen des Problems durch Computer
        // Erklärung der Lösung durch NPC
        // Erklärung von Notwendigkeit für mindestens 4x4 Schachbrett
        setupStep.setNext(new Step(
            () -> {
                // TODO Audio vom NPC abspielen lassen
                player.sendMessage(Component.textOfChildren(EMPTY_LINE, NPC_EXPLAIN_4x4_SOLUTION));
                // 3x3 Brett entfernen
                chessBoards[2].despawnAllPieces();
                chessBoards[2].despawnChessBoard();
                // 4x4 Brett spawnen und lösen, wurde evtl schon mal gelöst
                chessBoards[1].spawnChessBoard();
                if(!chessBoards[1].isSolved()) chessBoards[1].playBacktrack(new Queen());
                chessBoards[1].updatePieces();
                // TODO evtl Verzögerung einbauen, sodass completed erst true gesetzt wird wenn der NPC fertig ist
            },
            () -> {
                // 4x4 Feld entfernen
                chessBoards[1].despawnAllPieces();
                chessBoards[1].despawnChessBoard();
                // 3x3 Feld spawnen und Damen platzieren siehe voriger Schritt
                chessBoards[2].spawnChessBoard();
                chessBoards[2].spawnAllPieces();
                // TODO NPC zum Schweigen bringen
            }
        ));
        setupStep = setupStep.getNext();

        // Löschen aller Damen und Löschen des Schachbretts
        setupStep.setNext(new Step(
            () -> {
                // 4x4 Brett samt Figuren entfernen
                chessBoards[1].despawnAllPieces();
                chessBoards[1].despawnChessBoard();
            },
            () -> {
                chessBoards[1].spawnChessBoard();
                chessBoards[1].updatePieces();
            }
        ));
        setupStep = setupStep.getNext();

        // alle Steps in beide Richtungen miteinander verknüpfen
        currentStep.backLink();
    }
    
}
