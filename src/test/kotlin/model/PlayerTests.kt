package model

import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerTests {

    @Test
    fun opponentIsCorrect() {
        assertEquals(Player.WHITE, Player.BLACK.opponent)
        assertEquals(Player.BLACK, Player.WHITE.opponent)
    }

    @Test
    fun toStringReturnsSymbol() {
        assertEquals("#", Player.BLACK.toString())
        assertEquals("@", Player.WHITE.toString())
    }
}
