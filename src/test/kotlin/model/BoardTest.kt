package model

import kotlin.test.*

/**
 * Unit tests for the [Board] class.
 *
 * Verifies the initial setup of the Reversi board and checks
 * that the correct number of empty cells exist.
 */
class BoardTest {

    /**
     * Tests that the initial board has the 4 central pieces correctly placed:
     * - Top-left center: player1 (@)
     * - Top-right center: player2 (#)
     * - Bottom-left center: player2 (#)
     * - Bottom-right center: player1 (@)
     */
    @Test
    fun initialPiecesTest() {
        val board = Board()

        assertEquals(player1, board.grid[3][3])
        assertEquals(player2, board.grid[3][4])
        assertEquals(player2, board.grid[4][3])
        assertEquals(player1, board.grid[4][4])
    }

    /**
     * Tests that all other cells on the initial board are empty.
     * The total number of empty cells should equal the total
     * board size minus 4 (the 4 central pieces).
     */
    @Test
    fun emptyPiecesCheck() {
        val board = Board()
        val countEmpty = board.grid.sumOf { row -> row.count { it == cell } }
        assertEquals(boardSize * boardSize - 4, countEmpty)
    }
}
