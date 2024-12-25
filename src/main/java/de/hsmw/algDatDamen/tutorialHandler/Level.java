package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import de.hsmw.algDatDamen.ChessBoard.Piece;
import de.hsmw.algDatDamen.ChessBoard.Queen;
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
    private long cooldownMillis;

    public Level(boolean console, String name, String description, Player player, Location startLocation, Location teleporterLocation, boolean completed, Tutorial parent) {

        this.console = console;
        this.LEVEL_NAME = Component.text(name, NamedTextColor.BLUE);
        this.LEVEL_DESCRIPTION = Component.text(description, NamedTextColor.AQUA);
        this.player = player;
        this.startLocation = startLocation;
        this.completed = completed;
        this.parentTutorial = parent;
        this.teleporter = new Teleporter(teleporterLocation.add(0, 1, 0));
        this.npc = new NPC(startLocation, console);

        cooldownMillis = 0;
    }

    // Abstrakte Methoden
    protected abstract void configureChessBoards();

    protected abstract void initializeSteps();

    // Standardmethoden
    public void start() {
        if (console)
            System.out.println("Level: starte level");
        active = true;
        teleportToStart();

        configureChessBoards();

        teleporter.spawnTeleporter();
        npc.spawn();

        // alle Schritte erzeugen
        initializeSteps();
        countSteps();
        currentStepID = 0;

        player.setRespawnLocation(startLocation);
        player.sendMessage(Component.textOfChildren(EMPTY_LINE, LEVEL_NAME));
        player.sendMessage(Component.textOfChildren(EMPTY_LINE, LEVEL_DESCRIPTION));

        setInventory();
        // ersten Schritt starten
        currentStep.start();

        // TODO Logik einbauen wenn Level beendet wird parentTutorial.incProgress()
        // aufrufen
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
        currentStep.checkForCompletion();
        
        // return wenn currentStep noch nicht abgeschlossen oder letzter Step
        if (!currentStep.completed()) {
            player.sendMessage(Component.text("Du musst den aktuellen Schritt erst abschließen.", NamedTextColor.RED));
            return;
        }
        if (currentStep.getNext() == null) {
            player.sendMessage(Component.text("Du kannst ins nächste Level vorrücken.", NamedTextColor.RED));
            return;
        }

        // Increase Level
        currentStepID++;
        if (console)
            System.out.printf("[AlgDat_Damen] Step ID: %d of %d\n", currentStepID, stepCount);
        if (currentStepID <= stepCount) {
            player.setExp((float) currentStepID / stepCount);
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
            player.setExp((float) currentStepID / stepCount);
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
        parentTutorial.incProgress();
        parentTutorial.getCurrentLevel().start();
    }

    private void tryPlaceQueen(PlayerInteractEvent event, boolean exploding) {
        for(MChessBoard cb : chessBoards) {
            Block clickedBlock = event.getClickedBlock();
            Location clickedLocation = clickedBlock.getLocation();
            if(console) System.out.println(player.getName() + " clicked on " + clickedLocation.toString());
            if(cb.isActive() && cb.isPartOfBoard(clickedLocation) && clickedBlock.getType() != Material.AIR) {
                Piece existingQueen = cb.getPieceAt(clickedLocation);
                if(existingQueen != null) cb.removePiece(existingQueen);
                else {
                    if(exploding) cb.addExplodingPiece(clickedLocation, new Queen());
                    else cb.addPiece(clickedLocation, new Queen());
                }
                // completion prüfen, falls der Step nach Damen-Aktion beendet sein könnte
                //currentStep.checkForCompletion();
                cb.updateBoard();
            }
        }
    }

    /**
     * Retrieves the chess board associated with the clicked block, if any.
     * 
     * @param event Triggering event.
     * @return Corresponding chess board or null if not found.
     */
    public MChessBoard getClickedMCB(PlayerInteractEvent event) {
        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock == null || clickedBlock.getType() == Material.AIR) {
            return null;
        }
        for (MChessBoard mcB : chessBoards) {
            if (mcB.isPartOfBoard(clickedBlock.getLocation())) {
                return mcB;
            }
        }
        return null;
    }

    /**
     * AsyncChatEvent behandeln
     * @param event auslösendes Event
     */
    public void handleChatEvent(AsyncChatEvent event) {
        this.latestPlayerInput = PlainTextComponentSerializer.plainText().serialize(event.message());
        if(console) System.out.println(event.getPlayer() + " (Chat): " + latestPlayerInput);
        currentStep.checkForCompletion(); // prüfen, ob Eingabe den Step beendet
        event.setCancelled(true);
    }

    /**
     * PlayerInteractionEvent behandeln
     * @param item entsprechendes ControlItem in der Hand des Spielers
     * @param event auslösendes Event
     * je nach benutztem ControlItem wird die entsprechende Funktion ausgeführt
     */
    public void handleInteractionEvent(ControlItem item, PlayerInteractEvent event) {
        MChessBoard mcb = null;
        if(System.currentTimeMillis() < cooldownMillis) return;
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
                if (teleporter.isTeleportBlock(event.getClickedBlock()) && teleporter.isEnabled() && currentStep.getNext() == null) {
                    player.sendMessage("Teleport zu nächstem Level");
                    startNextLevel();
                } else {
                    player.sendMessage("Irgendwas hat hier noch nicht geklappt");
                    player.sendMessage("Aktiv: " + teleporter.isEnabled(), "Next Step: ", "Block: " + teleporter.isTeleportBlock(event.getClickedBlock()));
                }
                break;
            case PLACE_QUEEN:
                tryPlaceQueen(event, false);
                break;
            case PLACE_EXPLODING_QUEEN:
                tryPlaceQueen(event, true);
                break;
            case BACKTRACKING_FORWARD_Q:
                mcb = getClickedMCB(event);
                if(!mcb.isSolved())mcb.animationStepToNextField(new Queen());
                break;
            
            case BACKTRACKING_BACKWARD_Q:
                mcb = getClickedMCB(event);
                mcb.animationReverseStepToNextField(null);
                break;
            
            default:
                player.sendMessage("Fehler beim Teleport.", "Event Item: " + event.getItem(), "Control Item: " + item);
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
}
