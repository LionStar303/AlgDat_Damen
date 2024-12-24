package de.hsmw.algDatDamen.tutorialHandler;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.kyori.adventure.text.Component;

public enum ControlItem {

    PREVIOUS_STEP(Material.RED_DYE, Component.text("zurück")),
    RESET_STEP(Material.GREEN_DYE, Component.text("wiederhole")),
    NEXT_STEP(Material.BLUE_DYE, Component.text("weiter")),
    PLACE_QUEEN(Material.YELLOW_DYE, Component.text("Dame")),
    PLACE_KNIGHT(Material.ORANGE_DYE, Component.text("Springer")),
    PLACE_SUPERQUEEN(Material.LIGHT_BLUE_DYE, Component.text("Superdame")),
    BACKTRACKING_FORWARD(Material.LIME_DYE, Component.text("weiter")),
    BACKTRACKING_BACKWARD(Material.PINK_DYE, Component.text("zurück")),
    NEXT_LEVEL(Material.WHITE_DYE, Component.text("Flohpulver")),
    SPAWN_CARPET(Material.MAGENTA_DYE, Component.text("")),
    SHOW_CARPET(Material.BROWN_DYE, Component.text("")),
    CHECK_CARPET(Material.BLACK_DYE, Component.text(""));

    private final Material material;
    private final Component displayName;

    ControlItem(Material material, Component displayName) {
        this.material = material;
        this.displayName = displayName;
    }

    public Material getMaterial() {
        return material;
    }

    public Component getDisplayName() {
        return displayName;
    }

    // gibt den ItemStack zurück, z.B. um Item ins Inventar zu legen
    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(displayName);
        item.setItemMeta(meta);
        return item;
    }

    // gibt das auf ein übergebenes Item zutreffende ControlItem zurück
    public static ControlItem fromItem(ItemStack item) {
        return fromItem(item.getType(), item.getItemMeta().displayName());
    }

    public static ControlItem fromItem(Material material, Component displayName) {
        for (ControlItem item : values()) {
            if (item.material == material && item.displayName.equals(displayName)) {
                return item;
            }
        }
        System.out.println("Kein Controll Item erkannt.");
        return null; // kein passendes Control Item
    }
}
