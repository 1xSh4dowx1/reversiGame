package model

/**
 * Represents the result of a Reversi game.
 * Can be one of:
 *  - Ongoing: the game is still in progress.
 *  - Draw: the game ended in a tie.
 *  - Winner: the game has a winner (Player.BLACK or Player.WHITE).
 */
sealed class GameResult {

    /** The game is still ongoing, players can continue making moves */
    data object Ongoing : GameResult()

    /** The game ended in a draw, both players have the same number of pieces */
    data object Draw : GameResult()

    /** Represents a winner of the game */
    data class Winner(val player: Player) : GameResult()
}
