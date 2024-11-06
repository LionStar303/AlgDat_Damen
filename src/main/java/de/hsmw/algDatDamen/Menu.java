package de.hsmw.algDatDamen;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Menu implements Listener {
    private final Inventory inventory;

    public Menu(AlgDatDamen plugin) {
        this.inventory = Bukkit.createInventory(null, 27, Component.text("Schach Menü"));

        ItemStack item1 = new ItemStack(Material.DIAMOND);
        ItemMeta item1Meta = item1.getItemMeta();
        item1Meta.displayName(Component.text("Spawne Schachbrett"));
        item1.setItemMeta(item1Meta);

        ItemStack item2 = new ItemStack(Material.BARRIER);
        ItemMeta item2Meta = item2.getItemMeta();
        item2Meta.displayName(Component.text("Entferne Schachbrett"));
        item2.setItemMeta(item2Meta);

        inventory.setItem(11, item1);
        inventory.setItem(12, item2);
    }

    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().title().equals(Component.text("Schach Menü"))) return;

        event.setCancelled(true);  // Verhindert das Bewegen der Items im Menü

        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || !clickedItem.hasItemMeta()) return;

        // Überprüfe das Item und führe entsprechende Funktion aus
        /*if (clickedItem.getType() == Material.DIAMOND) {
            player.sendMessage("Funktion: Schachbrett spawnen");
        }
        if (clickedItem.getType() == Material.BARRIER) {
            player.sendMessage("Funktion: Entferne Schachbrett");
        }*/

        switch (clickedItem.getType()) {
            case DIAMOND: {
                player.sendMessage("Funktion: Spawne Schachbrett");
                break;
            }
            case BARRIER: {
                player.sendMessage("Funktion: Entferne Schachbrett");
                break;
            }
        }
    }
}
