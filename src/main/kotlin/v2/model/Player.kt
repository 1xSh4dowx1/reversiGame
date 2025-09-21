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

    companion object {
        /**
         * Converts an integer (from BitBoard) into a Player2 enum.
         *
         * @param value The integer value (1 or 2)
         * @return Corresponding Player2
         * @throws IllegalArgumentException if value is invalid
         */
        fun fromValue(value: Int): Player = when (value) {
            BitBoard.PLAYER1 -> PLAYER1
            BitBoard.PLAYER2 -> PLAYER2
            else -> throw IllegalArgumentException("Invalid player value: $value")
        }
    }
}
