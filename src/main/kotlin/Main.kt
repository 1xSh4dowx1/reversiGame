import model.*
import storage.*

/**
 * Console entry point for Reversi with multiplayer support.
 */
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

fun main() {
    val storage: GameStorage = TextFileStorage("games", GameSerializer)
    var clash = Clash(storage)

    println("Welcome to Reversi (Multiplayer)")
    println("Commands: new (#|@) [name], join <name>, play <idx|d3>, pass, refresh, targets [ON|OFF], show, exit")

    while (true) {
        print("> ")
        val input = readlnOrNull()?.trim()?.split(" ") ?: continue
        val cmd = input.firstOrNull() ?: continue
        val args = input.drop(1)

        try {
            clash = when (cmd) {
                "new" -> {
                    val symbol = args.firstOrNull()
                    val player = when (symbol) {
                        "#" -> Player.BLACK
                        "@" -> Player.WHITE
                        else -> Player.BLACK
                    }
                    val name = if (args.size > 1) args[1] else "local"

                    val chosenStorage =
                        if (name == "local") MemoryStorage()
                        else storage

                    val newClash = Clash(chosenStorage).start(name, player)
                    if (newClash is ClashRun) {
                        println("You are player ${newClash.sidePlayer} in game ${newClash.name}")
                    }
                    newClash
                }

                "join" -> {
                    val newClash = clash.join(args.first())
                    if (newClash is ClashRun) {
                        println("You are player ${newClash.sidePlayer} in game ${newClash.name}")
                    }
                    newClash
                }

                "play" -> {
                    val arg = args.first()
                    val pos = arg.toIntOrNull()?.let { Position(it) }
                        ?: arg.toPositionOrNull()
                        ?: throw IllegalArgumentException("Invalid position: $arg")
                    clash.play(pos)
                }

                "pass" -> clash.pass()
                "refresh" -> clash.refresh()

                "targets" -> {
                    check(clash is ClashRun)
                    val showTargets = args.firstOrNull()?.uppercase() == "ON"
                    ClashRun(
                        clash.storage, clash.name,
                        clash.game.toggleTargets(showTargets),
                        clash.sidePlayer
                    )
                }

                "show" -> {
                    check(clash is ClashRun)
                    println(clash.game.show())
                    clash
                }

                "exit" -> return

                else -> {
                    println("Invalid command")
                    clash
                }
            }

            if (clash is ClashRun && cmd !in listOf("show", "exit")) {
                println(clash.game.show())
            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}

