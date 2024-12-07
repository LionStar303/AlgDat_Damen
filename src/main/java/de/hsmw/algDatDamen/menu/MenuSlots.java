package de.hsmw.algDatDamen.menu;

/**
 * Contains the slot numbers of the menu. Only works with Inventories with >= 27
 * slots
 */
public enum MenuSlots {
    ADD_BOARD(0),
    REMOVE_BOARD(9),
    BOARD_SIZE(18),

    WHITE_FIELD_MATERIAL(36),
    BLACK_FIELD_MATERIAL(45),

    QUEEN(19),
    TESTED_QUEEN(28),
    UPDATED_PIECE(10),
    REMOVE_ALL_QUEENS(37),
    ROTATE_QUEENS(46),
    PIECE(1),

    CARPETS(3),
    PLACE_USER_CARPET(11),
    CHECK_USER_CARPETS(20),

    BACKTRACK_FULL(16),
    BACKTRACK_STEP(7),
    BACKTRACK_UNTIL(25),
    BACKTRACK_ROW(24),
    BACKTRACK_ANIMATION(34),
    BACKTRACK_ANIMATION_FAST(43),

    REVERSE_STEP(5),
    REVERSE_ANIMATION(32),
    REVERSE_ANIMATION_FAST(41),

    BONGO_SOLVE(24),


    MOVEMENT_SOLUTION(6);

    public final int slot;

    MenuSlots(int slot) {
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }
}
