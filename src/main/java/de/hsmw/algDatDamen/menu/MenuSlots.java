package de.hsmw.algDatDamen.menu;

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
    BACKTRACK_UNTIL(25),
    BACKTRACK_ANIMATION(26),
    BACKTRACK_ANIMATION_FAST(17),
    PLACE_USER_CARPET(10),
    CHECK_USER_CARPETS(19);


    public final int slot;

    MenuSlots(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}
