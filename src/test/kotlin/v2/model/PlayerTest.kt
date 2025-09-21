package v2.model

import kotlin.test.*

/**
 * Unit tests for the [Player] enum.
 *
 * Verifies opponent lookup and conversion from BitBoard values.
 */
class PlayerTest {

    /**
     * Tests that opponent() returns the correct opposite player.
     */
    @Test
    fun opponentTest() {
        assertEquals(Player.PLAYER2, Player.PLAYER1.opponent())
        assertEquals(Player.PLAYER1, Player.PLAYER2.opponent())
    }
}
