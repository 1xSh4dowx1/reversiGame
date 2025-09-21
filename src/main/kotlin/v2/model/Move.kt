package v2.model

/**
 * Represents a move in Reversi.
 *
 * A move is simply a pair of coordinates (row, col).
 * This class provides type safety instead of passing raw Pairs.
 *
 * It could also be implemented as an inline value class for zero-overhead.
 */
@JvmInline
value class Move(private val position: Pair<Int, Int>) {
    val row: Int get() = position.first
    val col: Int get() = position.second
}
