package model

import kotlin.test.*


class SerializerTests {

    @Test
    fun serializeAndDeserializeGame() {
        val game = Game().new(Player.BLACK)
        val text = GameSerializer.serialize(game)
        val copy = GameSerializer.deserialize(text)
        assertEquals(game.board.turn, copy.board.turn)
        assertEquals(game.board.cells.size, copy.board.cells.size)
    }

    @Test
    fun deserializeEmptyBoard() {
        val text = "BLACK # false # "
        val game = GameSerializer.deserialize(text)
        assertEquals(Player.BLACK, game.board.turn)
        assertTrue(game.board.cells.isEmpty())
    }
}
