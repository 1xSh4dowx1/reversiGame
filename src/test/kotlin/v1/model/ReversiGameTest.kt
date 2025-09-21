package v1.model

import v1.model.Move
import v1.model.ReversiGame
import v1.model.player1
import v1.model.player2
import kotlin.test.*

/**
 * Unit tests for the [ReversiGame] class.
 *
 * Verifies the initial state of the game, valid moves, immutability,
 * piece counting, and proper capturing behavior.
 */
class ReversiGameTest {

    /**
     * Tests that the initial board has exactly 4 valid moves
     * for the first player.
     */
    @Test
    fun initialValidOptionsTest() {
        val game = ReversiGame()
        val moves = game.getValidMoves()
        assertEquals(4, moves.size)
    }

    /**
     * Tests that performing a valid move creates a new [ReversiGame] instance,
     * ensuring immutability, and that the current player switches correctly.
     */
    @Test
    fun newPlayerTest() {
        val game = ReversiGame()
        val move = game.getValidMoves().first()
        val newGame = game.play(move)

        assertNotNull(newGame)
        assertNotSame(game, newGame)
        assertNotEquals(game.currentPlayer, newGame.currentPlayer)
    }

    /**
     * Tests that attempting to play an invalid move (e.g., at position 0,0)
     * returns null.
     */
    @Test
    fun invalidPlayTest() {
        val game = ReversiGame()
        val move = Move(0, 0)
        val result = game.play(move)
        assertNull(result)
    }

    /**
     * Tests that the initial piece count is correct:
     * 2 pieces for player1 and 2 pieces for player2.
     */
    @Test
    fun countStartPiecesTest() {
        val game = ReversiGame()
        val p1Count = game.board.grid.sumOf { row -> row.count { it == player1 } }
        val p2Count = game.board.grid.sumOf { row -> row.count { it == player2 } }

        assertEquals(2, p1Count)
        assertEquals(2, p2Count)
    }

    /**
     * Tests that after a valid move, the number of pieces for player1
     * increases if a capture occurs, verifying correct flipping of opponent pieces.
     */
    @Test
    fun validCaptureTest() {
        var game = ReversiGame()
        val move = game.getValidMoves().first()
        game = game.play(move)!!

        val p1Count = game.board.grid.sumOf { row -> row.count { it == player1 } }
        assertTrue(p1Count > 2)
    }
}
