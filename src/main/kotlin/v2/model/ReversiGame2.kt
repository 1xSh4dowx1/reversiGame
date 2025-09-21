package v2.model

import colors.Colors


/**
 * Immutable Reversi game using BitBoard and Player2 enum.
 *
 * Each valid move returns a new instance (functional style).
 */
class ReversiGame2(
    val board: BitBoard = BitBoard(),
    val currentPlayer: Player = Player.PLAYER1
) {

    fun printGame() {
        printPlayer()
        board.printBoard(getValidMoves())
        printScore()
    }

    private fun printPlayer() {
        val symbol = board.playerSymbol(currentPlayer.value)
        val colored = if (currentPlayer == Player.PLAYER1)
            "${Colors.GREEN}$symbol${Colors.RESET}"
        else
            "${Colors.RED}$symbol${Colors.RESET}"
        println("You are player $colored")
    }

    private fun printScore() {
        val p1 = board.count(BitBoard.PLAYER1)
        val p2 = board.count(BitBoard.PLAYER2)
        println("${board.playerSymbol(BitBoard.PLAYER1)} = $p1 | ${board.playerSymbol(BitBoard.PLAYER2)} = $p2")

    }

    /**
     * Returns a list of valid moves for the current player.
     */
    fun getValidMoves(): List<Move> {
        val moves = mutableListOf<Move>()
        for (row in 0..<8) {
            for (col in 0..<8) {
                if (board.get(row, col) == BitBoard.EMPTY &&
                    hasCapture(row, col, currentPlayer)
                ) {
                    moves.add(Move(row to col))
                }
            }
        }
        return moves
    }

    /**
     * Checks if placing a piece at (row, col) captures any opponent pieces.
     */
    private fun hasCapture(row: Int, col: Int, player: Player): Boolean {
        val opponent = player.opponent().value
        for (dRow in -1..1) {
            for (dCol in -1..1) {
                if (dRow == 0 && dCol == 0) continue
                var r = row + dRow
                var c = col + dCol
                var foundOpponent = false
                while (r in 0..7 && c in 0..7) {
                    val v = board.get(r, c)
                    if (v == opponent) foundOpponent = true
                    else if (v == player.value && foundOpponent) return true
                    else break
                    r += dRow
                    c += dCol
                }
            }
        }
        return false
    }

    /**
     * Plays a move if valid; returns new ReversiGame2 or null.
     */
    fun play(move: Move): ReversiGame2? {
        if (board.get(move.row, move.col) != BitBoard.EMPTY) return null
        if (!hasCapture(move.row, move.col, currentPlayer)) return null

        val newBoard = boardCopy()
        newBoard.set(move.row, move.col, currentPlayer.value)
        flipPieces(newBoard, move.row, move.col, currentPlayer)

        return ReversiGame2(newBoard, currentPlayer.opponent())
    }

    /**
     * Creates a copy of the current BitBoard.
     */
    private fun boardCopy(): BitBoard {
        val copy = BitBoard()
        for (r in 0..<8) {
            for (c in 0..<8) {
                copy.set(r, c, board.get(r, c))
            }
        }
        return copy
    }

    /**
     * Flips opponent pieces in all directions starting from (row, col).
     */
    private fun flipPieces(board: BitBoard, row: Int, col: Int, player: Player) {
        val opponent = player.opponent().value
        for (dRow in -1..1) {
            for (dCol in -1..1) {
                if (dRow == 0 && dCol == 0) continue
                val toFlip = mutableListOf<Pair<Int, Int>>()
                var r = row + dRow
                var c = col + dCol
                while (r in 0..<8 && c in 0..<8) {
                    val v = board.get(r, c)
                    if (v == opponent) toFlip.add(r to c)
                    else if (v == player.value) {
                        for ((fr, fc) in toFlip) board.set(fr, fc, player.value)
                        break
                    } else break
                    r += dRow
                    c += dCol
                }
            }
        }
    }

    /**
     * Counts the number of pieces of a given player.
     */
    fun countPieces(player: Player): Int = board.count(player.value)

    /**
     * Returns the current game state.
     */
    fun gameState(): GameState = GameState.evaluate(board)

    /**
     * Returns a string representation of the board.
     */
    override fun toString(): String = board.toString()
}
