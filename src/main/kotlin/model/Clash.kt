package model

import storage.Storage

/** Alias for game storage. */
typealias GameStorage = Storage<String, Game> // Type alias makes use of an existent type while giving it an alternative name

/** Base class for a Clash, referencing the storage. */
open class Clash(val storage: GameStorage) // Open class -> generic base class that can have subclasses (ClashRun)

/** Running clash with a name, game state and side player. */
class ClashRun(
    storage: GameStorage,
    val name: String,
    val game: Game,
    val sidePlayer: Player
) : Clash(storage)

/** Start a new clash. */
fun Clash.start(name: String, first: Player): Clash = ClashRun(
    storage, name, Game().new(first), first
).also { storage.create(name, it.game) }

/** Join an existing clash. */
fun Clash.join(name: String): Clash {
    val game = checkNotNull(storage.read(name)) { "Clash $name not found" }
    // Assign opponent based on first player stored in the game
    val joinPlayer = game.board.turn.opponent
    return ClashRun(storage, name, game, joinPlayer)
}


/** Refresh clash state. */
fun Clash.refresh(): Clash {
    check(this is ClashRun) { "Not in a running clash" }
    val newGame = checkNotNull(storage.read(name)) { "Clash not found" }
    check(newGame != game) { "No changes in clash $name" }
    return ClashRun(storage, name, newGame, sidePlayer)
}

/** Play a move. */
fun Clash.play(pos: Position): Clash {
    check(this is ClashRun) { "Not in a running clash" }
    check(game.board.turn == sidePlayer) { "Not your turn" }
    val newGame = game.play(pos)
    storage.update(name, newGame)
    return ClashRun(storage, name, newGame, sidePlayer)
}

/** Pass turn. */
fun Clash.pass(): Clash {
    check(this is ClashRun) { "Not in a running clash" }
    check(game.board.turn == sidePlayer) { "Not your turn" }
    val newGame = game.pass()
    storage.update(name, newGame)
    return ClashRun(storage, name, newGame, sidePlayer)
}
