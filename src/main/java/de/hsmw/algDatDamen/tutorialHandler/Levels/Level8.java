package de.hsmw.algDatDamen.tutorialHandler.Levels;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.hsmw.algDatDamen.tutorialHandler.Level;
import de.hsmw.algDatDamen.tutorialHandler.NPCTrack;
import de.hsmw.algDatDamen.tutorialHandler.Step;
import de.hsmw.algDatDamen.tutorialHandler.Tutorial;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class Level8 extends Level {

    private final static String LEVEL_NAME = "Level 8 - Freies Spiel";
    private final static String LEVEL_DESCRIPTION = "Beachte das dieses Level nicht mehr Teil des eigentlichen Tutorials ist und somit nicht umfassend getestet wurde, es kann also zu Fehlern kommen";

    public Level8(boolean console, Player player, Tutorial parent) {
        this(console, player, new Location(player.getWorld(), -17, -44, 144, 150, 0),
                new Location(player.getWorld(), -38, -43, 140), parent);
    }

    public Level8(boolean console, Player player, Location startLocation, Location teleporterLocation,
            Tutorial parent) {
        this(console, player, startLocation, teleporterLocation, false, parent);
    }

    public Level8(boolean console, Player player, Location startLocation, Location teleporterLocation,
            boolean completed, Tutorial parent) {
        // ruft den Konstruktor der Elternklasse Level auf
        super(console, LEVEL_NAME, LEVEL_DESCRIPTION, player, startLocation, teleporterLocation, completed, parent);
    }

    @Override
    /*
     * erstellt alle für das Level benötigten Schachbretter und speichert diese in
     * chessBoards
     */
    protected void configureChessBoards() {
    }

    @Override
    public void initializeSteps() {
        currentStep = new Step(
                () -> {
                    setInventory();
                    player.setGameMode(GameMode.CREATIVE);
                    ItemStack emerald = new ItemStack(Material.EMERALD);
                    ItemMeta meta = emerald.getItemMeta();

                    if (meta != null) {
                        meta.displayName(Component.text("Developer Menü", NamedTextColor.BLUE));
                        meta.addEnchant(Enchantment.FORTUNE, 1, true);
                        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

                        emerald.setItemMeta(meta);
                    }

                    player.getInventory().addItem(emerald);
                    npc.playTrack(NPCTrack.NPC_801_INTRO);
                    npc.moveVillagerWithPathfinding(new Location(player.getWorld(), -19, -44, 136), 0.5);
                },
                () -> {
                    setInventory();
                    player.setGameMode(GameMode.SURVIVAL);
                });

        Step setupStep = currentStep;

        setupStep.setNext(new Step(
                () -> {
                },
                () -> {
                }));

        currentStep.backLink();
    }

}
