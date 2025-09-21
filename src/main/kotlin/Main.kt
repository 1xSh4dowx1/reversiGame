import v2.commandLine.ReversiCLI2
import v1.commandLine.ReversiCLI


/**
 * Entry point of the Reversi application. Choose game1 or game2 for different type of boards and implementations.
 */
fun main() {

    val game1 = ReversiCLI()
    val game2 = ReversiCLI2()
    game1.start()
}
