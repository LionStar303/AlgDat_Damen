package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.hsmw.algDatDamen.ChessBoard.MChessBoard;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public abstract class Level implements Listener{

    private final String BACK_TEXT = "zurück";
    private final String RETURN_TEXT = "wiederhole";
    private final String FORWARD_TEXT = "weiter";

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
    protected void setControlItems() {
        ItemStack backItem = new ItemStack(Material.RED_DYE);
        ItemStack returnItem = new ItemStack(Material.GREEN_DYE);
        ItemStack forwardItem = new ItemStack(Material.BLUE_DYE);
        
        ItemMeta meta = backItem.getItemMeta();
        meta.displayName(Component.text(BACK_TEXT));
        backItem.setItemMeta(meta);

        meta = returnItem.getItemMeta();
        meta.displayName(Component.text(RETURN_TEXT));
        returnItem.setItemMeta(meta);

        meta = forwardItem.getItemMeta();
        meta.displayName(Component.text(FORWARD_TEXT));
        forwardItem.setItemMeta(meta);
        
        player.getInventory().setItem(6, backItem);
        player.getInventory().setItem(7, returnItem);
        player.getInventory().setItem(8, forwardItem);
    }

    private void nextStep() {
        // return wenn currentStep noch nicht abgeschlossen oder letzter Step
        if(!currentStep.completed()) {
            player.sendMessage(Component.text("Du musst den aktuellen Schritt erst abschließen.", NamedTextColor.RED));
            return;
        }
        if(currentStep.getNext() == null) {
            player.sendMessage(Component.text("Du kannst ins nächste Level vorrücken.", NamedTextColor.RED));
            return;
        }
        currentStep = currentStep.getNext();
    }
    private void prevStep() {
        currentStep.reset();
        if(currentStep.getPrev() != null) currentStep = currentStep.getPrev();
    }
    private void resetStep() {
        currentStep.reset();
        currentStep.start();
    }

    public void handleEvent(PlayerInteractEvent event) {
        // Event wird nur aufgerufen, wenn der Spieler ein Item in der Hand hält
        ItemStack itemInHand = event.getItem();
        if(!itemInHand.hasItemMeta()) return;

        System.out.println("Clicked with " + itemInHand.getType().toString() + " " + itemInHand.getItemMeta().displayName().toString());

        /* slot 6 - red dye - vorheriger Step
         * slot 7 - green dye - wiederhole Step
         * slot 8 - blue dye - nächster Step
        */
        if(itemInHand.getType() == Material.RED_DYE && itemInHand.getItemMeta().displayName().equals(Component.text(BACK_TEXT))) prevStep();
        else if(itemInHand.getType() == Material.GREEN_DYE && itemInHand.getItemMeta().displayName().equals(Component.text(RETURN_TEXT))) resetStep();
        else if(itemInHand.getType() == Material.BLUE_DYE && itemInHand.getItemMeta().displayName().equals(Component.text(FORWARD_TEXT))) nextStep();

        event.setCancelled(true);
    }
}
