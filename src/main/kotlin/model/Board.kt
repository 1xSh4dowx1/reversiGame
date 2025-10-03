package model

import colors.Colors

/**
 * Represents the Reversi board state.
 * The board is immutable: all operations return a new instance.
 *
 * Responsible for:
 * - Piece placement
 * - Valid move calculation
 * - Piece flipping
 * - Pretty-printing with colors
 */
data class Board(
    val cells: Map<Position, Player> = emptyMap(),
    val turn: Player = Player.BLACK
) {

    companion object {
        /** 8 directions for Reversi moves */
        private val DIRECTIONS = listOf(
            -1 to -1, -1 to 0, -1 to 1,
            0 to -1,          0 to 1,
            1 to -1,  1 to 0,  1 to 1
        )

        /** Creates the initial Reversi board with 4 starting pieces. */
        fun initial(): Board {
            val mid = Position.BOARD_SIZE / 2
            return Board(
                mapOf(
                    Position.of(mid - 1, mid - 1) to Player.WHITE,
                    Position.of(mid, mid) to Player.WHITE,
                    Position.of(mid - 1, mid) to Player.BLACK,
                    Position.of(mid, mid - 1) to Player.BLACK
                ),
                turn = Player.BLACK
            )
        }
    }

    private fun inBounds(row: Int, col: Int) =
        row in 0..<Position.BOARD_SIZE && col in 0..<Position.BOARD_SIZE

    /** Returns opponent pieces to flip for a given move. */
    private fun flips(pos: Position, player: Player): List<Position> {
        if (pos in cells) return emptyList()
        val flips = mutableListOf<Position>()
        for ((dr, dc) in DIRECTIONS) {
            val line = mutableListOf<Position>()
            var r = pos.row + dr
            var c = pos.col + dc
            while (inBounds(r, c)) {
                val p = Position.of(r, c)
                val piece = cells[p] ?: break
                if (piece == player.opponent) {
                    line.add(p)
                } else if (piece == player) {
                    flips += line
                    break
                } else break
                r += dr
                c += dc
            }
        }
        return flips
    }

    /** Returns all valid moves for the current player. */
    fun validMoves(): Set<Position> =
        Position.values.filter { flips(it, turn).isNotEmpty() }.toSet()

    /** Applies a move and flips pieces. */
    fun play(pos: Position): Board {
        val toFlip = flips(pos, turn)
        require(toFlip.isNotEmpty()) { "Invalid move at $pos" }
        val newCells = cells.toMutableMap()
        newCells[pos] = turn
        for (p in toFlip) newCells[p] = turn
        return Board(newCells, turn.opponent)
    }

    /** Returns the score for both players. */
    fun score(): Map<Player, Int> =
        Player.entries.associateWith { p -> cells.values.count { it == p } }

    /**
     * Pretty-print the board with colors:
     * - Black (#) pieces in RED
     * - White (@) pieces in GREEN
     * - Valid moves (*) in YELLOW
     */
    fun render(showTargets: Boolean = false): String {
        val moves = if (showTargets) validMoves() else emptySet()
        val sb = StringBuilder()

        // Column headers
        sb.append("   ")
        for (c in 0..<Position.BOARD_SIZE) {
            sb.append(('a' + c)).append(" ")
        }
        sb.appendLine()

        // Rows with cells
        for (r in 0..<Position.BOARD_SIZE) {
            sb.append("${r + 1}  ")
            for (c in 0..<Position.BOARD_SIZE) {
                val pos = Position.of(r, c)
                sb.append(
                    when (pos) {
                        in cells -> when (cells[pos]) {
                            Player.BLACK -> "${Colors.RED}${Player.BLACK.symbol}${Colors.RESET}"
                            Player.WHITE -> "${Colors.GREEN}${Player.WHITE.symbol}${Colors.RESET}"
                            else -> "."
                        }
                        in moves -> "${Colors.YELLOW}*${Colors.RESET}"
                        else -> "."
                    }
                ).append(' ')
            }
            sb.appendLine()
        }
        return sb.toString()
    }
}
