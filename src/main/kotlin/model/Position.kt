package model

/**
 * Represents a position on the Reversi board.
 * Each position is identified by an index [0..63] in a 8x8 grid.
 *
 * Provides factories for creating positions from row/column or index.
 */
@JvmInline
value class Position private constructor(val index: Int) {
    val row: Int get() = index / BOARD_SIZE
    val col: Int get() = index % BOARD_SIZE

    companion object {
        const val BOARD_SIZE = 8
        const val BOARD_CELLS = BOARD_SIZE * BOARD_SIZE

        val values = List(BOARD_CELLS) { Position(it) }

        /** Factory from row/col. */
        fun of(row: Int, col: Int): Position =
            values[row * BOARD_SIZE + col]

        /** Factory from index. */
        operator fun invoke(index: Int) = values[index]
    }

    override fun toString() = index.toString()
}
