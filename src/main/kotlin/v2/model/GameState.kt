package v2.model

/**
 * Represents the possible game outcomes when no moves remain.
 */
enum class GameState {
    PLAYER1_WINS,
    PLAYER2_WINS,
    DRAW,
    ONGOING;

    companion object {
        /**
         * Evaluates the current game state to determine if it is finished.
         *
         * @param board The board state
         * @return The game result: win, draw, or ongoing
         */
        fun evaluate(board: BitBoard): GameState {
            val p1 = board.count(BitBoard.PLAYER1)
            val p2 = board.count(BitBoard.PLAYER2)
            val empty = board.count(BitBoard.EMPTY)

            return when {
                empty > 0 -> ONGOING
                p1 > p2 -> PLAYER1_WINS
                p2 > p1 -> PLAYER2_WINS
                else -> DRAW
            }
        }
    }
}
