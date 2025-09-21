package v1.model

import colors.Colors

/**
 * Implementation of the Reversi game.
 *
 * Each move creates a new instance of ReversiGame with
 * the updated board and current player
 *
 * @property board the board
 * @property currentPlayer the player who has the next turn
 * @property lobbyID an identifier for the game session
 */
data class ReversiGame(
    val board: Board = Board(),
    val currentPlayer: Char = player1,
    val lobbyID: String = "0"
) {

    /**
     * Prints the full state of the game to the console,
     * including the current player, board with valid moves highlighted,
     * and the score of each player.
     */
    fun printGame() {
        printPlayer()
        board.printBoard(getValidMoves())
        printScore()
    }

    /**
     * Prints the current player's information with lobby ID.
     */
    private fun printPlayer() =
        println("You are player ${getColoredPlayer()} in game $lobbyID")

    /**
     * Prints the current score for both players.
     */
    private fun printScore() =
        println("$player2 = ${countPieces(player2)} | $player1 = ${countPieces(player1)}")

    /**
     * Returns the current player's symbol with an ANSI color applied.
     *
     * @return colored string representing the current player
     */
    fun getColoredPlayer(): String {
        return if (currentPlayer == player1) {
            "${Colors.GREEN}$currentPlayer${Colors.RESET}"
        } else {
            "${Colors.RED}$currentPlayer${Colors.RESET}"
        }
    }

    /**
     * Counts the number of pieces of a given player currently on the board.
     *
     * @param player the player symbol
     * @return the number of pieces belonging to the given player
     */
    private fun countPieces(player: Char): Int =
        board.grid.sumOf { row -> row.count { it == player } }

    /**
     * Returns all valid moves for the current player.
     *
     * @return a list of [Move] objects representing possible moves
     */
    fun getValidMoves(): List<Move> {
        val moves = mutableListOf<Move>()
        for (row in 0..<boardSize) {
            for (col in 0..<boardSize) {
                val move = Move(row, col)
                if (isValidMove(move, currentPlayer)) {
                    moves.add(move)
                }
            }
        }
        return moves
    }

    /**
     * Attempts to play a move for the current player.
     * Returns a new [ReversiGame] instance with the updated board and next player if valid,
     * otherwise returns null.
     *
     * @param move the [Move] to attempt
     * @return a new [ReversiGame] instance if the move is valid, null otherwise
     */
    fun play(move: Move): ReversiGame? {
        if (!isValidMove(move, currentPlayer)) return null
        val newBoard = applyMove(move, currentPlayer)
        val nextPlayer = if (currentPlayer == player1) player2 else player1
        return ReversiGame(newBoard, nextPlayer, lobbyID)
    }

    /**
     * Checks whether a given move is valid for the specified player.
     *
     * @param move the [Move] to validate
     * @param player the player symbol
     * @return true if the move is valid, false otherwise
     */
    private fun isValidMove(move: Move, player: Char): Boolean {
        if (board.grid[move.row][move.col] != cell) return false
        return directions.any { canCapture(move, player, it) }
    }

    /**
     * Checks if placing a piece in a given direction will capture opponent pieces.
     *
     * @param move the [Move] being considered
     * @param player the current player symbol
     * @param dir a pair representing the direction (row, col)
     * @return true if the move can capture opponent pieces in this direction, false otherwise
     */
    private fun canCapture(move: Move, player: Char, dir: Pair<Int, Int>): Boolean {
        val opponent = if (player == player1) player2 else player1
        var row = move.row + dir.first
        var col = move.col + dir.second
        var foundOpponent = false

        while (row in 0..<boardSize && col in 0..<boardSize) {
            when (board.grid[row][col]) {
                opponent -> foundOpponent = true
                player -> return foundOpponent
                else -> break
            }
            row += dir.first
            col += dir.second
        }
        return false
    }

    /**
     * Applies a move to the board in an immutable manner.
     * Returns a new [Board] with the move applied and opponent pieces flipped.
     *
     * @param move the [Move] to apply
     * @param player the player making the move
     * @return a new [Board] instance with the move applied
     */
    private fun applyMove(move: Move, player: Char): Board {
        val opponent = if (player == player1) player2 else player1
        val newGrid = board.grid.map { it.copyOf() }.toTypedArray()

        newGrid[move.row][move.col] = player

        for (dir in directions) {
            if (canCapture(move, player, dir)) {
                var r = move.row + dir.first
                var c = move.col + dir.second
                while (newGrid[r][c] == opponent) {
                    newGrid[r][c] = player
                    r += dir.first
                    c += dir.second
                }
            }
        }
        return Board(newGrid)
    }

    companion object {
        /**
         * List of all 8 possible directions for capturing opponent pieces.
         * Each pair represents a (row, col):
         * top-left, top, top-right, left, right, bottom-left, bottom, bottom-right.
         */
        val directions = listOf(
            -1 to -1, -1 to 0, -1 to 1,
            0 to -1,          0 to 1,
            1 to -1,  1 to 0, 1 to 1
        )
    }
}
