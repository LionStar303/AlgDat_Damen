package de.hsmw.algDatDamen;

/**
 * Contains the slot numbers of the menu. Only works with Inventories with >= 27 slots
 */
public enum MenuSlots {
    ADD_BOARD(1),
    REMOVE_BOARD(2),
    BOARD_SIZE(3),
    CARPETS(11),
    QUEEN(5),
    TESTED_QUEEN(14),
    REMOVE_ALL_QUEENS(23),
    ROTATE_QUEENS(24),
    BACKTRACK_FULL(7),
    BACKTRACK_STEP(16),
    BACKTRACK_UNTIL(25);

    public final int slot;

    MenuSlots(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}
