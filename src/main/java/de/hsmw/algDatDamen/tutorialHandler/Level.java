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
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public abstract class Level implements Listener {

    protected final static Component EMPTY_LINE = Component.text("----\n", NamedTextColor.AQUA);

    private final Component LEVEL_NAME; // vielleicht als Bossbar anzeigen
    private final Component LEVEL_DESCRIPTION;
    private final Location startLocation;
    private final Location teleporterLocation;
    protected Tutorial parentTutorial;
    protected MChessBoard[] chessBoards;
    protected Player player;
    protected boolean active;
    protected boolean completed;
    protected Step currentStep;
    protected boolean console;
    private int stepCount;
    private int currentStepID;
    private long cooldownMillis;

    public Level(boolean console, String name, String description, Player player, Location startLocation, Location teleporterLocation, boolean completed, Tutorial parent) {
        this.console = console;
        this.LEVEL_NAME = Component.text(name, NamedTextColor.BLUE);
        this.LEVEL_DESCRIPTION = Component.text(description, NamedTextColor.AQUA);
        this.player = player;
        this.startLocation = startLocation;
        this.teleporterLocation = teleporterLocation;
        this.completed = completed;
        this.parentTutorial = parent;
        cooldownMillis = 0;
    }

    // Abstrakte Methoden
    protected abstract void configureChessBoards();
    protected abstract void setInventory();
    protected abstract void initializeSteps();

    // Standardmethoden
    public void start() {
        if(console) System.out.println("Level: starte level");
        active = true;
        teleportToStart();

        configureChessBoards();
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

    private void teleportToStart() {
        player.teleport(startLocation);
        player.setFlying(false);
    }

    protected void setControlItems() {
        player.getInventory().setItem(6, ControlItem.PREVIOUS_STEP.getItemStack());
        player.getInventory().setItem(7, ControlItem.RESET_STEP.getItemStack());
        player.getInventory().setItem(8, ControlItem.NEXT_STEP.getItemStack());
    }

    private void nextStep() {
        if(console) System.out.println("running next step");
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
        if(console) System.out.printf("[AlgDat_Damen] Step ID: %d of %d\n", currentStepID, stepCount);
        if (currentStepID <= stepCount) {
            player.setExp((float) currentStepID / stepCount);
        }

        currentStep = currentStep.getNext();
        currentStep.start();
    }

    private void prevStep() {
        if(console) System.out.println("running prev step");
        currentStep.reset();
        if (currentStep.getPrev() != null) {
            currentStep = currentStep.getPrev();
            currentStepID--;
        } 

        if (currentStepID <= stepCount) {
            player.setExp((float) currentStepID / stepCount);
        }

        currentStep.reset(); // reset prev step
        currentStep.start();
    }

    private void resetStep() {
        if(console) System.out.println("running reset step");
        currentStep.reset();
        currentStep.start();
    }

    private void startNextLevel() {
        parentTutorial.incProgress();
        parentTutorial.getCurrentLevel().start();
    }

    private void tryPlaceQueen(PlayerInteractEvent event, boolean tested) {
        for(MChessBoard cb : chessBoards) {
            Block clickedBlock = event.getClickedBlock();
            Location clickedLocation = clickedBlock.getLocation();
            if(console) System.out.println(player.getName() + " clicked on " + clickedLocation.toString());
            if(cb.isActive() && cb.isPartOfBoard(clickedLocation) && clickedBlock.getType() != Material.AIR) {
                Piece existingQueen = cb.getPieceAt(clickedLocation);
                if(existingQueen != null) cb.removePiece(existingQueen);
                else cb.addPiece(clickedLocation, new Queen());
                if(console) System.out.println("Dame gesetzt");
                cb.updatePieces();
                cb.updateCollisionCarpets();
                currentStep.checkForCompletion();
            }
        }
    }

    public void handleEvent(ControlItem item, PlayerInteractEvent event) {
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
            case PLACE_QUEEN:
                tryPlaceQueen(event, false);
                break;
            case NEXT_LEVEL:
                // starte nächstes Level, wenn Teleporter angeklickt und letzter Step
                if(event.getClickedBlock().getLocation().equals(teleporterLocation) && currentStep.getNext() == null) startNextLevel();
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
        if (currentStep == null) stepCount = 0;
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
