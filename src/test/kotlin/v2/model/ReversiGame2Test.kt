package v2.model

import kotlin.test.*

/**
 * Unit tests for the [ReversiGame2] class.
 *
 * Verifies game initialization, valid moves, playing, captures, and counting.
 */
class ReversiGame2Test {

    /**
     * Tests that the initial game has 4 valid moves for PLAYER1.
     */
    @Test
    fun initialValidMovesTest() {
        val game = ReversiGame2()
        val moves = game.getValidMoves()
        assertEquals(4, moves.size)
    }

    /**
     * Tests that playing a valid move returns a new game instance with switched player.
     */
    @Test
    fun playValidMoveTest() {
        val game = ReversiGame2()
        val move = game.getValidMoves().first()
        val newGame = game.play(move)
        assertNotNull(newGame)
        assertNotSame(game, newGame)
        assertNotEquals(game.currentPlayer, newGame.currentPlayer)
    }

    /**
     * Tests that an invalid move returns null.
     */
    @Test
    fun playInvalidMoveTest() {
        val game = ReversiGame2()
        val invalidMove = Move(0 to 0)
        val result = game.play(invalidMove)
        assertNull(result)
    }

    /**
     * Tests that piece counting works correctly.
     */
    @Test
    fun countPiecesTest() {
        val game = ReversiGame2()
        val p1Count = game.countPieces(Player.PLAYER1)
        val p2Count = game.countPieces(Player.PLAYER2)
        assertEquals(2, p1Count)
        assertEquals(2, p2Count)
    }
}
