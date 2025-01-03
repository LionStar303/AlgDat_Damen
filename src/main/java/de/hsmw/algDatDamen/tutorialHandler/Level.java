package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import de.hsmw.algDatDamen.AlgDatDamen;
import de.hsmw.algDatDamen.ChessBoard.Knight;
import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.ChessBoard.Piece;
import de.hsmw.algDatDamen.ChessBoard.Queen;
import de.hsmw.algDatDamen.ChessBoard.Superqueen;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public abstract class Level implements Listener {

    protected final static Component EMPTY_LINE = Component.text("----\n", NamedTextColor.AQUA);

    private final Component LEVEL_NAME; // vielleicht als Bossbar anzeigen
    private final Component LEVEL_DESCRIPTION;
    private final Location startLocation;
    protected Tutorial parentTutorial;
    protected MChessBoard[] chessBoards;
    protected Teleporter teleporter;
    protected Player player;
    protected Step currentStep;
    protected String latestPlayerInput;
    protected boolean active;
    protected boolean completed;
    protected boolean console;
    protected NPC npc;
    private int stepCount;
    private int currentStepID;
    public long cooldownMillis;
    public int currentCBID;
    protected boolean bbisRunning;
    protected BukkitRunnable bossBarTask;
    protected BossBar bossBar;
    protected BukkitTask animation;

    public Level(boolean console, String name, String description, Player player, Location startLocation,
            Location teleporterLocation, boolean completed, Tutorial parent) {

        this.console = console;
        this.LEVEL_NAME = Component.text(name, NamedTextColor.BLUE);
        this.LEVEL_DESCRIPTION = Component.text(description, NamedTextColor.AQUA);
        this.player = player;
        this.startLocation = startLocation;
        this.completed = completed;
        this.active = false;
        this.parentTutorial = parent;
        this.teleporter = new Teleporter(teleporterLocation.add(0, 1, 0), player);
        this.currentCBID = 0;
        this.cooldownMillis = 0;
        this.bbisRunning = false;
        this.bossBarTask = null;
        this.bossBar = null;
        this.animation = null;
    }

    // Abstrakte Methoden
    protected abstract void spawnVillager();
    protected abstract void configureChessBoards();
    protected abstract void initializeSteps();

    protected void initBossBar() {
        this.bossBar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SOLID);
        bossBar.setVisible(false);
        bossBar.addPlayer(player);
    }

    // Standardmethoden
    public void start() {
        // töte alle Entities, welche sich im Spiel befinden
        List<Entity> entities = player.getWorld().getEntities();
        for (Entity entity : entities)
            if (!(entity instanceof Player))
                entity.remove();

        // Spieler wird immer zum Start teleportiert, auch wenn das Level schon läuft
        teleportToStart();

        // Abbruch, wenn das Level schon läuft
        if (active)
            return;
        if (console)
            System.out.println("Level: starte level");
        active = true;

        configureChessBoards();

        teleporter.spawnTeleporter();
        spawnVillager();

        // alle Schritte erzeugen
        initializeSteps();
        countSteps();
        currentStepID = 0;

        player.setRespawnLocation(startLocation, true);
        player.sendMessage(Component.textOfChildren(LEVEL_NAME));
        player.sendMessage(Component.textOfChildren(EMPTY_LINE, LEVEL_DESCRIPTION));
        player.sendMessage("\n\n");
        setInventory();
        // ersten Schritt starten
        currentStep.start();
    }

    protected void setInventory() {
        player.getInventory().clear();
        setControlItems();
    }

    private void teleportToStart() {
        player.teleport(startLocation);
        player.setFlying(false);
    }

    private void setControlItems() {
        player.getInventory().setItem(6, ControlItem.PREVIOUS_STEP.getItemStack());
        player.getInventory().setItem(7, ControlItem.RESET_STEP.getItemStack());
        player.getInventory().setItem(8, ControlItem.NEXT_STEP.getItemStack());
    }

    private void nextStep() {
        if (console)
            System.out.println("running next step");

        if(!currentStep.completed) {
            if(!currentStep.checkForCompletion()) {
                // negativen Track abspielen wenn der Schritt noch nicht abgeschlossen ist
                npc.playTrackNegative();
                return;
            }
        }

        // return wenn currentStep noch nicht abgeschlossen oder letzter Step
        /*
         * if (!currentStep.completed()) {
         * if(player == null)return;
         * player.sendMessage(Component.
         * text("Du musst den aktuellen Schritt erst abschließen.",
         * NamedTextColor.RED));
         * return;
         * }
         */
        if (currentStep.getNext() == null) {
            if (player == null)
                return;
            player.sendMessage(Component.text("Du kannst ins nächste Level vorrücken.", NamedTextColor.RED));
            return;
        }

        // Increase Level
        currentStepID++;
        if (console)
            System.out.printf("[AlgDat_Damen] Step ID: %d of %d\n", currentStepID, stepCount);
        if (currentStepID <= stepCount) {
            player.setExp((float) currentStepID / (stepCount - 1));
            if (currentStepID == stepCount - 1) {
                player.setExp(0.9999f);
            }
        }

        currentStep = currentStep.getNext();
        npc.stopSound();
        currentStep.start();
    }

    private void prevStep() {
        if (console)
            System.out.println("running prev step");
        currentStep.reset();
        if (currentStep.getPrev() != null) {
            currentStep = currentStep.getPrev();
            currentStepID--;
        }

        if (currentStepID <= stepCount) {
            player.setExp((float) currentStepID / (stepCount - 1));
            if (currentStepID == stepCount - 1) {
                player.setExp(0.9999f);
            }
        }

        resetStep();
    }

    private void resetStep() {
        if (console)
            System.out.println("running reset step");
        currentStep.reset();
        npc.stopSound();
        currentStep.start();
    }

    private void startNextLevel() {
        active = false;
        parentTutorial.incProgress();
        parentTutorial.getCurrentLevel().start();
    }

    private void tryPlacePiece(PlayerInteractEvent event, Piece p) {
        for (MChessBoard cb : chessBoards) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock == null)
                continue;
            Location clickedLocation = clickedBlock.getLocation();
            if (console)
                System.out.println(player.getName() + " clicked on " + clickedLocation.toString());
            cb.addPiece(clickedLocation, p.clone());

            // completion prüfen, falls der Step nach Damen-Aktion beendet sein könnte
            if (!currentStep.completed)
                currentStep.checkForCompletion();
        }
    }

    protected void currentStepcheckForCompletion() {
        if (!currentStep.completed)
            currentStep.checkForCompletion();
    }

    /**
     * AsyncChatEvent behandeln
     * 
     * @param event auslösendes Event
     */
    public void handleChatEvent(AsyncChatEvent event) {
        this.latestPlayerInput = PlainTextComponentSerializer.plainText().serialize(event.message());
        if (console)
            System.out.println(event.getPlayer() + " (Chat): " + latestPlayerInput);
        if (!currentStep.completed)
            currentStep.checkForCompletion(); // prüfen, ob Eingabe den Step beendet
        event.setCancelled(true);
    }

    /**
     * PlayerInteractionEvent behandeln
     * 
     * @param item  entsprechendes ControlItem in der Hand des Spielers
     * @param event auslösendes Event
     *              je nach benutztem ControlItem wird die entsprechende Funktion
     *              ausgeführt
     */
    public void handleInteractionEvent(ControlItem item, PlayerInteractEvent event) {
        if (System.currentTimeMillis() < cooldownMillis)
            return;
        cooldownMillis = System.currentTimeMillis() + 100;
        switch (item) {
            case PREVIOUS_STEP:
                prevStep();
                break;
            case RESET_STEP:
                resetStep();
                break;
            case NEXT_STEP:
                nextStep();
                break;
            case NEXT_LEVEL:
                boolean fireClicked = false;
                if (event.getClickedBlock() != null)
                    fireClicked = teleporter.isTeleportBlock(event.getClickedBlock());

                if (((fireClicked) || (player.getLocation().distance(teleporter.getLocation()) <= 2))
                        && teleporter.isEnabled() && currentStep.getNext() == null)
                    startNextLevel();
                break;
            case PLACE_QUEEN:
                tryPlacePiece(event, new Queen());
                break;
            case PLACE_KNIGHT:
                tryPlacePiece(event, new Knight());
                break;
            case PLACE_SUPERQUEEN:
                tryPlacePiece(event, new Superqueen());
                break;
            case BACKTRACKING_FORWARD_Q:
                if (chessBoards[currentCBID] == null)
                    return;
                if (chessBoards[currentCBID].getPieces().size() != 0)
                    chessBoards[currentCBID].verfyPieces(new Queen());
                if (!chessBoards[currentCBID].isSolved()) {
                    chessBoards[currentCBID].animationStepToNextField(new Queen());
                } else {
                    currentStepcheckForCompletion();
                }
                break;
            case BACKTRACKING_FORWARDFAST_Q:
                if (chessBoards[currentCBID] == null)
                    return;
                if (chessBoards[currentCBID].getPieces().size() != 0)
                    chessBoards[currentCBID].verfyPieces(new Queen());
                if (!chessBoards[currentCBID].isSolved()) {
                    chessBoards[currentCBID].animationStepToNextPiece(new Queen());
                } else {
                    currentStepcheckForCompletion();
                }

                break;

            case BACKTRACKING_BACKWARD_Q:
                // chessBoards[currentCBID].verfyPieces(new Queen());
                if (chessBoards[currentCBID] == null)
                    return;
                if (chessBoards[currentCBID].getPieces().size() != 0) {
                    chessBoards[currentCBID].animationReverseStepToNextField(new Queen());
                }
                if (chessBoards[currentCBID].isSolved()) {
                    currentStepcheckForCompletion();
                }
                break;

            case BACKTRACKING_BACKWARDFAST_Q:
                if (chessBoards[currentCBID] == null)
                    return;
                chessBoards[currentCBID].verfyPieces(new Queen());
                if (chessBoards[currentCBID].getPieces().size() != 0) {
                    chessBoards[currentCBID].animationReverseStepToNextPiece(new Queen());
                }
                if (chessBoards[currentCBID].isSolved()) {
                    currentStepcheckForCompletion();
                }
                break;
            case SHOW_CARPET:
                if (chessBoards[currentCBID] == null)
                    return;
                chessBoards[currentCBID].setCollisionCarpets(!chessBoards[currentCBID].isCollisionCarpets());
                System.out.println("collision carpets " + chessBoards[currentCBID].isCollisionCarpets());
                chessBoards[currentCBID].updateCollisionCarpets();
                System.out.println("collision carpets " + chessBoards[currentCBID].isCollisionCarpets());
                break;
            case SPAWN_CARPET:
                if (event.getClickedBlock() == null)
                    return;
                if (chessBoards[currentCBID] == null)
                    return;
                chessBoards[currentCBID].spawnUserCarpet(event.getClickedBlock().getLocation());
                currentStep.checkForCompletion();
                break;
            default:
                break;
        }
        event.setCancelled(true);
    }

    /**
     * Counts the steps of the level and stores it in stepCount variable.
     */
    private void countSteps() {
        if (currentStep == null)
            stepCount = 0;
        int count = 1;
        Step step = currentStep;
        while (step.getNext() != null) {
            step = step.getNext();
            count++;
        }
        stepCount = count;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public boolean isActive() {
        return active;
    }

    public MChessBoard[] getChessBoards() {
        return chessBoards;
    }

    protected void startBossBarTimer(int minutes, String title) {
        bbisRunning = true;

        if (bossBarTask != null && !bossBarTask.isCancelled()) {
            bossBarTask.cancel();
        }

        bossBar.setVisible(true);

        bossBarTask = new BukkitRunnable() {
            private int time = minutes * 60; // Minuten in Sekunden

            @Override
            public void run() {
                if (time <= 0) {
                    bossBar.setVisible(false);
                    bbisRunning = false;
                    currentStepcheckForCompletion();
                    cancel();
                } else {
                    int minutesLeft = time / 60;
                    int secondsLeft = time % 60;
                    bossBar.setTitle(title + " " + minutesLeft + "m " + secondsLeft + "s");

                    double progress = (double) time / (minutes * 60);
                    bossBar.setProgress(progress);

                    time--;
                }
            }
        };

        bossBarTask.runTaskTimer(AlgDatDamen.getInstance(), 0L, 20L); // 20 Ticks = 1 Sekunde
    }

    protected void stopBossBar() {
        if (bossBarTask != null) {
            bossBarTask.cancel();
        }
        bossBar.setVisible(false);
        bbisRunning = false;
    }

}
