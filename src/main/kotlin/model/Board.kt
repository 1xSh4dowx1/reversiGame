package model

import colors.Colors

const val player1 = '@'
const val player2 = '#'
const val cell = '.'
const val boardSize = 8
const val halfSize = boardSize / 2

/**
 * Represents the Reversi board (boardSize x boardSize grid).
 * @property grid the current state of the board
 */
class Board(val grid: Array<CharArray>) {

    /**
     * Creates the initial starting board with 4 pieces in the center.
     */
    constructor() : this(
        Array(boardSize) { row ->
            CharArray(boardSize) { col ->
                when {
                    row == halfSize - 1 && col == halfSize - 1 -> player1
                    row == halfSize - 1 && col == halfSize     -> player2
                    row == halfSize     && col == halfSize - 1 -> player2
                    row == halfSize     && col == halfSize     -> player1
                    else -> cell
                }
            }
        }
    )

    /**
     * Prints the board in textual format with colors.
     * Can show valid moves for the current player.
     *
     * @param validMoves list of valid moves for the current player
     */
    fun printBoard(validMoves: List<Move> = emptyList()) {
        println("  A B C D E F G H")
        for (row in 0..<boardSize) {
            print("${row + 1} ")
            for (col in 0..<boardSize) {
                val cell = grid[row][col]
                val symbol = when {
                    cell == player1 -> "${Colors.GREEN}$player1${Colors.RESET}"
                    cell == player2 -> "${Colors.RED}$player2${Colors.RESET}"
                    validMoves.any { it.row == row && it.col == col } ->
                        "${Colors.YELLOW}$cell${Colors.RESET}"
                    else -> model.cell.toString()
                }
                print("$symbol ")
            }
            println()
        }
    }
}
