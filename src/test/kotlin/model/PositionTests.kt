package model

import kotlin.test.Test
import kotlin.test.assertEquals

class PositionTests {

    @Test
    fun ofRowColCreatesCorrectIndex() {
        val pos = Position.of(2, 3)
        assertEquals(2, pos.row)
        assertEquals(3, pos.col)
    }

    @Test
    fun invokeCreatesFromIndex() {
        val pos = Position(10)
        assertEquals(10, pos.index)
    }

    @Test
    fun valuesHasAllBoardPositions() {
        assertEquals(Position.BOARD_CELLS, Position.values.size)
    }
}
