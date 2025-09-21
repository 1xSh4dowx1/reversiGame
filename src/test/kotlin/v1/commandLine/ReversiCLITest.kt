package v1.commandLine

import v1.commandLine.ReversiCLI
import kotlin.test.*

/**
 * Unit tests for the [ReversiCLI] class.
 *
 * Verifies that commands are parsed correctly and invalid inputs are properly handled.
 */
class ReversiCLITest {

    /**
     * Tests that a valid play command (e.g., "play 4c") is correctly parsed
     * into row and column indices.
     */
    @Test
    fun playCommandTest() {
        val cli = ReversiCLI()
        val move = cli.getMove("play 4c")
        assertNotNull(move)
        assertEquals(3, move.first)  // row = 4 -> index 3
        assertEquals(2, move.second) // col = c -> index 2
    }

    /**
     * Tests that the "exit" command returns null, indicating the game should terminate.
     */
    @Test
    fun exitCommandTest() {
        val cli = ReversiCLI()
        val move = cli.getMove("exit")
        assertNull(move)
    }

    /**
     * Tests that invalid commands throw an exception, including:
     * - Invalid row/column (e.g., "play 0z")
     * - Unknown commands (e.g., "move 4c")
     * - Incomplete commands (e.g., "play")
     */
    @Test
    fun invalidCommandTest() {
        val cli = ReversiCLI()
        assertFails { cli.getMove("play 0z") }
        assertFails { cli.getMove("move 4c") }
        assertFails { cli.getMove("play") }
    }
}
