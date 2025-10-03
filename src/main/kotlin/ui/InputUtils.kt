package ui

import model.Position

fun String.toPositionOrNull(): Position? {
    if (length != 2) return null
    val colChar = this[0].lowercaseChar()
    val rowChar = this[1]
    val col = colChar - 'a'
    val row = rowChar.digitToIntOrNull()?.minus(1) ?: return null
    return if (row in 0..<Position.BOARD_SIZE && col in 0..<Position.BOARD_SIZE)
        Position.of(row, col)
    else null
}