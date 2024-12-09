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
    WHITE_FIELD_MATERIAL(3),
    BLACK_FIELD_MATERIAL(4),

    // Schachfiguren und Aktionen
    PIECE(9),
    QUEEN(10),
    TESTED_QUEEN(11),
    UPDATED_PIECE(12),
    REMOVE_ALL_QUEENS(13),
    ROTATE_QUEENS(14),

    // Teppiche und Interaktionen
    CARPETS(18),
    PLACE_USER_CARPET(19),
    CHECK_USER_CARPETS(20),

    // Backtracking Optionen
    BACKTRACK_FULL(21),
    BACKTRACK_STEP(22),
    BACKTRACK_ROW(23),
    BACKTRACK_UNTIL(24),
    BACKTRACK_ANIMATION(25), // Erweiterung im 27-Slot-Inventar
    BACKTRACK_ANIMATION_FAST(26), // Erweiterung im 27-Slot-Inventar

    // Rückschritte
    REVERSE_STEP(5),
    REVERSE_ANIMATION(6), // Erweiterung
    REVERSE_ANIMATION_FAST(7), // Erweiterung

    // Spezialaktionen
    MOVEMENT_SOLUTION(8),
    BONGO_SOLVE(15); // Für eine gute Gruppierung;

    public final int slot;

    MenuSlots(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}
