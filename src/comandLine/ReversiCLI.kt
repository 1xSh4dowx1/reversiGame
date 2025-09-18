package comandLine

import model.ReversiGame
import colors.Colors
import model.Move

/**
 * Command Line Interface (CLI) for playing Reversi.
 *
 * Interacts with the user and displays the immutable game state,
 * including highlighting valid moves in yellow.
 */
class ReversiCLI {

    /**
     * Starts the CLI game loop.
     */
    fun start() {
        var game = ReversiGame()

        println("Move command (e.g., play 4c)\n")
        game.printGame()

        while (true) {
            val validMoves = game.getValidMoves()
            if (validMoves.isEmpty()) {
                println("${Colors.RED}No valid moves available!${Colors.RESET}")
                break
            }

            print("Turn ${game.getColoredPlayer()} > ")
            val input = readlnOrNull()?.trim() ?: break

            if (input == "exit") break
            if (input.startsWith("play") && input.length >= 7) {
                val (row, col) = getMove(input)
                val move = Move(row, col)
                val nextGame = game.play(move)
                if (nextGame != null) {
                    println("${Colors.GREEN}Valid move!${Colors.RESET}")
                    game = nextGame
                } else {
                    println("${Colors.RED}Invalid move!${Colors.RESET}")
                }
            }
            game.printGame()
        }
    }

    /**
     * Parses a move command (e.g., "play 4c").
     *
     * @param cmd the command string
     * @return a Pair containing row and column indexes
     */
    private fun getMove(cmd: String): Pair<Int, Int> {
        val row = cmd[5].digitToInt() - 1
        val col = cmd[6].lowercaseChar() - 'a'
        return row to col
    }
}
