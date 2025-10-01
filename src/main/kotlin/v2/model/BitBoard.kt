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
data class BitBoard(
    val part1: Long = 0L, // Stores cells 0..31
    val part2: Long = 0L  // Stores cells 32..63
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

    /** Gets the value of a cell at position (row, col). */
    fun get(row: Int, col: Int): Int {
        val index = row * 8 + col
        val (part, shift) = if (index < 32) Pair(part1, index * 2) else Pair(part2, (index - 32) * 2)
        return ((part shr shift) and 3L).toInt()
    }

    /**
     * Returns a new BitBoard with updated cell value (immutability preserved).
     */
    fun set(row: Int, col: Int, value: Int): BitBoard {
        val index = row * 8 + col
        return if (index < 32) {
            val shift = index * 2
            val cleared = part1 and (3L shl shift).inv()
            copy(part1 = cleared or (value.toLong() shl shift))
        } else {
            val shift = (index - 32) * 2
            val cleared = part2 and (3L shl shift).inv()
            copy(part2 = cleared or (value.toLong() shl shift))
        }
    }


    /** Counts how many cells contain the given value. */
    fun count(value: Int): Int =
        (0..<64).count { get(it / 8, it % 8) == value }


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

