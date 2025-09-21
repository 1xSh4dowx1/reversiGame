package v2.commandLine

import kotlin.test.*

/**
 * Unit tests for the [ReversiCLI2] class.
 *
 * Verifies command parsing and validation.
 */
class ReversiCLI2Test {

    /**
     * Tests parsing of a valid "play" command.
     */
    @Test
    fun parseValidPlayCommandTest() {
        val cli = ReversiCLI2()
        val validMoves = cli.game.getValidMoves()
        val move = cli.parseCommand("play 3e", validMoves)
        assertNotNull(move)
        assertEquals(2, move.row)
        assertEquals(4, move.col)
    }

    /**
     * Tests that "exit" returns null.
     */
    @Test
    fun parseExitCommandTest() {
        val cli = ReversiCLI2()
        val move = cli.parseCommand("exit", emptyList())
        assertNull(move)
    }

    /**
     * Tests invalid commands return null.
     */
    @Test
    fun parseInvalidCommandTest() {
        val cli = ReversiCLI2()
        val validMoves = cli.game.getValidMoves()
        assertNull(cli.parseCommand("play 0z", validMoves))
        assertNull(cli.parseCommand("move 4c", validMoves))
        assertNull(cli.parseCommand("play", validMoves))
    }
}
