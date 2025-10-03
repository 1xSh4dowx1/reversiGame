package model

import kotlin.test.*

class BoardTests {

    @Test
    fun initialBoardHasFourPieces() {
        val board = Board.initial()
        assertEquals(4, board.cells.size)
    }

    @Test
    fun validMovesExistAtStart() {
        val board = Board.initial()
        val moves = board.validMoves()
        assertTrue(moves.isNotEmpty(), "Initial board should have valid moves")
    }

    @Test
    fun playingInvalidMoveThrowsException() {
        val board = Board.initial()
        val pos = Position.of(0, 0)
        assertFailsWith<IllegalArgumentException> {
            board.play(pos)
        }
    }
}
