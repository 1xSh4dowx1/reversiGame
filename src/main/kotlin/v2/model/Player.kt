package v2.model

/**
 * Enum representing the two players in Reversi.
 *
 * The enum holds the corresponding integer encoding used in BitBoard.
 */
enum class Player(val value: Int) {
    PLAYER1(BitBoard.PLAYER1),
    PLAYER2(BitBoard.PLAYER2);

    /**
     * Gets the opponent of the current player.
     */
    fun opponent(): Player =
        if (this == PLAYER1) PLAYER2 else PLAYER1
}
