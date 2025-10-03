package model

import kotlin.test.Test
import kotlin.test.assertEquals

class GameResultTest {

    @Test
    fun testBlackWin() {
        val cells = mutableMapOf<Position, Player>()
        for (i in 0..<64) cells[Position(i)] = if (i < 40) Player.BLACK else Player.WHITE
        val game = Game(Board(cells))
        assertEquals(GameResult.Winner(Player.BLACK), game.result())
    }

    @Test
    fun testDraw() {
        val cells = mutableMapOf<Position, Player>()
        for (i in 0..<64) cells[Position(i)] = if (i % 2 == 0) Player.BLACK else Player.WHITE
        val game = Game(Board(cells))
        assertEquals(GameResult.Draw, game.result())
    }

    @Test
    fun testOngoing() {
        val game = Game()
        assertEquals(GameResult.Ongoing, game.result())
    }
}
