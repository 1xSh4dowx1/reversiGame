import model.*
import storage.*
import ui.getCommands
import ui.readCommand

/**
 * Console entry point for Reversi with multiplayer support.
 */

fun main() {
    val fileStorage: GameStorage = TextFileStorage("games", GameSerializer)
    val memoryStorage: GameStorage = MemoryStorage()
    var clash = Clash(fileStorage)

    println("Welcome to Reversi (Multiplayer)")
    val commands = getCommands(fileStorage, memoryStorage)

    while (true) {
        val (rawName, args) = readCommand()
        val name = rawName.uppercase()
        val command = commands[name]

        if (command == null) {
            println("Invalid command")
        } else if (command.isTerminate) {
            return
        } else {
            try {
                clash = command.execute(args, clash)
                if (clash is ClashRun && name !in listOf("SHOW")) {
                    println(clash.game.show())
                }
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}
