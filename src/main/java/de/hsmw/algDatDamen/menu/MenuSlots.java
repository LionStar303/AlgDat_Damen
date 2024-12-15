package de.hsmw.algDatDamen.menu;

/**
 * Contains the slot numbers of the menu. Only works with Inventories with >= 27
 * slots
 */
public enum MenuSlots {
    // Grundlegende Optionen
    ADD_BOARD(0),
    REMOVE_BOARD(1),
    BOARD_SIZE(2),

    // Materialien
    WHITE_FIELD_MATERIAL(45),
    BLACK_FIELD_MATERIAL(46),

    // Schachfiguren und Aktionen
    PIECE(48),
    SPAWN_QUEEN(13),
    TESTED_QUEEN(14),
    UPDATED_PIECE(17),
    REMOVE_ALL_QUEENS(15),
    ROTATE_QUEENS(16),

    // Teppiche und Interaktionen
    CARPETS(18),
    PLACE_USER_CARPET(19),
    CHECK_USER_CARPETS(20),

    // Backtracking Optionen
    BACKTRACK_FULL(32),
    BACKTRACK_STEP(33),
    BACKTRACK_ROW(53),
    BACKTRACK_UNTIL(35),
    BACKTRACK_ANIMATION(41), // Erweiterung im 27-Slot-Inventar
    BACKTRACK_ANIMATION_FAST(42), // Erweiterung im 27-Slot-Inventar

    // Rückschritte
    REVERSE_STEP(34),
    REVERSE_ANIMATION(50), // Erweiterung
    REVERSE_ANIMATION_FAST(51), // Erweiterung

    // Spezialaktionen
    MOVEMENT_SOLUTION(21),
    BONGO_SOLVE(38); // Für eine gute Gruppierung;

    public final int slot;

    MenuSlots(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}
