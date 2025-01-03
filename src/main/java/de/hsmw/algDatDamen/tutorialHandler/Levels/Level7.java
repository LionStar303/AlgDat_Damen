package de.hsmw.algDatDamen.tutorialHandler.Levels;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import de.hsmw.algDatDamen.AlgDatDamen;
import de.hsmw.algDatDamen.ChessBoard.Knight;
import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.ChessBoard.MChessBoardMode;
import de.hsmw.algDatDamen.ChessBoard.Piece;
import de.hsmw.algDatDamen.ChessBoard.Queen;
import de.hsmw.algDatDamen.ChessBoard.Superqueen;
import de.hsmw.algDatDamen.tutorialHandler.ControlItem;
import de.hsmw.algDatDamen.tutorialHandler.Level;
import de.hsmw.algDatDamen.tutorialHandler.NPC;
import de.hsmw.algDatDamen.tutorialHandler.NPCTrack;
import de.hsmw.algDatDamen.tutorialHandler.Step;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;

// TODO muss getestet werden
public class Level7 extends Level {

    private final static String LEVEL_NAME = "Level 7 - Eiskönigin";
    private final static String LEVEL_DESCRIPTION = "Erweiterung des Problems mit Einführung und Anwendung der Springerfigur und der Superdame.";

    public Level7(boolean console, Player player, Tutorial parent) {
        this(console, player, new Location(player.getWorld(), -127, -7, -68, 180, 0),
                new Location(player.getWorld(), -127, -8, -67), parent);
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
    protected void spawnVillager() {
        this.npc = new NPC(new Location(player.getWorld(), -129, -7, -71), console);
        this.npc.setType(Villager.Type.SNOW);
        this.npc.spawn();
    }

    @Override
    protected void configureChessBoards() {
        chessBoards = new MChessBoard[1];
        chessBoards[0] = new MChessBoard(new Location(player.getWorld(), -129, -8, -98), 10, player);
    }

    @Override
    protected void initializeSteps() {

        // Erzeugung eines 8x8 Schachbretts
        // Springer spawnen
        currentStep = new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_702_KNIGHT_1);
                    npc.moveVillagerWithPathfinding(new Location(player.getWorld(), -130, -7, -88), 0.5);
                    chessBoards[0].addPiece(new Knight(5, 5));
                    chessBoards[0].updateBoard();
                },
                () -> {}
        );

        // setupStep wird bis zum Ende durchgegeben und jeweils mit dem vorherigen
        // verknüpft
        Step setupStep = currentStep;

        // collision carpets spawnen
        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_703_KNIGHT_2);
                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].updateBoard();
                },
                () -> {
                    chessBoards[0].setCollisionCarpets(false);
                    chessBoards[0].updateBoard();
                }
        ));
        setupStep = setupStep.getNext();

        // Dame Spawnen
        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_704_KNIGHT_AND_QUEEN_1);
                    chessBoards[0].addPiece(new Queen(4, 3));
                    chessBoards[0].updateBoard();
                },
                () -> {
                    chessBoards[0].removeLastPiece();
                    chessBoards[0].updateBoard();
                }));
        setupStep = setupStep.getNext();

        // Spieler muss Dame und Springer selber setzen
        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_705_KNIGHT_AND_QUEEN_2);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setCollisionCarpets(false);
                    chessBoards[0].updateBoard();
                    chessBoards[0].setMode(MChessBoardMode.NORMAL);

                    setInventory();
                    player.getInventory().setItem(0, ControlItem.PLACE_QUEEN.getItemStack());
                    player.getInventory().setItem(1, ControlItem.PLACE_KNIGHT.getItemStack());
                    player.getInventory().setItem(2, ControlItem.SHOW_CARPET.getItemStack());
                },
                () -> {
                    setInventory();
                    chessBoards[0].setMode(MChessBoardMode.INACTIVE);
                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].addPiece(new Knight(5, 5));
                    chessBoards[0].addPiece(new Queen(4, 3));
                },
                // Step ist complete wenn das Schachbrett gelöst ist

                unused -> {
                    int Kcount = 0;
                    int Qcount = 0;
                    if (console) {
                        System.out.println("Condition: chessBoards[0].getPieces().size() = "
                                + chessBoards[0].getPieces().size() + " < 2");
                    }
                    if (chessBoards[0].getPieces().size() < 2) {
                        return false;
                    }
                    for (Piece p : chessBoards[0].getPieces()) {
                        if (chessBoards[0].checkCollision(p)) {
                            if (console) {
                                System.out.println("Collision detected with piece: " + p);
                            }

                            return false;
                        }
                        if (p.getLetter() == 'K') {
                            Kcount++;
                        }
                        if (p.getLetter() == 'Q') {
                            Qcount++;
                        }
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
                }
        ));
        setupStep = setupStep.getNext();

        // Dame spawnen
        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_706_SUPERQUEEN_INTRO);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setCollisionCarpets(false);
                    chessBoards[0].addPiece(new Queen(5, 5));
                    chessBoards[0].updateBoard();

                    // Inventar leeren
                    setInventory();
                },
                () -> {
                    chessBoards[0].removeAllPieces();
                }
        ));
        setupStep = setupStep.getNext();

        // Superdame erklären
        setupStep.setNext(new Step(
                () -> npc.playTrack(NPCTrack.NPC_707_SUPERQUEEN_EXPLAIN),
                () -> {}
        ));
        setupStep = setupStep.getNext();

        // Superdame erzeugen
        setupStep.setNext(new Step(
                () -> {
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].addPiece(new Superqueen(5, 5));
                    chessBoards[0].updateBoard();
                    player.getWorld().strikeLightning(chessBoards[0].getOriginCorner().clone().add(5.5, 0, 5.5));
                },
                () -> {
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].addPiece(new Queen(5, 5));
                    chessBoards[0].updateBoard();
                }));
        setupStep = setupStep.getNext();

        // Bewegungen anzeigen
        setupStep.setNext(new Step(
                () -> {
                    npc.playTrack(NPCTrack.NPC_708_SUPERQUEEN_MOVE_1);
                    chessBoards[0].setCollisionCarpets(true);
                    chessBoards[0].updateBoard();
                },
                () -> {
                    chessBoards[0].setCollisionCarpets(false);
                    chessBoards[0].updateBoard();
                }));
        setupStep = setupStep.getNext();

        // drei Damen durch Algorithmus setzen lassen
        // Spieler muss bis Dame 8 setzen (nach Backtracking Algorithmus)
        // Ab da übernimmt der Algorithmus
        setupStep.setNext(new Step(() -> {
            npc.playTrack(NPCTrack.NPC_709_SUPERQUEEN_MOVE_2);
            chessBoards[0].removeAllPieces();
            chessBoards[0].setCollisionCarpets(true);
            chessBoards[0].updateBoard();

            chessBoards[0].setMode(MChessBoardMode.INACTIVE);

            chessBoards[0].setStateX(0);
            chessBoards[0].setStateY(0);

            // BukkitRunnable für die Animation verwenden
            animation = new BukkitRunnable() {
                @Override
                public void run() {
                    if (chessBoards[0].getPieces().size() >= 3) {
                        this.cancel(); // Stoppt den Runnable, wenn genug Figuren gesetzt sind
                        chessBoards[0].updateBoard();
                        chessBoards[0].setMode(MChessBoardMode.EXPLODING);
                        setInventory();
                        player.getInventory().setItem(0, ControlItem.PLACE_SUPERQUEEN.getItemStack());
                        player.getInventory().setItem(1, ControlItem.SHOW_CARPET.getItemStack());
                        currentStepcheckForCompletion();
                        return;
                    }
                    chessBoards[0].animationStepToNextField(new Superqueen());
                }
            }.runTaskTimer(AlgDatDamen.getInstance(), 0, 10L); // 10L = 10 Ticks = 0.5 Sekunden

        },
                () -> {
                    if (animation != null)
                        animation.cancel();
                    chessBoards[0].stopCurrentAnimation();
                    setInventory();
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].setCollisionCarpets(false);
                    chessBoards[0].updateBoard();

                },
                // Step ist complete wenn das Schachbrett gelöst ist
                unused -> {
                    MChessBoard cb = chessBoards[0].clone();
                    cb.verfyPieces(new Superqueen());

                    if (chessBoards[0].isSolved()) {
                        return true;
                    }

                    if (cb.getPieces().size() > 7) {
                        npc.playTrackPositive();
                        chessBoards[0].setMode(MChessBoardMode.INACTIVE);
                        chessBoards[0].animationPiece2Piece(AlgDatDamen.getInstance(), 1, new Superqueen());
                    }
                    return false;
                }));
        setupStep = setupStep.getNext();

        // Feuerwerksanimation abspielen
        setupStep.setNext(new Step(
                () -> {
                    chessBoards[0].stopCurrentAnimation();
                    if (animation != null)
                        animation.cancel();
                    npc.playTrack(NPCTrack.NPC_710_END);
                    chessBoards[0].removeAllPieces();
                    chessBoards[0].updateBoard();
                    teleporter.setEnabled(true, true);
                    int[][] positions = {
                            { -126, 10, -141 },
                            { -90, 10, -86 },
                            { -93, 10, -113 },
                            { -162, 10, -113 },
                            { -162, 10, -93 }
                    };
                    startFireworkShow(player.getWorld(), positions);
                    setInventory();
                    player.getInventory().setItem(4, ControlItem.NEXT_LEVEL.getItemStack());
                },
                () -> {
                    setInventory();
                    teleporter.setEnabled(false, true);
                    if (animation != null)
                        animation.cancel();
                }));
        setupStep = setupStep.getNext();

        // alle Steps in beide Richtungen miteinander verknüpfen
        currentStep.backLink();
    }

    public void startFireworkShow(World world, int[][] positions) {
        if (world == null) {
            return;
        }

        if (animation != null) {
            animation.cancel();
            animation = null;
        }

        Random random = new Random();

        // Startet die Animation
        animation = new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {

                for (int i = 0; i < positions.length; i++) {

                    if (ticks >= 60 * 20) { // 60 Sekunden (20 Ticks pro Sekunde)
                        cancel();
                        return;
                    }

                    // Zufällige Offsets für X und Z innerhalb eines Radius von 5 Blöcken
                    int offsetX = random.nextInt(11) - 5; // Werte von -5 bis +5
                    int offsetZ = random.nextInt(11) - 5; // Werte von -5 bis +5

                    // Neue zufällige Position für das Feuerwerk
                    Location location = new Location(world, positions[i][0] + offsetX, positions[i][1],
                            positions[i][2] + offsetZ);

                    // Spawnt ein Feuerwerk an der zufälligen Position
                    Firework firework = world.spawn(location, Firework.class);
                    FireworkMeta meta = firework.getFireworkMeta();

                    // Zufällige Farben
                    Color[] colors = {
                            Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
                            Color.PURPLE, Color.ORANGE, Color.WHITE
                    };
                    Color primary = colors[random.nextInt(colors.length)];
                    Color fade = colors[random.nextInt(colors.length)];

                    // Zufällige Explosionseffekte
                    FireworkEffect.Type type = FireworkEffect.Type.values()[random
                            .nextInt(FireworkEffect.Type.values().length)];

                    // Erstellen des Effekts
                    FireworkEffect effect = FireworkEffect.builder()
                            .withColor(primary)
                            .withFade(fade)
                            .flicker(random.nextBoolean())
                            .trail(true)
                            .with(type)
                            .build();

                    meta.addEffect(effect);
                    meta.setPower(random.nextInt(3) + 1); // Flugzeit zwischen 1 und 3
                    firework.setFireworkMeta(meta);

                    ticks += 10; // Alle 10 Ticks (0,5 Sekunden) ein Feuerwerk
                }
            }
        }.runTaskTimer(AlgDatDamen.getInstance(), 0L, 10L); // Start sofort, Wiederholung alle 10 Ticks
    }

}
