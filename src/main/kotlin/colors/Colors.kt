package colors

/**
 * ANSI color codes for terminal text output.
 * Used to highlight Reversi board elements in the console.
 */
object Colors {
    const val RESET = "\u001B[0m"
    const val RED = "\u001B[31m"     // Black pieces (#)
    const val GREEN = "\u001B[32m"   // White pieces (@)
    const val YELLOW = "\u001B[33m"  // Valid target moves (*)
}
