package v2.model

import kotlin.test.*

/**
 * Unit tests for the [BitBoard] class.
 *
 * Verifies that cell access, setting, counting, and printing work as expected.
 */
class BitBoardTest {

    /**
     * Tests that the initial central pieces are set correctly.
     */
    @Test
    fun initialPiecesTest() {
        val board = BitBoard()
        assertEquals(BitBoard.PLAYER1, board.get(3, 3))
        assertEquals(BitBoard.PLAYER2, board.get(3, 4))
        assertEquals(BitBoard.PLAYER2, board.get(4, 3))
        assertEquals(BitBoard.PLAYER1, board.get(4, 4))
    }

    /**
     * Tests setting and getting individual cells.
     */
    @Test
    fun setAndGetCellTest() {
        val board = BitBoard()
        board.set(0, 0, BitBoard.PLAYER1)
        assertEquals(BitBoard.PLAYER1, board.get(0, 0))
        board.set(7, 7, BitBoard.PLAYER2)
        assertEquals(BitBoard.PLAYER2, board.get(7, 7))
    }

    /**
     * Tests counting of pieces for each player and empty cells.
     */
    @Test
    fun countPiecesTest() {
        val board = BitBoard()
        assertEquals(2, board.count(BitBoard.PLAYER1))
        assertEquals(2, board.count(BitBoard.PLAYER2))
        assertEquals(60, board.count(BitBoard.EMPTY))
    }

    /**
     * Tests that player symbols are returned correctly.
     */
    @Test
    fun playerSymbolTest() {
        val board = BitBoard()
        assertEquals("@", board.playerSymbol(BitBoard.PLAYER1))
        assertEquals("#", board.playerSymbol(BitBoard.PLAYER2))
        assertEquals(".", board.playerSymbol(BitBoard.EMPTY))
    }
}
