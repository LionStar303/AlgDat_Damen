package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;

public abstract class Level implements Listener{

    private String name; // vielleicht als Bossbar anzeigen
    private String description;
    private Location startLocation;
    private int stepCount;
    protected MChessBoard chessBoard;
    protected Player player;
    protected boolean active;
    protected boolean completed;
    protected Step[] steps = new Step[stepCount];
    protected Step currentStep;

    public Level(String name, String description, MChessBoard chessBoard, Player player, Location startLocation, boolean completed) {
        this.name = name;
        this.description = description;
        this.chessBoard = chessBoard;
        this.player = player;
        this.startLocation = startLocation;
        this.completed = completed;
    }

    public void start() {
        System.out.println("Level: starte level");
        active = true;
        teleportToStart();

        setInventory();
        // Erzeugung eines 8x8 Schachbretts
        chessBoard.setSize(8);
        chessBoard.spawnChessBoard();

        player.setRespawnLocation(startLocation);
        player.sendMessage(name);
        player.sendMessage(description);
        
        currentStep.start();
    }

    private void teleportToStart() {
        player.teleport(startLocation);
        player.setFlying(false);
    }

    protected abstract void setInventory();
    private void nextStep() {
        // return wenn currentStep noch nicht abgeschlossen oder letzter Step
        if(!currentStep.completed() || currentStep.getNext() == null) return;
        currentStep = currentStep.getNext();
    }
    private void prevStep() {
        if(currentStep.getPrev() != null) currentStep = currentStep.getPrev();
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        // return wenn anderer Spieler geklickt hat
        if(player != event.getPlayer()) return;
        ItemStack itemInHand = event.getItem();
        if(itemInHand == null || !itemInHand.hasItemMeta()) return;

        /* red dye - vorheriger Step
         * green dye - wiederhole Step
         * blue dye - nächster Step
        */
        if(itemInHand.getType() == Material.RED_DYE && itemInHand.getItemMeta().displayName().toString() == "zurück") prevStep();
        else if(itemInHand.getType() == Material.GREEN_DYE && itemInHand.getItemMeta().displayName().toString() == "wiederhole") currentStep.start();
        else if(itemInHand.getType() == Material.BLUE_DYE && itemInHand.getItemMeta().displayName().toString() == "weiter") nextStep();
    }
}
