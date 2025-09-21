package v1.commandLine

import v1.model.ReversiGame
import colors.Colors
import v1.model.Move

/**
 * Command Line Interface (CLI) for playing Reversi using ReversiGame.
 *
 * Highlights valid moves in yellow and prints colored pieces.
 */
class ReversiCLI {

    /**
     * Starts the CLI game loop.
     */
    fun start() {
        var game = ReversiGame()

        println("Commands: play <row><col> (e.g., play 4c) | exit\n")
        game.printGame()

        while (game.getValidMoves().isNotEmpty()) {
            val validMoves = game.getValidMoves()
            if (validMoves.isEmpty()) {
                println("${Colors.RED}No valid moves available!${Colors.RESET}")
                break
            }

            print("Turn ${game.getColoredPlayer()} > ")
            val input = readlnOrNull()?.trim() ?: break

            try {
                val move = getMove(input) ?: break
                val nextGame = game.play(Move(move.first, move.second))
                if (nextGame != null) {
                    println("${Colors.GREEN}Valid move!${Colors.RESET}")
                    game = nextGame
                } else {
                    println("${Colors.RED}Invalid move!${Colors.RESET}")
                }
            } catch (e: IllegalArgumentException) {
                println("${Colors.RED}${e.message}${Colors.RESET}")
            }

            game.printGame()
        }
    }

    /**
     * Parses and validates a command.
     *
     * @param cmd the command string
     * @return a Pair(row, col) for play command, or null for exit
     * @throws IllegalArgumentException if the command is invalid
     */
    fun getMove(cmd: String): Pair<Int, Int>? {
        if (cmd == "exit") return null

        if (cmd.startsWith("play ") && cmd.length == 7) {
            val rowChar = cmd[5]
            val colChar = cmd[6].lowercaseChar()

            if (rowChar !in '1'..'8') throw IllegalArgumentException("Row must be 1–8")
            if (colChar !in 'a'..'h') throw IllegalArgumentException("Column must be a–h")

            val row = rowChar.digitToInt() - 1
            val col = colChar - 'a'
            return row to col
        }

        throw IllegalArgumentException("Unknown command: $cmd")
    }
}
