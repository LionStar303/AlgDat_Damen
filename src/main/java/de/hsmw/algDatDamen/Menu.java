package de.hsmw.algDatDamen;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Menu implements Listener {
    private final Inventory inventory;
    private final Map<Integer, CommandData> commandsMap = new HashMap<>();
    private PlayerInteractEvent event;

    public Menu() {
        this.inventory = Bukkit.createInventory(null, 27, Component.text("Schach Menü"));
    }

    public void openInventory(Player player, PlayerInteractEvent event) {
        player.openInventory(inventory);
        this.event = event;
    }

    private record CommandData(String command, int[] arguments) {
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null || !event.getView().title().equals(Component.text("Schach Menü"))) return;

        event.setCancelled(true);

        int slot = event.getSlot();
        System.out.println("Slot: " + slot);

        if (commandsMap.containsKey(slot)) {
            CommandData commandData = commandsMap.get(slot);
            try {
                // Get class
                Class<?> externalClass = Class.forName("de.hsmw.algDatDamen.DevelopmentHandles");
                Object instance = Bukkit.getPluginManager().getPlugin("AlgDatDamen");

                System.out.println(commandData.command());
                System.out.println(Arrays.toString(commandData.arguments()));

                // Get method and invoke it with given arguments
                if (commandData.arguments.length == 0) {
                    Method method = externalClass.getDeclaredMethod(commandData.command, PlayerInteractEvent.class);
                    method.invoke(instance, this.event);
                } else if (commandData.arguments.length == 1) {
                    Method method = externalClass.getDeclaredMethod(commandData.command, PlayerInteractEvent.class, int.class);
                    method.invoke(instance, this.event, commandData.arguments[0]);
                } else {
                    Method method = externalClass.getDeclaredMethod(commandData.command, int[].class);
                    method.invoke(instance, (Object) commandData.arguments);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds a new Item to the menu.
     * @param material The item to be shown.
     * @param displayName The display name of the item.
     * @param slot The slot number.
     * @param command The name of the function to be executed. The function has to be in DevelopmentHandles class.
     * @param arguments Arguments for the given function. It automatically adds the PlayerInteractEvent as first argument.
     *                  The other arguments need to be integers.
     */
    public void addMenuItem(Material material, String displayName, int slot, String command, int... arguments) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(displayName));
        item.setItemMeta(meta);

        // Füge das Item ins Inventar ein
        inventory.setItem(slot, item);

        // Speichere den Befehl und die Argumente in der Map
        commandsMap.put(slot, new CommandData(command, arguments));
    }

}
