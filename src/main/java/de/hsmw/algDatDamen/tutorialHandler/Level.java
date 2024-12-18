package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public abstract class Level implements Listener {

    protected final static Component EMPTY_LINE = Component.text("----\n", NamedTextColor.AQUA);

    private final Component LEVEL_NAME; // vielleicht als Bossbar anzeigen
    private final Component LEVEL_DESCRIPTION;
    private Location startLocation;
    protected Tutorial parentTutorial;
    protected MChessBoard[] chessBoards;
    protected Player player;
    protected boolean active;
    protected boolean completed;
    protected Step currentStep;
    protected boolean console;
    private int stepCount;
    private int currentStepID;

    public Level(boolean console, String name, String description, Player player, Location startLocation, boolean completed, Tutorial parent) {
        this.console = console;
        this.LEVEL_NAME = Component.text(name, NamedTextColor.BLUE);
        this.LEVEL_DESCRIPTION = Component.text(description, NamedTextColor.AQUA);
        this.player = player;
        this.startLocation = startLocation;
        this.completed = completed;
        this.parentTutorial = parent;
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

    public void handleEvent(ControlItem item) {
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
            default:
                break;
        }
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
