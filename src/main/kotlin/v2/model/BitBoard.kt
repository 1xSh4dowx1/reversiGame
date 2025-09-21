package v2.model

import colors.Colors

/**
 * BitBoard is a compact representation of a 8x8 Reversi board using only 16 bytes.
 *
 * Each cell is encoded with 2 bits:
 * - 00 -> empty
 * - 01 -> player 1
 * - 10 -> player 2
 *
 * Since the board has 64 cells, we need 64 * 2 = 128 bits.
 * These 128 bits are split across two Long values (64 bits each).
 *
 * This representation is much more memory efficient than an array of Chars.
 */
class BitBoard(
    private var part1: Long = 0L, // Stores cells 0..31
    private var part2: Long = 0L  // Stores cells 32..63
) {

    companion object {
        const val EMPTY = 0
        const val PLAYER1 = 1
        const val PLAYER2 = 2
    }

    init {
        // Set the 4 central starting pieces
        set(3, 3, PLAYER1)
        set(3, 4, PLAYER2)
        set(4, 3, PLAYER2)
        set(4, 4, PLAYER1)
    }

    /**
     * Returns the symbol for a player or empty.
     */
    fun playerSymbol(value: Int): String = when (value) {
        PLAYER1 -> "@"
        PLAYER2 -> "#"
        else -> "."
    }

    /**
     * Gets the value of a cell at position (row, col).
     *
     * @param row Row index (0..7)
     * @param col Column index (0..7)
     * @return The value of the cell: EMPTY, PLAYER1 or PLAYER2
     */
    fun get(row: Int, col: Int): Int {
        val index = row * 8 + col                       // Convert 2D position to linear index (0..63)
        val (part, shift) = if (index < 32) {
                                                        // First half of the board is stored in part1
            Pair(part1, index * 2)
        } else {
                                                        // Second half of the board is stored in part2
                                                        // Subtract 32 because part2 starts at cell index 32
            Pair(part2, (index - 32) * 2)
        }

                                                        // Extract 2 bits by shifting right and masking with binary 11 (decimal 3)
        return ((part shr shift) and 3L).toInt()        // Returns the values 0, 1 or 2 for the correct cells
    }

    /**
     * Sets the value of a cell at position (row, col).
     *
     * @param row Row index (0..7)
     * @param col Column index (0..7)
     * @param value The value to assign: EMPTY, PLAYER1 or PLAYER2
     */
    fun set(row: Int, col: Int, value: Int) {
        val index = row * 8 + col
        if (index < 32) {
            val shift = index * 2
            // Clear the previous 2 bits with AND NOT (mask = 11 << shift)
            part1 = part1 and (3L shl shift).inv()
            // Write the new value in the correct position
            part1 = part1 or (value.toLong() shl shift)
        } else {
            val shift = (index - 32) * 2
            part2 = part2 and (3L shl shift).inv()
            part2 = part2 or (value.toLong() shl shift)
        }
    }

    /**
     * Counts how many cells contain the given value.
     *
     * @param value The value to count (EMPTY, PLAYER1, PLAYER2)
     * @return The number of cells with the given value
     */
    fun count(value: Int): Int {
        var count = 0
        for (i in 0..<64) {
            if (get(i / 8, i % 8) == value) {
                count++
            }
        }
        return count
    }


    /**
     * Prints the board, optionally highlighting valid moves.
     */
    fun printBoard(validMoves: List<Move> = emptyList()) {
        println("  A B C D E F G H")
        for (r in 0..<8) {
            print("${r + 1} ")
            for (c in 0..<8) {
                val v = get(r, c)
                val symbol = when {
                    v == PLAYER1 -> "${Colors.GREEN}${playerSymbol(v)}${Colors.RESET}"
                    v == PLAYER2 -> "${Colors.RED}${playerSymbol(v)}${Colors.RESET}"
                    validMoves.any { it.row == r && it.col == c } -> "${Colors.YELLOW}.${Colors.RESET}"
                    else -> "."
                }
                print("$symbol ")
            }
            println()
        }
    }
}

