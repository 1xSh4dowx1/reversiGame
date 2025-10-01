package model

/**
 * Represents a player in the Reversi game.
 * Black pieces are represented by '#', White pieces by '@'.
 */
enum class Player(val symbol: Char) {
    BLACK('#'),
    WHITE('@');

    /** The opponent of the current player. */
    val opponent: Player
        get() = if (this == BLACK) WHITE else BLACK

    override fun toString() = symbol.toString()
}

