package model

import storage.Serializer

/**
 * Serializer for the Reversi Game.
 *
 * Format: <turn> # <targets> # <cells>
 * Example:
 * "BLACK # false # 27:BLACK 28:WHITE 35:WHITE 36:BLACK"
 */
object GameSerializer : Serializer<Game> {
    override fun serialize(data: Game): String {
        val cellsStr = data.board.cells.entries.joinToString(" ") {
            "${it.key.index}:${it.value.name}"
        }
        return "${data.board.turn.name} - ${data.targets} - $cellsStr"
    }

    override fun deserialize(text: String): Game {
        val parts = text.split(" - ")
        require(parts.size == 3) { "Invalid game format" }
        val turn = Player.valueOf(parts[0])
        val targets = parts[1].toBoolean()
        val cells = if (parts[2].isNotBlank()) {
            parts[2].split(" ").associate {
                val (idx, pl) = it.split(":")
                Position(idx.toInt()) to Player.valueOf(pl)
            }
        } else emptyMap()
        return Game(Board(cells, turn), targets)
    }
}
