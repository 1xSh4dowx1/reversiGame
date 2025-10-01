package model

import colors.Colors

/**
 * Represents a Reversi game state.
 * Wraps the board and handles game-level operations.
 */
data class Game(
    val board: Board = Board.initial(),
    val targets: Boolean = false
) {

    /** Starts a new game with a given first player. */
    fun new(first: Player = Player.BLACK): Game =
        Game(Board.initial().copy(turn = first))

    /** Executes a play. */
    fun play(pos: Position): Game =
        copy(board = board.play(pos))

    /** Passes the turn. */
    fun pass(): Game =
        copy(board = board.copy(turn = board.turn.opponent))

    /** Toggles or sets targets ON/OFF. */
    fun toggleTargets(on: Boolean? = null): Game =
        copy(targets = on ?: !targets)

    /**
     * String representation for console:
     * - Calls Board.render() to draw the grid
     * - Adds score and current turn below, with colors
     */
    fun show(): String {
        val sb = StringBuilder()
        sb.append(board.render(targets))

        val scores = board.score()
        val black = scores[Player.BLACK] ?: 0
        val white = scores[Player.WHITE] ?: 0
        sb.appendLine()
        sb.appendLine("${Colors.RED}# = $black${Colors.RESET} | ${Colors.GREEN}@ = $white${Colors.RESET}")

        // Current turn
        val turnColor = if (board.turn == Player.BLACK) Colors.RED else Colors.GREEN
        sb.appendLine("Turn: $turnColor${board.turn.symbol}${Colors.RESET}")

        return sb.toString()
    }
}
