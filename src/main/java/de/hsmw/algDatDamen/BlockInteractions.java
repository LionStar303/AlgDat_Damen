package de.hsmw.algDatDamen;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.hsmw.algDatDamen.tutorialHandler.ControlItem;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class BlockInteractions implements Listener {

    @EventHandler
    public void onBlockBreak(PlayerInteractEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void PlayerDropItemEvent(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = event.getItem();
        Block block = event.getClickedBlock();

        // Sicherheitsprüfung: Ist der Block null?
        if (block != null) {

            // Tag, Nacht Setzen
            if ((block.getType() == Material.POLISHED_BLACKSTONE_BUTTON || block.getType() == Material.SPRUCE_WALL_SIGN) && block.getLocation().getBlockX() == 9
                    && (block.getLocation().getBlockY() == -44 || block.getLocation().getBlockY() == -43)) {

                World world = event.getPlayer().getWorld();

                // case für z-koordinates
                switch (block.getLocation().getBlockZ()) {
                    // Tag
                    case 175:
                        world.setTime(2000);
                        break;

                    // Tutorial starten
                    case 176:
                        event.getPlayer().performCommand("starttutorial");
                        break;

                    // Nacht
                    case 177:
                        world.setTime(18000);
                        break;
                    default:
                        break;
                }
            }
        }

        if (itemInHand == null) {
            return;
        }

        Material itemInHandType = itemInHand.getType();

        // Check for Development menu item
        if (itemInHandType == Material.EMERALD && itemInHand.hasItemMeta() && itemInHand.getItemMeta().displayName()
                .equals(Component.text("Developer Menü", NamedTextColor.BLUE))) {
            AlgDatDamen.devMenu.openInventory(player, event);
            event.setCancelled(true);
        }

        // Prüfen, ob das Item ein ControlItem ist
        ControlItem controlItem = ControlItem.fromItem(event.getItem());
        if (controlItem == null) {
            return; // Kein gültiges ControlItem, nichts weiter tun
        }

        // Passendes Tutorial für den Spieler finden
        Tutorial playerTutorial = AlgDatDamen.saveManager.getTutorialList().stream()
                .filter(t -> t.getPlayer().equals(player))
                .findFirst()
                .orElse(null);

        if (playerTutorial != null) {
            // Event an das aktuelle Level des Tutorials übergeben
            playerTutorial.getCurrentLevel().handleInteractionEvent(controlItem, event);
            event.setCancelled(true); // Nur abbrechen, wenn etwas verarbeitet wurde
        }
    }

}
