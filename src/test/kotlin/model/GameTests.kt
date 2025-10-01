package model

import kotlin.test.*

class GameTests {

    @Test
    fun initialGameSetup() {
        val game = Game()
        val scores = game.board.score()

        assertEquals(2, scores[Player.BLACK])
        assertEquals(2, scores[Player.WHITE])
        assertEquals(Player.BLACK, game.board.turn)
    }

    @Test
    fun playValidMoveUpdatesBoard() {
        var game = Game().new(Player.BLACK)
        val pos = Position.of(2, 3) // d3

        game = game.play(pos)

        val scores = game.board.score()
        assertTrue(scores[Player.BLACK]!! > 2)
        assertTrue(scores[Player.WHITE]!! < 2)
    }

    @Test
    fun passChangesTurn() {
        var game = Game().new(Player.BLACK)
        val before = game.board.turn

        game = game.pass()

        assertEquals(before.opponent, game.board.turn)
    }
}
