package de.hsmw.algDatDamen.menu;

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

import static de.hsmw.algDatDamen.menu.DevelopmentHandles.*;

public class Menu implements Listener {
    private final Inventory inventory;
    private final Map<Integer, CommandData> commandsMap = new HashMap<>();
    private PlayerInteractEvent event;
    private final int size;

    /**
     * Generates an Inventory Menu with size 27 (3x9)
     */
    public Menu() {
        this.inventory = Bukkit.createInventory(null, 27, Component.text("Schach Menü"));
        this.size = 27;
    }

    /**
     * Generates an inventory menu with the given size.
     * 
     * @param size Has to be a number dividable by 9.
     */
    public Menu(int size) {
        this.inventory = Bukkit.createInventory(null, 27, Component.text("Schach Menü"));
        this.size = size;
    }

    /**
     * Opens the menu inventory and stores the event.
     * 
     * @param player Player which has to see the menu.
     * @param event  Triggering event.
     */
    public void openInventory(Player player, PlayerInteractEvent event) {
        player.openInventory(inventory);
        this.event = event;
    }

    private record CommandData(String command, Integer[] arguments) {
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // For Development Menu
        if (event.getClickedInventory() == null || !event.getView().title().equals(Component.text("Schach Menü")))
            return;
        event.setCancelled(true);

        int slot = event.getSlot();
        System.out.println("Slot: " + slot);

        // Get assigned function and execute it
        if (commandsMap.containsKey(slot)) {
            CommandData commandData = commandsMap.get(slot);
            try {
                // Get class
                Class<?> externalClass = Class.forName("de.hsmw.algDatDamen.menu.DevelopmentHandles");
                Object instance = Bukkit.getPluginManager().getPlugin("AlgDatDamen");

                System.out.println(commandData.command());
                System.out.println(Arrays.toString(commandData.arguments()));

                // Get method and invoke it with given arguments.
                // It automatically adds the event as the first argument.
                if (commandData.arguments.length == 0) {
                    Method method = externalClass.getDeclaredMethod(commandData.command, PlayerInteractEvent.class);
                    method.invoke(instance, this.event);
                } else if (commandData.arguments.length == 1) {
                    Method method = externalClass.getDeclaredMethod(commandData.command, PlayerInteractEvent.class,
                            Integer.class);
                    method.invoke(instance, this.event, commandData.arguments[0]);
                } else {
                    Method method = externalClass.getDeclaredMethod(commandData.command, Integer[].class);
                    method.invoke(instance, (Object) commandData.arguments);
                }

                if (slot == MenuSlots.BACKTRACK_ROW.slot || slot == MenuSlots.BOARD_SIZE.slot
                        || inventory.getItem(slot).getType() == Material.LIGHT_BLUE_STAINED_GLASS_PANE) {
                    return;
                } else {
                    inventory.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void init(Integer boardSize) {
        // - Chess Board Functions
        this.addMenuItem(Material.DIAMOND, "Spawne Schachbrett", MenuSlots.ADD_BOARD, "handleBoardCreation", boardSize);
        this.addMenuItem(Material.BARRIER, "Entferne Schachbrett", MenuSlots.REMOVE_BOARD,
                "removeChessBoardFromGame");
        this.addMenuItem(Material.REDSTONE_TORCH, "Größe: " + boardSize, MenuSlots.BOARD_SIZE, "increaseBoardSize");
        this.addMenuItem(Material.RED_CARPET, "Zeige Teppiche", MenuSlots.CARPETS, "handleCollisionCarpets");
        // - Queen Functions
        this.addMenuItem(Material.IRON_HELMET, "Spawne/Entferne Königin", MenuSlots.QUEEN, "placeQueen");
        this.addMenuItem(Material.GOLDEN_HELMET, "Spawne getestete Königin", MenuSlots.TESTED_QUEEN,
                "placeTestedQueen");
        this.addMenuItem(Material.TNT, "Entferne alle Königinnen", MenuSlots.REMOVE_ALL_QUEENS, "removeAllQueens");
        this.addMenuItem(Material.COMPASS, "Rotiere Königinnen", MenuSlots.ROTATE_QUEENS, "rotateQueens");
        // - Backtrack Functions
        this.addMenuItem(Material.DIAMOND_SWORD, "Löse Schachbrett", MenuSlots.BACKTRACK_FULL, "handleBacktrack");
        this.addMenuItem(Material.IRON_SWORD, "Backtracking nächster Schritt", MenuSlots.BACKTRACK_STEP,
                "handleBacktrackStep");
        this.addMenuItem(Material.IRON_AXE, "Backtrack bis...", MenuSlots.BACKTRACK_UNTIL, "handleBacktrackToRow");
        this.addMenuItem(Material.EMERALD, "Backtrack Zeile: " + backtrackRow, MenuSlots.BACKTRACK_ROW,
                "increaseBacktrackRow");
        this.addMenuItem(Material.DIAMOND_AXE, "Backtracking Animation", MenuSlots.BACKTRACK_ANIMATION,
                "handleBacktrackAnimation");
        this.addMenuItem(Material.GOLDEN_AXE, "Backtracking Animation schnell", MenuSlots.BACKTRACK_ANIMATION_FAST,
                "handleBacktrackAnimationQueenStep");
        this.addMenuItem(Material.GREEN_CARPET, "Damen Movement Carpets checken", MenuSlots.CHECK_USER_CARPETS,
                "checkUserCarpets");
        this.addMenuItem(Material.PURPLE_CARPET, "Damen Movement Carpet setzen", MenuSlots.PLACE_USER_CARPET,
                "placeUserCarpet");
        this.addMenuItem(customWhiteFieldMaterial, "Ändere Weiße Blöcke", MenuSlots.WHITE_FIELD_MATERIAL,
                "changeWhiteFieldMaterial");
        this.addMenuItem(customBlackFieldMaterial, "Ändere Schwarze Blöcke", MenuSlots.BLACK_FIELD_MATERIAL,
                "changeBlackFieldMaterial");

        this.fillEmptySlots();
    }

    /**
     * Adds a new Item to the menu.
     * 
     * @param material    The item to be shown.
     * @param displayName The display name of the item.
     * @param slot        The slot number.
     * @param function    The name of the function to be executed. The function has
     *                    to be in DevelopmentHandles class.
     * @param arguments   Arguments for the given function. It automatically adds
     *                    the PlayerInteractEvent as first argument.
     *                    The other arguments need to be Integers.
     */
    public void addMenuItem(Material material, String displayName, MenuSlots slot, String function,
            Integer... arguments) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text(displayName));
        item.setItemMeta(meta);

        // Füge das Item ins Inventar ein
        inventory.setItem(slot.getSlot(), item);

        // Speichere den Befehl und die Argumente in der Map
        commandsMap.put(slot.getSlot(), new CommandData(function, arguments));
    }

    /**
     * Updates the name of the item in the given slot.
     * 
     * @param slot        The MenuSlot.
     * @param displayName New display name.
     */
    public void updateItemName(MenuSlots slot, String displayName) {
        ItemStack item = inventory.getItem(slot.getSlot());
        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta == null)
            return;

        itemMeta.displayName(Component.text(displayName));
        item.setItemMeta(itemMeta);
    }

    public void updateItemMaterial(MenuSlots slot, Material material) {
        inventory.setItem(slot.getSlot(), new ItemStack(material));
    }

    public void fillEmptySlots() {
        for (int i = 0; i < size; i++) {
            if (inventory.getItem(i) == null)
                inventory.setItem(i, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
        }
    }

}
