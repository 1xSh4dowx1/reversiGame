package ui

import model.*

class Command(
    val isTerminate: Boolean = false,
    val execute: (args: List<String>, clash: Clash) -> Clash = { _, clash -> clash }
)

private fun commandNoArgs(action: Clash.() -> Clash) =
    Command { _, clash -> clash.action() }

private fun commandWithName(action: Clash.(String) -> Clash) =
    Command { args, clash ->
        require(args.isNotEmpty()) { "Missing name" }
        clash.action(args[0])
    }

private val playCommand = Command { args, clash ->
    require(args.isNotEmpty()) { "Missing position" }
    val arg = args[0]
    val pos = arg.toIntOrNull()?.let { Position(it) }
        ?: arg.toPositionOrNull()
        ?: throw IllegalArgumentException("Invalid position: $arg")
    clash.play(pos)
}

fun getCommands(fileStorage: GameStorage, memoryStorage: GameStorage) = mapOf(
    "EXIT" to Command(isTerminate = true),

    "NEW" to Command { args, _ ->
        val symbol = args.firstOrNull()
        val player = when (symbol) {
            Player.BLACK.symbol.toString() -> Player.BLACK
            Player.WHITE.symbol.toString() -> Player.WHITE
            else -> Player.BLACK
        }
        val name = if (args.size > 1) args[1] else "local"
        val chosenStorage = if (name == "local") memoryStorage else fileStorage
        Clash(chosenStorage).start(name, player)
    },

    "JOIN" to commandWithName { name ->
        join(name)
    },

    "PLAY" to playCommand,

    "PASS" to commandNoArgs { pass() },

    "REFRESH" to commandNoArgs { refresh() },

    "SHOW" to Command { _, clash ->
        (clash as? ClashRun)?.also { runClash ->
            println(runClash.game.show(runClash.sidePlayer, runClash.name))
        } ?: clash
    },

    "TARGETS" to Command { args, clash ->
        require(clash is ClashRun)
        val showTargets = args.firstOrNull()?.uppercase() == "ON"
        ClashRun(clash.storage, clash.name, clash.game.toggleTargets(showTargets), clash.sidePlayer)
    }
)


