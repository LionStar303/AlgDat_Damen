package de.hsmw.algDatDamen;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import de.hsmw.algDatDamen.tutorialHandler.ControlItem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class BlockInteractions implements Listener {

    @EventHandler
    public void onBlockBreak(PlayerInteractEvent event) {
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
            if (block.getType() == Material.POLISHED_BLACKSTONE_BUTTON && block.getLocation().getBlockX() == 9
                    && block.getLocation().getBlockY() == -44) {

                World world = event.getPlayer().getWorld();

                // case für z-koordinate
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

        // nach Control Item aus Tutorial suchen
        ControlItem controlItem = ControlItem.fromItem(event.getItem());
        if (controlItem != null) {
            // nach passendem Tutorial suchen
            AlgDatDamen.saveManager.getTutorialList().forEach((t) -> {
                if (t.getPlayer().equals(player)) {
                    // Event an Tutorial des Spielers übergeben
                    t.getCurrentLevel().handleInteractionEvent(controlItem, event);
                    event.setCancelled(true);
                    return;
                }
            });
        }
        event.setCancelled(true);
    }

}
