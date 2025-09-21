package v2.commandLine

import v2.model.*
import colors.Colors

/**
 * Command Line Interface (CLI) for playing Reversi using ReversiGame with the BitBoard.
 *
 * Highlights valid moves in yellow and prints colored pieces.
 */
class ReversiCLI2(
    var game: ReversiGame2 = ReversiGame2()
) {

    /**
     * Starts the CLI game loop.
     */
    fun start() {
        println("Commands: play <row><col> (e.g., play 4c) | exit\n")
        game.printGame()

        while (game.gameState() == GameState.ONGOING) {
            val validMoves = game.getValidMoves()
            if (validMoves.isEmpty()) {
                println("${Colors.RED}No valid moves available!${Colors.RESET}")
                break
            }

            print("Turn ${getColoredPlayer()} > ")
            val input = readlnOrNull()?.trim() ?: break

            val move = parseCommand(input, validMoves)
            if (move == null) {
                if (input.lowercase() == "exit") break
                println("${Colors.RED}Invalid command!${Colors.RESET}")
                continue
            }

            val nextGame = game.play(move)
            if (nextGame != null) {
                println("${Colors.GREEN}Valid move!${Colors.RESET}")
                game = nextGame
            } else {
                println("${Colors.RED}Invalid move!${Colors.RESET}")
            }

            game.printGame()
        }

        println("Game over! Result: ${game.gameState()}")
    }


    /**
     * Parses a command like "play 4f" into a Move if valid.
     *
     * @param cmd the input string command
     * @param validMoves the list of currently valid moves
     * @return a valid Move or null if invalid
     */
    fun parseCommand(cmd: String, validMoves: List<Move>): Move? {
        if (!cmd.startsWith("play") || cmd.length < 7) return null
        val row = cmd[5].digitToIntOrNull()?.minus(1) ?: return null
        val col = cmd[6].lowercaseChar() - 'a'
        if (row !in 0..7 || col !in 0..7) return null

        val move = Move(row to col)
        return if (move in validMoves) move else null
    }



    private fun getColoredPlayer(): String {
        return when (game.currentPlayer) {
            Player.PLAYER1 -> "${Colors.GREEN}${game.board.playerSymbol(Player.PLAYER1.value)}${Colors.RESET}"
            Player.PLAYER2 -> "${Colors.RED}${game.board.playerSymbol(Player.PLAYER2.value)}${Colors.RESET}"
        }
    }
}

